package csv;

import entity.Cliente;
import entity.Factura;
import entity.FacturaProducto;
import entity.Producto;
import org.apache.commons.csv.CSVParser;
import java.util.List;
import java.util.stream.Collectors;

public class CSVService {

    public List<Cliente> parseClients(CSVParser parser) {
        return parser.stream()
                .map(row -> Cliente.builder()
                        .idCliente(Integer.parseInt(row.get("idCliente")))
                        .nombre(row.get("nombre"))
                        .email(row.get("email"))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Factura> parseInvoices(CSVParser parser) {
        return parser.stream()
                .map(row -> Factura.builder()
                        .idFactura(Integer.parseInt(row.get("idFactura")))
                        .idCliente(Integer.parseInt(row.get("idCliente")))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Producto> parseProducts(CSVParser parser) {
        return parser.stream()
                .map(row -> Producto.builder()
                        .idProducto(Integer.parseInt(row.get("idProducto")))
                        .nombre(row.get("nombre"))
                        .valor(Float.parseFloat(row.get("valor")))
                        .build())
                .collect(Collectors.toList());
    }

    public List<FacturaProducto> parseInvoiceProduct(CSVParser parser) {
        return parser.stream()
                .map(row -> FacturaProducto.builder()
                        .idFactura(Integer.parseInt(row.get("idFactura")))
                        .idProducto(Integer.parseInt(row.get("idProducto")))
                        .cantidad(Integer.parseInt(row.get("cantidad")))
                        .build())
                .collect(Collectors.toList());
    }
}
