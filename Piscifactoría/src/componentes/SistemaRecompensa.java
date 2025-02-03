package componentes;

import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Clase que se encarga de la creación y gestión del sistema de recompensas.
 */
public class SistemaRecompensa {

    /**
     * Directorio donde se almacenan las recompensas.
     */
    private static final String directorio = "rewards";

    /**
     * Objeto para la generación de números aleatorios.
     */
    private static final Random random = new Random();

    /**
     * Crea o actualiza una recompensa.
     * @param type Tipo de la recompensa.
     * @param name Nombre de la recompensa.
     * @param origin Origen de la recompensa.
     * @param desc Descripción de la recompensa.
     * @param rarity Raredad de la recompensa.
     * @param subtype Subtipo de la recompensa.
     * @param cantidad Cantidad de recompensa.
     * @param cantidadInicial Cantidad de recompensas que hay.
     * @param parte
     * @param total 
     */
    public static void createReward(String type, String name, String origin, String desc, int rarity, String subtype, int cantidad, int cantidadInicial, String parte, String total) throws IOException{
        File archivoDeRecompensa = new File(directorio, name + ".xml");

        try {
            if (SistemaFicheros.existeArhivo(archivoDeRecompensa.getAbsolutePath())) {
                Document doc = new SAXReader().read(archivoDeRecompensa);
                Element root = doc.getRootElement();
                Element cantidadElement = root.element("quantity");
                int cantidadActual = Integer.parseInt(cantidadElement.getText());
                cantidadElement.setText(String.valueOf(cantidadActual + cantidadInicial));
                saveDocument(doc, archivoDeRecompensa);
                System.out.println("Recompensa " + name + " actualizada. Nueva cantidad: " + (cantidadActual + cantidadInicial));
            } 
            else {
                Document doc = DocumentHelper.createDocument();
                Element root = doc.addElement("reward");

                root.addElement("name").setText(name);
                root.addElement("origin").setText(origin);
                root.addElement("desc").setText(desc);
                root.addElement("rarity").setText(String.valueOf(rarity));

                Element give = root.addElement("give");
                switch (type) {
                    case "food":
                        give.addElement("food").addAttribute("type", subtype).setText(String.valueOf(cantidad));
                        break;
                    case "coins":
                        give.addElement("coins").setText(String.valueOf(cantidad));
                        break;
                    case "building":
                        give.addElement("building").addAttribute("code", subtype).setText(desc);
                        if (parte != null && total != null) {
                            give.addElement("part").setText(parte);
                            give.addElement("total").setText(total);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de recompensa no válido: " + type);
                }

                root.addElement("quantity").setText(String.valueOf(cantidadInicial));
                saveDocument(doc, archivoDeRecompensa);
                System.out.println("Recompensa " + name + " creada.");
            }
        } catch (Exception e) {
            throw new IOException("Error al generar la recompensa.");
        }
    }

    // Agregar una recompensa aleatoria
    public static void addRandomReward() throws IOException{
        int rewardType = random.nextInt(8); // 0: food, 1: coins, 2: building-part, 3: building-other
        switch (rewardType) {
            case 0 -> {
                String[] foodNames = {"Algas I", "Algas II", "Algas III", "Algas IV", "Algas V"};
                String[] foodDescriptions = {
                    "100 cápsulas de algas para alimentar peces filtradores y omnívoros.",
                    "200 cápsulas de algas para alimentar peces filtradores y omnívoros.",
                    "500 cápsulas de algas para alimentar peces filtradores y omnívoros.",
                    "1000 cápsulas de algas para alimentar peces filtradores y omnívoros.",
                    "2000 cápsulas de algas para alimentar peces filtradores y omnívoros."
                };
                String[] foodTypes = {"algae", "algae", "algae", "algae", "algae"};
                int[] Cantidades = {100, 200, 500, 1000, 2000};
                int[] foodRarities = {0, 1, 2, 3, 4};

                int valorAleatorio = random.nextInt(foodNames.length);
                createReward("food", foodNames[valorAleatorio], "Adrián", foodDescriptions[valorAleatorio], foodRarities[valorAleatorio], foodTypes[valorAleatorio], Cantidades[valorAleatorio], 1, null, null);
            }
            case 1 -> {
                String[] coinNames = {"Monedas I", "Monedas II", "Monedas III", "Monedas IV", "Monedas V"};
                String[] coinDescriptions = {"100 monedas", "300 monedas", "500 monedas", "7500 monedas", "1000 monedas"};
                int[] Cantidades = {100, 300, 500, 750, 1000};
                int[] coinRarities = {0, 1, 2, 3, 4};

                int valorAleatorio = random.nextInt(coinNames.length);
                createReward("coins", coinNames[valorAleatorio], "Adrián", coinDescriptions[valorAleatorio], coinRarities[valorAleatorio], null, Cantidades[valorAleatorio], 1, null, null);
            }
            case 2 -> {
                String[] parteNames = {"Almacén central [A]", "Almacén central [B]", "Almacén central [C]", "Almacén central [D]"};
                String[] parteDescriptions = {
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.",
                    "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita."
                };
                String[] parteCodes = {"A", "B", "C", "D"};
                int[] parteRarities = {3, 3, 3, 3};

                int valorAleatorio = random.nextInt(parteNames.length);
                createReward("building", parteNames[valorAleatorio], "Adrián", parteDescriptions[valorAleatorio], parteRarities[valorAleatorio], "4", 1, 1, parteCodes[valorAleatorio], "ABCD");
            }
            case 3 -> {
                String[] foodNames = {"Comida general I", "Comida general II", "Comida general III", "Comida general IV", "Comida general V"};
                String[] foodDescriptions = {
                    "50 unidades de pienso multipropósito para todo tipo de peces.",
                    "100 unidades de pienso multipropósito para todo tipo de peces.",
                    "250 unidades de pienso multipropósito para todo tipo de peces.",
                    "500 unidades de pienso multipropósito para todo tipo de peces.",
                    "1000 unidades de pienso multipropósito para todo tipo de peces."
                };
                int[] Cantidades = {50, 100, 250, 500, 1000};
                int[] foodRarities = {0, 1, 2, 3, 4};

                int valorAleatorio = random.nextInt(foodNames.length);
                createReward("food", foodNames[valorAleatorio], "Adrián", foodDescriptions[valorAleatorio], foodRarities[valorAleatorio], "general", Cantidades[valorAleatorio], 1, null, null);

            }
            case 4 -> {
                String[] piensoNames = {"Pienso de peces I", "Pienso de peces II", "Pienso de peces III", "Pienso de peces IV", "Pienso de peces V"};
                String[] piensoDescriptions = {
                    "100 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                    "200 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                    "500 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                    "1000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.",
                    "2000 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros."
                };
                int[] Cantidades = {100, 200, 500, 1000, 2000};
                int[] piensoRarities = {0, 1, 2, 3, 4};

                int valorAleatorio = random.nextInt(piensoNames.length);
                createReward("food", piensoNames[valorAleatorio], "Adrián", piensoDescriptions[valorAleatorio], piensoRarities[valorAleatorio], "animal", Cantidades[valorAleatorio], 1, null, null);
            }
            case 5 -> {
                String[] piscifactoriaMarNames = {"Piscifactoría de mar [A]", "Piscifactoría de mar [B]"};
                String piscifactoriaMarDesc = "Materiales para la construcción de una piscifactoria de mar. Con la parte A y B, puedes obtenerla de forma gratuita.";
                String[] piscifactoriaMarPartes = {"A", "B"};

                int valorAleatorio = random.nextInt(piscifactoriaMarNames.length);
                createReward("building", piscifactoriaMarNames[valorAleatorio], "Adrián", piscifactoriaMarDesc, 4, "1", 1, 1, piscifactoriaMarPartes[valorAleatorio], "AB");

            }
            case 6 -> {
                String[] piscifactoriaRioNames = {"Piscifactoría de río [A]", "Piscifactoría de río [B]"};
                String piscifactoriaRioDesc = "Materiales para la construcción de una piscifactoria de rio. Con la parte A y B, puedes obtenerla de forma gratuita.";
                String[] piscifactoriaRioPartes = {"A", "B"};

                int valorAleatorio = random.nextInt(piscifactoriaRioNames.length);
                createReward("building", piscifactoriaRioNames[valorAleatorio], "Adrián", piscifactoriaRioDesc, 3, "0", 1, 1, piscifactoriaRioPartes[valorAleatorio], "AB");

            }
            case 7 -> {
                String[] tanqueNames = {"Tanque de mar", "Tanque de río"};
                String[] tanqueDescriptions = {
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoria de mar.",
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoria de rio."
                };
                int[] tanqueRarities = {3, 2};
                String[] tanqueCodes = {"3", "2"};

                int valorAleatorio = random.nextInt(tanqueNames.length);
                createReward("building", tanqueNames[valorAleatorio], "Adrián", tanqueDescriptions[valorAleatorio], tanqueRarities[valorAleatorio], tanqueCodes[valorAleatorio], 1, 1, "A", "A");
            }
        }
    }

    /**
     * Guarda un documento XML cargado en memoria en el sistema de ficheros.
     * @param doc Documento XML cargado en memoria.
     * @param file Archivo en el que se guardará el XML en el sistema de ficheros.
     * @throws IOException Cuando no se ha podido guardar el XML o cerrar el búfer de escritura.
     */
    public static void saveDocument(Document doc, File file) throws IOException {
        XMLWriter writer = null;

        try{
            writer = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.flush();
        }
        catch(IOException e){
            throw e;
        }
        finally{
            if(writer != null){
                try{
                    writer.close();
                }
                catch(IOException e){
                    throw e;
                }
            }
        }
    }

    // Borra un documento XML
    public static void deleteDocument(String file){
        // Crear una referencia al archivo
        File referencia = new File(directorio + File.separator + file);

        // Verificar si el archivo existe y eliminarlo
        if (referencia.exists() && referencia.isFile()) {
            if (referencia.delete()) {
                System.out.println("Archivo eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe o no es válido.");
        }
    }

    // Lista las recompensas que se encuentra en la carpeta "rewards"
   public static void listarRecompensas() {
        File carpeta = new File(directorio);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {
            for (String nombreArchivo : listado) {
                // Buscar la posición del último punto
                int indiceUltimoPunto = nombreArchivo.lastIndexOf('.');
                // Si hay un punto, extraer el nombre antes del punto, si no, usar el nombre completo
                String nombreSinExtension = (indiceUltimoPunto > 0) 
                                            ? nombreArchivo.substring(0, indiceUltimoPunto) 
                                            : nombreArchivo;
                System.out.println(nombreSinExtension);
            }
        }
    }

    // Lista las recompensas que se pueden redimir
    public static void listarRecompensasDisponibles() {
        File carpeta = new File(directorio);
        String[] listado = carpeta.list();
    
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        }
    
        // Sets para rastrear las versiones encontradas
        Set<String> piscifactoriaDeMar = new HashSet<>();
        Set<String> piscifactoriaDeRio = new HashSet<>();
        Set<String> almacenCentral = new HashSet<>();
    
        // Conjunto de nombres específicos
        Set<String> nombresEspecificos = Set.of(
            "Piscifactoria de mar [A]", "Piscifactoria de mar [B]",
            "Piscifactoria de rio [A]", "Piscifactoria de rio [B]",
            "Almacen central [A]", "Almacen central [B]",
            "Almacen central [C]", "Almacen central [D]"
        );
    
        // Procesar cada archivo
        for (String nombreArchivo : listado) {
            if (nombresEspecificos.contains(nombreArchivo)) {
                // Clasificar los archivos específicos
                switch (nombreArchivo) {
                    case "Piscifactoria de mar [A]": piscifactoriaDeMar.add("A"); break;
                    case "Piscifactoria de mar [B]": piscifactoriaDeMar.add("B"); break;
                    case "Piscifactoria de rio [A]": piscifactoriaDeRio.add("A"); break;
                    case "Piscifactoria de rio [B]": piscifactoriaDeRio.add("B"); break;
                    case "Almacen central [A]": almacenCentral.add("A"); break;
                    case "Almacen central [B]": almacenCentral.add("B"); break;
                    case "Almacen central [C]": almacenCentral.add("C"); break;
                    case "Almacen central [D]": almacenCentral.add("D"); break;
                }
            } else {
                // Imprimir solo los nombres que no son específicos
                int indiceUltimoPunto = nombreArchivo.lastIndexOf('.');
                String nombreSinExtension = (indiceUltimoPunto > 0)
                    ? nombreArchivo.substring(0, indiceUltimoPunto)
                    : nombreArchivo;
                System.out.println(nombreSinExtension);
            }
        }
    
        // Verificar si se cumplen las condiciones para cada conjunto
        boolean piscifactoriaDeMarCompleta = piscifactoriaDeMar.contains("A") && piscifactoriaDeMar.contains("B");
        boolean piscifactoriaDeRioCompleta = piscifactoriaDeRio.contains("A") && piscifactoriaDeRio.contains("B");
        boolean almacenCentralCompleto = almacenCentral.contains("A") && almacenCentral.contains("B")
            && almacenCentral.contains("C") && almacenCentral.contains("D");
    
        // Mostrar resultados finales según las condiciones
        if (piscifactoriaDeMarCompleta) {
            System.out.println("Piscifactoria de mar disponible");
        } else {
            System.out.println("Piscifactoria de mar: Faltan versiones");
        }
    
        if (piscifactoriaDeRioCompleta) {
            System.out.println("Piscifactoria de rio disponible");
        } else {
            System.out.println("Piscifactoria de rio: Faltan versiones");
        }
    
        if (almacenCentralCompleto) {
            System.out.println("Almacen central disponible");
        } else {
            System.out.println("Almacen central: Faltan versiones");
        }
    }
}
