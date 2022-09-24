package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * Entidad Producto
 *
 */
@Data
@AllArgsConstructor
@Builder
public class Producto {
	private int idProducto;
	private String nombre;
	private Float valor;
}
