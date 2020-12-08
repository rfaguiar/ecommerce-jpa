package com.ecommerce.consiltasnativas;

import com.ecommerce.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ConsultasNativaTest extends EntityManagerTest {

    @Test
    public void executarSQL() {
        var sql = "select id, nome from produto";
        Query nativeQuery = entityManager.createNativeQuery(sql);

        List<Object[]> resultList = nativeQuery.getResultList();

        resultList.forEach(arr -> System.out.printf("Produto => ED: %s, Nome: %s%n", arr[0], arr[1]));

    }

}
