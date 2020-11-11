package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Categoria;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AutoRelacionamentoTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        var categoriaPai = new Categoria(null, "Eletrônicos", null, null);
        var categoria = new Categoria(null, "Celulares", categoriaPai, null);

        entityManager.persist(categoriaPai);
        entityManager.persist(categoria);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var categoriaFilhaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertNotNull(categoriaFilhaVerificacao.getCategoriaPai());

        var categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
        assertFalse(categoriaPaiVerificacao.getCategorias().isEmpty());
    }
}
