package componentes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import simulador.Simulador;

import java.io.*;

/**
 * Clase para gestionar la lectura y escritura de ficheros JSON.
 */
public class LecturaEscrituraJSON {

    /**
     * Constructor de JSON en un formato amigable para el ser humano.
     */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Guarda un objeto en un archivo JSON.
     *
     * @param archivo Archivo donde se guardará el objeto JSON.
     * @param objeto  Objeto a guardar.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    public static <T> void guardarJSON(File archivo, T objeto) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            gson.toJson(objeto, writer);
        }
    }

    /**
     * Carga un objeto desde un archivo JSON.
     *
     * @param archivo Archivo JSON desde donde se cargará el objeto.
     * @param <T> Tipo del objeto a cargar.
     * @param Class<T> Clase del objeto que se quiere cargar.
     * @return El objeto cargado.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static <T> T cargarJSON(File archivo) throws IOException {
        if (!archivo.exists()) {
            throw new IOException("El archivo no existe: " + archivo.getPath());
        }

        JsonReader lectorJson = null;
        T objeto = null;

        try{
            lectorJson = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "UTF-8")));
            objeto = (T) new Gson().fromJson(lectorJson, Simulador.class);
        }
        catch(IOException e){
            throw e;
        }
        finally{
            try{
                lectorJson.close();
            }
            catch(IOException e){
                throw e;
            }
        }

        return objeto;
    }
}
