package componentes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * Clase que se encarga de gestionar las recompensas.
 */
public class SistemaRecompensas {

    /**
     * Genera la base de un XML de una recompensa.
     * @param nombre Nombre de la recompensa.
     * @param origen Origen de la recompensa representado por el código del usuario.
     * @param descripcion Descripción de la recompensa.
     * @param raredad Raredad de la recompensa.
     * @return Documento XML con la base de la recompensa.
     */
    private static Document generarBaseRecompensa(String nombre, String origen, String descripcion, int raredad){
        Document baseRecompensa = DocumentHelper.createDocument();
        Element elementoRaiz = baseRecompensa.addElement("reward");
        elementoRaiz.addElement("name").addText(nombre);
        elementoRaiz.addElement("origin").addText(origen);
        elementoRaiz.addElement("desc").addText(descripcion);
        elementoRaiz.addElement("rarity").addText(Integer.toString(raredad));
        elementoRaiz.addElement("give");
        elementoRaiz.addElement("quantity").addText("1");
        return baseRecompensa;
    }

    /**
     * Genera una recompensa de algas.
     * @param nivel Nivel de la recompensa de algas.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaAlgas(int nivel, String origen){
        switch(nivel){
            case 1 -> {Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas I", origen, "100 cápsulas de algas para alimentar peces filtradores y omnívoros.", 0);
                       documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae").addText("100");
                       
                       XMLWriter escritorXML = null;
                    
                       try{
                        escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/algas_1.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                        escritorXML.write(documentoBase);
                        escritorXML.flush();
                       }
                       catch(IOException e){
                        System.out.println("Hubo un problema a la hora de crear la recompensa.");
                       }
                       finally{
                        if(escritorXML != null){
                            try{
                                escritorXML.close();
                            }
                            catch(IOException e){


                            }
                        }
                       }
                    }
            case 2 -> {Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas II", origen, "200 cápsulas de algas para alimentar peces filtradores y omnívoros.", 1);
                       documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae").addText("200");
                       
                       XMLWriter escritorXML = null;
                    
                       try{
                        escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/algas_2.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                        escritorXML.write(documentoBase);
                        escritorXML.flush();
                       }
                       catch(IOException e){
                        System.out.println("Hubo un problema a la hora de crear la recompensa.");
                       }
                       finally{
                        if(escritorXML != null){
                            try{
                                escritorXML.close();
                            }
                            catch(IOException e){


                            }
                        }
                       }
                    }
            case 3 -> {Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas III", origen, "500 cápsulas de algas para alimentar peces filtradores y omnívoros.", 2);
                       documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae").addText("500");
                       
                       XMLWriter escritorXML = null;
                    
                       try{
                        escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/algas_3.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                        escritorXML.write(documentoBase);
                        escritorXML.flush();
                       }
                       catch(IOException e){
                        System.out.println("Hubo un problema a la hora de crear la recompensa.");
                       }
                       finally{
                        if(escritorXML != null){
                            try{
                                escritorXML.close();
                            }
                            catch(IOException e){


                            }
                        }
                       }
                    }
            case 4 -> {Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas IV", origen, "1000 cápsulas de algas para alimentar peces filtradores y omnívoros.", 3);
                       documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae").addText("1000");
                       
                       XMLWriter escritorXML = null;
                    
                       try{
                        escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/algas_4.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                        escritorXML.write(documentoBase);
                        escritorXML.flush();
                       }
                       catch(IOException e){
                        System.out.println("Hubo un problema a la hora de crear la recompensa.");
                       }
                       finally{
                        if(escritorXML != null){
                            try{
                                escritorXML.close();
                            }
                            catch(IOException e){


                            }
                        }
                       }
                    }
            case 5 -> {Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas V", origen, "2000 cápsulas de algas para alimentar peces filtradores y omnívoros.", 4);
                       documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae").addText("2000");
                       
                       XMLWriter escritorXML = null;
                    
                       try{
                        escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/algas_5.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                        escritorXML.write(documentoBase);
                        escritorXML.flush();
                       }
                       catch(IOException e){
                        System.out.println("Hubo un problema a la hora de crear la recompensa.");
                       }
                       finally{
                        if(escritorXML != null){
                            try{
                                escritorXML.close();
                            }
                            catch(IOException e){

                            }
                        }
                       }
                    }
        }
    }

    /**
     * Genera una recompensa de material de construcción del almacén central.
     * @param parte Parte a la que pertenecen los metariales de construcción.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaAlmacenCentral(char parte, String origen){
        switch(parte){
            case 'A' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [A]", origen, "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.", 3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("A");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/almacen_a.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){

                        }
                    }
                }
            }
            case 'B' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [B]", origen, "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.", 3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("B");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/almacen_b.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){

                        }
                    }
                }
            }
            case 'C' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [C]", origen, "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.", 3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("C");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/almacen_c.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){

                        }
                    }
                }
            }
            case 'D' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [D]", origen, "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.", 3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("D");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/almacen_d.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de comida general.
     * @param nivel Nivel de la recompensa de comida general.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaComida(int nivel, String origen){
        switch(nivel){
            case 1 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general I", origen, "50 unidades de pienso multipropósito para todo tipo de peces.", 0);
                documentoBase.addElement("food").addAttribute("type", "general").addText("50");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/comida_1.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){

                        }
                    }
                }
            }
            case 2 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general II", origen, "100 unidades de pienso multipropósito para todo tipo de peces.", 1);
                documentoBase.addElement("food").addAttribute("type", "general").addText("100");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/comida_2.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){
                            
                        }
                    }
                }
            }
            case 3 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general III", origen, "250 unidades de pienso multipropósito para todo tipo de peces.", 2);
                documentoBase.addElement("food").addAttribute("type", "general").addText("250");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/comida_3.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){
                            
                        }
                    }
                }
            }
            case 4 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general IV", origen, "500 unidades de pienso multipropósito para todo tipo de peces.", 3);
                documentoBase.addElement("food").addAttribute("type", "general").addText("500");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/comida_4.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){
                            
                        }
                    }
                }
            }
            case 5 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general V", origen, "1000 unidades de pienso multipropósito para todo tipo de peces.", 4);
                documentoBase.addElement("food").addAttribute("type", "general").addText("1000");

                XMLWriter escritorXML = null;

                try{
                    escritorXML = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("rewards/comida_5.xml")), "UTF-8")), OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                }
                catch(IOException e){
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                }
                finally{
                    if(escritorXML != null){
                        try{
                            escritorXML.close();
                        }
                        catch(IOException e){
                            
                        }
                    }
                }
            }   
        }
    }
}
