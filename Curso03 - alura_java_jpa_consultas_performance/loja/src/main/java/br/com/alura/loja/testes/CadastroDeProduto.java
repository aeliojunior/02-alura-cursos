package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {
    public static void main(String[] args) {

        //cadastrarProdutoCategoria();

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        List<Produto> produtos = produtoDAO.buscarPorDescricao("Tablet Samsung");
        produtos.forEach(p -> System.out.println(p) );

        entityManager.close();

    }

    private static void cadastrarProdutoCategoria() {
        Categoria categoria = new Categoria("Smartphone");

        Produto produto = new Produto("Galax A73", "Celular Samsung",
                new BigDecimal("1980"), categoria);

        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();

        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        categoriaDAO.cadastrar(categoria);

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        produtoDAO.cadastrar(produto);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void testesCadastro() {
        Categoria categoria = new Categoria("Smartphone");

        Produto produto = new Produto("Galax A73", "Celular Samsung",
                                    new BigDecimal("1980"), categoria);

        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();

        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        categoriaDAO.cadastrar(categoria);

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        produtoDAO.cadastrar(produto);

        //produto.setDescricao("Bom smartphone");
        //entityManager.flush();

        //produto.setPreco(new BigDecimal(1500));
        //produtoDAO.atualizar(produto);

        entityManager.getTransaction().commit();
        entityManager.close();

        ////////////////////////////////

        /*EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 4l);

        produto.setDescricao("Teste 1");

        em.getTransaction().commit();*/

        ////////////////////////////////

        /*EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 4l);

        produto.setDescricao("Teste 1");
        em.flush();

        produto.setDescricao("Teste 2");
        em.flush();

        //em.merge(produto);
        produto.setDescricao("Teste 3");
        em.flush();

        produto.setDescricao("Teste 4");

        em.getTransaction().commit();*/

        ////////////////////////////////

        /*
        em.clear(); //limpa os estados das entidade no entity manager

        em.getTransaction().begin();*/

        /*produto = em.merge(produto);
        produto.setDescricao("Teste 5");*/

        /*produto.setDescricao("Teste 5");
        em.merge(produto);

        em.getTransaction().commit(); */

        ////////////////////////////////

        /*EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 5l);

        em.clear();

        em.remove(em.merge(produto));

        em.getTransaction().commit();*/

        ////////////////////////////////

        /*EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 6l);

        produto.setDescricao("Teste 2");
        em.flush();

        em.clear();

        em.remove(produto); //erro pq a entidade não está managed

        em.getTransaction().commit();*/

        ////////////////////////////////

        /*EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 6l);

        produto.setDescricao("Teste 3");
        //em.flush();

        em.clear(); // remove a entidade no managed e, por isso, não atualiza no banco

        em.getTransaction().commit();*/

        //em.close();

        /*EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);*/

        //Produto produto = produtoDAO.buscarPorId(6l);
        //System.out.println(produto);

        //List<Produto> produtos = produtoDAO.buscarTodos();
        //produtos.forEach(p -> System.out.println(p) );

        /*List<Produto> produtos = produtoDAO.buscarPorNome("Galax note");
        produtos.forEach(p -> System.out.println(p) );*/

        /*List<Produto> produtos = produtoDAO.buscarPorNomeCategoria("Tablet2");
        produtos.forEach(p -> System.out.println(p) );*/

       /* BigDecimal precoProduto = produtoDAO.buscarPrecoProdutoPorNome("Galax note");
        System.out.println(precoProduto);*/
    }
}
