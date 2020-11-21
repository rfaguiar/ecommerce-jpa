package com.ecommerce.model;

import com.ecommerce.listener.GenericoListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "unq_nome", columnNames = {"nome"})
},
        indexes = {@Index(name = "idx_nome", columnList = "nome")})
public class Produto extends EntidadeBaseInteger {

    @Column(name = "data_criacao", updatable = false , nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @EqualsAndHashCode.Include
    @Column(length = 100, nullable = false)
    private String nome;

    @Lob
    private String descricao;

    private BigDecimal preco;

    @Lob
    private byte[] foto;

    @ManyToMany
    @JoinTable(name = "produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")),
        inverseJoinColumns = @JoinColumn(name = "categoria_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria")))
    private Set<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection
    @CollectionTable(name = "produto_tag",
            joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_produto_tag_produto")))
    @Column(name = "tag", length = 50, nullable = false)
    private Set<String> tags;

    @ElementCollection
    @CollectionTable(name = "produto_atributo",
            joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_atributo_produto")))
    private Set<Atributo> atributos;


    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
    }
}
