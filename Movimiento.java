package proyectoPOO;

public class Movimiento {

    public String fecha; 
    public Empleado empleado;
    public Producto producto;
    public String tipo;
    public int cambioUnidades;

    public Movimiento(String fecha, Empleado empleado, Producto producto, String tipo, int cambioUnidades) {
        this.fecha = fecha;
        this.empleado = empleado;
        this. producto = producto;
        this.tipo = tipo;
        this.cambioUnidades = cambioUnidades;
    }

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCambioUnidades() {
		return cambioUnidades;
	}

	public void setCambioUnidades(int cambioUnidades) {
		this.cambioUnidades = cambioUnidades;
	}

	@Override
	public String toString() {
		return "Movimiento [fecha=" + fecha + ", producto=" + producto + ", tipo=" + tipo + ", cambioUnidades="
				+ cambioUnidades + "]";
	}
    
}
