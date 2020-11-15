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
        var itemPedido = ItemPedido.builder()
                .precoProduto(BigDecimal.ONE)
                .quantidade(5)
                .pedido(pedido)
                .produto(produto)
                .build();

        entityManager.persist(itemPedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getPedidoId());
        assertNotNull(itemPedidoVerificacao.getPedido());
    }

    @Test
    public void verificarRelacionamentoItemPedidoProduto() {
        entityManager.getTransaction().begin();

        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.TEN);
        pedido.setCliente(cliente);

        entityManager.persist(pedido);

        // Pode ser que logo ao executar o método "persist" o JPA já faça a sincronização com a base.
        // Mas caso isso não aconteça, o flush garante a sincronização.
        entityManager.flush();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedidoId(pedido.getId());
        itemPedido.setProdutoId(produto.getId());
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        entityManager.persist(itemPedido);

        entityManager.getTransaction().commit();

        entityManager.clear();

        ItemPedido itemPedidoVerificacao = entityManager.find(
                ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
        assertNotNull(itemPedidoVerificacao.getPedido());
        assertNotNull(itemPedidoVerificacao.getProduto());
    }
}
