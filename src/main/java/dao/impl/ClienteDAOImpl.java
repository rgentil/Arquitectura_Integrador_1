package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.ClienteDAO;
import entity.Cliente;
import factory.DAOFactory;

/**
 * Clase que implementa métodos.
 *
 * @see Connection
 * @see ClienteDAO
 */
public class ClienteDAOImpl extends BaseDAO<Cliente> implements ClienteDAO {

	private static final String DELETE_CLIENTS = "DELETE FROM Cliente";
	private static final String CREATE_CLIENTS = "CREATE TABLE Cliente("
			+ "idCliente INT,"
			+ "nombre VARCHAR(500),"
			+ "email VARCHAR(150),"
			+ "PRIMARY KEY(idCliente)"
			+ ")";
	private static final String INSERT_CLIENT = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
	private static final String SELECT_CLIENTS = "SELECT * FROM Cliente ORDER BY 1";
	private static final String GET_MOST_BILLED =
			"SELECT c.idCliente, c.nombre, c.email, SUM(p.valor * fp.cantidad) as total "
			+ " FROM Cliente c "
			+ " LEFT JOIN Factura f ON (c.idCliente = f.idCliente) "
			+ " LEFT JOIN Factura_Producto fp ON (f.idFactura = fp.idFactura) "
			+ " LEFT JOIN Producto p ON (p.idProducto = fp.idProducto) "
			+ " GROUP BY c.idCliente, c.nombre, c.email "
			+ " ORDER BY 4 DESC";

	private DAOFactory factory;
	
	public ClienteDAOImpl(DAOFactory factory) throws SQLException {
		this.factory = factory;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() {
		try {
			runStatement(DELETE_CLIENTS, factory);
		} catch (SQLException e) {
			System.out.println("Error borrando clientes, statement=" + DELETE_CLIENTS);
		}
	}
	
	@Override
	public void create() {
		try {
			runStatement(CREATE_CLIENTS, factory);
		} catch (SQLException e) {
			System.out.println("La tabla clientes no fue creada");
		}
	}

	@Override
	public void insertAll(List<Cliente> clients) throws SQLException{
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			for(Cliente client: clients) {
				st = connection.prepareStatement(INSERT_CLIENT);
				st.setLong(1, client.getIdCliente());
				st.setString(2, client.getNombre());
				st.setString(3, client.getEmail());
				st.executeUpdate();
				connection.commit();
			}
		} finally {
			closeConnection(connection, st, rs);
		}
	}

	@Override
	public List<Cliente> getAll() throws Exception {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement(SELECT_CLIENTS);
			rs = st.executeQuery();
			return toList(rs);
		} finally {
			closeConnection(connection, st,rs);
		}
	}
	
	@Override
	public List<Cliente> getMostBilled() throws Exception {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement(GET_MOST_BILLED);
			rs = st.executeQuery();			
			return toList(rs);
		} finally {
			closeConnection(connection, st,rs);
		}
	}
	private List<Cliente> toList(ResultSet rs) throws SQLException {
		List<Cliente> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(Cliente.builder()
					.idCliente(rs.getInt("idCliente"))
					.nombre(rs.getString("nombre"))
					.email(rs.getString("email"))
					.build());
		}
		return lista;
	}
}
