package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JoinTest extends EntityManagerTest {

    @Test
    public void fazerJoinTest() {
        var jpql = "select p from Pedido p join p.pagamento pag ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        assertEquals(1, lista.size());
    }

}
