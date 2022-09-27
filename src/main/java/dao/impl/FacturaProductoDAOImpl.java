package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.FacturaProductoDAO;
import entity.FacturaProducto;
import factory.DAOFactory;

public class FacturaProductoDAOImpl extends BaseDAO implements FacturaProductoDAO{

	private static final String DELETE_INVOICE_PRODUCTS = "DELETE FROM Factura_Producto";
	private static final String CREATE_INVOICE_PRODUCTS = "CREATE TABLE Factura_Producto("
															+ "idFactura INT,"
															+ "idProducto INT,"
															+ "cantidad INT,"
															+ "PRIMARY KEY(idFactura, idProducto),"
															+ "FOREIGN KEY(idFactura) references Factura(idFactura),"
															+ "FOREIGN KEY(idProducto) references Producto(idProducto))";
	private static final String INSERT_PRODUCTS = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

	private DAOFactory factory;
	
	public FacturaProductoDAOImpl(DAOFactory factory) throws SQLException {
		this.factory = factory;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() {
		try {
			runStatement(DELETE_INVOICE_PRODUCTS, factory);
		} catch (SQLException e) {
			System.out.println("Error borrando factura productos, statement=" + DELETE_INVOICE_PRODUCTS);
		}
	}
	
	@Override
	public void create() {
		try {
			runStatement(CREATE_INVOICE_PRODUCTS, factory);
		} catch (SQLException e) {
			System.out.println("La tabla FacturaProducto no fue creada");
		}
	}

	@Override
	public void insertAll(List<FacturaProducto> invoiceProducts) throws SQLException{
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			for(FacturaProducto invoiceProduct: invoiceProducts) {
				st = connection.prepareStatement(INSERT_PRODUCTS);
				st.setLong(1, invoiceProduct.getIdFactura());
				st.setLong(2, invoiceProduct.getIdProducto());
				st.setInt(3, invoiceProduct.getCantidad());
				st.executeUpdate();
				connection.commit();
			}			
		} finally {
			closeConnection(connection, st, rs);
		}
	}

	@Override
	public List<FacturaProducto> getAll() throws Exception {
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = factory.createConnection();
			st = connection.prepareStatement("SELECT * FROM Factura_Producto ORDER BY 1");
			rs = st.executeQuery();
			return toList(rs);
		} finally {
			closeConnection(connection, st,rs);
		}
	}

	private List<FacturaProducto> toList(ResultSet rs) throws SQLException {
		List<FacturaProducto> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(FacturaProducto.builder()
					.idFactura(rs.getInt("idFactura"))
					.idProducto(rs.getInt("idProducto"))
					.cantidad(rs.getInt("cantidad"))
					.build());
		}
		return lista;
	}
}
