package proyectoPOO;

import java.util.ArrayList;
import java.io.Serializable;

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
	public String curp;
    public String nombre;
    public String apellido; 
    public String clave;
    public ArrayList<Movimiento> movimientos;
    
    public Empleado(String curp, String nombre, String apellido, String clave) {
        this.curp = curp;
        this.nombre = nombre; 
        this.apellido = apellido;
        this.clave = clave;
    }

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

    public void registrarMovimiento(Movimiento mov) {
        movimientos.add(mov);
    }

	@Override
	public String toString() {
		return "Empleado [curp=" + curp + ", nombre=" + nombre + ", apellido=" + apellido + ", clave=" + clave;
	}
    
}
