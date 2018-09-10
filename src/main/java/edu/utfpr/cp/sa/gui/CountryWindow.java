package main.java.edu.utfpr.cp.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import main.java.edu.utfpr.cp.sa.dao.DAOCountry;
import main.java.edu.utfpr.cp.sa.dao.DAOCustomer;
import main.java.edu.utfpr.cp.sa.entity.Country;
import main.java.edu.utfpr.cp.sa.entity.Customer;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

class CountryTableModel extends AbstractTableModel {
	
	private ArrayList<Country> countries;
	private String columnNames[] = {"Name", "Acronym", "Phone Digits"};
	
	public CountryTableModel() {
		DAOCountry daoCountry = new DAOCountry();
		this.countries =daoCountry.returnAllCountries();
		 
	}
	
	@Override
	public int getRowCount() {
		return countries.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch (columnIndex) {
			case 0:
				return this.countries.get(rowIndex).getName();
				
			case 1:
				return this.countries.get(rowIndex).getAcronym();
				
			case 2:
				return this.countries.get(rowIndex).getPhoneDigits();
	
			default:
				break;
		}
		
		return null;
	}
	
}

public class CountryWindow extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField acronym;
	private JTextField phoneDigits;
	private JTable table;
	private Set<Country> countries;
	JButton btnCreate = new JButton("Create");
	
	private void create () {
		if(!btnCreate.getText().equals("Save")){
			Country c = new Country();
			c.setName(name.getText());
			c.setAcronym(acronym.getText());
			c.setPhoneDigits(new Integer(phoneDigits.getText()));
			DAOCountry daoCountry = new DAOCountry();
			
			if (daoCountry.Insert(c)) {
				JOptionPane.showMessageDialog(this, "Country successfully added!");
				this.table.setModel(new CountryTableModel());
			
			} else
				JOptionPane.showMessageDialog(this, "Sorry, country already exists");
			
		}else {
			Country c = new Country();
			c.setName(name.getText());
			c.setAcronym(acronym.getText());
			c.setPhoneDigits(new Integer(phoneDigits.getText()));
			DAOCountry daoCountry = new DAOCountry();
			
			if (daoCountry.update(c)) {
				JOptionPane.showMessageDialog(this, "Country updated added!");
				btnCreate.setText("Create");
				this.table.setModel(new CountryTableModel());
				name.setEditable(true);
				name.setText("");
				acronym.setText("");
				phoneDigits.setText("");
			
			} else {
				JOptionPane.showMessageDialog(this, "Erro!");
				btnCreate.setText("Create");
			}
		}
		
	}
	private void delete() {
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Country not selected");
		}else {
			String name = (String) table.getValueAt(table.getSelectedRow(), 0);
			DAOCountry daoCountry = new DAOCountry();
			Country country = daoCountry.searchName(name);
			if(!daoCountry.remove(country))
				JOptionPane.showMessageDialog(this,"There are Customers linked to this country!");
			else {
				JOptionPane.showMessageDialog(this, "Country Deleted!");
				this.table.setModel(new CountryTableModel());
			}
		}
	}
	private void update() {
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Country not selected");
		}else {
			btnCreate.setText("Save");
			name.setText((String)table.getValueAt(table.getSelectedRow(), 0));
			name.setEditable(false);
			acronym.setText((String)table.getValueAt(table.getSelectedRow(), 1));
			phoneDigits.setText(Integer.toString((Integer)table.getValueAt(table.getSelectedRow(), 2)));
		}
		
	}
	
	public CountryWindow(Set<Country> countries) {
		this.countries = countries;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane panelTable = new JScrollPane();
		contentPane.add(panelTable, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new CountryTableModel());
		panelTable.setViewportView(table);
		
		JPanel panelInclusion = new JPanel();
		contentPane.add(panelInclusion, BorderLayout.NORTH);
		panelInclusion.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panelInclusion.add(lblName);
		
		name = new JTextField();
		panelInclusion.add(name);
		name.setColumns(10);
		
		JLabel lblAcronym = new JLabel("Acronym");
		panelInclusion.add(lblAcronym);
		
		acronym = new JTextField();
		panelInclusion.add(acronym);
		acronym.setColumns(10);
		
		JLabel lblPhoneDigits = new JLabel("Phone Digits");
		panelInclusion.add(lblPhoneDigits);
		
		phoneDigits = new JTextField();
		panelInclusion.add(phoneDigits);
		phoneDigits.setColumns(10);
		
		panelInclusion.add(btnCreate);
		btnCreate.addActionListener(e -> this.create());
		
		JButton btnClose = new JButton("Close");
		panelInclusion.add(btnClose);
		btnClose.addActionListener(e -> this.dispose());
		
		JButton btnDelete = new JButton("Delete");
		panelInclusion.add(btnDelete);
		btnDelete.addActionListener(e -> this.delete());

		JButton btnUpdate = new JButton("Update");
		panelInclusion.add(btnUpdate);
		btnUpdate.addActionListener(e -> this.update());
		
		this.pack();
		this.setVisible(true);
	}

}
