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
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

public class RelacionamentoManyToOneTest extends EntityManagerTest {

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

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getCliente());
    }

    @Test
    public void verificarRelacionamentoItemPedidoCliente() {
        var produto = entityManager.find(Produto.class, 1);
        var cliente = entityManager.find(Cliente.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .status(StatusPedido.AGUARDANDO)
                .total(produto.getPreco())
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .precoProduto(BigDecimal.ONE)
                .quantidade(5)
                .pedido(pedido)
                .produto(produto)
                .build();

        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
        assertNotNull(itemPedidoVerificacao.getPedido());
    }

    @Test
    public void verificarRelacionamentoItemPedidoProduto() {

        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.TEN);
        pedido.setCliente(cliente);

        ItemPedido itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .precoProduto(produto.getPreco())
                .quantidade(1)
                .pedido(pedido)
                .produto(produto)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        ItemPedido itemPedidoVerificacao = entityManager.find(
                ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
        assertNotNull(itemPedidoVerificacao.getPedido());
        assertNotNull(itemPedidoVerificacao.getProduto());
    }
}
