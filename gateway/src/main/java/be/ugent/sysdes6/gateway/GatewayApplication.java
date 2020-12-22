package be.ugent.sysdes6.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@CrossOrigin(origins="*")
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/*@Value("${medicijnen}")
	private String medicijnen = "medicijnen:8080";

	@Value("${order}")
	private String order = "order:2222";

	@Value("${gebruikers}")
	private String gebruikers = "gebruikers:8080";

	@Value("${bestellingen}")
	private String bestellingen = "bestellingen:8080";

	@Value("${koelcelmonitor}")
	private String koelcelmonitor = "koelcelmonitor:8000";

	@Value("${ticketdienst}")
	private String ticketdienst = "ticketdienst:3000";

	@Value("${boekhoudsdienst}")
	private String boekhoudsdienst = "boekhoudsdienst:3000";*/

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes().
				route(r->r.host("*").and().path("/api/voorraad/**").uri("medicijnen.default.svc.cluster.local:8080/api/voorraad/")).
				route(r->r.host("*").and().path("/api/catalogus/**").uri("medicijnen.default.svc.cluster.local:8080/api/catalogus/")).
				route(r->r.host("*").and().path("/api/order/afval/**").uri("order.default.svc.cluster.local:2222/api/order/afval/")).
				route(r->r.host("*").and().path("/api/gebruikers/**").uri("gebruikers.default.svc.cluster.local:3000/api/gebruikers/")).
				route(r->r.host("*").and().path("/api/bestellingen/**").uri("bestellingen.default.svc.cluster.local:8080/api/bestellingen/")).
				route(r->r.host("*").and().path("/api/statistieken/**").uri("bestellingen.default.svc.cluster.local:8080/api/statistieken/")).
				route(r->r.host("*").and().path("/api/koelcellen/**").uri("koelcelmonitor.default.svc.cluster.local:8000/api/koelcellen/")).
				route(r->r.host("*").and().path("/api/ticket/**").uri("ticketdienst.default.svc.cluster.local:3000/api/ticket/")).
				route(r->r.host("*").and().path("/api/boekhoud/**").uri("boekhoudsdienst.default.svc.cluster.local:3000/api/boekhoud/")).
				build();
	}
}
