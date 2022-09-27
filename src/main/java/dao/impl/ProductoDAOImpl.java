package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import conection.Conection;
import dao.ProductoDAO;
import entity.Producto;

public class ProductoDAOImpl extends Conection implements ProductoDAO {

	private String db;
	
	public ProductoDAOImpl(String db) throws SQLException {
		this.db = db;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() throws SQLException{
		try {
			getConection(db);
			String table = "DELETE FROM Producto";
			conection().prepareStatement(table).execute();
			commit();
		} catch (Exception e) {
			System.out.println("No hace falta el DELETE FROM");
		} finally {
			closeConection(null, null);	
		}				
	}

	@Override
	public void create() throws SQLException{
		try {
			getConection(db);
			String table = "CREATE TABLE Producto("
					+ "idProducto INT,"
					+ "nombre VARCHAR(45),"
					+ "valor FLOAT,"
					+ "PRIMARY KEY(idProducto))";
			conection().prepareStatement(table).execute();
			commit();
		} catch (Exception e) {
			System.out.println("No hace falta el CREATE TABLE para Producto");
		} finally {
			closeConection(null, null);	
		}		
	}

	@Override
	public void insertAll(List<Producto> products) throws SQLException{
		this.getConection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(Producto product: products) {
				String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
				st = conection().prepareStatement(insert);
				st.setInt(1, product.getIdProducto());
				st.setString(2, product.getNombre());
				st.setFloat(3, product.getValor());
				st.executeUpdate();
				commit();
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			closeConection(st, rs);
		}
	}

	@Override
	public List<Producto> getAll() throws Exception {
		List<Producto> lista = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Producto ORDER BY 1");
			rs = st.executeQuery();			
			while (rs.next()) {
				Producto p = new Producto(rs.getInt("idProducto"),rs.getString("nombre"),rs.getFloat("valor"));
				lista.add(p);
			}
			for(Producto producto : lista) {
				System.out.println(producto.getIdProducto() + " - " + producto.getNombre() + " - " + producto.getValor());
			}
			return lista;
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

	@Override
	public Optional<Producto> getHighestGrossed() throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			String query = "SELECT p.idProducto, p.nombre, p.valor, SUM(p.valor * fp.cantidad) as total "
					+ " FROM Producto p "
					+ " LEFT JOIN Factura_Producto fp ON (p.idProducto = fp.idProducto) "
					+ " GROUP BY p.idProducto, p.nombre, p.valor "
					+ " ORDER BY 4 DESC ";
			st = conection().prepareStatement(query);
			rs = st.executeQuery();
			if (rs.next()) {
				return Optional.of(Producto.builder()
						.idProducto(rs.getInt("idProducto"))
						.nombre(rs.getString("nombre"))
						.valor(rs.getFloat("valor"))
						.build());
			}
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
		return Optional.empty();
	}
}
