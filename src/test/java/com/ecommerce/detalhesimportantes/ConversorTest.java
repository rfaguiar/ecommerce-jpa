package com.ecommerce.detalhesimportantes;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class ConversorTest  extends EntityManagerTest {

    @Test
    public void converterTest() {
        var produto = Produto.builder()
                .dataCriacao(LocalDateTime.now())
                .nome("Carregador de Notebook Dell")
                .ativo(Boolean.TRUE)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertTrue(produtoVerificacao.getAtivo());
    }

}
