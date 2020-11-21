package com.ecommerce.util;

import com.ecommerce.model.Produto;
import lombok.extern.java.Log;

import javax.persistence.Persistence;

@Log
public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {
        var entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
        var entityManager = entityManagerFactory.createEntityManager();

        var produto = entityManager.find(Produto.class, 1);
        log.info(produto.getNome());

        entityManager.close();
        entityManagerFactory.close();
    }
}
