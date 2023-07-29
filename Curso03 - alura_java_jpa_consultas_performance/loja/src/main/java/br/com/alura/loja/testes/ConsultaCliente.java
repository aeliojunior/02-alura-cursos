package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ClientePF;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ConsultaCliente {

    public static void main(String[] args) {

        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();

        ClienteDAO clienteDAO = new ClienteDAO(entityManager);

        Cliente cliente = clienteDAO.buscarPorId(1l);
        System.out.println(cliente);

        Cliente novoClientePF = new ClientePF("Fulano de Tal", "fulano@gmail.com","147258369", LocalDate.now());
        clienteDAO.cadastrar(novoClientePF);

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
