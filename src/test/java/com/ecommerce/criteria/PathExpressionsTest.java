package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente_;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedido_;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Pedido_;
import com.ecommerce.model.Produto_;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class PathExpressionsTest extends EntityManagerTest {

    @Test
    public void buscarPedidosComProdutoDeIDIgual1Exercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.itensPedido);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.equal(
                        joinItemPedido.get(ItemPedido_.produto).get(Produto_.id), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

    @Test
    public void PathExpression() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);

        selectPedido.where(
                criteriaBuilder.like(
                        fromPedido.get(Pedido_.cliente).get(Cliente_.nome), "M%"
                )
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

}
