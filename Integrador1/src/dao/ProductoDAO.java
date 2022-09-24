package dao;

import java.util.List;

import entity.Producto;

/**
 * 
 * Interface para Producto
 *
 */
public interface ProductoDAO extends DAO<Producto>{
	
	public List<Producto> getMasRecaudo() throws Exception; 
	
}
