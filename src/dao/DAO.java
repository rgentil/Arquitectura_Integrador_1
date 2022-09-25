package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface DAO
 * 
 * Métodos que se van a implementar en cada entidad dao
 * 
 *
 * @param <T> Cualquier entidad mapeada de la base de datos
 */
public interface DAO<T> {
	
	void delete() throws SQLException;

	void create() throws SQLException;

	List<T> getAll() throws Exception;

	void insertAll(List<T> elements) throws SQLException;
}