package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

public class RelacionamentoManyToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = new Pedido(null, LocalDateTime.now(), null,
                BigDecimal.TEN, StatusPedido.AGUARDANDO, null, cliente, null, null, null);

        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getCliente());
    }

    @Test
    public void verificarRelacionamentoItemPedidoCliente() {
        var produto = entityManager.find(Produto.class, 1);
        var pedido = entityManager.find(Pedido.class, 1);
        var itemPedido = new ItemPedido(null, BigDecimal.ONE, 5, pedido, produto);

        entityManager.persist(itemPedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        assertNotNull(itemPedidoVerificacao.getPedido());
    }

    @Test
    public void verificarRelacionamentoItemPedidoProduto() {
        var produto = entityManager.find(Produto.class, 1);
        var pedido = entityManager.find(Pedido.class, 1);
        var itemPedido = new ItemPedido(null, BigDecimal.ONE, 5, pedido, produto);

        entityManager.persist(itemPedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        assertNotNull(itemPedidoVerificacao.getProduto());
    }
}