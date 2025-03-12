package org.alvarub.fulbitoapi.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Randomizador de equipos(?")
                        .description("API desarrollada por puro hobby para servir como backend para una página para elegir " +
                                "equipos aleatorios filtrando por confederaciones, paises, ligas y temporadas. El propósito de dicha página " +
                                "es principalmente para elegir equipos para jugar fifa/pes.")
                        .contact(new Contact()
                                .name("Alvaro Rubina")
                                .email("alvarorubina132@gmail.com")
                                .url("https://github.com/Alvaro-Rubina")
                                )
                );
    }
}
