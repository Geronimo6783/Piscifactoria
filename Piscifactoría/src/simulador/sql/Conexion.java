package simulador.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import componentes.Logs;

/**
 * Clase que se encarga de abrir y cerrar la conexión con la base de datos.
 */
public class Conexion {

    /**
     * Usuario de la base de datos.
     */
    private static final String USER = "administrador_piscifactoria";
    
    /**
     * Contraseña del usuairo de la base de datos.
     */
    private static final String PASSWORD = "yu40/&23?Pl";
    
    /**
     * Servidor del sistema gestor de base de datos.
     */
    private static final String SERVER = "ldominguez.iescotarelo.es";
    
    /**
     * Puerto del sistema gestor de base de datos.
     */
    private static final int PORT = 3306;
    
    /**
     * Base de datos a la que se conecta.
     */
    private static final String BD = "Piscifactoria";
    
    /**
     * Conexión de la base de datos.
     */
    private static Connection conexion = null;

    /**
     * Permite obtener la conexión de la base de datos.
     * @return Conexión de la base de datos.
     */
    public static Connection getConexion(){
        if(conexion == null){
            Properties propiedadesConexion = new Properties();
            propiedadesConexion.put("user", USER);
            propiedadesConexion.put("password", PASSWORD);
            try{
                conexion = DriverManager.getConnection("jdbc:mysql://" + SERVER + ":" + PORT + "/" + BD + "?rewriteBatchedStatements=true", propiedadesConexion);
            }
            catch(SQLException e){
                Logs.escribirError("No se pudo establecer la conexión con la base de datos.");
            }
        }
        
        return conexion;
    }
    
    /**
     * Cierra la conexión a la base de datos.
     */
    public static void close(){
        if(conexion != null){
            try{
                conexion.close();
            }
            catch(SQLException e){
                Logs.escribirError("Hubo un problema al intentar cerrar la conexión a la base de datos.");
            }
        }
    }
}
