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

public class SistemaRecompensa{
    public void recompensas() {
    XMLWriter writer=null;
    try{
        //intento de creacion de recompensa
        Document document= DocumentHelper.createDocument();
        Element rootElement=document.addElement("reward");
        rootElement=document.getRootElement();
        Iterator<Element> itr=rootElement.elementIterator();
        while(itr.hasNext()){
            Element elem=itr.next();
            
            recompensaAlgae(document,rootElement);

            System.out.println("Recompensa: "+elem.getName());
            System.out.println("Descripción: "+elem.getText());
            
        }
        //deberia guardar
        writer=new XMLWriter(new FileWriter(new File("src/componentes/recompensas")),OutputFormat.createPrettyPrint());
        writer.write(document);
        writer.flush();

    }catch (IOException e){
        System.out.println("Falló");
        e.printStackTrace();
    }
    }

    public void recompensaAlgae(Document document,Element rootElement){
            //intento de creacion de recompensa 
                Random r=new Random();
                int valor=r.nextInt(6);
                if(valor==1){
                    rootElement.addElement("name").addText("Algas I");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("100 cápsulas de algas para alimentar peces filtradores y omnívoros.");
                    rootElement.addElement("rarity").addText("0");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "algae").addText("100");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }

                if(valor==2){
                    rootElement.addElement("name").addText("Algas II");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("200 cápsulas de algas para alimentar peces filtradores y omnívoros.");
                    rootElement.addElement("rarity").addText("1");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "algae").addText("200");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }
    
                if(valor==3){
                    rootElement.addElement("name").addText("Algas III");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("500 cápsulas de algas para alimentar peces filtradores y omnívoros.");
                    rootElement.addElement("rarity").addText("2");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "algae").addText("500");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }

                if(valor==4){
                    rootElement.addElement("name").addText("Algas IV");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("1000 cápsulas de algas para alimentar peces filtradores y omnívoros.");
                    rootElement.addElement("rarity").addText("3");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "algae").addText("1000");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }

                if(valor==5){
                    rootElement.addElement("name").addText("Algas V");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("2000 cápsulas de algas para alimentar peces filtradores y omnívoros.");
                    rootElement.addElement("rarity").addText("4");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "algae").addText("2000");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }   
        }

        public void recompensaAlmacen(Document document,Element rootElement){
            //intento de creacion de recompensa 
            Random r=new Random();
            int valor=r.nextInt(5);
                if(valor==1){
                    rootElement.addElement("name").addText("Almacen Central [A]");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
                    rootElement.addElement("rarity").addText("3");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
                    rootElement2.addElement("part").addText("A");
                    rootElement2.addElement("total").addText("ABCD");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }

                if(valor==2){
                    rootElement.addElement("name").addText("Almacen Central [B]");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
                    rootElement.addElement("rarity").addText("3");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
                    rootElement2.addElement("part").addText("B");
                    rootElement2.addElement("total").addText("ABCD");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }
    
                if(valor==3){
                    rootElement.addElement("name").addText("Almacen Central [C]");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
                    rootElement.addElement("rarity").addText("3");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
                    rootElement2.addElement("part").addText("C");
                    rootElement2.addElement("total").addText("ABCD");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }

                if(valor==4){
                    rootElement.addElement("name").addText("Almacen Central [D]");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
                    rootElement.addElement("rarity").addText("3");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("building").addAttribute("code", "4").addText("Almacén central");
                    rootElement2.addElement("part").addText("D");
                    rootElement2.addElement("total").addText("ABCD");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }   
        }

        public void recompensaComida(Document document,Element rootElement){
            //intento de creacion de recompensa 
            Random r=new Random();
            int valor=r.nextInt(5);
                if(valor==1){
                    rootElement.addElement("name").addText("Comida general I");
                    rootElement.addElement("origin").addText("Sistema");
                    rootElement.addElement("desc").addText("50 unidades de pienso multipropósito para todo tipo de peces.");
                    rootElement.addElement("rarity").addText("0");
        
                    Element rootElement2=document.addElement("give");
                    rootElement2.addElement("food").addAttribute("type", "general").addText("50");
                    Element rootElement3=document.addElement("quantity");
                    rootElement3.addText("1");
                    rootElement.add(rootElement2);
                    rootElement.add(rootElement3);
                }
            }

            public void recompensaMonedas(Document document,Element rootElement){
                //intento de creacion de recompensa 
                Random r=new Random();
                int valor=r.nextInt(5);
                    if(valor==1){
                        rootElement.addElement("name").addText("Monedas I");
                        rootElement.addElement("origin").addText("Sistema");
                        rootElement.addElement("desc").addText("100 monedas");
                        rootElement.addElement("rarity").addText("0");
            
                        Element rootElement2=document.addElement("give");
                        rootElement2.addElement("coins").addText("100");
                        Element rootElement3=document.addElement("quantity");
                        rootElement3.addText("1");
                        rootElement.add(rootElement2);
                        rootElement.add(rootElement3);
                    }
                }
                public void recompensaPienso(Document document,Element rootElement){
                    //intento de creacion de recompensa 
                    Random r=new Random();
                    int valor=r.nextInt(5);
                        if(valor==1){
                            rootElement.addElement("name").addText("Pienso de peces I");
                            rootElement.addElement("origin").addText("Sistema");
                            rootElement.addElement("desc").addText("100 unidades de pienso hecho a partir de peces, moluscos y otros seres marinos para alimentar a peces carnívoros y omnívoros.");
                            rootElement.addElement("rarity").addText("0");
                
                            Element rootElement2=document.addElement("give");
                            rootElement2.addElement("food").addAttribute("type", "animal").addText("100");
                            Element rootElement3=document.addElement("quantity");
                            rootElement3.addText("1");
                            rootElement.add(rootElement2);
                            rootElement.add(rootElement3);
                        }
                    }
            
                    public void recompensaPiscifac(Document document,Element rootElement){
                        //intento de creacion de recompensa 
                        Random r=new Random();
                        int valor=r.nextInt(4);
                            if(valor==1){
                                rootElement.addElement("name").addText("Piscifactoría de mar [A]");
                                rootElement.addElement("origin").addText("Sistema");
                                rootElement.addElement("desc").addText("Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.");
                                rootElement.addElement("rarity").addText("4");
                    
                                Element rootElement2=document.addElement("give");
                                rootElement2.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
                                rootElement2.addElement("part").addText("A");
                                rootElement2.addElement("total").addText("AB");
                                Element rootElement3=document.addElement("quantity");
                                rootElement3.addText("1");
                                rootElement.add(rootElement2);
                                rootElement.add(rootElement3);
                            }
                        }
                    
                        public void recompensaTanque(Document document,Element rootElement){
                            //intento de creacion de recompensa 
                            Random r=new Random();
                            int valor=r.nextInt(2);
                                if(valor==1){
                                    rootElement.addElement("name").addText("Tanque de mar");
                                    rootElement.addElement("origin").addText("Sistema");
                                    rootElement.addElement("desc").addText("Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de mar.");
                                    rootElement.addElement("rarity").addText("3");
                        
                                    Element rootElement2=document.addElement("give");
                                    rootElement2.addElement("building").addAttribute("code", "3").addText("Tanque de mar");
                                    rootElement2.addElement("part").addText("A");
                                    rootElement2.addElement("total").addText("A");
                                    Element rootElement3=document.addElement("quantity");
                                    rootElement3.addText("1");
                                    rootElement.add(rootElement2);
                                    rootElement.add(rootElement3);
                                }
                            }
}