package com.ecommerce.operacoesemcascata;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Categoria;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CascadeTypeMergeTest extends EntityManagerTest {

    //    @Test
    public void atualizarPedidoComItens() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.AGUARDANDO)
                .build();
        pedido.setId(1);

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(3)
                .precoProduto(produto.getPreco())
                .build();
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());

        pedido.setItensPedido(Set.of(itemPedido));// CasdadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao);
        assertTrue(pedidoVerificacao.getItensPedido()
                .stream()
                .findFirst()
                .get()
                .getQuantidade()
                .equals(3));
    }

    //    @Test
    public void atualizarItemPedidoComPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.PAGO)
                .build();
        pedido.setId(1);

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(5)
                .precoProduto(produto.getPreco())
                .build();
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());

        pedido.setItensPedido(Set.of(itemPedido));// CasdadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        assertNotNull(itemPedidoVerificacao);
        assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
    }


    @Test
    public void atualizarProdutoComCategoria() {
        var produto = Produto.builder()
                .preco(new BigDecimal(500))
                .nome("Kindle")
                .descricao("Agora com iluminação embutida ajustável")
                .build();
        produto.setId(1);

        var categoria = Categoria.builder()
                .nome("Tablets")
                .build();
        categoria.setId(2);

        produto.setCategorias(Set.of(categoria));//cascadeType.MERGE


        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();


        var categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertNotNull(categoriaVerificacao);
    }
}
