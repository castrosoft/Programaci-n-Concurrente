package progconcurrente;

import org.apache.commons.math3.geometry.partitioning.utilities.AVLTree;
import org.apache.commons.math3.linear.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLPetriNetReader {

    private File fXMLFile;

    private NodeList transitionNodesList;
    private NodeList placeNodesList;
    private NodeList arcNodesList;

    private RealMatrix prevIncidence;
    private RealMatrix postIncidence;

    public XMLPetriNetReader(){}

    private void separateXMLFileIntoNodeLists(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);
            transitionNodesList = doc.getElementsByTagName("transition");
            placeNodesList = doc.getElementsByTagName("place");
            arcNodesList = doc.getElementsByTagName("arc");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setfXMLFile(File fXMLFile) {

        this.fXMLFile = fXMLFile;

        this.separateXMLFileIntoNodeLists();
        System.out.println("*** Se encontraron: "+transitionNodesList.getLength()+" transiciones.");
        System.out.println("*** Se encontraron: "+placeNodesList.getLength()+" plazas.");
        System.out.println("*** Se encontraron: "+arcNodesList.getLength()+" arcos.");

    }

    public int getIndex(NodeList nList, String identifier){
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String elementId = eElement.getAttribute("id");
                if (elementId.equals(identifier)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void calculateIncidenceMatrix(){ //ToDo: IncidenceMatrix  Implementar, reemplazar void por lo que corresponda.
        try {
            prevIncidence = new Array2DRowRealMatrix(placeNodesList.getLength(), transitionNodesList.getLength());
            postIncidence = new Array2DRowRealMatrix(placeNodesList.getLength(), transitionNodesList.getLength());

            for (int i = 0; i < arcNodesList.getLength(); i++) {

                Node nNode = arcNodesList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String elementId = eElement.getAttribute("id");
                    String sourceId = eElement.getAttribute("source");
                    String targetId = eElement.getAttribute("target");
                    String arc_type = eElement.getElementsByTagName("type").item(0).getAttributes().getNamedItem("value").getTextContent();

                    if(elementId.startsWith("P") && arc_type.equals("normal")){
                        String[] values_content = eElement.getElementsByTagName("value").item(0).getTextContent().split(",");
                        int arcWeight = Integer.parseInt(values_content[values_content.length-1]);
                        int placeIdx = getIndex(placeNodesList,sourceId);
                        int transitionIdx = getIndex(transitionNodesList,targetId);
                        prevIncidence.setEntry(placeIdx,transitionIdx,arcWeight);
                    }

                    if(elementId.startsWith("T") && arc_type.equals("normal")){
                        String[] values_content = eElement.getElementsByTagName("value").item(0).getTextContent().split(",");
                        int arcWeight = Integer.parseInt(values_content[values_content.length-1]);
                        int placeIdx = getIndex(placeNodesList,targetId);
                        int transitionIdx = getIndex(transitionNodesList,sourceId);
                        postIncidence.setEntry(placeIdx,transitionIdx,arcWeight);
                    }
                }
            }
            System.out.println("Backwards incidence matrix I-");
            printMatrix(prevIncidence);
            System.out.println();

            System.out.println("Forwards incidence matrix I+");
            printMatrix(postIncidence);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printMatrix(RealMatrix m){
        for(int i=0; i<m.getRowDimension()-1; i++){
            for(int j=0; j<m.getColumnDimension()-1; j++){
                System.out.print(m.getEntry(i,j)+"     ");
            }
            System.out.println();
        }
    }

    public void getMarking(){} //ToDo: Marking:  Implementar, reemplazar void por lo que corresponda.

    public void getInihibitions(){} //ToDo: Inihibitions:  Implementar, reemplazar void por lo que corresponda.

    public void getTimeIntervals(){}//ToDo: TimeIntervals:  Implementar, reemplazar void por lo que corresponda.

    public void getPolicies(){} //ToDo: Policies:  Implementar, reemplazar void por lo que corresponda.



}

