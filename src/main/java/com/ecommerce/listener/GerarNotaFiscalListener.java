package com.ecommerce.listener;

import com.ecommerce.model.Pedido;
import com.ecommerce.service.NotaFiscalService;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class GerarNotaFiscalListener {

    private final NotaFiscalService notaFiscalService;

    public GerarNotaFiscalListener() {
        this.notaFiscalService = new NotaFiscalService();
    }

    @PrePersist
    @PreUpdate
    public void gerar(Pedido pedido) {
        if (pedido.isPago() && pedido.getNotaFiscal() == null) {
            notaFiscalService.gerar(pedido);
        }
    }
}
