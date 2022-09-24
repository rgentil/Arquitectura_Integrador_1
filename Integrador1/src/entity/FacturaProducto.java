package entity;

/**
 * 
 * Entidad FacturaProducto
 *
 */
public class FacturaProducto {
	private int idFactura;
	private int idProducto;
	private int cantidad;

	public FacturaProducto(int idFactura, int idProducto, int cantidad) {
		super();
		this.idFactura = idFactura;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public int getIdProducto() {
		return idProducto;
	}

	@Override
	public String toString() {
		return "FacturaProducto [idFactura=" + idFactura + ", idProducto=" + idProducto + ", cantidad=" + cantidad
				+ "]";
	}

}
