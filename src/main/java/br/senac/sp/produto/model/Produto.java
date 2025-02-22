package br.senac.sp.produto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

//@Entity
//@Table(name = "produto")
@Document(collection = "produto")
public class Produto {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String id;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private String lote;
    @Field("codigo_barra") // Mapeia o nome do campo no MongoDB
    @JsonProperty("codigo_barra") // Mapeia o JSON serializado para o cliente
    private String codigoBarra;

    public String getId() {
        return id;
    }

    public Produto setId(String id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Produto setPreco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public String getLote() {
        return lote;
    }

    public Produto setLote(String lote) {
        this.lote = lote;
        return this;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public Produto setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
        return this;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", lote='" + lote + '\'' +
                ", codigoBarra='" + codigoBarra + '\'' +
                '}';
    }

}