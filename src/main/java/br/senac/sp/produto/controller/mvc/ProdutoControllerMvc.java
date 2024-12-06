package br.senac.sp.produto.controller.mvc;

import br.senac.sp.produto.controller.ProdutoRequest;
import br.senac.sp.produto.model.Produto;
import br.senac.sp.produto.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("produtos")
@Tag(name = "MVC - Produto Controller API", description = "Controller para tratar requisições de Produtos na API")
public class ProdutoControllerMvc {

    private final ProdutoRepository produtoRepository;

    public ProdutoControllerMvc(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    @GetMapping
    public String exibirPaginaInicial(Model model){
        return "index";
    }


    @GetMapping("/cadastro")
    @Operation(summary = "Exibir Formulario", description = "Redireciona para pagina de formulario")
    public String exibirFormulario(@RequestParam(required = false) Long id, Model model){
        var request = new ProdutoRequest();
        if(Objects.nonNull(id)){
            Optional<Produto> produto = produtoRepository.findById(id);
            if (produto.isPresent()) {
                request.setId(produto.get().getId());
                request.setDescricao(produto.get().getDescricao());
                request.setLote(produto.get().getLote());
                request.setPreco(produto.get().getPreco());
                request.setQuantidade(produto.get().getQuantidade());
                request.setCodigoBarra(produto.get().getCodigoBarra());
            } else {
                // Caso o ID não exista, pode adicionar uma mensagem ou redirecionar
                model.addAttribute("errorMessage", "Produto não encontrado.");
            }
        }
        model.addAttribute("produtoRequest", request);
        return "formularioProduto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute ProdutoRequest request, Model model){
        var p = new Produto()
                .setId(request.getId())
                .setDescricao(request.getDescricao())
                .setPreco(request.getPreco())
                .setCodigoBarra(request.getCodigoBarra())
                .setLote(request.getLote())
                .setQuantidade(request.getQuantidade());
        produtoRepository.save(p);
        model.addAttribute("produtoSalvo", request);
        return "sucesso";
    }

//    @GetMapping("/listar")
//    public String exibirProdutos(Model model){
//        var lstProdutos = produtoRepository.findAll();
//        model.addAttribute("produtos", lstProdutos);
//        return "listaProdutos";
//    }

    @GetMapping("/listar")
    public String exibirProdutos(
            @RequestParam(defaultValue = "0") int page, // Página atual
            @RequestParam(defaultValue = "10") int size, // Tamanho da página
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtosPage = produtoRepository.findAll(pageable);

        int totalPages = produtosPage.getTotalPages();
        int currentPage = page;

        // Lógica para limitar os números de páginas visíveis
        int maxVisiblePages = 5; // Número máximo de páginas visíveis
        int startPage = Math.max(0, currentPage - (maxVisiblePages / 2));
        int endPage = Math.min(totalPages, startPage + maxVisiblePages);

        if (endPage - startPage < maxVisiblePages) {
            startPage = Math.max(0, endPage - maxVisiblePages);
        }

        model.addAttribute("produtos", produtosPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "listaProdutos";
    }

}
