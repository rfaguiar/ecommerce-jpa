package com.ecommerce;

import com.ecommerce.model.Categoria;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EstrategiaChavePrimariaTest extends EntityManagerTest {

    @Test
    public void testarEstrategiaChave() {
        var categoria = Categoria.builder()
                .nome("Jogos")
                .build();

        entityManager.persist(categoria);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertNotNull(categoriaVerificacao);
    }
}
