package crow.teomant.springboot3test;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/test")
public interface TestClient {

    @GetExchange("/2")
    String testing();
}
