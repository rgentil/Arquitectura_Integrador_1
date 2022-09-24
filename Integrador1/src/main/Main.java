package main;

import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import entity.Cliente;
import factory.DAOFactory;
import util.Constante;

/**
 * Clase principal. Test para validar lo especificado 
 * @author 
 * <ul>
 * 	<li>Guillermina Lauge</li>
 *  <li>Pablo Mendoza</li>
 *  <li>Ricardo Gentil</li>
 * </ul>
 * @version 1.0.0
 * @since 2022
 *
 */

public class Main {
	/**
	 * Clase principal
	 * @param args Argumentos de la app
	 * @throws Exception Posible errores
	 */
	public static void main(String[] args) throws Exception{
		
		//String db = Constante.MYSQL;
		String db = Constante.DERBY;
		System.out.println("Se crea una instancia de la base de datos " + db);
		DAOFactory factory = DAOFactory.getDAOFactory(db);
		
		//Iniciar Tablas para persistir datos.
		FacturaProductoDAO facturaProductoDAO = factory.getFacturaProductoDAO(db);
		ProductoDAO productoDAO = factory.getProductoDAO(db);
		FacturaDAO facturaDAO = factory.getFacturaDAO(db);
		ClienteDAO clienteDAO = factory.getClienteDAO(db);
		
		//Levantar datos de los archivos csv
		CSVParser parserProducto = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/productos.csv"));
		CSVParser parserCliente = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/clientes.csv"));
		CSVParser parserFactura = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/facturas.csv"));
		CSVParser parserFacturaProducto = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/facturas-productos.csv"));
		
		clienteDAO.insertCSV(parserCliente);
		productoDAO.insertCSV(parserProducto);
		facturaDAO.insertCSV(parserFactura);
		facturaProductoDAO.insertCSV(parserFacturaProducto);			
		
		//Imprimir el producto que más recaudo
		System.out.println("Producto más recaudado: ");
		System.out.println(productoDAO.getMasRecaudo() + System.lineSeparator());
		
		//Imprimir el listado de clientes ordenado por facturaci�n
		System.out.println("Listado de clientes ordenado por facturaci�n: ");
		ArrayList<Cliente> clientes = (ArrayList<Cliente>) clienteDAO.getMasFacturados();
		for(Cliente c: clientes) {
			System.out.println(c);
		}

	}

}
