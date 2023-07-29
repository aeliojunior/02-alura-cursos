package br.com.alura.loja.dao;

import br.com.alura.loja.vo.RelatorioVendasVo;
import br.com.alura.loja.modelo.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO extends GenericDAO {

    public PedidoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Pedido buscarPorId(Long id) {
        return getEntityManager().find(Pedido.class, id);
    }

    public BigDecimal valorTotalVendido(){
        String jpql = "SELECT SUM(valorTotal) FROM Pedido";
        return getEntityManager().createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioVendasVo> relatorioVendasVo(){
        String jpql = "SELECT new br.com.alura.loja.vo.RelatorioVendasVo("
                + "produto.nome, "
                + "SUM(item.quantidade) as quantidadeTotal, "
                + "MAX(pedido.data)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.itens item "
                + "JOIN item.produto produto "
                + "GROUP BY produto.nome "
                + "ORDER BY quantidadeTotal DESC";
        return getEntityManager().createQuery(jpql, RelatorioVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id){
        String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id";
        return getEntityManager().createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
