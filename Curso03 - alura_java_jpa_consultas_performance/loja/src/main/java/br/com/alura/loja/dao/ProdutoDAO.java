package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public List<Produto> buscarTodos() {
        String jpql = "SELECT p FROM Produto p";
        return this.entityManager.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
        return this.entityManager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorDescricao(String descricao) {
        return this.entityManager.createNamedQuery("Produto.buscarPorDescricao", Produto.class)
                .setParameter("descricao", descricao)
                .getResultList();
    }

    public List<Produto> buscarPorNomeCategoria(String nome) {
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        //join automático feito pelo JPA
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
        return this.entityManager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoProdutoPorNome(String nome) {
        //a jpql considera o nome da entidade e seus atributos, não da tabela, pq é orientado a objetos
        //assim, Produto é a entidade produto no sistema
        //join automático feito pelo JPA
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return this.entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public List<Produto> buscarParametrosVariaveisJPQL(String nome, LocalDate dataCadastro) {

        String jpql = "SELECT p FROM Produto p WHERE 1=1";

        if (nome != null && !nome.isEmpty()) {
            jpql += " AND p.nome = :nome";
        }
        if (dataCadastro != null) {
            jpql += " AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = this.entityManager.createQuery(jpql, Produto.class);

        if (nome != null && !nome.isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscarParametrosVariaveisCriteria(String nome, LocalDate dataCadastro) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = builder.and();

        if (nome != null && !nome.isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if (dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), nome));
        }

        query.where(filtros);

        return this.entityManager.createQuery(query).getResultList();
    }
}
