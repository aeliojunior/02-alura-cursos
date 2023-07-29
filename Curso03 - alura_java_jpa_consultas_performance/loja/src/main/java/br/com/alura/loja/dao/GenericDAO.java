package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;

public class GenericDAO<Entidade> {

    private EntityManager entityManager;

    public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void cadastrar(Entidade entidade) {
        this.entityManager.persist(entidade);
    }

    public void atualizar(Entidade entidade) {
        this.entityManager.merge(entidade);
    }

    public void remover(Entidade entidade) {
        this.entityManager.remove(this.entityManager.merge(entidade));
    }

}
