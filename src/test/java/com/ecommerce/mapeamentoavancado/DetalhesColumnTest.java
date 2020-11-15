package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DetalhesColumnTest extends EntityManagerTest {

    @Test
    public void impedirInsercaoDaColunaAtualizacao() {
        var produto = Produto.builder()
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAtualizacao(LocalDateTime.now())
                .nome("Teclado para smartpho")
                .descricao("O mais conforável")
                .preco(BigDecimal.ONE)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao.getDataCriacao());
        assertNull(produtoVerificacao.getDataUltimaAtualizacao());
    }

    @Test
    public void impedirAtualizacaoDaColunaCriacao() {
        var produto = entityManager.find(Produto.class, 1);
        produto.setPreco(BigDecimal.TEN);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataUltimaAtualizacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotEquals(
                produto.getDataCriacao().truncatedTo(ChronoUnit.SECONDS),
                produtoVerificacao.getDataCriacao().truncatedTo(ChronoUnit.SECONDS));

        assertEquals(
                produto.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS),
                produtoVerificacao.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS)
                );
    }
}
