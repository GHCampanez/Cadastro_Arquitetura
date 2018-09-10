package main.java.edu.utfpr.cp.sa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    public Connection connection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con;
            
            con = DriverManager.getConnection("jdbc:mysql://localhost/arquitetura?autoReconnect=true&useSSL=false", "root", "123@qwe");
            //System.out.println("Passou");
            return con;
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não encontrada");
        } catch (SQLException ex){
            System.out.println("Erro ao conectar com o banco");
        }
        return null;
    }
}
