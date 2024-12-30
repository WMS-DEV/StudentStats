package pl.wmsdev;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockedDataTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnStaticMockedData() {
        ResponseEntity<String> response = restTemplate.getForEntity("/getStaticMockedData", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.toString().contains("Kowalski")).isEqualTo(true);
    }
}
