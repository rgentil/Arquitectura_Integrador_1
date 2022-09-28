package csv;

import entity.Cliente;
import entity.Factura;
import entity.FacturaProducto;
import entity.Producto;
import org.apache.commons.csv.CSVFormat;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CSVService {

    public enum ClientHeaders {
        idCliente,
        nombre,
        email
    }

    public enum ProductHeaders {
        idProducto,
        nombre,
        valor
    }

    public enum InvoiceHeaders {
        idFactura,
        idCliente
    }

    public enum InvoiceProductHeaders {
        idFactura,
        idProducto,
        cantidad
    }

    private CSVFormat clientParser = CSVFormat.DEFAULT.builder()
                                        .setHeader(ClientHeaders.class)
                                        .setSkipHeaderRecord(true)
                                        .build();
    private CSVFormat productParser = CSVFormat.DEFAULT.builder()
            .setHeader(ProductHeaders.class)
            .setSkipHeaderRecord(true)
            .build();
    private CSVFormat invoiceParser = CSVFormat.DEFAULT.builder()
            .setHeader(InvoiceHeaders.class)
            .setSkipHeaderRecord(true)
            .build();
    private CSVFormat productInvoiceParser = CSVFormat.DEFAULT.builder()
            .setHeader(InvoiceProductHeaders.class)
            .setSkipHeaderRecord(true)
            .build();



    public List<Cliente> parseClients(String filename) throws IOException {
        return clientParser.parse(new FileReader(filename)).stream()
                .map(row -> Cliente.builder()
                                .idCliente(Integer.parseInt(row.get("idCliente")))
                                .nombre(row.get("nombre"))
                                .email(row.get("email"))
                                .build())
                .collect(Collectors.toList());
    }

    public List<Factura> parseInvoices(String filename) throws IOException {
        return invoiceParser.parse(new FileReader(filename)).stream()
                .map(row -> Factura.builder()
                        .idFactura(Integer.parseInt(row.get("idFactura")))
                        .idCliente(Integer.parseInt(row.get("idCliente")))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Producto> parseProducts(String filename) throws IOException {
        return productParser.parse(new FileReader(filename)).stream()
                .map(row -> Producto.builder()
                        .idProducto(Integer.parseInt(row.get("idProducto")))
                        .nombre(row.get("nombre"))
                        .valor(Float.parseFloat(row.get("valor")))
                        .build())
                .collect(Collectors.toList());
    }

    public List<FacturaProducto> parseInvoiceProduct(String filename) throws IOException {
        return productInvoiceParser.parse(new FileReader(filename)).stream()
                .map(row -> FacturaProducto.builder()
                        .idFactura(Integer.parseInt(row.get("idFactura")))
                        .idProducto(Integer.parseInt(row.get("idProducto")))
                        .cantidad(Integer.parseInt(row.get("cantidad")))
                        .build())
                .collect(Collectors.toList());
    }
}
