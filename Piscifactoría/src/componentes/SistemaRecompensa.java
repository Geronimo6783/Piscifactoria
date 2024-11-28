package componentes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SistemaRecompensa {
    public static void main(String[] args) {
        recompensas();
    }

    public static void recompensas() {
        XMLWriter writer = null;
        try {
            // intento de creacion de recompensa
            Document document = DocumentHelper.createDocument();
            Element rootElement = document.addElement("reward");
            //rootElement = document.getRootElement();
            Iterator<Element> itr = rootElement.elementIterator();
            while (itr.hasNext()) {
                Element elem = itr.next();
                Random r = new Random();
                int valor =1; //r.nextInt(7) + 1;
                switch (valor) {
                    case 1:
                        recompensaAlgae(document, rootElement);
                        break;
                    case 2:
                        recompensaAlmacen(document, rootElement);
                        break;
                    case 3:
                        recompensaComida(document, rootElement);
                        break;
                    case 4:
                        recompensaMonedas(document, rootElement);
                        break;
                    case 5:
                        recompensaPienso(document, rootElement);
                        break;
                    case 6:
                        recompensaPiscifac(document, rootElement);
                        break;
                    case 7:
                        recompensaTanque(document, rootElement);
                        break;
                    default:
                        break;
                }

                System.out.println("Recompensa: " + elem.getName());
                System.out.println("Descripción: " + elem.getText());

            }
            // deberia guardar
            writer = new XMLWriter(new FileWriter(new File("src/componentes/recompensas/ejemplo.xml")),
                    OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.flush();

        } catch (IOException e) {
            System.out.println("Falló");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaAlgae(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(5) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Algas I");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc")
                    .addText("100 cápsulas de algas para alimentar peces filtradores y omnívoros.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "algae").addText("100");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
            document.add(rootElement);
        }

        if (valor == 2) {
            rootElement.addElement("name").addText("Algas II");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc")
                    .addText("200 cápsulas de algas para alimentar peces filtradores y omnívoros.");
            rootElement.addElement("rarity").addText("1");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "algae").addText("200");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 3) {
            rootElement.addElement("name").addText("Algas III");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc")
                    .addText("500 cápsulas de algas para alimentar peces filtradores y omnívoros.");
            rootElement.addElement("rarity").addText("2");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "algae").addText("500");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Algas IV");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc")
                    .addText("1000 cápsulas de algas para alimentar peces filtradores y omnívoros.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "algae").addText("1000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 5) {
            rootElement.addElement("name").addText("Algas V");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc")
                    .addText("2000 cápsulas de algas para alimentar peces filtradores y omnívoros.");
            rootElement.addElement("rarity").addText("4");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "algae").addText("2000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }  

    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaAlmacen(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(4) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Almacen Central [A]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
            rootElement2.addElement("part").addText("A");
            rootElement2.addElement("total").addText("ABCD");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 2) {
            rootElement.addElement("name").addText("Almacen Central [B]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
            rootElement2.addElement("part").addText("B");
            rootElement2.addElement("total").addText("ABCD");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 3) {
            rootElement.addElement("name").addText("Almacen Central [C]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
            rootElement2.addElement("part").addText("C");
            rootElement2.addElement("total").addText("ABCD");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Almacen Central [D]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
            rootElement2.addElement("part").addText("D");
            rootElement2.addElement("total").addText("ABCD");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }


    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaComida(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(5) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Comida general I");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("50 unidades de pienso multipropósito para todo tipo de peces.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "general").addText("50");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
        if (valor == 2) {
            rootElement.addElement("name").addText("Comida general II");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("100 unidades de pienso multipropósito para todo tipo de peces.");
            rootElement.addElement("rarity").addText("1");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "general").addText("100");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 3) {
            rootElement.addElement("name").addText("Comida general III");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("250 unidades de pienso multipropósito para todo tipo de peces.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "general").addText("250");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Comida general IV");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("500 unidades de pienso multipropósito para todo tipo de peces.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "general").addText("500");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 1) {
            rootElement.addElement("name").addText("Comida general V");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("1000 unidades de pienso multipropósito para todo tipo de peces.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "general").addText("1000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }


    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaMonedas(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(5) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Monedas I");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("100 monedas");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("coins").addText("100");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 2) {
            rootElement.addElement("name").addText("Monedas II");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("300 monedas");
            rootElement.addElement("rarity").addText("1");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("coins").addText("300");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 3) {
            rootElement.addElement("name").addText("Monedas III");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("500 monedas");
            rootElement.addElement("rarity").addText("2");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("coins").addText("500");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Monedas IV");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("750 monedas");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("coins").addText("750");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
        if (valor == 5) {
            rootElement.addElement("name").addText("Monedas V");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText("1000 monedas");
            rootElement.addElement("rarity").addText("4");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("coins").addText("1000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }

    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaPienso(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(5) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Pienso de peces I");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "100 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "animal").addText("100");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
        if (valor == 2) {
            rootElement.addElement("name").addText("Pienso de peces II");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "200 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
            rootElement.addElement("rarity").addText("1");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "animal").addText("200");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
        if (valor == 3) {
            rootElement.addElement("name").addText("Pienso de peces III");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "500 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
            rootElement.addElement("rarity").addText("2");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "animal").addText("500");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Pienso de peces IV");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "1000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "animal").addText("1000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 5) {
            rootElement.addElement("name").addText("Pienso de peces V");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "2000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
            rootElement.addElement("rarity").addText("0");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("food").addAttribute("type", "animal").addText("2000");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }

    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaPiscifac(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(4) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Piscifactoría de mar [A]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.");
            rootElement.addElement("rarity").addText("4");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
            rootElement2.addElement("part").addText("A");
            rootElement2.addElement("total").addText("AB");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 2) {
            rootElement.addElement("name").addText("Piscifactoría de mar [B]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.");
            rootElement.addElement("rarity").addText("4");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
            rootElement2.addElement("part").addText("B");
            rootElement2.addElement("total").addText("AB");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 3) {
            rootElement.addElement("name").addText("Piscifactoría de río [A]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "0").addText("Piscifactoría de río");
            rootElement2.addElement("part").addText("A");
            rootElement2.addElement("total").addText("AB");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 4) {
            rootElement.addElement("name").addText("Piscifactoría de río [A]");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "0").addText("Piscifactoría de río");
            rootElement2.addElement("part").addText("B");
            rootElement2.addElement("total").addText("AB");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }

    /**
     * 
     * @param document
     * @param rootElement
     */
    public static void recompensaTanque(Document document, Element rootElement) {
        // intento de creacion de recompensa
        Random r = new Random();
        int valor = r.nextInt(2) + 1;
        if (valor == 1) {
            rootElement.addElement("name").addText("Tanque de mar");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de mar.");
            rootElement.addElement("rarity").addText("3");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "3").addText("Tanque de mar");
            rootElement2.addElement("part").addText("A");
            rootElement2.addElement("total").addText("A");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }

        if (valor == 2) {
            rootElement.addElement("name").addText("Tanque de río");
            rootElement.addElement("origin").addText("Sistema");
            rootElement.addElement("desc").addText(
                    "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de río.");
            rootElement.addElement("rarity").addText("2");

            Element rootElement2 = document.addElement("give");
            rootElement2.addElement("building").addAttribute("code", "2").addText("Tanque de mar");
            rootElement2.addElement("part").addText("A");
            rootElement2.addElement("total").addText("A");
            Element rootElement3 = document.addElement("quantity");
            rootElement3.addText("1");
            rootElement.add(rootElement2);
            rootElement.add(rootElement3);
        }
    }
}