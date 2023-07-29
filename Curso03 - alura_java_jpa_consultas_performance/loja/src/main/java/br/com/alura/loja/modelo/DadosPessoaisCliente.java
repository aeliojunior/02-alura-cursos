package br.com.alura.loja.modelo;

import javax.persistence.Embeddable;

@Embeddable //embutível, integravel
public class DadosPessoaisCliente {

    private String nome;
    private String email;

    public DadosPessoaisCliente() {
    }

    public DadosPessoaisCliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
