package dao;

import java.util.List;
import java.util.Optional;

import entity.Producto;

/**
 * 
 * Interfaz para Producto
 *
 */
public interface ProductoDAO extends DAO<Producto>{

	/**
	 * Encuentra el producto que más recaudó. Se define
	 * recaudación como cantidad de productos vendidos multiplicada por su valor.
	 *
	 * @return El producto con mayor recaudación.
	 * @throws Exception
	 */
	Optional<Producto> getHighestGrossed() throws Exception;
}
