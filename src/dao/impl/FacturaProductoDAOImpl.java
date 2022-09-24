package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import conection.Conection;
import dao.FacturaProductoDAO;
import entity.FacturaProducto;

public class FacturaProductoDAOImpl extends Conection implements FacturaProductoDAO{

	private String db;
	
	public FacturaProductoDAOImpl(String db) throws SQLException {
		this.db = db;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() throws SQLException{
		try {
			getConection(db);
			String table = "DELETE FROM Factura_Producto";
			conection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el DELETE FROM");
		}finally {
			closeConection(null, null);	
		}				
	}
	
	@Override
	public void create() throws SQLException{
		try {
			getConection(db);
			String table = "CREATE TABLE Factura_Producto("
					+ "idFactura INT,"
					+ "idProducto INT,"
					+ "cantidad INT,"
					+ "PRIMARY KEY(idFactura, idProducto),"
					+ "FOREIGN KEY(idFactura) references Factura(idFactura),"
					+ "FOREIGN KEY(idProducto) references Producto(idProducto))";
			
			conection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el CREATE TABLE para FacturaProducto");
		}finally {
			closeConection(null, null);	
		}		
	}

	@Override
	public void insertAll(List<FacturaProducto> invoiceProducts) throws SQLException{
		this.getConection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(FacturaProducto invoiceProduct: invoiceProducts) {
				String insert = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
				st = conection().prepareStatement(insert);
				st.setLong(1, invoiceProduct.getIdFactura());
				st.setLong(2, invoiceProduct.getIdProducto());
				st.setInt(3, invoiceProduct.getCantidad());
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
	public List<FacturaProducto> getAll() throws Exception {
		List<FacturaProducto> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Factura_Producto ORDER BY 1");			
			lista = new ArrayList<FacturaProducto>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				FacturaProducto fp = new FacturaProducto(rs.getInt("idFactura"),rs.getInt("idProducto"),rs.getInt("cantidad"));
				lista.add(fp);
			}
			
			for(FacturaProducto facturaProducto : lista) {
				System.out.println(facturaProducto.getIdFactura() + " - " + facturaProducto.getIdProducto() + " - " + facturaProducto.getCantidad());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

}
