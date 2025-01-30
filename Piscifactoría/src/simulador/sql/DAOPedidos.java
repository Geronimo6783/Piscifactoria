package simulador.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import simulador.sql.dto.DTOCliente;
import simulador.sql.dto.DTOPedido;
import simulador.sql.dto.DTOPez;

/**
 * Clase DAO (Data Access Object) de la base de datos Piscifactoria.
 */
public class DAOPedidos {

    /**
     * Conexión con la base de datos.
     */
    private Connection conexion;

    /**
     * Consulta que obtiene todos los clientes de la base de datos.
     */
    PreparedStatement consultaClientes;

    /**
     * Consulta que obtiene todos los peces de la base de datos.
     */
    PreparedStatement consultaPeces;

    /**
     * Consulta que obtiene todos los pedidos de la base de datos.
     */
    PreparedStatement consultaPedidos;

    /**
     * Constructor parametrizado.
     * @param conexion Conexión con la base de datos.
     */
    public DAOPedidos(Connection conexion){
        this.conexion = conexion;
        try{
            consultaClientes = conexion.prepareStatement("SELECT * FROM Cliente;");
            consultaPeces = conexion.prepareStatement("SELECT * FROM Pez;");
            consultaPedidos = conexion.prepareStatement("SELECT * FROM Pedido;");
        }
        catch(SQLException e){
            System.out.println("No se han podido generar las consultar a la base de datos.");
        }
    }

    /**
     * Realiza una consulta en la que se obtienen todos los clientes en la base de datos.
     * @return Clientes en la base de datos.
     */
    public ArrayList<DTOCliente> obtenerClientes(){
        ResultSet resultadoConsulta = null;
        ArrayList<DTOCliente> clientes = new ArrayList<>();

        try{
            resultadoConsulta = consultaClientes.executeQuery();

            while(resultadoConsulta.next()){
                clientes.add(new DTOCliente(resultadoConsulta.getInt(1), resultadoConsulta.getString(2), resultadoConsulta.getString(3), resultadoConsulta.getString(4)));
            }
        }
        catch(SQLException e){
            System.out.println("No se ha podido realizar la consulta a la base de datos.");
        }
        finally{
            if(resultadoConsulta != null){
                try{
                    resultadoConsulta.close();
                }
                catch(SQLException e){
                    
                }
            }
        }

        return clientes;
    }

    /**
     * Realiza una consulta en la que se obtienen todos los peces en la base de datos.
     * @return Peces en la base de datos.
     */
    public ArrayList<DTOPez> obtenerPeces(){
        ResultSet resultadoConsulta = null;
        ArrayList<DTOPez> peces = new ArrayList<>();

        try{
            resultadoConsulta = consultaPeces.executeQuery();

            while(resultadoConsulta.next()){
                peces.add(new DTOPez(resultadoConsulta.getInt(1), resultadoConsulta.getString(2), resultadoConsulta.getString(3)));
            }
        }
        catch(SQLException e){
            System.out.println("No se ha podido realizar la consulta a la base de datos.");
        }
        finally{
            if(resultadoConsulta != null){
                try{
                    resultadoConsulta.close();
                }
                catch(SQLException e){

                }
            }
        }

        return peces;
    }

    /**
     * Realiza una consulta en la que se obtienen todos los pedidos en la base de datos.
     * @return Pedidos en la base de datos.
     */
    public ArrayList<DTOPedido> obtenerPedidos(){
        ResultSet resultadoConsulta = null;
        ArrayList<DTOPedido> pedidos = new ArrayList<>();

        try{
            resultadoConsulta = consultaPedidos.executeQuery();

            while(resultadoConsulta.next()){
                pedidos.add(new DTOPedido(resultadoConsulta.getInt(1),resultadoConsulta.getInt(2), resultadoConsulta.getInt(3), resultadoConsulta.getInt(4)));
            }
        }
        catch(SQLException e){
            System.out.println("No se ha podido realizar la consulta a la base de datos.");
        }
        finally{
            if(resultadoConsulta != null){
                try{
                    resultadoConsulta.close();
                }
                catch(SQLException e){

                }
            }
        }
        
        return pedidos;
    }

    /**
     * Cierra la conexión a la base de datos y las consultas preparadas.
     */
    public void close(){
        try{
            consultaClientes.close();
        }
        catch(SQLException e){

        }

        try{
            consultaPeces.close();
        }
        catch(SQLException e){

        }

        try{
            consultaPedidos.close();
        }
        catch(SQLException e){
            
        }

        try{
            conexion.close();
        }
        catch(SQLException e){

        }
    }
}
