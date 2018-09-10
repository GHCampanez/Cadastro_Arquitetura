package main.java.edu.utfpr.cp.sa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.utfpr.cp.sa.entity.Country;
import main.java.edu.utfpr.cp.sa.entity.Customer;

public class DAOCountry {
	public boolean Insert(Country country){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "INSERT INTO country (name, acronym, phoneDigits) VALUES (?,?,?)";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getAcronym());
            stmt.setString(3, Integer.toString(country.getPhoneDigits()));

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return false;
        }
        return true;
    }
	public Country searchName(String name) {
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "SELECT * FROM country WHERE name = ?";
        PreparedStatement stmt;
        Country country = new Country();

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);

            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                country.setName(rs.getString("name"));
                country.setPhoneDigits(Integer.parseInt(rs.getString("phoneDigits")));
                country.setAcronym(rs.getString("acronym"));
            }
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return null;
        }
        if("".equals(country.getName())) return null;
        return country;
    }
	public boolean remove(Country country){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "DELETE FROM country WHERE name = ?";
        PreparedStatement stmt;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, country.getName());

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return false;
        }

        return true;
    }
	public boolean update(Country country){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "UPDATE country SET  acronym = ?, phoneDigits = ? WHERE name = ?";
        PreparedStatement stmt;

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, country.getAcronym());
            stmt.setString(2, Integer.toString(country.getPhoneDigits()));
            stmt.setString(3, country.getName());

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return false;
        }

        return true;
    }
	public ArrayList<Country> returnAllCountries(){
        ConnectionJDBC conJdbc = new ConnectionJDBC();
        Connection con = conJdbc.connection();
        String query = "SELECT * FROM country";
        PreparedStatement stmt;
        ArrayList<Country> countries = new ArrayList();
        

        try {
            stmt = con.prepareStatement(query);

            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setName(rs.getString("name"));
                country.setAcronym(rs.getString("acronym"));
                country.setPhoneDigits(Integer.parseInt(rs.getString("phoneDigits")));
                countries.add(country);
            }
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro de Sql:" + ex);
            return null;
        }
        return countries;
    }
	
}
