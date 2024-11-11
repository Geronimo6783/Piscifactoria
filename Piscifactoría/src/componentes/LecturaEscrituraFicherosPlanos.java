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
     * @throws IOException Cuando surge un error al escribir el texto o cuando se intenta cerrar el bufer de escritura.
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
}
