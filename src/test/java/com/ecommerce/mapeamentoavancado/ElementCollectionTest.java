package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void aplicarTags() {
        entityManager.getTransaction().begin();

        var produto = entityManager.find(Produto.class, 1);
        produto.setTags(List.of("ebook", "livro-digital"));

        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertFalse(produtoVerificacao.getTags().isEmpty());
    }

}
