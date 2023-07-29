package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class CategoriaDAO {

    private EntityManager entityManager;

    public CategoriaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Categoria categoria) {
        this.entityManager.persist(categoria);
    }

    public void atualizar(Categoria categoria) {
        this.entityManager.merge(categoria);
    }

    public void remover(Categoria categoria) {
        //categoria precisa estar no estado managed, por isso a chamado merge antes do remove
        this.entityManager.remove(this.entityManager.merge(categoria));
    }

}
