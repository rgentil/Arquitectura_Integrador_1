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
 * 
 */
public class DerbyDAOFactory extends DAOFactory{

	/*
	 * Los datos de la conexion a la base los saco desde un archivo properties
	 */
	// private static final String JDBC_DRIVER =
	// "org.apache.derby.jdbc.EmbeddedDriver"
	// private static final String DB_URL = "jdbc:derby:MyDerbyDb;create=true"

	public DerbyDAOFactory() {
		DerbyDAOFactory.registrarDriver();
	}

	/**
	 * Metodo que regitra el driver de la base de datos
	 */
	private static void registrarDriver() {
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
	 * Crea la conexion a la base de datos.
	 * 
	 * @return Conexion a la base,
	 */
	public static Connection createConection() throws SQLException {
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
