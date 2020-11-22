package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class JoinTest extends EntityManagerTest {

    @Test
    public void fazerJoinTest() {
        var jpql = "select p from Pedido p join p.pagamento pag ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void fazerLeftJoinTest() {
        var jpql = "select p from Pedido p left join p.pagamento pag ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }


    @Test
    public void fazerJoinFetchTest() {
        var jpql = "select p from Pedido p join fetch p.itensPedido where p.id = 1 ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido lista = typedQuery.getSingleResult();
        assertFalse(lista.getItensPedido().isEmpty());
    }


}
