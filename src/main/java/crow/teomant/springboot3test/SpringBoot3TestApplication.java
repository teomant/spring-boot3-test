package crow.teomant.springboot3test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class SpringBoot3TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3TestApplication.class, args);
	}

	@Bean
	WebClient webClient(ObjectMapper objectMapper) {
		return WebClient.builder()
			.baseUrl("http://localhost:8080")
			.build();
	}

	@Bean
	TestClient postClient(WebClient webClient) {
		HttpServiceProxyFactory httpServiceProxyFactory =
			HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
				.build();
		return httpServiceProxyFactory.createClient(TestClient.class);
	}

}
