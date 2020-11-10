package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

}
