package crow.teomant.springboot3test;

import com.fasterxml.jackson.databind.ObjectMapper;
import crow.teomant.springboot3test.document.TestDocumentMongoRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.observability.ContextProviderFactory;
import org.springframework.data.mongodb.observability.MongoObservationCommandListener;
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

	@Bean
	MongoClientSettingsBuilderCustomizer mongoMetricsSynchronousContextProvider(ObservationRegistry registry) {
		return (clientSettingsBuilder) -> {
			clientSettingsBuilder.contextProvider(ContextProviderFactory.create(registry))
				.addCommandListener(new MongoObservationCommandListener(registry));
		};
	}
}
