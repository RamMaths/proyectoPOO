package proyectoPOO;

import java.io.Serializable;
import java.util.ArrayList;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
	private String id;
    private String descripcion;
    private int stockInicial;
    private int existencias;
    private int stockFinal;
    private ArrayList<Movimiento> movimientos;
    private ArrayList<Subdivision> subdivisiones;

    public Producto(String id, String descripcion, int stockInicial) {
        this.id = id;
        this. descripcion = descripcion;
        this.stockInicial = stockInicial;
        this.existencias = this.stockInicial;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getStockInicial() {
		return stockInicial;
	}

	public void setStockInicial(int stockInicial) {
		this.stockInicial = stockInicial;
	}

	public int getStockFinal() {
		return stockFinal;
	}

	public void setStockFinal(int stockFinal) {
		this.stockFinal = stockFinal;
	}
	
	public void ingreso(int cuantos) {
        existencias += cuantos;
    }
	
    public void egreso(int cuantos) {
        existencias -= cuantos;
    }

    public boolean esCongruente () {
        return (stockFinal == existencias);
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nDescripción: " + descripcion + "\nstockInicial: " + stockInicial + "\nstockFinal: " + stockFinal;
    }

}
