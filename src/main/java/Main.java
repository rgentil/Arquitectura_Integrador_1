import java.util.List;

import csv.CSVService;
import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import entity.Cliente;
import entity.Factura;
import entity.FacturaProducto;
import entity.Producto;
import factory.DAOFactory;
import util.Databases;

/**
 * Clase principal. Test para validar lo especificado 
 *
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
	 * @param args Argumentos de la app
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		// 1) Cree un programa utilizando JDBC que cree el esquema de la base de datos.
		String db = Databases.DERBY;
		//String db = Databases.MYSQL;
		System.out.println("Obteniendo la factory para la base de datos " + db);
		DAOFactory factory = DAOFactory.getDAOFactory(db);

		//Creamos las Tablas
		ClienteDAO clienteDAO = factory.getClienteDAO(db);
		FacturaDAO facturaDAO = factory.getFacturaDAO(db);
		ProductoDAO productoDAO = factory.getProductoDAO(db);
		FacturaProductoDAO facturaProductoDAO = factory.getFacturaProductoDAO(db);

		// 2) Considere los CSV dados y escriba un programa JDBC que cargue los datos a la base de
		// datos. Considere utilizar la biblioteca Apache Commons CSV, disponible en Maven central,
		// para leer los archivos.

		CSVService csvService = new CSVService();
		//Levantamos los datos de los archivos csv
		List<Producto> products = csvService.parseProducts("src/main/resources/input/productos.csv");
		List<Cliente> clients = csvService.parseClients("src/main/resources/input/clientes.csv");
		List<Factura> invoices = csvService.parseInvoices("src/main/resources/input/facturas.csv");
		List<FacturaProducto> invoiceProducts = csvService.parseInvoiceProduct("src/main/resources/input/facturas-productos.csv");
		// Y los persistimos
		clienteDAO.insertAll(clients);
		productoDAO.insertAll(products);
		facturaDAO.insertAll(invoices);
		facturaProductoDAO.insertAll(invoiceProducts);

		// 3) Escriba un programa JDBC que retorne el producto que más recaudó. Se define
		// "recaudación" como cantidad de productos vendidos multiplicada por su valor.
		System.out.println("Producto más recaudado: ");
		System.out.println(productoDAO.getHighestGrossed().get() + System.lineSeparator());

		// 4) Escriba un programa JDBC que imprima una lista de clientes, ordenada por a cuál se le
		// facturó más.
		System.out.println("Listado de clientes ordenado por facturación: ");
		clienteDAO.getMostBilled().forEach(System.out::println);
	}
}
