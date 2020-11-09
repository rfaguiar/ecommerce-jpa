package com.ecommerce;

import com.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void abrirEFecharATransacao() {
//        var produto = new Produto();
        entityManager.getTransaction().begin();;

//        entityManager.persist(produto);
//        entityManager.merge(produto);
//        entityManager.remove(produto);

        entityManager.getTransaction().commit();
    }

    @Test
    public void inserirOPrimeiroObjeto() {
        var produto = new Produto(2, "Câmera Canon", "A melhor definição para suas fotos.", new BigDecimal(5000));

        entityManager.persist(produto);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
    }

    @Test
    public void removerObjeto() {
        var produto = entityManager.find(Produto.class, 3);
        entityManager.remove(produto);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();

        var produtoVerificacao = entityManager.find(Produto.class, 3);
        assertNull(produtoVerificacao);
    }
}