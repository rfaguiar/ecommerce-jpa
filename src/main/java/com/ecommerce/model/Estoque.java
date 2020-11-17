package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger {

    private Integer quantidade;

    @OneToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
