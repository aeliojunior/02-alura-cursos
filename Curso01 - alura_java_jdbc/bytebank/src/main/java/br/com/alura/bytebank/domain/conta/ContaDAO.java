package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection connection;

    public ContaDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), BigDecimal.ZERO, cliente, true);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, ativa) " +
                "VALUES (?,?,?,?,?,?)";

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, cliente.getNome());
            preparedStatement.setString(4, cliente.getCpf());
            preparedStatement.setString(5, cliente.getEmail());
            preparedStatement.setBoolean(6, conta.isAtiva());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void alterarSaldo(int numeroConta, BigDecimal valor){

        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBigDecimal(1, valor);
            preparedStatement.setInt(2, numeroConta);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();

        String sql = "SELECT * FROM conta WHERE ativa = true";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int numero = resultSet.getInt("numero");
                BigDecimal saldo = resultSet.getBigDecimal("saldo");
                String cliente_nome = resultSet.getString("cliente_nome");
                String cliente_cpf = resultSet.getString("cliente_cpf");
                String cliente_email = resultSet.getString("cliente_email");
                Boolean ativa = resultSet.getBoolean("ativa");

                contas.add(new Conta(numero, saldo,
                                new Cliente(
                                    new DadosCadastroCliente(cliente_nome,cliente_cpf, cliente_email)), ativa ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return contas;
    }

    public Set<Conta> listarInativas(){
        Set<Conta> contas = new HashSet<>();

        String sql = "SELECT * FROM conta WHERE ativa = false";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int numero = resultSet.getInt("numero");
                BigDecimal saldo = resultSet.getBigDecimal("saldo");
                String cliente_nome = resultSet.getString("cliente_nome");
                String cliente_cpf = resultSet.getString("cliente_cpf");
                String cliente_email = resultSet.getString("cliente_email");
                Boolean ativa = resultSet.getBoolean("ativa");

                contas.add(new Conta(numero, saldo,
                        new Cliente(
                                new DadosCadastroCliente(cliente_nome,cliente_cpf, cliente_email)), ativa ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return contas;
    }

    public Conta listarPorNumero(int numeroConta){
        String sql = "SELECT * FROM conta WHERE numero = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Conta conta = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, numeroConta);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int numero = resultSet.getInt("numero");
                BigDecimal saldo = resultSet.getBigDecimal("saldo");
                String cliente_nome = resultSet.getString("cliente_nome");
                String cliente_cpf = resultSet.getString("cliente_cpf");
                String cliente_email = resultSet.getString("cliente_email");
                Boolean ativa = resultSet.getBoolean("ativa");

                conta = new Conta(numero, saldo,
                        new Cliente(
                                new DadosCadastroCliente(cliente_nome, cliente_cpf, cliente_email)), ativa);
            } else {
                throw new RuntimeException("Conta não existe!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conta;
    }

    public void excluir(Integer numeroConta){

        String sql = "DELETE FROM conta WHERE numero = ?";

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, numeroConta);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void inativar(int numeroConta){

        String sql = "UPDATE conta SET ativa = false WHERE numero = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, numeroConta);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void reativar(int numeroConta){

        String sql = "UPDATE conta SET ativa = true WHERE numero = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, numeroConta);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
