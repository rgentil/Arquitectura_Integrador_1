package entity;

/**
 * 
 * Entidad Producto
 *
 */
public class Producto {
	private int idProducto;
	private String nombre;
	private double valor;

	public Producto(int idProducto, String nombre, double valor) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getIdProducto() {
		return idProducto;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", valor=" + valor + "]";
	}

}
