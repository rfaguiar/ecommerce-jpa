package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Atributo {

    @Column(nullable = false, length = 100)
    private String nome;

    private String valor;

}
