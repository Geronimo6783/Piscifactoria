package componentes;

import java.io.File;
import java.io.IOException;

/**
 * Clase con utilidades relativas al sistema de ficheros.
 */
public class SistemaFicheros {

    /**
     * Crear un archivo en el sistema de ficheros.
     * @param rutaArchivo Ruta del archivo a crear en el sistema de ficheros.
     * @throws IOException Cuando no se puede crear el archivo en el sistema de ficheros.
     */
    public static void crearArchivo(String rutaArchivo) throws IOException{
        File archivoACrear = new File(rutaArchivo);

        if(!archivoACrear.createNewFile()){
            throw new IOException("No se ha podido crear el archivo en el sistema de ficheros.");
        }
    }

    /**
     * Crea una carpeta en el sistema de ficheros.
     * @param rutaCarpeta Ruta de la carpeta a crear.
     * @throws IOException Cuando no se puede crear la carpeta en el sistema de ficheros.
     */
    public static void crearCarpeta(String rutaCarpeta) throws IOException{
        File carpetaACrear = new File(rutaCarpeta);
        
        if(!carpetaACrear.mkdir()){
            throw new IOException("No se ha podido crear la carpeta en el sistema de ficheros.");
        }
    }

    /**
     * Crea una estructura de carpetas en el sistema de ficheros.
     * @param estructuraCarpetas Ruta de la estructura de carpetas a crear.
     * @throws IOException Cuando no se puede crear la estructura de carpetas en el sistema de ficheros.
     */
    public static void crearEstructuraCarpetas(String estructuraCarpetas) throws IOException{
        File estructuraCarpetasACrear = new File(estructuraCarpetas);

        if(!estructuraCarpetasACrear.mkdirs()){
            throw new IOException("No se ha podido crear la estructura de carpetas en el sistema de ficheros.");
        }
    }

    /**
     * Permite comprobar la existencia de un archivo.
     * @param rutaArchivo Ruta del archivo a comprobar su existencia.
     * @return Si el archivo existe o no.
     * @throws IOException Cuando el archivo del que se comprueba la existencia es un directorio.
     */
    public static boolean existeArhivo(String rutaArchivo) throws IOException{
        File archivo = new File(rutaArchivo);

        if(archivo.exists()){
            if(archivo.isFile()){
                return true;
            }
            else{
                throw new IOException("No se trata de un archivo si no de un directorio.");
            }
        }
        else{
            return false;
        }
    }

    /**
     * Permite comprobar la existencia de un directorio.
     * @param rutaDirectorio Ruta del directorio a comprobar su existencia.
     * @return Si existe el directorio o no.
     * @throws IOException Cuando el directorio del que se comprueba la existencia es un archivo.
     */
    public static boolean existeDirectorio(String rutaDirectorio) throws IOException{
        File directorio = new File(rutaDirectorio);

        if(directorio.exists()){
            if(directorio.isDirectory()){
                return true;
            }
            else{
                throw new IOException("No se trata de un directorio si no de un archivo.");
            }
        }
        else{
            return false;
        }
    }

    /**
     * Indica si un directorio está vacío.
     * @param rutaDirectorio Ruta del directorio del que se quiere comprobar que está vacío.
     * @return Si el directorio está vacío o no.
     * @throws IOException Si el directorio no existe o se trata de un fichero.
     */
    public static boolean isDirectorioVacio(String rutaDirectorio) throws IOException{
        if(existeDirectorio(rutaDirectorio)){
            File directorio = new File(rutaDirectorio);
                
            if(directorio.listFiles().length == 0){
                return true;
            }
            else{
                return false;
            }   
        }
        else{
            throw new IOException("El directorio no existe.");
        }
    }

    /**
     * Borra un archivo del sistema de ficheros.
     * @param rutaArchivo Ruta del archivo a borrar.
     * @throws IOException Cuando el archivo no ha podido ser eliminado o el archivo no existe.
     */
    public static void borrarArchivo(String rutaArchivo) throws IOException{
        if(existeArhivo(rutaArchivo)){
            File archivo = new File(rutaArchivo);
            if(!archivo.delete()){
                throw new IOException("El archivo no ha podido ser eliminado.");
            }
        }
        else{
            throw new IOException("El archivo a eliminar no existe.");
        }
    }
}
