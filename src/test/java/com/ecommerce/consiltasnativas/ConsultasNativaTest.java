package com.ecommerce.consiltasnativas;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ConsultasNativaTest extends EntityManagerTest {


    @Test
    public void executarSQLFieldResult() {
        var sql = "select * from produto";
        Query nativeQuery = entityManager.createNativeQuery(sql, "ecm_produto.Produto");

        List<Produto> resultList = nativeQuery.getResultList();

        resultList.forEach(obj -> System.out.printf("Produto => ED: %s, Nome: %s%n", obj.getId(), obj.getNome()));
    }

    @Test
    public void usarSQLResultSetMapping02() {
        String sql = "select ip.*, p.* from item_pedido ip join produto p on p.id = ip.produto_id";

        Query query = entityManager.createNativeQuery(sql,
                "item_pedido-produto.ItemPedido-Produto");

        List<Object[]> lista = query.getResultList();

        lista.stream().forEach(arr -> System.out.println(
                String.format("Pedido => ID: %s --- Produto => ID: %s, Nome: %s",
                        ((ItemPedido) arr[0]).getId().getPedidoId(),
                        ((Produto)arr[1]).getId(), ((Produto)arr[1]).getNome())));
    }

    @Test
    public void executarSQLResultSetMapping01() {
        var sql = "select * from produto";
        Query nativeQuery = entityManager.createNativeQuery(sql, "produto_loja.Produto");

        List<Produto> resultList = nativeQuery.getResultList();

        resultList.forEach(obj -> System.out.printf("Produto => ED: %s, Nome: %s%n", obj.getId(), obj.getNome()));
    }

    @Test
    public void executarSQLPassarParametros() {
        var sql = "select * from produto where id = :id";
        Query nativeQuery = entityManager.createNativeQuery(sql, Produto.class);
        nativeQuery.setParameter("id", 1);

        List<Produto> resultList = nativeQuery.getResultList();

        resultList.forEach(obj -> System.out.printf("Produto => ED: %s, Nome: %s%n", obj.getId(), obj.getNome()));
    }

    @Test
    public void executarSQLRetornandoEntidade() {
        var sql = "select * from produto";
        Query nativeQuery = entityManager.createNativeQuery(sql, Produto.class);

        List<Produto> resultList = nativeQuery.getResultList();

        resultList.forEach(obj -> System.out.printf("Produto => ED: %s, Nome: %s%n", obj.getId(), obj.getNome()));
    }

    @Test
    public void executarSQL() {
        var sql = "select id, nome from produto";
        Query nativeQuery = entityManager.createNativeQuery(sql);

        List<Object[]> resultList = nativeQuery.getResultList();

        resultList.forEach(arr -> System.out.printf("Produto => ED: %s, Nome: %s%n", arr[0], arr[1]));
    }

}
