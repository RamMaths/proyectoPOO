package proyectoPOO;

import java.util.ArrayList;

public class Subdivision {
    private String id;
    private char tipo;
    private String nombre;
    private ArrayList<Producto> productos;

    public Subdivision(String id, char tipo, String nombre) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public String toString() {
        return "Nombre: " + nombre + "\nId: " + id + "\nTipo: " + tipo;
    }
}
