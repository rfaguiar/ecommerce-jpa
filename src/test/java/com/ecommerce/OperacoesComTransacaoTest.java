package com.ecommerce;

import com.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
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
        var produto = Produto.builder()
                .nome("Câmera Canon")
                .descricao("A melhor definição para suas fotos.")
                .preco(new BigDecimal(5000))
                .build();

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

    @Test
    public void atualizarObjeto() {
        var produto = Produto.builder()
                .nome("Kindle Paperwhite")
                .descricao("Conheça o novo Kindle.")
                .preco(new BigDecimal(599))
                .build();

        entityManager.getTransaction().begin();
        produto = entityManager.merge(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
        assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
    }

    @Test
    public void atualizarObjetoGerenciado() {
        var produto = entityManager.find(Produto.class, 1);

        produto.setNome("Kindle Paperwhite 2 geração");
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
        assertEquals("Kindle Paperwhite 2 geração", produtoVerificacao.getNome());
    }

    @Test
    public void impedirOperacaoComBancoDeDados() {
        var produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto);

        produto.setNome("Kindle Paperwhite 2 geração");
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
        assertEquals("Kindle", produtoVerificacao.getNome());
    }

    @Test
    public void inserirObjetoComMerge() {
        var produto = Produto.builder()
                .nome("Microfone Rode Videmic")
                .descricao("A melhor qualidade de som.")
                .preco(new BigDecimal(1000))
                .build();

        produto = entityManager.merge(produto);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
    }

    @Test
    public void mostrarDiferancaPersistMerge() {
        var produtoPersist = Produto.builder()
                .nome("Smartphone One Plus")
                .descricao("O processador mais rápido.")
                .preco(new BigDecimal(2000))
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist);
        produtoPersist.setNome("Smartphone Two Plus");
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        assertNotNull(produtoVerificacaoPersist);
        assertEquals("Smartphone Two Plus", produtoVerificacaoPersist.getNome());



        var produtoMerge = Produto.builder()
                .nome("Notebook Dell")
                .descricao("O melhor da categoria.")
                .preco(new BigDecimal(2000))
                .build();
        produtoMerge.setId(5);

        entityManager.getTransaction().begin();
        produtoMerge = entityManager.merge(produtoMerge);
        produtoMerge.setNome("Notebook Dell 2");
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        assertNotNull(produtoVerificacaoMerge);
        assertEquals("Notebook Dell 2", produtoVerificacaoMerge.getNome());
    }
}
