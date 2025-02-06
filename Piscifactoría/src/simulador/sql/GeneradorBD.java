package simulador.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import simulador.sql.dto.DTOCliente;
import simulador.sql.dto.DTOPez;

import propiedades.AlmacenPropiedades;

/**
 * Clase encarga de crear la base de datos y insertar los datos iniciales en ella.
 */
public class GeneradorBD {

    /**
     * Conexión con la base de datos.
     */
    private static Connection conexion = Conexion.getConexion();

    /**
     * Método que se encarga de crear tablas.
     */
    public static void crearTablas(){
        Statement sentencia = null;

        try{
            sentencia = conexion.createStatement();
            sentencia.execute("CREATE TABLE IF NOT EXISTS Cliente(id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), nif CHAR(9), telefono CHAR(12));");
            sentencia.execute("CREATE TABLE IF NOT EXISTS Pez(id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), nombre_cientifico VARCHAR(255))");
            sentencia.execute("CREATE TABLE IF NOT EXISTS Pedido(Numero_referencia INT PRIMARY KEY AUTO_INCREMENT, fk_id_cliente INT, fk_id_pez INT, peces_solicitados INT, peces_enviados INT, FOREIGN KEY (fk_id_cliente) REFERENCES Cliente(id), FOREIGN KEY (fk_id_pez) REFERENCES Pez(id));");
        }
        catch(SQLException e){
            System.out.println("Hubo un problema al generar las tablas.");
        }
        finally{
            if(sentencia != null){
                try{
                    sentencia.close();
                }
                catch(SQLException e){

                }
            }
        }
    }

    /**
     * Permite saber si un cliente existe con anterioridad en la base de datos.
     * @param nombre Nombre del cliente.
     * @param nif Nif del cliente.
     * @param telefono Teléfono del cliente.
     * @return Si el cliente existe o no en la base de datos.
     */
    private static boolean existeCliente(String nombre, String nif, String telefono){
        ArrayList<DTOCliente> clientesBaseDeDatos = new DAOPedidos(conexion).obtenerClientes();

        for(DTOCliente cliente : clientesBaseDeDatos){
            if(cliente.getNombre().equals(nombre) && cliente.getNif().equals(nif) && cliente.getTelefono().equals(telefono)){
                return true;
            }
        }

        return false;
    }

    /**
     * Inserta 10 clientes a la base de datos.
     */
    public static void insertarClientes(){
        PreparedStatement sentencia = null;

        try{
            sentencia = conexion.prepareStatement("INSERT INTO Cliente (nombre,nif,telefono) VALUES (?,?,?)");
            String[] nombres = {"Luis", "Martín", "Laura", "Carmen", "Sofía", "Noelia", "Martina", "Martín", "José", "Santiago"};
            String[] nifs = {"45324254V", "39021234D", "94823245H", "90127389A", "45632434C", "78982354D", "34533212T", "67542342J", "80898932C", "23435412E"};
            String[] telefonos = {"+34689345893", "+34905896567", "+34567890879", "+34967847534", "+34124567890", "+34789045045", "+34567098789", "+34678456098", "+34234567098", "+34789678345"};

            for(int i = 0; i < nombres.length; i++){
                if(!existeCliente(nombres[i], nifs[i], telefonos[i])){
                    sentencia.setString(1, nombres[i]);
                    sentencia.setString(2, nifs[i]);
                    sentencia.setString(3, telefonos[i]);
                    sentencia.addBatch();
                }
            }

            sentencia.executeBatch();
        }
        catch(SQLException e){
            System.out.println("Hubo un problema al insertar los datos en la base de datos.");
        }
        finally{
            if(sentencia != null){
                try{
                    sentencia.close();
                }
                catch(SQLException e){

                }
            }
        }
    }

    /**
     * Permite saber si un pez existe con anterioridad en la base de datos.
     * @param nombre Nombre del pez.
     * @param nombreCientifico Nombre científico del pez.
     * @return Si el pez existe o no en la base de datos.
     */
    private static boolean existePez(String nombre, String nombreCientifico){
        ArrayList<DTOPez> pecesBaseDeDatos = new DAOPedidos(conexion).obtenerPeces();

        for(DTOPez pez : pecesBaseDeDatos){
            if(pez.getNombre().equals(nombre) && pez.getNombreCientifico().equals(nombreCientifico)){
                return true;
            }
        }

        return false;
    }

    /**
     * Inserta los datos de los peces implementados en la base de datos.
     */
    public static void insertarPeces(){
        PreparedStatement sentencia = null;

        try{
            sentencia = conexion.prepareStatement("INSERT INTO Pez (nombre,nombre_cientifico) VALUES (?,?)");
            String[] nombres = new String[]{ AlmacenPropiedades.ABADEJO.getNombre(),AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(), 
            AlmacenPropiedades.CABALLA.getNombre(),AlmacenPropiedades.CARPIN_TRES_ESPINAS.getNombre(), AlmacenPropiedades.DORADA.getNombre(),
            AlmacenPropiedades.PEJERREY.getNombre(),AlmacenPropiedades.PERCA_EUROPEA.getNombre(), AlmacenPropiedades.ROBALO.getNombre(),AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
            AlmacenPropiedades.SALMON_CHINOOK.getNombre(), AlmacenPropiedades.SARGO.getNombre(), AlmacenPropiedades.TILAPIA_NILO.getNombre()};
            String[] nombresCientificos = new String[]{ AlmacenPropiedades.ABADEJO.getCientifico(),AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(), 
            AlmacenPropiedades.CABALLA.getCientifico(),AlmacenPropiedades.CARPIN_TRES_ESPINAS.getCientifico(), AlmacenPropiedades.DORADA.getCientifico(),
            AlmacenPropiedades.PEJERREY.getCientifico(),AlmacenPropiedades.PERCA_EUROPEA.getCientifico(), AlmacenPropiedades.ROBALO.getCientifico(),AlmacenPropiedades.SALMON_ATLANTICO.getCientifico(),
            AlmacenPropiedades.SALMON_CHINOOK.getCientifico(), AlmacenPropiedades.SARGO.getCientifico(), AlmacenPropiedades.TILAPIA_NILO.getCientifico()};

            for(int i = 0; i < nombres.length; i++){
                if(!existePez(nombres[i], nombresCientificos[i])){
                    sentencia.setString(1, nombres[i]);
                    sentencia.setString(2, nombresCientificos[i]);
                    sentencia.addBatch();
                }
            }

            sentencia.executeBatch();
        }
        catch(SQLException e){
            System.out.println("Hubo un problema al insertar los datos en la base de datos.");
        }
        finally{
            if(sentencia != null){
                try{
                    sentencia.close();
                }
                catch(SQLException e){

                }
            }
        }
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public static void close(){
        try{
            conexion.close();
        }
        catch(SQLException e){

        }
    }

    public static void main(String[] args) {
        GeneradorBD.crearTablas();
        GeneradorBD.insertarClientes();
        GeneradorBD.insertarPeces();
        GeneradorBD.close();
    }
}
