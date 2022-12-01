package proyectoPOO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.EOFException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.ArrayList;

public class AppProyecto1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        // creamos productos previo a la ejecución real para así tener una base con --------------------------
        // la que trabajar por si el usuario no ingresa productos---------------------------------------------

        ArrayList<Producto> lista = new ArrayList<>();
        // int seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar un producto");
        // while(seguir==0) {
        //     try{
        //         lista.add(new AppProyecto1().crearProducto());
        //     } catch (IOException e) {
        //         JOptionPane.showMessageDialog(null, "No se pudo crear el producto");
        //     }

        //     seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar otro producto");
        // }

        // try {
        //     new AppProyecto1().exportarProductos(lista);
        // } catch (IOException e) {
        //     JOptionPane.showMessageDialog(null, "Error de escritura");
        // }
        

        //paso 1.- cargamos el archivo de productos
        try {
            lista = new AppProyecto1().cargarProductos();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error de lectura");
        } catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clases no compatibles");
        }

        //paso 2.- el usuario se identifica con sus datos personales
        


        //borramos el archivo y lo volvemos a escribir por si el usuario añadió un nuevo producto
        File archivo = new File("productos.dat");
        archivo.delete();
	}

    //este método nos sirve para crear un producto (noten que regresa un objeto producto)
    public Producto crearProducto() throws IOException {

        //leer datos del teclado y esciribilos al archivo
        JLabel lblId = new JLabel("Id de producto: ");
        JTextField txtId = new JTextField();
        JLabel lblDescripcion = new JLabel("Descripcion del producto: ");
        JTextField txtDescripcion = new JTextField();
        JLabel lblStockI = new JLabel("Stock inicial: ");
        JTextField txtStockI = new JTextField();
        JLabel lblStockF = new JLabel("Stock final: ");
        JTextField txtStockF = new JTextField();

        Object[] formato = {lblId, txtId, lblDescripcion, txtDescripcion, lblStockI, txtStockI, lblStockF, txtStockF};

        JOptionPane.showMessageDialog(null, formato);

        return new Producto(txtId.getText(),
                txtDescripcion.getText(),
                Integer.parseInt(txtStockI.getText()),
                Integer.parseInt(txtStockF.getText()));
    }

    //cargamos la lista con productos que previamente va estar llena así es como los datos son persistentes
    public ArrayList<Producto> cargarProductos() throws IOException, ClassNotFoundException {
        ObjectInputStream dis = new ObjectInputStream(new FileInputStream(new File("productos.dat")));
        
        Producto newP;
        ArrayList<Producto> lista = new ArrayList<>();

        while(true) {
            try {
                newP = (Producto)dis.readObject();
            } catch(EOFException e) {
                break;
            }

            lista.add(newP);
        }

        return lista;
    }

    //este método nos servirá para exportar la lista con productos
    public void exportarProductos(ArrayList<Producto> lista) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("productos.dat")));

        for(Producto producto : lista) {
            oos.writeObject(producto);
        } 

        //cerramos el archivo
        oos.close();
    }

}
