package br.senac.sp.produto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public CompletableFuture<String> enviarEmailAsyncTask(String destinatario, Long timeMillis) {
        logger.info("Iniciando tarefa de envio de e-mail para {}", destinatario);
        try {
            // Simula processamento demorado
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("e-mail {} enviado em {} milisegundos!", destinatario, timeMillis);
        return CompletableFuture.completedFuture("Tarefa do e-mail " + destinatario + " concluída!");
    }

}
