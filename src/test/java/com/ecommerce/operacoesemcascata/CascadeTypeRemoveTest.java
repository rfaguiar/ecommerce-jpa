package com.ecommerce.operacoesemcascata;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CascadeTypeRemoveTest extends EntityManagerTest {

//    @Test
    public void removerPedidoEItens() {
        var pedido = entityManager.find(Pedido.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNull(pedidoVerificacao);
    }

//    @Test
    public void removerItemPedidoEPedido() {
        var itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
        assertNull(pedidoVerificacao);
    }

    @Test
    public void removerRelacaoProdutoCategoria() {
        var produto = entityManager.find(Produto.class, 1);
        assertFalse(produto.getCategorias().isEmpty());

        entityManager.getTransaction().begin();
        produto.getCategorias().clear();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertTrue(produtoVerificacao.getCategorias().isEmpty());
    }

}
