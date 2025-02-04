package br.senac.sp.produto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LojaProdutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaProdutosApplication.class, args);
	}

}
