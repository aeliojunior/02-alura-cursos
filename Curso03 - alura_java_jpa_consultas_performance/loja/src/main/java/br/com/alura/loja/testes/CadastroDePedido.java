package br.com.alura.loja.testes;

import br.com.alura.loja.dao.*;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;

public class CadastroDePedido {

    public static void main(String[] args) {

        //cadastrarCliente();
        //consultarCliente();

        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        //GenericDAO genericDAO = new GenericDAO(entityManager);

        ClienteDAO dao = new ClienteDAO(entityManager);
        Cliente cliente = dao.buscarPorId(1l);

        Pedido pedido = new Pedido(cliente);

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);

        Produto produto = produtoDAO.buscarPorId(1l);

        //ItemPedido itemPedido = new ItemPedido(1, pedido, produto);
        //genericDAO.cadastrar(itemPedido); //desnecessário pq no rel OneToMany foi acrescentado o cascade

        pedido.adicionarItem(new ItemPedido(1, pedido, produto));

        produto = produtoDAO.buscarPorId(2l);

        //itemPedido = new ItemPedido(1, pedido, produto);
        //genericDAO.cadastrar(itemPedido);

        pedido.adicionarItem(new ItemPedido(1, pedido, produto));

        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);

        pedidoDAO.cadastrar(pedido);

        entityManager.getTransaction().commit();

        System.out.println("Valor total vendido: " + pedidoDAO.valorTotalVendido());

        pedidoDAO.relatorioVendasVo().forEach(System.out::println);

        entityManager.close();

    }

    private static void cadastrarCliente() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        GenericDAO dao = new GenericDAO(entityManager);

        Cliente cliente = new Cliente("Beltrano Pereira", "454564646");
        dao.cadastrar(cliente);

        Categoria categoria = new Categoria("Notebook");
        dao.cadastrar(categoria);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void consultarCliente(){
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        ClienteDAO dao = new ClienteDAO(entityManager);

        Cliente cliente = dao.buscarPorId(1l);

        System.out.println(cliente);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
