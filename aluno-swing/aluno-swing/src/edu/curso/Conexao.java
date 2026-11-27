package edu.curso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USUARIO = "Lima";
    private static final String SENHA = "2122";

    public static Connection obter() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException | LinkageError ex) {
            throw new SQLException("Falha ao carregar o driver MySQL: " + ex, ex);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
