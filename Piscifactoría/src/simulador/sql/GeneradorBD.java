package simulador.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            sentencia.execute("CREATE TABLE IF NOT EXISTS Cliente(id BIGINT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), nif CHAR(9), telefono CHAR(12));");
            sentencia.execute("CREATE TABLE IF NOT EXISTS Pez(id BIGINT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), nombre_cientifico VARCHAR(255))");
            sentencia.execute("CREATE TABLE IF NOT EXISTS Pedido(fk_id_cliente BIGINT, fk_id_pez BIGINT, peces_solicitadoS BIGINT, peces_enviados BIGINT, FOREIGN KEY (fk_id_cliente) REFERENCES Cliente(id), FOREIGN KEY (fk_id_pez) REFERENCES Pez(id));");
        }
        catch(SQLException e){

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

    public static void main(String[] args) {
        GeneradorBD.crearTablas();
        try{
            GeneradorBD.conexion.close();
        }
        catch(SQLException e){

        }
    }
}
