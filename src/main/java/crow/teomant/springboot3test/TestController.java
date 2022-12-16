package crow.teomant.springboot3test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestClient client;

    @GetMapping("/1")
    public String test() {
        return client.testing();
    }

    @GetMapping("/2")
    public String test2() {
        return "test";
    }
}
