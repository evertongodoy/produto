package br.senac.sp.produto.controller;

import br.senac.sp.produto.model.Produto;
import br.senac.sp.produto.repository.ProdutoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository repository) {
        this.produtoRepository = repository;
    }

    @GetMapping("/get-produtos")
    public ResponseEntity<List<Produto>> recuperarTodos() {
        var produtos = produtoRepository.findAll();
        System.out.println("Total de Produtos " + produtos.size());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/get-produto/{idProduto}")
    public ResponseEntity<Produto> recuperarPorId(@PathVariable(name = "idProduto") Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID NAO LOCALIZADO"));
        System.out.println(produto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody ProdutoRequest request) {
        var p = new Produto().setDescricao(request.getDescricao())
                .setPreco(request.getPreco())
                .setCodigoBarra(request.getCodigoBarra())
                .setLote(request.getLote())
                .setQuantidade(request.getQuantidade());

        var produtoCriado = produtoRepository.save(p);
        System.out.println(produtoCriado);
        return ResponseEntity.ok(produtoCriado);
    }

    @PutMapping("/atualizar/{idProduto}")
    public ResponseEntity<Produto> alterarProdutoTotal(
            @PathVariable(name = "idProduto") Long id,
            @RequestBody ProdutoRequest request
    ) {
        if (Objects.isNull(request.getDescricao()) ||
                Objects.isNull(request.getPreco()) ||
                Objects.isNull(request.getQuantidade()) ||
                Objects.isNull(request.getLote()) ||
                Objects.isNull(request.getCodigoBarra())
        ) {
            throw new RuntimeException("OS ATRIBUTOS NAO PODEM SER NULOS");
        }

        Produto p = new Produto();

        var produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new RuntimeException("PRODUTO NAO EXISTE");
        }

        p.setId(id);
        p.setDescricao(request.getDescricao());
        p.setPreco(request.getPreco());
        p.setLote(request.getLote());
        p.setQuantidade(request.getQuantidade());
        p.setCodigoBarra(request.getCodigoBarra());

        Produto produtoSalvo = produtoRepository.save(p);
        return ResponseEntity.ok().body(produtoSalvo);
    }

    @PatchMapping("/atualizar/{idProduto}")
    public ResponseEntity<Produto> alterarProdutoParcial(
            @PathVariable(name = "idProduto") Long id,
            @RequestBody ProdutoRequest request
    ) {
        Produto produtoEntidade = new Produto();

        var produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new RuntimeException("PRODUTO NAO EXISTE");
        }
        var produtoBancoDados = produtoOptional.get();

        produtoEntidade.setId(id);
        produtoEntidade.setDescricao(Objects.isNull(request.getDescricao()) ?
                produtoBancoDados.getDescricao() : request.getDescricao());
        produtoEntidade.setQuantidade(Objects.isNull(request.getQuantidade()) ?
                produtoBancoDados.getQuantidade() : request.getQuantidade());
        produtoEntidade.setLote(Objects.isNull(request.getLote()) ?
                produtoBancoDados.getLote() : request.getLote());
        produtoEntidade.setPreco(Objects.isNull(request.getPreco()) ?
                produtoBancoDados.getPreco() : request.getPreco());
        produtoEntidade.setCodigoBarra(Objects.isNull(request.getCodigoBarra()) ?
                produtoBancoDados.getCodigoBarra() : request.getCodigoBarra());
        Produto produtoAtualizado = produtoRepository.save(produtoEntidade);

        return ResponseEntity.ok().body(produtoAtualizado);
    }

    @DeleteMapping("/deletar/{idProduto}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idProduto") Long id){
        var produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new RuntimeException("PRODUTO NAO EXISTE");
        }
        produtoRepository.delete(produtoOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("paginador")
    public ResponseEntity<Page<Produto>> getProdutosPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int itens,
            @RequestParam(defaultValue = "id") String ordernarPor,
            @RequestParam(defaultValue = "asc") String ordem
    ){
        var ordenacao = ordem.equalsIgnoreCase("asc") ? Sort.by(ordernarPor).ascending() :
                Sort.by(ordernarPor).descending();
        var paginador = PageRequest.of(pagina, itens, ordenacao);
        var produtosPaginados = produtoRepository.findAll(paginador);
        return ResponseEntity.ok(produtosPaginados);
    }


    @GetMapping("/somar-precos/{lote}")
    public ResponseEntity<BigDecimal> calcularPrecosPorLote(@PathVariable String lote){
        var lstProdutos = produtoRepository.findByLote(lote);
        if (lstProdutos.isEmpty()) {
            throw new RuntimeException("PRODUTOS NAO LOCALIZADOS PARA LOTE " + lote);
        }
        var valores = lstProdutos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return ResponseEntity.ok(valores);
    }
























    ///////////////////////////////////////////
    @PostMapping("/set-cookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "john_doe");
        cookie.setMaxAge(2 * 60); // cookie.setMaxAge(24 * 60 * 60); // Expira em 1 dia
        cookie.setHttpOnly(true); // Apenas acessível via HTTP (não disponível para JS)
        cookie.setPath("/"); // Disponível para toda a aplicação
        response.addCookie(cookie);
        return "Cookie configurado com sucesso!";
    }


    @GetMapping("/get-cookie")
    public String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    return "Cookie encontrado: " + cookie.getValue();
                }
            }
        }
        return "Nenhum cookie encontrado";
    }

}
