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
		// poorten nog aanpassen
		return builder.routes().
				route(r->r.host("*").and().path("/voorraad/**").uri("http:://localhost:8080/api/voorraad")).
				route(r->r.host("*").and().path("/catalogus/**").uri("http:://localhost:8080/api/catalogus")).
				route(r->r.host("*").and().path("/afval/**").uri("http:://localhost:8080/api/order/afval")).
				route(r->r.host("*").and().path("/gebruikers/**").uri("http:://localhost:8080/api/gebruikers")).
				build();
	}
}
