package be.ugent.sysdes6.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;


@SpringBootApplication
@CrossOrigin(origins="*")
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Value("${medicijnen}")
	private String medicijnen = "medicijnen:8080";

	@Value("${order}")
	private String order = "order:2222";

	@Value("${gebruikers}")
	private String gebruikers = "gebruikers:3000";

	@Value("${bestellingen}")
	private String bestellingen = "bestellingen:8080";

	@Value("${koelcelmonitor}")
	private String koelcelmonitor = "koelcelmonitor:8000";

	@Value("${ticketdienst}")
	private String ticketdienst = "ticketdienst:3000";

	@Value("${boekhoudsdienst}")
	private String boekhoudsdienst = "boekhoudsdienst:3000";

	@Value("${verzendingsdienst}")
	private String verzendingsdienst = "verzendingsdienst:8080";

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes().
				route(r->r.host("*").and().path("/api/voorraad/**").uri("http://" + medicijnen + "/api/voorraad/")).
				route(r->r.host("*").and().path("/api/catalogus/**").uri("http://" + medicijnen + "/api/catalogus/")).
				route(r->r.host("*").and().path("/api/order/afval/**").uri("http://" + order + "/api/order/afval/")).
				route(r->r.host("*").and().path("/api/gebruikers/**").uri("http://" + gebruikers + "/api/gebruikers/")).
				route(r->r.host("*").and().path("/api/bestellingen/**").uri("http://" + bestellingen + "/api/bestellingen/")).
				route(r->r.host("*").and().path("/api/statistieken/**").uri("http://" + bestellingen + "/api/statistieken/")).
				route(r->r.host("*").and().path("/api/koelcellen/**").uri("http://" + koelcelmonitor + "/api/koelcellen/")).
				route(r->r.host("*").and().path("/api/ticket/**").uri("http://" + ticketdienst + "/api/ticket/")).
				route(r->r.host("*").and().path("/api/boekhoud/**").uri("http://" + boekhoudsdienst + "/api/boekhoud/")).
				route(r->r.host("*").and().path("/api/bpost/**").uri("http://" + verzendingsdienst + "/api/bpost/")).
				build();
	}
}