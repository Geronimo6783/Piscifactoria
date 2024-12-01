package componentes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Clase para gestionar la lectura y escritura de ficheros JSON.
 */
public class LecturaEscrituraJSON {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Guarda un objeto en un archivo JSON.
     *
     * @param archivo Archivo donde se guardará el objeto JSON.
     * @param objeto  Objeto a guardar.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    public static void guardarJSON(File archivo, Object objeto) throws IOException {
        if (!archivo.exists()) {
            SistemaFicheros.crearArchivo(archivo.getPath());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            gson.toJson(objeto, writer);
            System.out.println("Datos guardados correctamente en: " + archivo.getPath());
        }
    }

    /**
     * Carga un objeto desde un archivo JSON.
     *
     * @param archivo Archivo JSON desde donde se cargará el objeto.
     * @param clase   Clase del objeto a cargar.
     * @param <T>     Tipo del objeto a cargar.
     * @return El objeto cargado.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static <T> T cargarJSON(File archivo, Class<T> clase) throws IOException {
        if (!archivo.exists()) {
            throw new IOException("El archivo no existe: " + archivo.getPath());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            return gson.fromJson(reader, clase);
        }
    }
}
