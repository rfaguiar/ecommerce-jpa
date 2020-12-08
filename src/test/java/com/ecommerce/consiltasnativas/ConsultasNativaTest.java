package com.ecommerce.consiltasnativas;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ConsultasNativaTest extends EntityManagerTest {

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
