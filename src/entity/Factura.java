package entity;

import lombok.*;

/**
 * 
 * Entidad Factura
 *
 */
@Data
@AllArgsConstructor
@Builder
public class Factura {
	@Setter(AccessLevel.NONE)
	private int idFactura;
	private int idCliente;
}
