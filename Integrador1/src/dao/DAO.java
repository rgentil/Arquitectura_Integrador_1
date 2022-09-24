package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVParser;

/**
 * Interface DAO
 * 
 * Métodos que se van a implementar en cada entidad dao
 * 
 *
 * @param <T> Cualquier entidad mapeada de la base de datos
 */
public interface DAO<T> {
	
	public void deleteDataFromTable() throws SQLException;
	
	//public void dropTable() throws SQLException;
	
	public void createTable() throws SQLException;
	
	//public void initTable() throws SQLException;

	public void insertCSV(CSVParser parser) throws SQLException;
	
	public List<T> getAll() throws Exception;    

}