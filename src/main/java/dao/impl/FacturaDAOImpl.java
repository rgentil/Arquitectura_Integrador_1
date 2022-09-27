package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.FacturaDAO;
import entity.Factura;
import factory.DAOFactory;

public class FacturaDAOImpl extends BaseDAO implements FacturaDAO{

	private static final String DELETE_FACTURAS = "DELETE FROM Factura";
	private static final String CREATE_FACTURAS = "CREATE TABLE Factura("
													+ "idFactura INT,"
													+ "idCliente INT,"
													+ "PRIMARY KEY(idFactura),"
													+ "FOREIGN KEY(idCliente) references cliente(idCliente))";
	private static final String INSERT_FACTURA = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
	private static final String SELECT_FACTURAS = "SELECT * FROM Factura ORDER BY 1";

	private DAOFactory factory;
	
	public FacturaDAOImpl(DAOFactory factory) throws SQLException {
		this.factory = factory;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() {
		try {
			runStatement(DELETE_FACTURAS, factory);
		} catch (SQLException e) {
			System.out.println("Error borrando Facturas, statement=" + DELETE_FACTURAS);
		}
	}
	
	@Override
	public void create() throws SQLException{
		try {
			runStatement(CREATE_FACTURAS, factory);
		} catch (SQLException e) {
			System.out.println("La tabla Facturas no fue creada");
		}
	}

	@Override
	public void insertAll(List<Factura> invoices) throws SQLException {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			for(Factura invoice: invoices) {
				st = connection.prepareStatement(INSERT_FACTURA);
				st.setLong(1, invoice.getIdFactura());
				st.setLong(2, invoice.getIdCliente());
				st.executeUpdate();
				connection.commit();
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, st, rs);
		}
	}

	@Override
	public List<Factura> getAll() throws SQLException {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement(SELECT_FACTURAS);
			rs = st.executeQuery();
			return toList(rs);
		} finally {
			closeConnection(connection, st,rs);
		}
	}

	private List<Factura> toList(ResultSet rs) throws SQLException {
		List<Factura> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(Factura.builder()
					.idFactura(rs.getInt("idFactura"))
					.idCliente(rs.getInt("idCliente"))
					.build());
		}
		return lista;
	}
}
