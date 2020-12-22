package be.ugent.systemdesign.group6.bestellingen;

import be.ugent.systemdesign.group6.bestellingen.API.messaging.Channels;
import be.ugent.systemdesign.group6.bestellingen.API.rest.MedicijnViewModel;
import be.ugent.systemdesign.group6.bestellingen.application.BestelService;
import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.CQRS.StatistiekenQuery;
import be.ugent.systemdesign.group6.bestellingen.application.saga.BestelSaga;
import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingenRepository;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingDataModel;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingNotFoundException;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingenDataModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableAsync
@EnableBinding(Channels.class)
public class BestellingenApplication {

	private static final Logger log = LoggerFactory.getLogger(BestellingenApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BestellingenApplication.class, args);
	}

	//@Bean
	CommandLineRunner testRandomBetaling(BestelSaga bestelSaga, BestellingenRepository repo){
		return (args) -> {
			List<Medicijn> meds = new ArrayList<>();
			meds.add(new Medicijn(1, "med1", false, 30, 5));
			meds.add(new Medicijn(2, "med2", false, 40, 5));
			Bestelling bestelling = new Bestelling("klant5", "test", "test", meds);
			repo.saveBestelling(bestelling);
			bestelSaga.gereserveerdBestelling(bestelling.getId(), meds);
			repo.removeBestelling(bestelling.getId());
		};
	}

	//@Bean
	CommandLineRunner testBestellingGeannuleerdEvent(BestelService service, BestellingenRepository repo) {
		return (args) -> {
			List<Medicijn> medicijnen = new ArrayList<>();
			medicijnen.add(new Medicijn(1, "med1", false, 35, 5));
			medicijnen.add(new Medicijn(2, "med2", true, 40, 1));
			Bestelling bestelling = new Bestelling("klant5", "test", "test", medicijnen);
			repo.saveBestelling(bestelling);
			Response response = service.annuleerBestelling(bestelling.getId());
			logResponse(response);
			repo.removeBestelling(bestelling.getId());
		};
	}

	//@Bean
	CommandLineRunner testBestellingenDataModelMongoRepository(BestellingenDataModelRepository repo){
		return (args) -> {
			log.info("$Testing BestellingenDataModelRepository");
			List<Medicijn> medicijnen = new ArrayList<>();
			medicijnen.add(new Medicijn(1, "med1", false, 35, 5));
			medicijnen.add(new Medicijn(2, "med2", true, 40, 1));
			BestellingDataModel bestelling = new BestellingDataModel("id", "klant5", Status.BESTELD_DOOR_KLANT, "test", "test", medicijnen);
			repo.save(bestelling);
			logBestellingDataModel(bestelling);
			log.info("$Bestelling toegevoegd aan databank");
			BestellingDataModel res = repo.findById("id").orElseThrow(BestellingNotFoundException::new);
			log.info("$Bestelling opgehaald met id " + res.getId());
			logBestellingDataModel(res);
			log.info("$Status van bestelling ophalen uit de databank");
			try{
				String st = repo.readStatusById("id").get().getStatus();
				log.info("----- status: " + st);
			}
			catch(BestellingNotFoundException e) {
				log.error("Bestelling met id: " + "id" + " niet gevonden.");
			}
			repo.deleteById(res.getId());
		};
	}

	private static void logBestellingDataModel(BestellingDataModel b){
		log.info("-- Bestelling {id: " + b.getId() + "}, {klant: " + b.getKlantenId() + "}, {status: " + b.getStatus()
		 + "}, {thuis: " + b.getThuisAdres() + "}, {apotheek: " + b.getApotheekAdres() + "}");
		log.info("----- met medicijnen");
		for(Medicijn med : b.getMedicijnen()){
			log.info("----- {id: " + med.getId() + "}, {naam: " + med.getNaam() + "}, {voorschrift: " + med.getVoorschrift() +
					"}, {prijs: " + med.getPrijs() + "}, {aantal: " + med.getAantal() + "}");
		}
	}

	//@Bean
	CommandLineRunner testBestellingenRepository(BestellingenRepository repo){
		return (args) -> {
			log.info("$Testing BestellingenRepository");
			List<Medicijn> medicijnen = new ArrayList<>();
			medicijnen.add(new Medicijn(1, "med1", false, 35, 5));
			medicijnen.add(new Medicijn(2, "med2", true, 40, 1));
			Bestelling bestelling = new Bestelling("klant5", "test", "test", medicijnen);
			log.info("$Bestelling toevoegen aan databank");
			logBestelling(bestelling);
			repo.saveBestelling(bestelling);
			log.info("$Bestelling ophalen uit de databank");
			Bestelling res = repo.getBestelling(bestelling.getId());
			logBestelling(res);
			log.info("$Status opvragen van bestelling met id " + res.getId());
			Status st = repo.getStatus(res.getId());
			log.info("----- status: " + st);
			log.info("$Status opvragen van niet bestaande bestelling");
			try{
				st = repo.getStatus("test");
				log.info("----- status: " + st);
			}
			catch(BestellingNotFoundException e){
				log.error("Bestelling met id: " + "test" + " niet gevonden");
			}
			log.info("$Bestelling verwijderen die niet bestaat");
			repo.removeBestelling("test");
			repo.removeBestelling(res.getId());
		};
	}

	private static void logBestelling(Bestelling b){
		log.info("-- Bestelling {id: " + b.getId() + "}, {klant: " + b.getKlantenId() + "}, {status: " + b.getStatus()
				+ "}, {thuis: " + b.getThuisAdres() + "}, {apotheek: " + b.getApotheekAdres() + "}");
		log.info("----- met medicijnen");
		for(Medicijn med : b.getMedicijnen()){
			log.info("----- {id: " + med.getId() + "}, {naam: " + med.getNaam() + "}, {voorschrift: " + med.getVoorschrift() +
					"}, {prijs: " + med.getPrijs() + "}, {aantal: " + med.getAantal() + "}");
		}
	}

	//@Bean
	CommandLineRunner testBestelService(BestelService service){
		return (args) -> {
			log.info("$Testing BestelService");
			Response response;
			HashMap<Integer, Integer> meds = new HashMap<>();
			meds.put(1, 5);
			meds.put(2, 5);
			log.info("$Bestelling plaatsen door klant");
			response = service.plaatsBestelling("klant5", "thuis", "apo", meds);
			logResponse(response);
			log.info("$Bestelling plaatsen door ticket");
			response = service.plaatsBestellingTicket("klant5", "test", "test", meds);
			logResponse(response);
		};
	}

	private static void logResponse(Response response) {
		log.info("---- response status[{}] message[{}]", response.getStatus(), response.getMessage());
	}

	//@Bean
	CommandLineRunner testStatistiekenQuery(StatistiekenQuery query){
		return (args)->{
			log.info("$Testing StatistiekenQuery");
			List<MedicijnViewModel> meds = query.genereerStatistieken()
					.entrySet()
					.stream()
					.map(m -> new MedicijnViewModel(m.getValue()))
					.collect(Collectors.toList());
			for(MedicijnViewModel med : meds) {
				logMedicijnViewModel(med);
			}
		};
	}

	private static void logMedicijnViewModel(MedicijnViewModel med){
		log.info("-- medicijn " + med.getId());
		log.info("---- naam: " + med.getNaam());
		log.info("---- aantal: " + med.getAantal());
	}
}
