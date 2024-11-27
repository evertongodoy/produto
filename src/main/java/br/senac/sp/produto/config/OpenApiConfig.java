package br.senac.sp.produto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Exemplo de Produtos")
                        .version("1.0.0")
                        .description("Exemplo de configuração do OpenAPI com Springdoc Swagger"));
    }
}