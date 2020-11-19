package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categoria", uniqueConstraints = {
        @UniqueConstraint(name = "unq_nome", columnNames = {"nome"})
},
        indexes = {@Index(name = "idx_nome", columnList = "nome")})
public class Categoria extends EntidadeBaseInteger {

    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai_id")
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private Set<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private Set<Produto> produtos;
}
