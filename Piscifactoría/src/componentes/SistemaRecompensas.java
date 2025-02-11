package componentes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import simulador.Simulador;
import simulador.piscifactoria.Piscifactoria;
import simulador.piscifactoria.Piscifactoria.AlmacenComida;

import simulador.Simulador;
import simulador.Tanque;
import simulador.piscifactoria.Piscifactoria;
import simulador.piscifactoria.Piscifactoria.AlmacenComida;
import simulador.piscifactoria.PiscifactoriaMar;
import simulador.piscifactoria.PiscifactoriaRio;

/**
 * Clase que se encarga de gestionar las recompensas.
 */
public class SistemaRecompensas {

    /**
     * Genera la base de un XML de una recompensa.
     * 
     * @param nombre      Nombre de la recompensa.
     * @param origen      Origen de la recompensa representado por el código del
     *                    usuario.
     * @param descripcion Descripción de la recompensa.
     * @param raredad     Raredad de la recompensa.
     * @return Documento XML con la base de la recompensa.
     */
    private static Document generarBaseRecompensa(String nombre, String origen, String descripcion, int raredad) {
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
     * 
     * @param nivel  Nivel de la recompensa de algas.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaAlgas(int nivel, String origen) {
        switch (nivel) {
            case 1 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas I", origen,
                        "100 cápsulas de algas para alimentar peces filtradores y omnívoros.", 0);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae")
                        .addText("100");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/algas_1.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 2 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas II", origen,
                        "200 cápsulas de algas para alimentar peces filtradores y omnívoros.", 1);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae")
                        .addText("200");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/algas_2.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 3 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas III", origen,
                        "500 cápsulas de algas para alimentar peces filtradores y omnívoros.", 2);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae")
                        .addText("500");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/algas_3.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 4 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas IV", origen,
                        "1000 cápsulas de algas para alimentar peces filtradores y omnívoros.", 3);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae")
                        .addText("1000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/algas_4.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 5 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Algas V", origen,
                        "2000 cápsulas de algas para alimentar peces filtradores y omnívoros.", 4);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "algae")
                        .addText("2000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/algas_5.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de material de construcción del almacén central.
     * 
     * @param parte  Parte a la que pertenecen los metariales de construcción.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaAlmacenCentral(char parte, String origen) {
        switch (parte) {
            case 'A' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [A]", origen,
                        "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                        3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("A");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/almacen_a.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 'B' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [B]", origen,
                        "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                        3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("B");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/almacen_b.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 'C' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [C]", origen,
                        "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                        3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("C");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/almacen_c.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 'D' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Almacén central [D]", origen,
                        "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                        3);
                Element give = documentoBase.getRootElement().element("give");
                give.addElement("building").addAttribute("code", "4").addText("Almacén central");
                give.addElement("part").addText("D");
                give.addElement("total").addText("ABCD");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/almacen_d.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de comida general.
     * 
     * @param nivel  Nivel de la recompensa de comida general.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaComida(int nivel, String origen) {
        switch (nivel) {
            case 1 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general I", origen,
                        "50 unidades de pienso multipropósito para todo tipo de peces.", 0);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "general")
                        .addText("50");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/comida_1.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 2 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general II", origen,
                        "100 unidades de pienso multipropósito para todo tipo de peces.", 1);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "general")
                        .addText("100");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/comida_2.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 3 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general III", origen,
                        "250 unidades de pienso multipropósito para todo tipo de peces.", 2);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "general")
                        .addText("250");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/comida_3.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 4 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general IV", origen,
                        "500 unidades de pienso multipropósito para todo tipo de peces.", 3);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "general")
                        .addText("500");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/comida_4.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            case 5 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Comida general V", origen,
                        "1000 unidades de pienso multipropósito para todo tipo de peces.", 4);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "general")
                        .addText("1000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/comida_5.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de monedas.
     * 
     * @param nivel  Nivel de la recompensa de monedas a generar.
     * @param origen Origen de la recompensa de monedas representado por el código
     *               del usuario.
     */
    public static void generarRecompensaMonedas(int nivel, String origen) {
        switch (nivel) {
            case 1 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Monedas I", origen, "100 monedas",
                        0);
                documentoBase.getRootElement().element("give").addElement("coins").addText("100");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/monedas_1.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 2 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Monedas II", origen, "300 monedas",
                        1);
                documentoBase.getRootElement().element("give").addElement("coins").addText("300");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/monedas_2.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 3 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Monedas III", origen, "500 monedas",
                        2);
                documentoBase.getRootElement().element("give").addElement("coins").addText("500");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/monedas_3.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 4 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Monedas IV", origen, "750 monedas",
                        3);
                documentoBase.getRootElement().element("give").addElement("coins").addText("750");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/monedas_4.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 5 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Monedas v", origen, "1000 monedas",
                        4);
                documentoBase.getRootElement().element("give").addElement("coins").addText("1000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/monedas_5.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de pienso.
     * 
     * @param nivel  Nivel de la recompensa de pienso.
     * @param origen Origen de la recompensa de pienso representado por el código
     *               del usuario.
     */
    public static void generarRecompensaPienso(int nivel, String origen) {
        switch (nivel) {
            case 1 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Pienso de peces I", origen,
                        "100 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                        0);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "animal")
                        .addText("100");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pienso_1.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 2 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Pienso de peces II", origen,
                        "200 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                        1);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "animal")
                        .addText("200");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pienso_2.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 3 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Pienso de peces III", origen,
                        "500 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                        2);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "animal")
                        .addText("500");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pienso_3.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 4 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Pienso de peces IV", origen,
                        "1000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                        3);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "animal")
                        .addText("1000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pienso_4.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 5 -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Pienso de peces V", origen,
                        "2000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                        4);
                documentoBase.getRootElement().element("give").addElement("food").addAttribute("type", "animal")
                        .addText("2000");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pienso_5.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de piscifactoría de mar.
     * 
     * @param parte  Parte a la que pertenecen los materiales para la construcción.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaPiscifactoriaMar(char parte, String origen) {
        switch (parte) {
            case 'A' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Piscifactoría de mar [A]", origen,
                        "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.",
                        4);
                Element elementoGive = documentoBase.getRootElement().element("give");
                elementoGive.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
                elementoGive.addElement("part").addText("A");
                elementoGive.addElement("total").addText("AB");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pisci_m_a.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 'B' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Piscifactoría de mar [B]", origen,
                        "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.",
                        4);
                Element elementoGive = documentoBase.getRootElement().element("give");
                elementoGive.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
                elementoGive.addElement("part").addText("B");
                elementoGive.addElement("total").addText("AB");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pisci_m_b.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de piscifactoría de río.
     * 
     * @param parte  Parte a la que pertenecen los materiales para la construcción.
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public static void generarRecompensaPiscifactoriaRio(char parte, String origen) {
        switch (parte) {
            case 'A' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Piscifactoría de río [A]", origen,
                        "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.",
                        3);
                Element elementoGive = documentoBase.getRootElement().element("give");
                elementoGive.addElement("building").addAttribute("code", "0").addText("Piscifactoría de río");
                elementoGive.addElement("part").addText("A");
                elementoGive.addElement("total").addText("AB");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pisci_r_a.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }

            case 'B' -> {
                Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Piscifactoría de río [B]", origen,
                        "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.",
                        3);
                Element elementoGive = documentoBase.getRootElement().element("give");
                elementoGive.addElement("building").addAttribute("code", "0").addText("Piscifactoría de río");
                elementoGive.addElement("part").addText("B");
                elementoGive.addElement("total").addText("AB");

                XMLWriter escritorXML = null;

                try {
                    escritorXML = new XMLWriter(
                            new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("rewards/pisci_r_b.xml")), "UTF-8")),
                            OutputFormat.createPrettyPrint());
                    escritorXML.write(documentoBase);
                    escritorXML.flush();
                } catch (IOException e) {
                    System.out.println("Hubo un problema a la hora de crear la recompensa.");
                } finally {
                    if (escritorXML != null) {
                        try {
                            escritorXML.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }
    }

    /**
     * Genera una recompensa de tanque de mar.
     * 
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public void generarRecompensaTanqueMar(String origen) {
        Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Tanque de mar", origen,
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de mar.", 3);
        Element elementoGive = documentoBase.getRootElement().element("give");
        elementoGive.addElement("building").addAttribute("code", "3").addText("Tanque de mar");
        elementoGive.addElement("part").addText("A");
        elementoGive.addElement("total").addText("A");

        XMLWriter escritorXML = null;

        try {
            escritorXML = new XMLWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File("rewards/pisci_r_b.xml")), "UTF-8")),
                    OutputFormat.createPrettyPrint());
            escritorXML.write(documentoBase);
            escritorXML.flush();
        } catch (IOException e) {
            System.out.println("Hubo un problema a la hora de crear la recompensa.");
        } finally {
            if (escritorXML != null) {
                try {
                    escritorXML.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * Genera una recompensa de tanque de río.
     * 
     * @param origen Origen de la recompensa representado por el código del usuario.
     */
    public void generarRecompensaTanqueRio(String origen) {
        Document documentoBase = SistemaRecompensas.generarBaseRecompensa("Tanque de río", origen,
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de río.", 2);
        Element elementoGive = documentoBase.getRootElement().element("give");
        elementoGive.addElement("building").addAttribute("code", "2").addText("Tanque de río");
        elementoGive.addElement("part").addText("A");
        elementoGive.addElement("total").addText("A");

        XMLWriter escritorXML = null;

        try {
            escritorXML = new XMLWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File("rewards/pisci_r_b.xml")), "UTF-8")),
                    OutputFormat.createPrettyPrint());
            escritorXML.write(documentoBase);
            escritorXML.flush();
        } catch (IOException e) {
            System.out.println("Hubo un problema a la hora de crear la recompensa.");
        } finally {
            if (escritorXML != null) {
                try {
                    escritorXML.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * Permite obtener las recompensas disponibles para usar.
     * 
     * @return Array con las recompensas disponibles para usar.
     */
    public static String[] obtenerRecompensasDisponibles() {
        File[] archivos = new File("rewards").listFiles();
        ArrayList<File> recompensas = new ArrayList<>();
        ArrayList<String> recompensasDisponibles = new ArrayList<>();
        ArrayList<File> recompensasAlmacenCentral = new ArrayList<>();
        ArrayList<File> recompensasPiscifactoriaMar = new ArrayList<>();
        ArrayList<File> recompensasPiscifactoriaRio = new ArrayList<>();

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith(".xml")) {
                recompensas.add(archivo);
            }
        }

        try {
            SAXReader lectorXML = new SAXReader();
            Document xml;
            String nombreRecompensa;
            for (File recompensa : recompensas) {
                xml = lectorXML.read(recompensa);
                nombreRecompensa = xml.getRootElement().element("name").getText();

                if (nombreRecompensa.equals("Almacén central [A]") || nombreRecompensa.equals("Almacén central [B]")
                        || nombreRecompensa.equals("Almacén central [C]")
                        || nombreRecompensa.equals("Almacén central [D]")) {
                    recompensasAlmacenCentral.add(recompensa);
                } else {
                    if (nombreRecompensa.equals("Piscifactoría de mar [A]")
                            || nombreRecompensa.equals("Piscifactoría de mar [B]")) {
                        recompensasPiscifactoriaMar.add(recompensa);
                    } else {
                        if (nombreRecompensa.equals("Piscifactoría de río [A]")
                                || nombreRecompensa.equals("Piscifactoría de río [B]")) {
                            recompensasPiscifactoriaRio.add(recompensa);
                        } else {
                            recompensasDisponibles.add(nombreRecompensa);
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            System.out.println("No se ha podido leer el archivo XML.");
        }

        if (recompensasAlmacenCentral.size() == 4) {
            recompensasDisponibles.add("Almacén central");
        }

        if (recompensasPiscifactoriaMar.size() == 2) {
            recompensasDisponibles.add("Piscifactoría de mar");
        }

        if (recompensasPiscifactoriaRio.size() == 2) {
            recompensasDisponibles.add("Piscifactoría de río");
        }

        Collections.sort(recompensasDisponibles);

        String[] recompensasDisponiblesArray = new String[recompensasDisponibles.size()];

        return recompensasDisponibles.toArray(recompensasDisponiblesArray);
    }

    /**
     * Permite al usuario reclamar un recompensa seleccionada por el desde un menú.
     */
    public static void reclamarRecompensa(){
        File[] archivos = new File("rewards").listFiles();
        ArrayList<File> recompensas = new ArrayList<>();
        ArrayList<String> recompensasDisponibles = new ArrayList<>();
        ArrayList<File> recompensasAlmacenCentral = new ArrayList<>();
        ArrayList<File> recompensasPiscifactoriaMar = new ArrayList<>();
        ArrayList<File> recompensasPiscifactoriaRio = new ArrayList<>();

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith(".xml")) {
                recompensas.add(archivo);
            }
        }

        try {
            SAXReader lectorXML = new SAXReader();
            Document xml;
            String nombreRecompensa;
            for (File recompensa : recompensas) {
                xml = lectorXML.read(recompensa);
                nombreRecompensa = xml.getRootElement().element("name").getText();

                if (nombreRecompensa.equals("Almacén central [A]") || nombreRecompensa.equals("Almacén central [B]")
                        || nombreRecompensa.equals("Almacén central [C]")
                        || nombreRecompensa.equals("Almacén central [D]")) {
                    recompensasAlmacenCentral.add(recompensa);
                } else {
                    if (nombreRecompensa.equals("Piscifactoría de mar [A]")
                            || nombreRecompensa.equals("Piscifactoría de mar [B]")) {
                        recompensasPiscifactoriaMar.add(recompensa);
                    } else {
                        if (nombreRecompensa.equals("Piscifactoría de río [A]")
                                || nombreRecompensa.equals("Piscifactoría de río [B]")) {
                            recompensasPiscifactoriaRio.add(recompensa);
                        } else {
                            recompensasDisponibles.add(nombreRecompensa);
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            System.out.println("No se ha podido leer el archivo XML.");
        }

        if (recompensasAlmacenCentral.size() == 4) {
            recompensasDisponibles.add("Almacen central");
        }

        if (recompensasPiscifactoriaMar.size() == 2) {
            recompensasDisponibles.add("Piscifactoria de mar");
        }

        if (recompensasPiscifactoriaRio.size() == 2) {
            recompensasDisponibles.add("Piscifactoria de rio");
        }

        Collections.sort(recompensasDisponibles);

        String[] recompensasDisponiblesArray = new String[recompensasDisponibles.size()];

        int opcion = GeneradorMenus.generarMenuOperativo(recompensasDisponibles.toArray(recompensasDisponiblesArray), 1,
                recompensasDisponibles.size());

        String recompensaSeleccionada = recompensasDisponibles.get(opcion - 1);

        switch (recompensaSeleccionada) {
            case "Algas I" -> {
                reclamarRecompensaAlgas(1);
            }
            case "Algas II" -> {
                reclamarRecompensaAlgas(2);
            }
            case "Algas III" -> {
                reclamarRecompensaAlgas(3);
            }
            case "Algas IV" -> {
                reclamarRecompensaAlgas(4);
            }
            case "Algas V" -> {
                reclamarRecompensaAlgas(5);
            }
            case "Monedas I" -> {
                reclamarRecompensaMonedas(1);
            }
            case "Monedas II" -> {
                reclamarRecompensaMonedas(2);
            }
            case "Monedas III" -> {
                reclamarRecompensaMonedas(3);
            }
            case "Monedas IV" -> {
                reclamarRecompensaMonedas(4);
            }
            case "Monedas V" -> {
                reclamarRecompensaMonedas(5);
            }
            case "Comida I" -> {
                reclamarRecompensaComida(1);
            }
            case "Comida II" -> {
                reclamarRecompensaComida(2);
            }
            case "Comida III" -> {
                reclamarRecompensaComida(3);
            }
            case "Comida IV" -> {
                reclamarRecompensaComida(4);
            }
            case "Comida V" -> {
                reclamarRecompensaComida(5);
            }
            case "Pienso I" -> {
                reclamarRecompensaPienso(1);
            }
            case "Pienso II" -> {
                reclamarRecompensaPienso(2);
            }
            case "Pienso III" -> {
                reclamarRecompensaPienso(3);
            }
            case "Pienso IV" -> {
                reclamarRecompensaPienso(4);
            }
            case "Pienso V" -> {
                reclamarRecompensaPienso(5);
            }
            case "Tanque de rio" -> {
                reclamarRecompensaTanqueRio();
            }
            case "Tanque de mar" -> {
                reclamarRecompensaTanqueMar();
            }
            case "Piscifactoria de rio" -> {
                reclamarRecompensaPiscifactoriaRio();
            }
            case "Piscifactoria de mar" -> {
                reclamarRecompensaPiscifactoriaMar();
            }
            case "Almacen central" -> {
                reclamarRecompensaAlmacenCentral();
            }
        }
    }

    /**
     * Reduce la cantidad de una recompensa.
     * 
     * @param xmlRecompensa XML de la recompensa cargado en memoria.
     * @param recompensa    Archivo de la recompensa.
     */
    private static void reducirRecompensa(File recompensa) {
        Document xmlRecompensa = null;

        try {
            SAXReader lectorXML = new SAXReader();
            xmlRecompensa = lectorXML.read(recompensa);
        } catch (DocumentException e) {
            System.out.println("No se ha podido cargar el documento XML.");
        }

        int cantidadRecompensa = Integer.parseInt(xmlRecompensa.getRootElement().element("quantity").getText());

        if (cantidadRecompensa == 1) {
            recompensa.delete();
        } else {
            cantidadRecompensa--;
            xmlRecompensa.getRootElement().element("quantity").setText(Integer.toString(cantidadRecompensa));
            XMLWriter escritorXML = null;

            try {
                escritorXML = new XMLWriter(
                        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(recompensa), "UTF-8")),
                        OutputFormat.createPrettyPrint());
                escritorXML.write(xmlRecompensa);
                escritorXML.flush();
            } catch (IOException e) {
                System.out.println("No se ha podido reducir la cantidad de la recompensa.");
            } finally {
                if (escritorXML != null) {
                    try {
                        escritorXML.close();
                    } catch (IOException e) {

                    }
                }
            }
        }
    }

    /**
     * Reclama la recompensa de algas.
     * 
     * @param nivel Nivel de la recompensa a reclamar
     */
    private static void reclamarRecompensaAlgas(int nivel) {
        int capsulasAlgas = 0;

        switch (nivel) {
            case 1 -> {
                capsulasAlgas = 100;
            }
            case 2 -> {
                capsulasAlgas = 200;
            }
            case 3 -> {
                capsulasAlgas = 500;
            }
            case 4 -> {
                capsulasAlgas = 1000;
            }
            case 5 -> {
                capsulasAlgas = 2000;
            }
        }
        if (Simulador.simulador.almacenCentral.isDisponible()) {
            int cantidadComidaVegetal = Simulador.simulador.almacenCentral.getCantidadComidaVegetal();
            int cantidadMaximaComida = Simulador.simulador.almacenCentral.getCapacidadComida();

            if (cantidadMaximaComida - cantidadComidaVegetal > capsulasAlgas) {
                Simulador.simulador.almacenCentral.setCantidadComidaVegetal(cantidadComidaVegetal + capsulasAlgas);
            } else {
                Simulador.simulador.almacenCentral.setCantidadComidaVegetal(cantidadMaximaComida);
            }

            Simulador.simulador.repartirComida();
        } else {
            int comidaVegetalPiscifactoria;
            int espacioComidaVegetal;
            int cantidadMaximaVegetal;
            AlmacenComida almacenComida;

            for (Piscifactoria piscifactoria : Simulador.simulador.piscifactorias) {
                almacenComida = piscifactoria.getAlmacenInicial();
                comidaVegetalPiscifactoria = almacenComida.getCantidadComidaVegetal();
                cantidadMaximaVegetal = almacenComida.getCapacidadMaximaComida();
                espacioComidaVegetal = cantidadMaximaVegetal - comidaVegetalPiscifactoria;
                if (espacioComidaVegetal < capsulasAlgas) {
                    capsulasAlgas -= espacioComidaVegetal;
                    almacenComida.setCantidadComidaVegetal(cantidadMaximaVegetal);
                } else {
                    comidaVegetalPiscifactoria += capsulasAlgas;
                    capsulasAlgas = 0;
                    almacenComida.setCantidadComidaVegetal(comidaVegetalPiscifactoria);
                }
            }
        }
        reducirRecompensa(new File("rewards/algas_" + nivel + ".xml"));
    }

    /**
     * Reclama la recompensa de pienso.
     * 
     * @param nivel Nivel de la recompensa a reclamar
     */
    private static void reclamarRecompensaPienso(int nivel) {
        int capsulasPienso = 0;

        switch (nivel) {
            case 1 -> {
                capsulasPienso = 100;
            }
            case 2 -> {
                capsulasPienso = 200;
            }
            case 3 -> {
                capsulasPienso = 500;
            }
            case 4 -> {
                capsulasPienso = 1000;
            }
            case 5 -> {
                capsulasPienso = 2000;
            }
        }
        if (Simulador.simulador.almacenCentral.isDisponible()) {
            int cantidadcomidaAnimal = Simulador.simulador.almacenCentral.getCantidadComidaAnimal();
            int cantidadMaximaComida = Simulador.simulador.almacenCentral.getCapacidadComida();

            if (cantidadMaximaComida - cantidadcomidaAnimal > capsulasPienso) {
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(cantidadcomidaAnimal + capsulasPienso);
            } else {
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(cantidadMaximaComida);
            }

            Simulador.simulador.repartirComida();
        } else {
            int comidaAnimalPiscifactoria;
            int espaciocomidaAnimal;
            int cantidadMaximaAnimal;
            AlmacenComida almacenComida;

            for (Piscifactoria piscifactoria : Simulador.simulador.piscifactorias) {
                almacenComida = piscifactoria.getAlmacenInicial();
                comidaAnimalPiscifactoria = almacenComida.getCantidadComidaAnimal();
                cantidadMaximaAnimal = almacenComida.getCapacidadMaximaComida();
                espaciocomidaAnimal = cantidadMaximaAnimal - comidaAnimalPiscifactoria;
                if (espaciocomidaAnimal < capsulasPienso) {
                    capsulasPienso -= espaciocomidaAnimal;
                    almacenComida.setCantidadComidaAnimal(cantidadMaximaAnimal);
                } else {
                    comidaAnimalPiscifactoria += capsulasPienso;
                    capsulasPienso = 0;
                    almacenComida.setCantidadComidaAnimal(comidaAnimalPiscifactoria);
                }
            }
        }
        reducirRecompensa(new File("rewards/pienso_" + nivel + ".xml"));
    }

    /**
     * Reclama la recompensa de comida general.
     * 
     * @param nivel Nivel de la recompensa a reclamar
     */
    private static void reclamarRecompensaComida(int nivel) {
        int capsulasComida = 0;

        switch (nivel) {
            case 1 -> {
                capsulasComida = 50;
            }
            case 2 -> {
                capsulasComida = 100;
            }
            case 3 -> {
                capsulasComida = 250;
            }
            case 4 -> {
                capsulasComida = 500;
            }
            case 5 -> {
                capsulasComida = 1000;
            }
        }
        if (Simulador.simulador.almacenCentral.isDisponible()) {
            int cantidadComidaAnimal = Simulador.simulador.almacenCentral.getCantidadComidaAnimal();
            int cantidadComidaVegetal = Simulador.simulador.almacenCentral.getCantidadComidaVegetal();
            int cantidadMaximaComida = Simulador.simulador.almacenCentral.getCapacidadComida();

            if (cantidadMaximaComida - cantidadComidaAnimal > capsulasComida) {
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(cantidadComidaAnimal + capsulasComida);
            } else {
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(cantidadMaximaComida);
            }
            if (cantidadMaximaComida - cantidadComidaVegetal > capsulasComida) {
                Simulador.simulador.almacenCentral.setCantidadComidaAnimal(cantidadComidaVegetal + capsulasComida);
            } else {
                Simulador.simulador.almacenCentral.setCantidadComidaVegetal(cantidadMaximaComida);
            }

            Simulador.simulador.repartirComida();
        } else {
            int comidaAnimalPiscifactoria;
            int comidaVegetalPiscifactoria;
            int espacioComidaAnimal;
            int espacioComidaVegetal;
            int cantidadMaximaComida;
            AlmacenComida almacenComida;

            for (Piscifactoria piscifactoria : Simulador.simulador.piscifactorias) {
                almacenComida = piscifactoria.getAlmacenInicial();
                comidaAnimalPiscifactoria = almacenComida.getCantidadComidaAnimal();
                comidaVegetalPiscifactoria = almacenComida.getCantidadComidaVegetal();
                cantidadMaximaComida = almacenComida.getCapacidadMaximaComida();
                espacioComidaVegetal = cantidadMaximaComida - comidaVegetalPiscifactoria;
                espacioComidaAnimal = cantidadMaximaComida - comidaAnimalPiscifactoria;
                if (espacioComidaVegetal < capsulasComida) {
                    capsulasComida -= espacioComidaVegetal;
                    almacenComida.setCantidadComidaVegetal(cantidadMaximaComida);
                } else {
                    comidaVegetalPiscifactoria += capsulasComida;
                    capsulasComida = 0;
                    almacenComida.setCantidadComidaVegetal(comidaVegetalPiscifactoria);
                }
                if (espacioComidaAnimal < capsulasComida) {
                    capsulasComida -= espacioComidaAnimal;
                    almacenComida.setCantidadComidaAnimal(cantidadMaximaComida);
                } else {
                    comidaAnimalPiscifactoria += capsulasComida;
                    capsulasComida = 0;
                    almacenComida.setCantidadComidaAnimal(comidaAnimalPiscifactoria);
                }
            }
        }
        reducirRecompensa(new File("rewards/comida_" + nivel + ".xml"));
    }

    /**
     * Reclama la recompensa de monedas.
     * 
     * @param nivel Nivel de la recompensa a reclamar
     */
    private static void reclamarRecompensaMonedas(int nivel) {
        int capsulasMonedas = 0;

        switch (nivel) {
            case 1 -> {
                capsulasMonedas = 100;
            }
            case 2 -> {
                capsulasMonedas = 300;
            }
            case 3 -> {
                capsulasMonedas = 500;
            }
            case 4 -> {
                capsulasMonedas = 750;
            }
            case 5 -> {
                capsulasMonedas = 1000;
            }
        }
        int monedas = Simulador.simulador.sistemaMonedas.getMonedas();
        Simulador.simulador.sistemaMonedas.setMonedas(monedas + capsulasMonedas);

        reducirRecompensa(new File("rewards/monedas_" + nivel + ".xml"));
    }

    /**
     * Reclama la recompensa de tanque de mar.
     */
    private static void reclamarRecompensaTanqueMar() {
        int piscifactoriaSeleccionada = Simulador.simulador.selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = Simulador.simulador.piscifactorias.get(piscifactoriaSeleccionada - 1);
            if (piscifactoria instanceof PiscifactoriaMar) {
                int numeroTanques = piscifactoria.getTanques().size();
                if (numeroTanques < 25) {
                    piscifactoria.getTanques().add(new Tanque(numeroTanques + 1, 100));
                }
            }
        }
        reducirRecompensa(new File("rewards/tanque_m.xml"));
    }

    /**
     * Reclama la recompensa de tanque de rio.
     */
    private static void reclamarRecompensaTanqueRio() {
        int piscifactoriaSeleccionada = Simulador.simulador.selectPisc();

        if (piscifactoriaSeleccionada != 0) {
            Piscifactoria piscifactoria = Simulador.simulador.piscifactorias.get(piscifactoriaSeleccionada - 1);
            if (piscifactoria instanceof PiscifactoriaRio) {
                int numeroTanques = piscifactoria.getTanques().size();
                if (numeroTanques < 25) {
                    piscifactoria.getTanques().add(new Tanque(numeroTanques + 1, 25));
                }
            }
        }
        reducirRecompensa(new File("rewards/tanque_r.xml"));
    }

    /**
     * Reclama la recompensa de Piscifactoria de rio.
     */
    private static void reclamarRecompensaPiscifactoriaRio() {
        System.out.println("Introduzca el nombre de la piscifactoría: ");
        Simulador.simulador.piscifactorias.add(new PiscifactoriaRio(SistemaEntrada.entradaTexto()));

        reducirRecompensa(new File("rewards/pisci_r_a.xml"));
        reducirRecompensa(new File("rewards/pisci_r_b.xml"));
    }

    /**
     * Reclama la recompensa de Piscifactoria de mar.
     */
    private static void reclamarRecompensaPiscifactoriaMar() {
        System.out.println("Introduzca el nombre de la piscifactoría: ");
        Simulador.simulador.piscifactorias.add(new PiscifactoriaMar(SistemaEntrada.entradaTexto()));

        reducirRecompensa(new File("rewards/pisci_m_a.xml"));
        reducirRecompensa(new File("rewards/pisci_m_b.xml"));
    }

    /**
     * Reclama la recompensa de almacen central.
     */
    private static void reclamarRecompensaAlmacenCentral() {
        boolean disp = Simulador.simulador.almacenCentral.isDisponible();
        if (disp == true) {
            Simulador.simulador.almacenCentral.mejorar();
        } else {
            Simulador.simulador.almacenCentral.setDisponible(true);
        }
        reducirRecompensa(new File("rewards/almacen_a.xml"));
        reducirRecompensa(new File("rewards/almacen_b.xml"));
        reducirRecompensa(new File("rewards/almacen_c.xml"));
        reducirRecompensa(new File("rewards/almacen_d.xml"));
    }
}