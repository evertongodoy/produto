//package br.senac.sp.produto.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import java.util.concurrent.Executor;
//
//@Configuration
//public class AsyncConfig {
//
//    @Bean(name = "customTaskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5); // 2 Tamanho mínimo do pool de threads
//        executor.setMaxPoolSize(5); // 4 Tamanho máximo do pool de threads
//        executor.setQueueCapacity(50); // Capacidade da fila de espera
//        executor.setThreadNamePrefix("Async_Emails-"); // Nome do prefixo das threads
//        executor.initialize(); // Inicializa o executor
//        return executor;
//    }
//
//}
