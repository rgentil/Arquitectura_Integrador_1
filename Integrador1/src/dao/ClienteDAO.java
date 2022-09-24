package dao;

import java.util.List;

import entity.Cliente;
/**
 * 
 * Interface para Cliente
 *
 */
public interface ClienteDAO extends DAO<Cliente>{
	
	public List<Cliente> getMasFacturados() throws Exception; 
	
}
