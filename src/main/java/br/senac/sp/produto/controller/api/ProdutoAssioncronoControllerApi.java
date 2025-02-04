package br.senac.sp.produto.controller.api;

import br.senac.sp.produto.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "produto-assincrono")
public class ProdutoAssioncronoControllerApi {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoAssioncronoControllerApi.class);

    private final AsyncService asyncService;


    public ProdutoAssioncronoControllerApi(final AsyncService asyncService) {
        this.asyncService = asyncService;
    }


    @PostMapping(value = "send-email")
    public ResponseEntity<String> enviarEmails() {
        IntStream.rangeClosed(1, 50)
                .boxed()
                .toList()
                .forEach(cli ->
                        asyncService.enviarEmailAsyncTask(
                                "cli_" + cli + "@senacsp.br",
                                ThreadLocalRandom
                                        .current()
                                        .nextLong(20000L, 60000L + 1)));
        return ResponseEntity.ok().body("Chamada de envio de e-mail finalizada, em breve os e-mails serao enviados");
    }
}
