package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class OperacoesEmLoteTest extends EntityManagerTest {

    private static final int LIMITE_INSERCOES = 4;

    @Test
    public void inserirEmLote() throws IOException {
        try (
            var in = OperacoesEmLoteTest.class.getClassLoader()
                .getResourceAsStream("produtos/importar.txt");
            var reader = new BufferedReader(new InputStreamReader(in))
        ){

            entityManager.getTransaction().begin();

            int contadorInsercoes = 0;

            for (String linha : reader.lines().collect(Collectors.toList())) {
                if (linha.isBlank()) {
                    continue;
                }

                String[] produtoColuna = linha.split(";");
                var produto = new Produto();
                produto.setNome(produtoColuna[0]);
                produto.setDescricao(produtoColuna[1]);
                produto.setPreco(new BigDecimal(produtoColuna[2]));
                produto.setDataCriacao(LocalDateTime.now());

                entityManager.persist(produto);

                if (++contadorInsercoes == LIMITE_INSERCOES) {
                    entityManager.flush();
                    entityManager.clear();

                    contadorInsercoes = 0;

                    System.out.println("---------------------------------");
                }
            }

            entityManager.getTransaction().commit();
        }
    }

}
