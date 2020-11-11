package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RelacionamentoOneToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = new Pedido(null, LocalDateTime.now(), null,
                null, BigDecimal.TEN, StatusPedido.AGUARDANDO, null, cliente, null);

        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clientVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertFalse(clientVerificacao.getPedidos().isEmpty());
    }

    @Test
    public void verificarRelacionamentoItemPedidoCliente() {
        var pedido = entityManager.find(Pedido.class, 1);
        var itemPedido = new ItemPedido(null, BigDecimal.ONE, 5, pedido, null);

        entityManager.persist(itemPedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }
}
