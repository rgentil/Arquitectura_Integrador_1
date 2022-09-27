package entity;

import lombok.*;

/**
 * Entidad FacturaProducto
 */
@Data
@AllArgsConstructor
@Builder
public class FacturaProducto {
	private int idFactura;
	private int idProducto;
	private int cantidad;
}
