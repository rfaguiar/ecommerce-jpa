package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pagamento_boleto")
public class PagamentoBoleto {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;
    @Column(name = "pedido_id")
    private Integer pedidoId;
    private StatusPagamento status;
    @Column(name = "codigo_barras")
    private String codigoBarras;

}
