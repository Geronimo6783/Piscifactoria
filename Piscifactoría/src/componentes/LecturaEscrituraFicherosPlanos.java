package componentes;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Clase con utilidades para la lectura y escritura de ficheros en texto plano.
 */
public class LecturaEscrituraFicherosPlanos {

    /**
     * Permite la escritura en un fichero de texto plano.
     * @param archivo Archivo donde se escribirá el texto.
     * @param texto Texto a escribir.
     * @param coficacion Codificación del texto a escribir.
     * @throws IOException Cuando surge un error al escribir el texto o cuando surge un error al cerrar el bufer de escritura.
     */
    public static void escrituraFicheroTextoPlano(File archivo, String texto, String coficacion) throws IOException{
        BufferedWriter buferEscritura = null;

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), coficacion));
            buferEscritura.write(texto);
            buferEscritura.flush();
        }
        catch(IOException e){
            throw e;
        }
        finally{
            if(buferEscritura != null){
                try{
                    buferEscritura.close();
                }
                catch(IOException e){
                    throw e;
                }
            }
        }
    }

    /**
     * Permite la escritura en un fichero de texto plano.
     * @param archivo Archivo donde se escribirá el texto.
     * @param texto Texto a escribir.
     * @param coficacion Codificación del texto a escribir.
     * @throws IOException Cuando surge un error al escribir el texto o cuando surge un error al cerrar el bufer de escritura.
     */
    public static void escrituraFicheroTextoPlanoSinSobreescritura(File archivo, String texto, String coficacion) throws IOException{
        BufferedWriter buferEscritura = null;
        String textoArchivo = lecturaFicheroTextoPlano(archivo, coficacion);

        try{
            buferEscritura = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), coficacion));
            buferEscritura.write(textoArchivo);
            buferEscritura.flush();
            buferEscritura.write(texto);
            buferEscritura.flush();
        }
        catch(IOException e){
            throw e;
        }
        finally{
            if(buferEscritura != null){
                try{
                    buferEscritura.close();
                }
                catch(IOException e){
                    throw e;
                }
            }
        }
    }

    /**
     * Permite la lectura de un fichero de texto plano.
     * @param archivo Archivo de texto plano del cual se lee el texto.
     * @param codificacion Codificación del texto del archivo.
     * @return Texto que contiene el archivo de texto plano.
     * @throws IOException Cuando surge un error al leer el texto o al cerrar el búfer de lectura.
     */
    public static String lecturaFicheroTextoPlano(File archivo, String codificacion) throws IOException{
        BufferedReader buferLectura = null;
        StringBuilder contructorString = new StringBuilder();

        try{
            buferLectura = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), codificacion));
            String cadenaDeTextoLeida = buferLectura.readLine();
            while(cadenaDeTextoLeida != null){
                contructorString.append(cadenaDeTextoLeida + "\n");
                cadenaDeTextoLeida = buferLectura.readLine();
            }
        }
        catch(IOException e){
            throw e;
        }
        finally{
            if(buferLectura != null){
                try{
                    buferLectura.close();
                }
                catch(IOException e){
                    throw e;
                }
            }
        }

        return contructorString.toString();
    }
}
