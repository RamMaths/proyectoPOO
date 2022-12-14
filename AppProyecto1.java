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

        HashMap<String, Producto> productos = new HashMap<>();
        HashMap<String, Empleado> empleados = new HashMap<>();

        //paso 1.- cargamos el archivo de productos y el archivo de empleados

        try {
            productos = new AppProyecto1().cargarProductos();
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
        //
        String nombre = "";
        String clave = "";

        while(true) {
            nombre = JOptionPane.showInputDialog(null, "Identificate\nIngresa tu nombre");
            clave = JOptionPane.showInputDialog(null, "Ingresa tu clave");

            //este es un ejemplo del usuario administrador lo podemos sacar de un objeto empleado ya que los hayamos creado
            if(empleados.get(nombre)!=null && empleados.get(nombre).getClave().equals(clave)) {
                break;
            }else {
                JOptionPane.showMessageDialog(null, "Usuario no reconocido, porfavor ingrese los datos nuevamente");
            }
        }

        

        ArrayList<String> nombres = new ArrayList<>();
        
        productos.forEach((key, value) -> nombres.add(key));

        Object[] nombresA = nombres.toArray();


        ArrayList<Movimiento> movimientos = new ArrayList<>();
        int seguir = JOptionPane.showConfirmDialog(null, "Desea hacer un nuevo movimiento");
        while(seguir==0) {

            //paso 3.-el usuario elige, en un menú que muestra todos los productos creados
            String opc = (String)JOptionPane.showInputDialog(
                null,
                "Selecciona una opcion",
                "Tipo de figura",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nombresA,
                nombresA[0]);

            //paso 4.- ventana donde puede ingresar datos para crear un objeto Movimiento 
            try {
                movimientos.add(new AppProyecto1().crearMovimiento(productos.get(opc), empleados.get(nombre)));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "No se pudo crear el movimiento");
            }

            seguir = JOptionPane.showConfirmDialog(null, "Desea hacer un nuevo movimiento");
        }
        
        //paso 5.- checar que tipo de movimiento es y así añadirlos a las existencias o restarlos
        for(Movimiento mov : movimientos) {
        	if(mov.getTipo().equals("Ingreso")) {
        		mov.getProducto().ingreso(mov.getCambioUnidades());
        	}else {
        		mov.getProducto().egreso(mov.getCambioUnidades());
        	}
    
        }
        
        //paso 6.- preguntar al usuario de que tipo de porducto quiere un reporte final
        // preguntar por el stock final del producto en cuestión
            
        seguir = JOptionPane.showConfirmDialog(null, "Desea verificar algún producto");

        while(seguir==0) {
            String opc = (String)JOptionPane.showInputDialog(
                null,
                "Selecciona una opcion",
                "Tipo de producto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nombresA,
                nombresA[0]);

            int stockF = Integer.parseInt(JOptionPane.showInputDialog(null, "Cuál es el stock final de este producto"));

            productos.get(opc).setStockFinal(stockF);
            if(productos.get(opc).esCongruente()) {
                JOptionPane.showMessageDialog(null, "Los datos son congruentes");
            } else {
                JOptionPane.showMessageDialog(null, "Los datos no son congruentes por favor revise su inventario");
            }

            seguir = JOptionPane.showConfirmDialog(null, "Desea verificar algún producto");
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
        JLabel lblCambio = new JLabel("Cantidad: ");
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

    //-------------------------------------------------------------------------------------------------------------------------------------
}
