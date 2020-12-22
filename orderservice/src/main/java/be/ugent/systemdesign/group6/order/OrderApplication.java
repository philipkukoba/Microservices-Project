package be.ugent.systemdesign.group6.order;

import be.ugent.systemdesign.group6.order.API.messaging.Channels;
import be.ugent.systemdesign.group6.order.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@EnableBinding(Channels.class)
@SpringBootApplication
public class OrderApplication {

	private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

	public static void main(String[] args) {
		//The two parameters passed to the run()method are a configuration class and command line parameters.
		SpringApplication.run(OrderApplication.class, args);
	}

	//@Bean
	CommandLineRunner testAfvalcontainerRepository(AfvalcontainerRepository repo) {
		return(args)->{
			log.info("Testing het afval management.");
			/*
			Afvalcontainer a = new Afvalcontainer(1,10);
			repo.voegAfvalContainerToe(a);*/
			repo.gooiMedicijnenWeg(10);
			repo.haalAfvalOp();
		};
	}

	//@Bean
	CommandLineRunner testOrderRepository(OrderRepository repo) {
		return(args)->{
			log.info("Testing het order management.");
			Pakket p = new Pakket("adres", null);
			List<Pakket> lp = new ArrayList<>();
			lp.add(p);
			Order o = new Order("aaaa", lp);
			repo.voegOrderToe(o);
		};
	}

	/*@Bean
	CommandLineRunner testAssignRoomCommand(MessageOutputGateway outputGateway) {
		return (args) -> {
			outputGateway.sendAssignRoomCommand(new AssignRoomCommand("2", responseDestination));
		};
	}*/
}
