package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente_;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Pedido_;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class PathExpressionsTest extends EntityManagerTest {

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
