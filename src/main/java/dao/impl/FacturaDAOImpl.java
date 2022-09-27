package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conection.Conection;
import dao.FacturaDAO;
import entity.Factura;

public class FacturaDAOImpl extends Conection implements FacturaDAO{

	private String db;
	
	public FacturaDAOImpl(String db) throws SQLException {
		this.db = db;
		this.create();
		this.delete();
	}
	
	@Override
	public void delete() throws SQLException{
		try {
			getConection(db);
			String table = "DELETE FROM Factura";
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
			String table = "CREATE TABLE Factura("
					+ "idFactura INT,"
					+ "idCliente INT,"
					+ "PRIMARY KEY(idFactura),"
					+ "FOREIGN KEY(idCliente) references cliente(idCliente))";
			conection().prepareStatement(table).execute();
			commit();
		}catch (Exception e) {
			System.out.println("No hace falta el CREATE TABLE para Factura");
		}finally {
			closeConection(null, null);	
		}
	}

	@Override
	public void insertAll(List<Factura> invoices) throws SQLException{
		this.getConection(db);
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			for(Factura invoice: invoices) {
				String insert = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
				st = conection().prepareStatement(insert);
				st.setLong(1, invoice.getIdFactura());
				st.setLong(2, invoice.getIdCliente());
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
	public List<Factura> getAll() throws Exception {
		List<Factura> lista = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			this.getConection(db);
			st = conection().prepareStatement("SELECT * FROM Factura ORDER BY 1");			
			lista = new ArrayList<Factura>();			
			rs = st.executeQuery();			
			while (rs.next()) {
				Factura f = new Factura(rs.getInt("idFactura"),rs.getInt("idCliente"));
				lista.add(f);
			}
			
			for(Factura factura : lista) {
				System.out.println(factura.getIdFactura() + " - " + factura.getIdCliente());
			}
			
			return lista;
			
		} catch (Exception e) {
			throw e;
		} finally {			
			closeConection(st,rs);	
		}
	}

}
