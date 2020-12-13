package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Atributo {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    private String valor;

}
