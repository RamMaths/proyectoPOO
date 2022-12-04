package proyectoPOO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.EOFException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.HashMap;

public class AppProyecto1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        // creamos productos previo a la ejecución real para así tener una base con --------------------------
        // la que trabajar por si el usuario no ingresa productos---------------------------------------------

        ArrayList<Producto> listaProd = new ArrayList<>();
        // int seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar un producto");
        // while(seguir==0) {
        //     try{
        //         listaProd.add(new AppProyecto1().crearProducto());
        //     } catch (IOException e) {
        //         JOptionPane.showMessageDialog(null, "No se pudo crear el producto");
        //     }

        //     seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar otro producto");
        // }

        // try {
        //     new AppProyecto1().exportarProductos(listaProd);
        // } catch (IOException e) {
        //     JOptionPane.showMessageDialog(null, "Error de escritura");
        // }

        HashMap<String, Empleado> empleados = new HashMap<>();

        int seguir = JOptionPane.showConfirmDialog(null, "Desea ingresar un empleado");
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
        
        // --------------------------------------------------------------------------------------------------

        //paso 1.- cargamos el archivo de productos y el archivo de empleados

        try {
            listaProd = new AppProyecto1().cargarProductos();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error de lectura");
        } catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clases no compatibles");
        }

        try {
            empleados = new AppProyecto1().cargarEmpleados();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error de lectura");
        } catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clases no compatibles");
        }

        empleados.forEach((key, value) -> System.out.println(value.toString()));

        //paso 2.- el usuario se identifica con sus datos personales

        while(true) {
            String nombre = JOptionPane.showInputDialog(null, "Identificate\nIngresa tu nombre");
            String clave = JOptionPane.showInputDialog(null, "Ingresa tu clave");

            //este es un ejemplo del usuario administrador lo podemos sacar de un objeto empleado ya que los hayamos creado
            if(!nombre.equals("Pedro") || !clave.equals("123")) {
                JOptionPane.showMessageDialog(null, "Usuario no reconocido, porfavor ingrese los datos nuevamente");
            }else {
                break;
            }
        }

        //paso 3.-el usuario elige, en un menú que muestra todos los productos creados
        
        String nombres[] = new String[listaProd.size()];
        for(int i=0;i<listaProd.size();i++) {
            nombres[i] = listaProd.get(i).getDescripcion();
        }

        String opc = (String)JOptionPane.showInputDialog(
            null,
            "Selecciona una opcion",
            "Tipo de figura",
            JOptionPane.PLAIN_MESSAGE,
            null,
            nombres,
            nombres[0]);

        //paso 4.- ventana donde puede ingresar datos para crear un objeto Movimiento 

        //borramos el archivo y lo volvemos a escribir por si el usuario añadió un nuevo producto
        File archivo = new File("productos.dat");
        archivo.delete();
        try {
            new AppProyecto1().exportarProductos(listaProd);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de escritura");
        }
	}

    // métodos para crear objetos ----------------------------------------------------------------------------------------------

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

    //este método sirve para crear un movimiento (noten que este método regresa un movimiento)
    public Movimiento crearMovimiento(Producto producto, Empleado empleado) throws IOException {

        //leer datos del teclado y esciribilos al archivo
        JLabel lblFecha = new JLabel("Fecha: ");
        JTextField txtFecha = new JTextField();
        JLabel lblTipo = new JLabel("Tipo: ");
        JTextField txtTipo = new JTextField();
        JLabel lblCambio = new JLabel("Cambio: ");
        JTextField txtCambio = new JTextField();

        Object[] formato = {lblFecha, txtFecha, lblTipo, txtTipo, lblCambio, txtCambio};

        JOptionPane.showMessageDialog(null, formato);

        return new Movimiento(txtFecha.getText(),
                empleado,
                producto,
                txtTipo.getText(),
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

    //-------------------------------------------------------------------------------------------------------------------------------------
}
