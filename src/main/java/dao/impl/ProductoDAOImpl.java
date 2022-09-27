package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.BaseDAO;
import dao.ProductoDAO;
import entity.Cliente;
import entity.Producto;
import factory.DAOFactory;

public class ProductoDAOImpl extends BaseDAO implements ProductoDAO {

	private static final String DELETE_PRODUCTOS = "DELETE FROM Producto";
	private static final String CREATE_PRODUCTOS = "CREATE TABLE Producto("
													+ "idProducto INT,"
													+ "nombre VARCHAR(45),"
													+ "valor FLOAT,"
													+ "PRIMARY KEY(idProducto))";
	private static final String INSERT_PRODUCTOS = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
	private static final String SELECT_PRODUCTOS = "SELECT * FROM Producto ORDER BY 1";
	private static final String GET_HIGHEST_GROSSED = "SELECT p.idProducto, p.nombre, p.valor, SUM(p.valor * fp.cantidad) as total "
														+ " FROM Producto p "
														+ " LEFT JOIN Factura_Producto fp ON (p.idProducto = fp.idProducto) "
														+ " GROUP BY p.idProducto, p.nombre, p.valor "
														+ " ORDER BY 4 DESC ";

	private DAOFactory factory;
	
	public ProductoDAOImpl(DAOFactory factory) throws SQLException {
		this.factory = factory;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() {
		try {
			runStatement(DELETE_PRODUCTOS, factory);
		} catch (SQLException e) {
			System.out.println("Error borrando clientes, statement=" + DELETE_PRODUCTOS);
		}				
	}

	@Override
	public void create() {
		try {
			runStatement(CREATE_PRODUCTOS, factory);
		} catch (SQLException e) {
			System.out.println("La tabla Producto no fue creada");
		}
	}

	@Override
	public void insertAll(List<Producto> products) throws SQLException{
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			for(Producto product: products) {
				st = connection.prepareStatement(INSERT_PRODUCTOS);
				st.setInt(1, product.getIdProducto());
				st.setString(2, product.getNombre());
				st.setFloat(3, product.getValor());
				st.executeUpdate();
				connection.commit();
			}			
		} finally {
			closeConnection(connection, st, rs);
		}
	}

	@Override
	public List<Producto> getAll() throws SQLException {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement(SELECT_PRODUCTOS);
			rs = st.executeQuery();			
			return toList(rs);
		} finally {
			closeConnection(connection, st,rs);
		}
	}

	@Override
	public Optional<Producto> getHighestGrossed() throws Exception {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement(GET_HIGHEST_GROSSED);
			rs = st.executeQuery();
			if (rs.next()) {
				return Optional.of(Producto.builder()
						.idProducto(rs.getInt("idProducto"))
						.nombre(rs.getString("nombre"))
						.valor(rs.getFloat("valor"))
						.build());
			}
		} finally {
			closeConnection(connection, st,rs);
		}
		return Optional.empty();
	}

	private List<Producto> toList(ResultSet rs) throws SQLException {
		List<Producto> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(Producto.builder()
					.idProducto(rs.getInt("idProducto"))
					.nombre(rs.getString("nombre"))
					.valor(rs.getFloat("valor"))
					.build());
		}
		return lista;
	}
}
