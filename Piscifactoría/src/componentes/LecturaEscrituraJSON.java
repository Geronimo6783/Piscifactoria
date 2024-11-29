package componentes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase para gestionar la lectura y escritura de ficheros JSON.
 */
public class LecturaEscrituraJSON {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Guarda un objeto en un archivo JSON.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo JSON.
     * @param objeto      Objeto a guardar.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    public static void guardarJSON(String rutaArchivo, Object objeto) throws IOException {
        
        if (!SistemaFicheros.existeArhivo(rutaArchivo)) {
            SistemaFicheros.crearArchivo(rutaArchivo);
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(objeto, writer);
            System.out.println("Datos guardados correctamente en: " + rutaArchivo);
        }
    }

    /**
     * Carga un objeto desde un archivo JSON.
     *
     * @param rutaArchivo Ruta del archivo JSON.
     * @param clase       Clase del objeto a cargar.
     * @param <T>         Tipo del objeto a cargar.
     * @return El objeto cargado.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static <T> T cargarJSON(String rutaArchivo, Class<T> clase) throws IOException {
        
        if (!SistemaFicheros.existeArhivo(rutaArchivo)) {
            throw new IOException("El archivo no existe: " + rutaArchivo);
        }

        try (FileReader reader = new FileReader(rutaArchivo)) {
            return gson.fromJson(reader, clase);
        }
    }
}
