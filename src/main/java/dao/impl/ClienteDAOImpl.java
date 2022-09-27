package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.Connection;
import dao.ClienteDAO;
import entity.Cliente;
/**
 * Clase que implementa metodos.
 *
 * @see Connection
 * @see ClienteDAO
 */
public class ClienteDAOImpl extends Connection implements ClienteDAO{
	
	private String db;
	
	public ClienteDAOImpl(String db) throws SQLException {
		this.db = db;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() throws SQLException{
		try {
			getConnection(db);
			String table = "DELETE FROM Cliente";
			connection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el DELETE FROM");
		}finally {
			closeConnection(null, null);
		}				
	}
	
	@Override
	public void create() throws SQLException{
		try {
			getConnection(db);
			//String table = "CREATE TABLE IF NOT EXISTS Cliente("
			String table = "CREATE TABLE Cliente("
								+ "idCliente INT,"
								+ "nombre VARCHAR(500),"
								+ "email VARCHAR(150),"
								+ "PRIMARY KEY(idCliente)"
								+ ")";
			System.out.println("Scrip Cliente " + table);
			connection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el CREATE TABLE para Cliente");
		}finally {
			closeConnection(null, null);
		}
	}

	@Override
	public void insertAll(List<Cliente> clients) throws SQLException{
		this.getConnection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(Cliente client: clients) {
				String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
				st = connection().prepareStatement(insert);
				st.setLong(1, client.getIdCliente());
				st.setString(2, client.getNombre());
				st.setString(3, client.getEmail());
				st.executeUpdate();
				commit();
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(st, rs);
		}
	}

	@Override
	public List<Cliente> getAll() throws Exception {
		List<Cliente> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConnection(db);
			st = connection().prepareStatement("SELECT * FROM Cliente ORDER BY 1");
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
			closeConnection(st,rs);
		}
	}
	
	@Override
	public List<Cliente> getMostBilled() throws Exception {
		List<Cliente> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConnection(db);
			String query = "SELECT c.idCliente, c.nombre, c.email, SUM(p.valor * fp.cantidad) as total "
					+ " FROM Cliente c "
					+ " LEFT JOIN Factura f ON (c.idCliente = f.idCliente) "
					+ " LEFT JOIN Factura_Producto fp ON (f.idFactura = fp.idFactura) "
					+ " LEFT JOIN Producto p ON (p.idProducto = fp.idProducto) "
					+ "GROUP BY c.idCliente, c.nombre, c.email "
					+ "ORDER BY 4 DESC";
			st = connection().prepareStatement(query);
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
			closeConnection(st,rs);
		}
	}

	
}
