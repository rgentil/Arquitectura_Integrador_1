package dao;

import java.util.List;

import entity.Cliente;

/**
 * 
 * Interfaz para Cliente
 *
 */
public interface ClienteDAO extends DAO<Cliente>{

	/**
	 * Genera una lista de clientes, ordenada por a cuál se le
	 * facturó más.
	 *
	 * @return Los Clientes ordenados pon mayor volumen de facturación.
	 * @throws Exception
	 */
	List<Cliente> getMostBilled() throws Exception;
}
