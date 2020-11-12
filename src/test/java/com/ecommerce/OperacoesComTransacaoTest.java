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
        var produto = new Produto(null, "Câmera Canon", "A melhor definição para suas fotos.", new BigDecimal(5000), null, null);

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
        var produto = new Produto(1, "Kindle Paperwhite", "Conheça o novo Kindle.", new BigDecimal(599), null, null);

        entityManager.merge(produto);
        entityManager.getTransaction().begin();
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
        var produto = new Produto(4, "Microfone Rode Videmic", "A melhor qualidade de som.", new BigDecimal(1000), null, null);

        entityManager.merge(produto);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertNotNull(produtoVerificacao);
    }

    @Test
    public void mostrarDiferancaPersistMerge() {
        var produtoPersist = new Produto(null, "Smartphone One Plus", "O processador mais rápido.", new BigDecimal(2000), null, null);

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist);
        produtoPersist.setNome("Smartphone Two Plus");
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        assertNotNull(produtoVerificacaoPersist);
        assertEquals("Smartphone Two Plus", produtoVerificacaoPersist.getNome());



        var produtoMerge = new Produto(5, "Notebook Dell", "O melhor da categoria.", new BigDecimal(2000), null, null);

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
