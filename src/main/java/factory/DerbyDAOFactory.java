package factory;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

	public DerbyDAOFactory() {
		DerbyDAOFactory.registerDriver();
	}

	/**
	 * Registra el driver de la base de datos.
	 */
	private static void registerDriver() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("properties/dbDerby.properties"));
			String JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
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
	public static Connection createConnection() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("properties/dbDerby.properties"));
			String DB_URL = prop.getProperty("DB_URL");
			Connection conn = DriverManager.getConnection(DB_URL);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public ClienteDAO getClienteDAO(String db) throws SQLException {
		return new ClienteDAOImpl(db);
	}

	@Override
	public FacturaDAO getFacturaDAO(String db) throws SQLException {
		return new FacturaDAOImpl(db);
	}

	@Override
	public FacturaProductoDAO getFacturaProductoDAO(String db) throws SQLException {
		return new FacturaProductoDAOImpl(db);
	}

	@Override
	public ProductoDAO getProductoDAO(String db) throws SQLException {
		return new ProductoDAOImpl(db);
	}
}
