package factory;

import java.sql.SQLException;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import util.Constante;

public abstract class DAOFactory {

	public abstract ClienteDAO getClienteDAO(String db) throws SQLException;

	public abstract FacturaDAO getFacturaDAO(String db) throws SQLException;

	public abstract FacturaProductoDAO getFacturaProductoDAO(String db) throws SQLException;

	public abstract ProductoDAO getProductoDAO(String db) throws SQLException;

	public static DAOFactory getDAOFactory(String db) {
		switch (db) {
		case Constante.MYSQL:
			return new MySQLDAOFactory();
		case Constante.DERBY:
			return new DerbyDAOFactory();
		default:
			return null;
		}
	}
}