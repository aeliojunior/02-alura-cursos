package br.com.alura.loja.modelo;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded //embutido, integrado
    private DadosPessoaisCliente dadosPessoais;

    public Cliente() {
    }

    public Cliente(String nome, String email) {
        dadosPessoais = new DadosPessoaisCliente(nome, email);
    }

    public Long getId() {
        return id;
    }

    public DadosPessoaisCliente getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoaisCliente dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public String getNome(){
        return this.dadosPessoais.getNome();
    }

    public void setNome (String nome){
        this.dadosPessoais.setNome(nome);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + dadosPessoais.getNome() + '\'' +
                ", email='" + dadosPessoais.getEmail() + '\'' +
                '}';
    }
}
