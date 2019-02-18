package castro.detke;

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

public class GetDataXML {

    private File fXMLFile;

    public GetDataXML(File fXmlFile) throws IOException, ParserConfigurationException, SAXException {

        this.fXMLFile = fXmlFile;
    }

    public void getDataXML() {

        try {
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
        }
    }


}

