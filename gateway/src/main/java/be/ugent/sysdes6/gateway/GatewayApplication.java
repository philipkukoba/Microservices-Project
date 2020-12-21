package be.ugent.sysdes6.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes().
				route(r->r.host("*").and().path("/api/voorraad/**").uri("http://medicijnen:8080/api/voorraad/")).
				route(r->r.host("*").and().path("/api/catalogus/**").uri("http://medicijnen:8080/api/catalogus/")).
				route(r->r.host("*").and().path("/api/order/afval/**").uri("http://order:2222/api/order/afval/")).
				route(r->r.host("*").and().path("/api/gebruikers/**").uri("http://gebruikers:3000/api/gebruikers/")).
				route(r->r.host("*").and().path("/api/bestellingen/**").uri("http://bestellingen:8080/api/bestellingen/")).
				route(r->r.host("*").and().path("/api/statistieken/**").uri("http://bestellingen:8080/api/statistieken/")).
				route(r->r.host("*").and().path("/api/koelcellen/**").uri("http://koelcelmonitor:8000/api/koelcellen/")).
				route(r->r.host("*").and().path("/api/ticket/**").uri("http://ticketdienst:3000/api/ticket/")).
				route(r->r.host("*").and().path("/api/boekhoud/**").uri("http://boekhoudsdienst:3000/api/boekhoud/")).
				build();
	}
}
