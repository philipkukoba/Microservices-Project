package be.ugent.systemdesign.group6.verzendingsdienst;

import be.ugent.systemdesign.group6.verzendingsdienst.API.Channels;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.BestellingGeannuleerdCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.PlaatsGeannuleerdeBestellingTerugCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.OrderVerzondenEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableBinding(Channels.class)
@EnableAsync
public class VerzendingDienst {

	public static void main(String[] args) {

		SpringApplication.run(VerzendingDienst.class, args);
	}

	//testing
	@Bean
	CommandLineRunner makeCommandLineRunner1(CommandDispatcher commandDispatcher){
		return (args) ->
		{ commandDispatcher.plaatsGeannuleerdeBestellingTerugCommand(new PlaatsGeannuleerdeBestellingTerugCommand("a1")); };
	}

	@Bean
	CommandLineRunner makeCommandLineRunner2(CommandDispatcher commandDispatcher){
		return (args) ->
		{ commandDispatcher.bestellingGeannuleerdCommand(new BestellingGeannuleerdCommand("a2")); };
	}

	@Bean
	CommandLineRunner makeCommandLineRunner3(EventDispatcher eventDispatcher){
		return (args) ->
		{ eventDispatcher.publishOrderVerzondenEvent(new OrderVerzondenEvent("a3")); };
	}

}
