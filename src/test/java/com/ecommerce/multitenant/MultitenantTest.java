package com.ecommerce.multitenant;

import com.ecommerce.EntityManagerFactoryTest;
import com.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

public class MultitenantTest extends EntityManagerFactoryTest {

    @Test
    public void usarAbordagemPorSchema() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("ecommerce_jpa");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Produto produto1 = entityManager1.find(Produto.class, 1);
        assertEquals("Kindle", produto1.getNome());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("loja_ecommerce_jpa");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Produto produto2 = entityManager2.find(Produto.class, 1);
        assertEquals("Kindle", produto2.getNome());
        entityManager2.close();
    }

}
