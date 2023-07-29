package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Produto produto) {
        this.entityManager.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.entityManager.merge(produto);
    }

    public void remover(Produto produto) {
        //produto precisa estar no estado managed, por isso a chamado merge antes do remove
        this.entityManager.remove(this.entityManager.merge(produto));
    }

    public Produto buscarPorId(Long id) {
        return this.entityManager.find(Produto.class, id);
    }

    public List<Produto> buscarTodos(){
        String jpql = "SELECT p FROM Produto p";
        return this.entityManager.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome){
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
        return this.entityManager.createQuery(jpql, Produto.class)
                                .setParameter("nome", nome)
                                .getResultList();
    }

    public List<Produto> buscarPorNomeCategoria(String nome){
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        //join automático feito pelo JPA
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
        return this.entityManager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoProdutoPorNome(String nome){
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        //join automático feito pelo JPA
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return this.entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }
}
