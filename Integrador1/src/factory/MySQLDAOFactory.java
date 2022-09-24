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
import impl.ClienteDAOImpl;
import impl.FacturaDAOImpl;
import impl.FacturaProductoDAOImpl;
import impl.ProductoDAOImpl;

public class MySQLDAOFactory extends DAOFactory{

	public MySQLDAOFactory() {
		MySQLDAOFactory.registrarDriver();
	}
	
	/**
	 * Metodo que regitra el driver de la base de datos
	 */
	private static void registrarDriver() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("properties/dbMySQL.properties"));
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
			prop.load(new FileReader("properties/dbMySQL.properties"));
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
	public FacturaProductoDAO getFacturaProductoDAO(String db)throws SQLException {
		return new FacturaProductoDAOImpl(db);
	}

	@Override
	public ProductoDAO getProductoDAO(String db) throws SQLException {
		return new ProductoDAOImpl(db);
	}

}
