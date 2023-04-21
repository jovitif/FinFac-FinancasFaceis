package br.com.finfac.dao;

import java.sql.*;

public class ModuloConexao {
      public static Connection conectar() {
        Connection conexao;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbfinfac?characterEncoding=utf-8";
        String user = "root";
        String password = "mysql2023";
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
