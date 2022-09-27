package conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.DerbyDAOFactory;
import factory.MySQLDAOFactory;
import util.Constante;
/**
 * Clase encargada de establecion coneccion con la base de datos
 *
 */
public abstract class Conection {

	private Connection conn;

	public Conection() {
		super();
	}

	/**
	 * Crea una conexion con la bd seleccionada
	 * @param db nombre de la base de datos
	 * @throws SQLException Error
	 */
	public void getConection(String db) throws SQLException {
		switch (db) {
			case Constante.MYSQL:
				this.conn = MySQLDAOFactory.createConection();
				break;
			case Constante.DERBY:
				this.conn = DerbyDAOFactory.createConection();
				break;
		}
	}

	/**
	 * Cierra conecciones st, rs, conn
	 * @param st PreparedStatement
	 * @param rs ResultSet
	 * @throws SQLException Error
	 */
	public void closeConection(PreparedStatement st, ResultSet rs) throws SQLException {
		if (st != null && !st.isClosed()) {
			st.close();
		}
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
	
	public void commit() throws SQLException {
		conn.commit();
	}
	
	public Connection conection() throws SQLException {
		return conn;
	}

}