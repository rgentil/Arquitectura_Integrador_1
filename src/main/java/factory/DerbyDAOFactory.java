package factory;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import dao.impl.ClienteDAOImpl;
import dao.impl.FacturaDAOImpl;
import dao.impl.FacturaProductoDAOImpl;
import dao.impl.ProductoDAOImpl;

/**
 * Clase que conecta con la base de datos Derby
 */
public class DerbyDAOFactory extends DAOFactory{

	private Properties properties = new Properties();

	public DerbyDAOFactory() {
		try {
			properties.load(new FileReader("properties/dbDerby.properties"));
			String JDBC_DRIVER = properties.getProperty("JDBC_DRIVER");
			Class.forName(JDBC_DRIVER).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	/**
	 * Crea una conexión a la base de datos.
	 * 
	 * @return Una conexión a la base de datos
	 */
	@Override
	public Connection createConnection() {
		try {
			String DB_URL = properties.getProperty("DB_URL");
			return DriverManager.getConnection(DB_URL);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public ClienteDAO getClienteDAO(String db) throws SQLException {
		return new ClienteDAOImpl(this);
	}

	@Override
	public FacturaDAO getFacturaDAO(String db) throws SQLException {
		return new FacturaDAOImpl(this);
	}

	@Override
	public FacturaProductoDAO getFacturaProductoDAO(String db) throws SQLException {
		return new FacturaProductoDAOImpl(this);
	}

	@Override
	public ProductoDAO getProductoDAO(String db) throws SQLException {
		return new ProductoDAOImpl(this);
	}
}
