package crow.teomant.springboot3test;

import crow.teomant.springboot3test.document.TestDocument;
import crow.teomant.springboot3test.document.TestDocumentMongoRepository;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestClient client;
    private final TestDocumentMongoRepository documentMongoRepository;

    @GetMapping("/1")
    public String test() {
        return client.testing();
    }

    @GetMapping("/2")
    public String test2() {
        return "test";
    }

    @GetMapping("/document/{value}")
    public void documents(@PathVariable("value") String value) {
        switch (value) {
            case "1" -> saveOne();
            case "some" -> saveMany(3, 7);
            case "many" -> saveMany(30, 100);
            case "tonn" -> saveMany(500, 1000);
            default -> {
                return;
            }
        }
    }

    @GetMapping("/documents")
    public List<TestDocument> documents() {
        return documentMongoRepository.findAll();
    }

    private TestDocument saveOne() {
        return documentMongoRepository.save(new TestDocument(UUID.randomUUID(), UUID.randomUUID().toString()));
    }

    private List<TestDocument> saveMany(int randomNumberOrigin, int randomNumberBound) {
        return documentMongoRepository.saveAll(
            IntStream.rangeClosed(
                    1,
                    new Random().ints(randomNumberOrigin, randomNumberBound)
                        .findFirst()
                        .getAsInt()
                )
                .mapToObj(x -> new TestDocument(UUID.randomUUID(), UUID.randomUUID().toString()))
                .collect(Collectors.toList())
        );
    }
}
