package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conection.Conection;
import dao.ClienteDAO;
import entity.Cliente;
/**
 * Clase que implementa metodos.
 *
 * @see Conection
 * @see ClienteDAO
 */
public class ClienteDAOImpl extends Conection implements ClienteDAO{
	
	private String db;
	
	public ClienteDAOImpl(String db) throws SQLException {
		this.db = db;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() throws SQLException{
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
	public void create() throws SQLException{
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
			System.out.println("No hace falta el CREATE TABLE para Cliente");
		}finally {
			closeConection(null, null);	
		}
	}

	@Override
	public void insertAll(List<Cliente> clients) throws SQLException{
		this.getConection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(Cliente client: clients) {
				String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
				st = conection().prepareStatement(insert);
				st.setLong(1, client.getIdCliente());
				st.setString(2, client.getNombre());
				st.setString(3, client.getEmail());
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
			lista = new ArrayList<>();
			rs = st.executeQuery();			
			while (rs.next()) {
				Cliente c = Cliente.builder()
						.idCliente(rs.getInt("idCliente"))
						.nombre(rs.getString("nombre"))
						.email(rs.getString("email"))
						.build();
				lista.add(c);
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
	public List<Cliente> getMostBilled() throws Exception {
		List<Cliente> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			String query = "SELECT c.idCliente, c.nombre, c.email, SUM(p.valor * fp.cantidad) as total "
					+ " FROM Cliente c "
					+ " LEFT JOIN Factura f ON (c.idCliente = f.idCliente) "
					+ " LEFT JOIN Factura_Producto fp ON (f.idFactura = fp.idFactura) "
					+ " LEFT JOIN Producto p ON (p.idProducto = fp.idProducto) "
					+ "GROUP BY c.idCliente, c.nombre, c.email "
					+ "ORDER BY 4 DESC";
			st = conection().prepareStatement(query);			
			;
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
