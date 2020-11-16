package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SalvandoArquivosTest extends EntityManagerTest {

    @Test
    public void salvarXmlNota() {
        var pedido = entityManager.find(Pedido.class, 1);

        var notaFiscal = NotaFiscal.builder()
                .pedido(pedido)
                .xml(carregarNotaFiscal())
                .dataEmissao(new Date())
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        var notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        assertNotNull(notaFiscalVerificacao.getXml());
        assertTrue(notaFiscalVerificacao.getXml().length > 0);
    }

    private static byte[] carregarNotaFiscal() {
        try {
            return SalvandoArquivosTest.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
