package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Categoria;
import com.ecommerce.model.Produto;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertFalse;

public class RelacionamentoManyToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPedido() {
        var produto = entityManager.find(Produto.class, 1);
        var categoria = entityManager.find(Categoria.class, 1);

        produto.setCategorias(Set.of(categoria));
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var categoriasVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertFalse(categoriasVerificacao.getProdutos().isEmpty());
    }
}
