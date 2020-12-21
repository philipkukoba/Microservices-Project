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
				route(r->r.host("*").and().path("/voorraad/**").uri("http:://localhost:8080/api/voorraad")).
				route(r->r.host("*").and().path("/catalogus/**").uri("http:://localhost:8080/api/catalogus")).
				route(r->r.host("*").and().path("/afval/**").uri("http:://localhost:8082/api/order/afval")).
				route(r->r.host("*").and().path("/gebruikers/**").uri("http:://localhost:3000/api/gebruikers")).
				route(r->r.host("*").and().path("/bestellingen/**").uri("http:://localhost:8081/api/bestellingen")).
				route(r->r.host("*").and().path("/statistieken/**").uri("http:://localhost:8081/api/statistieken")).
				route(r->r.host("*").and().path("/koelcellen/**").uri("http:://localhost:8000/api/koelcellen")).
				route(r->r.host("*").and().path("/ticket/**").uri("http:://localhost:3002/api/ticket")).
				route(r->r.host("*").and().path("/boekhoud/**").uri("http:://localhost:3001/api/boekhoud")).
				build();
	}
}
