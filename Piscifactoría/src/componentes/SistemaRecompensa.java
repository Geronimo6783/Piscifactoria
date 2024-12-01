package componentes;

import java.io.*;
import java.util.Random;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SistemaRecompensa {
    private static final String directorio = "src/componentes/recompensas";
    private final Random random = new Random();

    public SistemaRecompensa() {
        // Crear directorio de recompensas si no existe
        File dir = new File(directorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Crear o actualizar una recompensa
    public void createReward(String type, String name, String origin, String desc, int rarity, String subtype, int cantidad, int cantidadInicial, String parte, String total) {
        File rewardFile = new File(directorio, name + ".xml");

        try {
            if (rewardFile.exists()) {
                // Actualizar cantidad si existe
                Document doc = new SAXReader().read(rewardFile);
                Element root = doc.getRootElement();
                Element cantidadElement = root.element("quantity");
                int cantidadActual = Integer.parseInt(cantidadElement.getText());
                cantidadElement.setText(String.valueOf(cantidadActual + cantidadInicial));
                saveDocument(doc, rewardFile);
                System.out.println("Recompensa " + name + " actualizada. Nueva cantidad: " + (cantidadActual + cantidadInicial));
            } else {
                // Crear nueva recompensa
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
                saveDocument(doc, rewardFile);
                System.out.println("Recompensa " + name + " creada.");
            }
        } catch (Exception e) {
            System.out.println("Error al manejar recompensa: " + e.getMessage());
        }
    }

    // Agregar una recompensa aleatoria
    public void addRandomReward() {
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
                String piscifactoriaMarDesc = "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.";
                String[] piscifactoriaMarPartes = {"A", "B"};

                int valorAleatorio = random.nextInt(piscifactoriaMarNames.length);
                createReward("building", piscifactoriaMarNames[valorAleatorio], "Adrián", piscifactoriaMarDesc, 4, "1", 1, 1, piscifactoriaMarPartes[valorAleatorio], "AB");

            }
            case 6 -> {
                String[] piscifactoriaRioNames = {"Piscifactoría de río [A]", "Piscifactoría de río [B]"};
                String piscifactoriaRioDesc = "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.";
                String[] piscifactoriaRioPartes = {"A", "B"};

                int valorAleatorio = random.nextInt(piscifactoriaRioNames.length);
                createReward("building", piscifactoriaRioNames[valorAleatorio], "Adrián", piscifactoriaRioDesc, 3, "0", 1, 1, piscifactoriaRioPartes[valorAleatorio], "AB");

            }
            case 7 -> {
                String[] tanqueNames = {"Tanque de mar", "Tanque de río"};
                String[] tanqueDescriptions = {
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de mar.",
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de río."
                };
                int[] tanqueRarities = {3, 2};
                String[] tanqueCodes = {"3", "2"};

                int valorAleatorio = random.nextInt(tanqueNames.length);
                createReward("building", tanqueNames[valorAleatorio], "Adrián", tanqueDescriptions[valorAleatorio], tanqueRarities[valorAleatorio], tanqueCodes[valorAleatorio], 1, 1, "A", "A");
            }
        }
    }

    // Guardar documento XML
    private void saveDocument(Document doc, File file) throws IOException {
        XMLWriter writer = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());
        writer.write(doc);
        writer.close();
    }

    // Main para pruebas
    public static void main(String[] args) {
        SistemaRecompensa manager = new SistemaRecompensa();

        // Generar recompensa aleatoria
        manager.addRandomReward();
    }
}
