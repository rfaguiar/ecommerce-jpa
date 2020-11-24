package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Categoria;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        var jpql = "select c from Categoria c order by c.nome";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(2);

        List<Categoria> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        assertEquals(2, lista.size());
    }

}
