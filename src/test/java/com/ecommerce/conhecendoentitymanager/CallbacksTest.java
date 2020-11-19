package com.ecommerce.conhecendoentitymanager;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

public class CallbacksTest extends EntityManagerTest {

    @Test
    public void acionarCallbacks() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = Pedido.builder()
                .cliente(cliente)
                .status(StatusPedido.AGUARDANDO)
                .total(BigDecimal.TEN)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.flush();

        pedido.setStatus(StatusPedido.PAGO);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getDataCriacao());
        assertNotNull(pedidoVerificacao.getDataAtualizacao());
    }
}
