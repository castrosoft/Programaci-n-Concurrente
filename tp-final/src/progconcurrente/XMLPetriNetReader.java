package progconcurrente;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLPetriNetReader {

    private File fXMLFile;

    public XMLPetriNetReader(){}

    public File getfXMLFile() {
        return fXMLFile;
    }

    public void setfXMLFile(File fXMLFile) {
        this.fXMLFile = fXMLFile;
    }

    public void getIncidenceMatrix(){} //ToDo: IncidenceMatrix  Implementar, reemplazar void por lo que corresponda.

    public void getMarking(){} //ToDo: Marking:  Implementar, reemplazar void por lo que corresponda.

    public void getInihibitions(){} //ToDo: Inihibitions:  Implementar, reemplazar void por lo que corresponda.

    public void getTimeIntervals(){}//ToDo: TimeIntervals:  Implementar, reemplazar void por lo que corresponda.

    public void getPolicies(){} //ToDo: Policies:  Implementar, reemplazar void por lo que corresponda.

    //ToDo: All: Implementar cada uno de los metodos de arriba para extraer los datos que se le pasaran al Modelo.
    //     Utilizar como guia el metodo "getDataXML".
    public void getDataXML() {

        /*try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            System.out.println();

            NodeList nList = doc.getElementsByTagName("place");

            for (int i = 0; i < nList.getLength(); i++) {

                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Plaza#: " + eElement.getAttribute("id"));
                    System.out.println("Valor Plaza: " + (eElement.getElementsByTagName("value").item(1).getTextContent()).substring(8));
                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


}

