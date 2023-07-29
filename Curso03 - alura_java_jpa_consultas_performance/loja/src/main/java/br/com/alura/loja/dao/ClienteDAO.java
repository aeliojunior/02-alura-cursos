package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class ClienteDAO extends GenericDAO {

    public ClienteDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Cliente buscarPorId(Long id) {
        return getEntityManager().find(Cliente.class, id);
    }

}
