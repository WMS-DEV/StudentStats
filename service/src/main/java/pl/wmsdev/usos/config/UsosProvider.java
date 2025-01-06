package pl.wmsdev.usos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import pl.wmsdev.usos.factory.UsosFactory;
import pl.wmsdev.usos4j.client.UsosServerAPI;
import pl.wmsdev.usos4j.client.UsosUserAPI;

@Configuration
@RequiredArgsConstructor
public class UsosProvider {

    private final UsosFactory usosFactory;

    @Bean
    @RequestScope
    public UsosUserAPI userApi() {
        return usosFactory.getUsosUserApi();
    }

    @Bean
    @RequestScope
    public UsosServerAPI serverApi() {
        return usosFactory.getUsosServerApi();
    }
}
