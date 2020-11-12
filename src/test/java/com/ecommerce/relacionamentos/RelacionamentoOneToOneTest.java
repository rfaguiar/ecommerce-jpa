package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.PagamentoCartao;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPagamento;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPagementoCartao() {
        var pedido = entityManager.find(Pedido.class, 1);
        var pagamentoCartao = new PagamentoCartao(null, StatusPagamento.PROCESSANDO, "123", pedido);

        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getPagamento());
    }
}
