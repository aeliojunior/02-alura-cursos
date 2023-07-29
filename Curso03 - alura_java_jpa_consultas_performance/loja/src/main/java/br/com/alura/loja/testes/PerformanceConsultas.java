package br.com.alura.loja.testes;

import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.util.JPAUtil;
import javax.persistence.EntityManager;

public class PerformanceConsultas {

    public static void main(String[] args) {

        EntityManager entityManager = JPAUtil.getEntityManager();

        //atributo cliente anotado para fetch = FetchType.LAZY
        Pedido pedido = entityManager.find(Pedido.class, 1l);
        System.out.println(pedido.getCliente());

        //consulta jpql com cláusula join fetch
        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);
        pedido = pedidoDAO.buscarPedidoComCliente(1l);
        System.out.println(pedido.getCliente());

        entityManager.close();
    }
}
