package componentes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

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
        Element rootElement=document.getRootElement();
        Iterator<Element> itr=rootElement.elementIterator();
        while(itr.hasNext()){
            Element elem=itr.next();
            rootElement.addElement("Algas 1").addAttribute("food", "algae").addText("100");
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
}
