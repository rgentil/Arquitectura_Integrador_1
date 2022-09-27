package entity;

import lombok.*;

/**
 * 
 * Entidad Cliente
 *
 */
@Data
@AllArgsConstructor
@Builder
public class Cliente {
	@Setter(AccessLevel.NONE)
	private int idCliente;
	private String nombre;
	private String email;
}
