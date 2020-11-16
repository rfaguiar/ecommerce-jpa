package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.PagamentoCartao;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPagamento;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class RelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPagementoCartao() {
        var pedido = entityManager.find(Pedido.class, 1);
        var pagamentoCartao = PagamentoCartao.builder()
                .pedido(pedido)
                .status(StatusPagamento.PROCESSANDO)
                .numero("123")
                .build();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getPagamento());
    }

    @Test
    public void verificarRelacionamentoNotaFiscal() {
        var pedido = entityManager.find(Pedido.class, 1);
        var notaFiscal = NotaFiscal.builder()
                .pedido(pedido)
                .xml("<xml/>".getBytes())
                .dataEmissao(new Date())
                .build();

        entityManager.persist(notaFiscal);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getNotaFiscal());
    }

}
