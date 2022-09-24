package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import conection.Conection;
import dao.ClienteDAO;
import entity.Cliente;

public class ClienteDAOImpl extends Conection implements ClienteDAO{
	
	private String db;
	
	public ClienteDAOImpl(String db) throws SQLException {
		this.db = db;
		this.createTable();	
		this.deleteDataFromTable();
	}
	
	@Override
	public void deleteDataFromTable() throws SQLException{
		try {
			getConection(db);
			String table = "DELETE FROM Cliente";
			conection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el DELETE FROM");
		}finally {
			closeConection(null, null);	
		}				
	}
	
	@Override
	public void createTable() throws SQLException{
		try {
			getConection(db);
			//String table = "CREATE TABLE IF NOT EXISTS Cliente("
			String table = "CREATE TABLE Cliente("
								+ "idCliente INT,"
								+ "nombre VARCHAR(500),"
								+ "email VARCHAR(150),"
								+ "PRIMARY KEY(idCliente)"
								+ ")";
			System.out.println("Scrip Cliente " + table);
			conection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el CREATE TABLE");			
		}finally {
			closeConection(null, null);	
		}
	}

	@Override
	public void insertCSV(CSVParser parser) throws SQLException{
		this.getConection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(CSVRecord row: parser) { 
				int id_cliente = Integer.parseInt(row.get("idCliente"));
				String nombre = row.get("nombre");
				String email = row.get("email");				
				String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
				st = conection().prepareStatement(insert);
				st.setLong(1, id_cliente);
				st.setString(2, nombre);
				st.setString(3, email);
				st.executeUpdate();
				commit();
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			closeConection(st, rs);
		}
		
	}

	@Override
	public List<Cliente> getAll() throws Exception {
		List<Cliente> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Cliente ORDER BY 1");			
			lista = new ArrayList<Cliente>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				Cliente c = new Cliente(rs.getInt("idCliente"),rs.getString("nombre"),rs.getString("email"));
				lista.add(c);
				//System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getInt(3));
			}
			
			for(Cliente cliente : lista) {
				System.out.println(cliente.getIdCliente() + " - " + cliente.getNombre() + " - " + cliente.getEmail());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}
	
	@Override
	public List<Cliente> getMasFacturados() throws Exception {
		List<Cliente> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Cliente ORDER BY 1");			
			lista = new ArrayList<Cliente>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				Cliente c = new Cliente(rs.getInt("idCliente"),rs.getString("nombre"),rs.getString("email"));
				lista.add(c);
				//System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getInt(3));
			}
			
			for(Cliente cliente : lista) {
				System.out.println(cliente.getIdCliente() + " - " + cliente.getNombre() + " - " + cliente.getEmail());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

	
}
