package proyectoPOO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppCrearArchivos {

	public static void main(String[] args) {
        HashMap<String, Producto> productos = new HashMap<>();

        int seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar un producto");
        while(seguir==0) {
            try{
                Producto prod = new AppCrearArchivos().crearProducto();
                productos.put(prod.getDescripcion(), prod);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "No se pudo crear el producto");
            }

            seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar otro producto");
        }

        try {
            new AppCrearArchivos().exportarProductos(productos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de escritura");
        }


        HashMap<String, Empleado> empleados = new HashMap<>();

        seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar un empleado");
        while(seguir==0) {
            try{
                Empleado emp = new AppProyecto1().crearEmpleado();
                empleados.put(emp.getNombre(), emp);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "No se pudo crear el empleado");
            }

            seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar otro empleado");
        }

        try {
            new AppProyecto1().exportarEmpleados(empleados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de escritura");
        }
        
      //borramos los archivos y lo volvemos a escribir por si el usuario añadió un nuevo producto o un nuevo usuario
        File archivoProd = new File("productos.dat");
        archivoProd.delete();
        File archivoEmp = new File("empleados.dat");
        archivoEmp.delete();
        try {
            new AppProyecto1().exportarProductos(productos);
            new AppProyecto1().exportarEmpleados(empleados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de escritura");
        }
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

        Object[] formato = {lblId, txtId, lblDescripcion, txtDescripcion, lblStockI, txtStockI};

        JOptionPane.showMessageDialog(null, formato);

        return new Producto(txtId.getText(),
                txtDescripcion.getText(),
                Integer.parseInt(txtStockI.getText()));
    }

    //este método sirve para crear un movimiento (noten que este método regresa un movimiento)
    public Movimiento crearMovimiento(Producto producto, Empleado empleado) throws IOException {

        String tipos[] = {"Ingreso" , "Egreso"};

        String tipo = (String)JOptionPane.showInputDialog(
                null,
                "Selecciona una opcion",
                "Tipo de figura",
                JOptionPane.PLAIN_MESSAGE,
                null,
                tipos,
                tipos[0]);

        //leer datos del teclado y esciribilos al archivo
        JLabel lblFecha = new JLabel("Fecha: ");
        JTextField txtFecha = new JTextField();
        JLabel lblCambio = new JLabel("Cambio: ");
        JTextField txtCambio = new JTextField();

        Object[] formato = {lblFecha, txtFecha, lblCambio, txtCambio};

        JOptionPane.showMessageDialog(null, formato);

        return new Movimiento(txtFecha.getText(),
                empleado,
                producto,
                tipo,
                Integer.parseInt(txtCambio.getText()));
    }


    public Empleado crearEmpleado() throws IOException {

        //leer datos del teclado y esciribilos al archivo
        JLabel lblCurp = new JLabel("Curp: ");
        JTextField txtCurp = new JTextField();
        JLabel lblNombre = new JLabel("Nombre: ");
        JTextField txtNombre = new JTextField();
        JLabel lblApellido = new JLabel("Apellido: ");
        JTextField txtApellido = new JTextField();
        JLabel lblClave = new JLabel("Clave: ");
        JTextField txtClave = new JTextField();

        Object[] formato = {lblCurp, txtCurp, lblNombre, txtNombre, lblApellido, txtApellido, lblClave, txtClave};

        JOptionPane.showMessageDialog(null, formato);

        return new Empleado(txtCurp.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtClave.getText());
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    //métodos para cargar archivos -----------------------------------------------------------------------------------------------------

    //cargamos la lista con productos que previamente va estar llena así es como los datos son persistentes

    public HashMap<String, Producto> cargarProductos() throws IOException, ClassNotFoundException {
        ObjectInputStream dis = new ObjectInputStream(new FileInputStream(new File("productos.dat")));
        
        Producto newP;
        HashMap<String, Producto> productos = new HashMap<>();

        while(true) {
            try {
                newP = (Producto)dis.readObject();
            } catch(EOFException e) {
                break;
            }

            productos.put(newP.getDescripcion(), newP);
        }

        return productos;

    }

    public HashMap<String, Empleado> cargarEmpleados() throws IOException, ClassNotFoundException {
        ObjectInputStream dis = new ObjectInputStream(new FileInputStream(new File("empleados.dat")));
        
        Empleado newE;
        HashMap<String, Empleado> empleados = new HashMap<>();

        while(true) {
            try {
                newE = (Empleado)dis.readObject();
            } catch(EOFException e) {
                break;
            }

            empleados.put(newE.getNombre(), newE);
        }

        return empleados;

    }

    //----------------------------------------------------------------------------------------------------------------------------------

    // Métodos para exportar archivos binarios --------------------------------------------------------------------------------------------
    //este método nos servirá para exportar la lista con productos
    public void exportarProductos(ArrayList<Producto> lista) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("productos.dat")));

        for(Producto producto : lista) {
            oos.writeObject(producto);
        } 

        //cerramos el archivo
        oos.close();
    }

    public void exportarProductos(HashMap<String, Producto> productos) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("productos.dat")));
            
        productos.forEach((key, value) -> {
			try {
				oos.writeObject(value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        //cerramos el archivo
        oos.close();
    }

    //este método nos servirá para exportar la lista con empleados
    public void exportarEmpleados(HashMap<String, Empleado> empleados) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("empleados.dat")));
            
        empleados.forEach((key, value) -> {
			try {
				oos.writeObject(value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        //cerramos el archivo
        oos.close();
    }

}
