package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;

public class RelacionamentoOneToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(BigDecimal.TEN)
                .status(StatusPedido.AGUARDANDO)
                .build();

        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clientVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertFalse(clientVerificacao.getPedidos().isEmpty());
    }

    @Test
    public void verificarRelacionamentoItemPedidoCliente() {
        var produto = entityManager.find(Produto.class, 1);
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .build();

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .precoProduto(BigDecimal.ONE)
                .quantidade(5)
                .pedido(pedido)
                .produto(produto)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }
}
