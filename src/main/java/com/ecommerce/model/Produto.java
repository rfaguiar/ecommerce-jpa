package com.ecommerce.model;

import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.listener.GenericoListener;
import com.ecommerce.model.converter.BooleanToSimNaoConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedNativeQuery(name = "produto_loja.listar", query = "select * from produto", resultClass = Produto.class)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "produto_loja.Produto",
                entities = {@EntityResult(entityClass = Produto.class)}),
        @SqlResultSetMapping(
                name = "ecm_produto.Produto",
                entities = {
                    @EntityResult(
                        entityClass = Produto.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "nome", column = "nome"),
                                @FieldResult(name = "descricao", column = "descricao"),
                                @FieldResult(name = "preco", column = "preco"),
                                @FieldResult(name = "foto", column = "foto"),
                                @FieldResult(name = "dataCriacao", column = "data_criacao"),
                                @FieldResult(name = "dataUltimaAtualizacao", column = "data_ultima_atualizacao"),
                        }
                    )
                }
        ),
        @SqlResultSetMapping(
                name = "ecm_produto.ProdutoDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = ProdutoDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "nome", type = String.class)
                                }
                        )
                }
        )
})
@NamedQueries({
        @NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
        @NamedQuery(name = "Produto.listarPorCategoria", query = "select p from Produto p where exists (select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)")
})
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto", uniqueConstraints = {
        @UniqueConstraint(name = "unq_produto_nome", columnNames = {"nome"})
},
        indexes = {@Index(name = "idx_produto_nome", columnList = "nome")})
public class Produto extends EntidadeBaseInteger {

    @PastOrPresent
    @NotNull
    @Column(name = "data_criacao", updatable = false , nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @NotBlank
    @EqualsAndHashCode.Include
    @Column(length = 100, nullable = false)
    private String nome;

//    @Lob
    private String descricao;

    @Positive
    private BigDecimal preco;

    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(length = 3, nullable = false)
    private Boolean ativo = Boolean.FALSE;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
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
