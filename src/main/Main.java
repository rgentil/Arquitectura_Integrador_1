package main;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

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
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		CSVService csvService = new CSVService();
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
		CSVParser productParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/productos.csv"));
		List<Producto> products = csvService.parseProducts(productParser);
		CSVParser clientParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/clientes.csv"));
		List<Cliente> clients = csvService.parseClients(clientParser);
		CSVParser invoiceParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/facturas.csv"));
		List<Factura> invoices = csvService.parseInvoices(invoiceParser);
		CSVParser invoiceProductParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./src/csv/facturas-productos.csv"));
		List<FacturaProducto> invoiceProducts = csvService.parseInvoiceProduct(invoiceProductParser);
		
		clienteDAO.insertAll(clients);
		productoDAO.insertAll(products);
		facturaDAO.insertAll(invoices);
		facturaProductoDAO.insertAll(invoiceProducts);
		
		//Imprimir el producto que más recaudo
		System.out.println("Producto más recaudado: ");
		System.out.println(productoDAO.getHighestGrossed().get() + System.lineSeparator());

		//Imprimir el listado de clientes ordenado por facturación
		System.out.println("Listado de clientes ordenado por facturación: ");
		ArrayList<Cliente> clientes = (ArrayList<Cliente>) clienteDAO.getMostBilled();
		for(Cliente c: clientes) {
			System.out.println(c);
		}
	}
}
