package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class ContaService {

    private ConnectionFactory connectionFactory;

    public ContaService() {
        this.connectionFactory = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        return new ContaDAO(connectionFactory.recuperarConexao()).listar();
    }

    public Set<Conta> listarContasInativas() {
        return new ContaDAO(connectionFactory.recuperarConexao()).listarInativas();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Connection connection = connectionFactory.recuperarConexao();
        new ContaDAO(connection).salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        if (!conta.isAtiva()) {
            throw new RegraDeNegocioException("Conta inativa!");
        }

        BigDecimal novoValor = conta.getSaldo().subtract(valor);
        new ContaDAO(connectionFactory.recuperarConexao()).alterarSaldo(numeroDaConta, novoValor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        if (!conta.isAtiva()) {
            throw new RegraDeNegocioException("Conta inativa!");
        }
        BigDecimal novoValor = conta.getSaldo().add(valor);
        new ContaDAO(connectionFactory.recuperarConexao()).alterarSaldo(numeroDaConta, novoValor);
    }

    public void realiarTransferencia(Integer numeroContaOrigem, Integer numeroContaDestino, BigDecimal valor) {
        this.realizarSaque(numeroContaOrigem, valor);
        this.realizarDeposito(numeroContaDestino, valor);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }
        new ContaDAO(connectionFactory.recuperarConexao()).excluir(numeroDaConta);
    }

    public void inativar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }
        new ContaDAO(connectionFactory.recuperarConexao()).inativar(numeroDaConta);
    }

    public void reativar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.isAtiva()) {
            throw new RegraDeNegocioException("Conta já ativa!");
        }
        new ContaDAO(connectionFactory.recuperarConexao()).reativar(numeroDaConta);
    }

    private Conta buscarContaPorNumero(Integer numero) {
        Conta conta = new ContaDAO(connectionFactory.recuperarConexao()).listarPorNumero(numero);
        return conta;
    }
}