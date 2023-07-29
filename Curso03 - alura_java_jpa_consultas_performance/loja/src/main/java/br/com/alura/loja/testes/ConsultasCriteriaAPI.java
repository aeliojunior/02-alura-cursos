package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;

public class ConsultasCriteriaAPI {

    public static void main(String[] args) {

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        System.out.println(produtoDAO.buscarParametrosVariaveisJPQL("Galax note",null));

        System.out.println(produtoDAO.buscarParametrosVariaveisCriteria("Galax note",null));

        entityManager.close();
    }
}
