package main.java.edu.utfpr.cp.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.utfpr.cp.sa.entity.Country;
import main.java.edu.utfpr.cp.sa.entity.Customer;

public class DAOCustomer {
	public boolean Insert(Customer customer){
       ConnectionJDBC conJdbc = new ConnectionJDBC();
       Connection con = conJdbc.connection();
       String query = "INSERT INTO customer (name, phone, age, credit_limit, country) VALUES (?,?,?,?,?)";
       PreparedStatement stmt;
       try {
           stmt = con.prepareStatement(query);
           stmt.setString(1, customer.getName());
           stmt.setString(2, customer.getPhone());
           stmt.setString(3, Integer.toString(customer.getAge()));
           stmt.setString(4, Double.toString(customer.getCreditLimit()));
           stmt.setString(5, customer.getCountry().getName());
           stmt.executeUpdate();
           stmt.close();
           con.close();

       } catch (SQLException ex) {
           System.out.println("Erro de Sql:" + ex);
           return false;
       }
       return true;
   }
	public Customer searchName(String name) {
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "SELECT * FROM customer WHERE name = ?";
        PreparedStatement stmt;
        Customer customer = new Customer();

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);

            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
            	try {
                    customer.setName(rs.getString("name"));
                    customer.setAge(Integer.parseInt(rs.getString("age")));
                    customer.setCreditLimit(Double.parseDouble(rs.getString("credit_limit")));
                    DAOCountry daoCountry = new DAOCountry();
                    customer.setCountry(daoCountry.searchName(rs.getString("country")));
                    customer.setPhone(rs.getString("phone"));
                    }
                    catch (Exception e) {
     				// TODO: handle exception
                    }
            }
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return null;
        }
        if("".equals(customer.getName())) return null;
        return customer;
    }
	public boolean remove(Customer customer){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "DELETE FROM customer WHERE name = ?";
        PreparedStatement stmt;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, customer.getName());

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return false;
        }

        return true;
    }
	public boolean update(Customer customer){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "UPDATE customer SET  phone = ?, age = ?, credit_limit = ?, country = ? WHERE name = ?";
        PreparedStatement stmt;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(5, customer.getName());
            stmt.setString(1, customer.getPhone());
            stmt.setString(2, Integer.toString(customer.getAge()));
            stmt.setString(3, Double.toString(customer.getCreditLimit()));
            stmt.setString(4, customer.getCountry().getName());
            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return false;
        }

        return true;
    }
	public ArrayList<Customer> returnAllCustomer(){
       ConnectionJDBC conJdbc = new ConnectionJDBC();
       Connection con = conJdbc.connection();
       String query = "SELECT * FROM customer";
       PreparedStatement stmt;
       ArrayList<Customer> customers = new ArrayList();
       

       try {
           stmt = con.prepareStatement(query);

           ResultSet rs = null;

           rs = stmt.executeQuery();
           while (rs.next()) {
               Customer customer = new Customer();
               try {
               customer.setName(rs.getString("name"));
               customer.setAge(Integer.parseInt(rs.getString("age")));
               customer.setCreditLimit(Double.parseDouble(rs.getString("credit_limit")));
               DAOCountry daoCountry = new DAOCountry();
               customer.setCountry(daoCountry.searchName(rs.getString("country")));
               customer.setPhone(rs.getString("phone"));
               }
               catch (Exception e) {
				// TODO: handle exception
               }
               customers.add(customer);
           }
           stmt.close();
           con.close();

       } catch (SQLException ex) {
           System.out.println("Erro de Sql:" + ex);
           return null;
       }
       return customers;
   }
}