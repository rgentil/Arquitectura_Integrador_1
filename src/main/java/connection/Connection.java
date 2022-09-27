package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.DerbyDAOFactory;
import factory.MySQLDAOFactory;
import util.Constante;

/**
 * Clase encargada de establecer conexión con la base de datos
 */
public abstract class Connection {

	private java.sql.Connection connection;

	public Connection() {
		super();
	}

	/**
	 * Crea una conexión con la bd seleccionada
	 * 
	 * @param db nombre de la base de datos
	 * @throws SQLException Error
	 */
	public void getConnection(String db) {
		switch (db) {
			case Constante.MYSQL:
				this.connection = MySQLDAOFactory.createConnection();
				break;
			case Constante.DERBY:
				this.connection = DerbyDAOFactory.createConnection();
				break;
		}
	}

	/**
	 * Cierra conecciones st, rs, conn
	 * @param st PreparedStatement
	 * @param rs ResultSet
	 * @throws SQLException Error
	 */
	public void closeConnection(PreparedStatement st, ResultSet rs) throws SQLException {
		if (st != null && !st.isClosed()) {
			st.close();
		}
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	public void commit() throws SQLException {
		connection.commit();
	}
	
	public java.sql.Connection connection() {
		return connection;
	}
}