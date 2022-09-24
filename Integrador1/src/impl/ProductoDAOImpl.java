package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import conection.Conection;
import dao.ProductoDAO;
import entity.Producto;

public class ProductoDAOImpl extends Conection implements ProductoDAO {

	private String db;
	
	public ProductoDAOImpl(String db) throws SQLException {
		this.db = db;
		this.createTable();	
		this.deleteDataFromTable();
	}
	
	@Override
	public void deleteDataFromTable() throws SQLException{
		try {
			getConection(db);
			String table = "DELETE FROM Producto";
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
			String table = "CREATE TABLE Producto("
					+ "idProducto INT,"
					+ "nombre VARCHAR(45),"
					+ "valor FLOAT,"
					+ "PRIMARY KEY(idProducto))";
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
				int id_producto = Integer.parseInt(row.get("idProducto"));
				String nombre = row.get("nombre");
				Float valor = Float.parseFloat(row.get("valor"));
				String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
				st = conection().prepareStatement(insert);
				st.setInt(1, id_producto);
				st.setString(2, nombre);
				st.setFloat(3, valor);
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
	public List<Producto> getAll() throws Exception {
		List<Producto> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Producto ORDER BY 1");			
			lista = new ArrayList<Producto>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				Producto p = new Producto(rs.getInt("idProducto"),rs.getString("nombre"),rs.getDouble("valor"));
				lista.add(p);
			}
			
			for(Producto producto : lista) {
				System.out.println(producto.getIdProducto() + " - " + producto.getNombre() + " - " + producto.getValor());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

	@Override
	public List<Producto> getMasRecaudo() throws Exception {
		List<Producto> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Producto ORDER BY 1");			
			lista = new ArrayList<Producto>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				Producto p = new Producto(rs.getInt("idProducto"),rs.getString("nombre"),rs.getDouble("valor"));
				lista.add(p);
			}
			
			for(Producto producto : lista) {
				System.out.println(producto.getIdProducto() + " - " + producto.getNombre() + " - " + producto.getValor());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

}
