package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import static org.junit.Assert.assertNotNull;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
//        entityManager.find(Pedido.class, 1);

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);
        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void mostrarDiferencaQueries() {
        var jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        assertNotNull(pedido1);

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        assertNotNull(pedido2);
    }

}
