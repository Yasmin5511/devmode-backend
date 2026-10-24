package br.com.fiap.devmode.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String urlDeConexao = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String login = "rm568618";
        String senha = "030700";

        try {
            return DriverManager.getConnection(urlDeConexao, login, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
