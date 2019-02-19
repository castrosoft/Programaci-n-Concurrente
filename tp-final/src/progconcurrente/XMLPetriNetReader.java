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

@SuppressWarnings("ALL")
public class XMLPetriNetReader {

    private File fXMLFile;

    private NodeList transitionNodesList;
    private NodeList placeNodesList;
    private NodeList arcNodesList;

    public RealMatrix getPrevIncidence() {
        return prevIncidence;
    }

    public RealMatrix getPostIncidence() {
        return postIncidence;
    }

    public RealMatrix getIncidence() {
        return incidence;
    }

    public RealMatrix getInhibition() {
        return inhibition;
    }

    public RealVector getMarking() {
        return marking;
    }

    private RealMatrix prevIncidence;
    private RealMatrix postIncidence;
    private RealMatrix incidence;
    private RealMatrix inhibition;
    private RealVector marking;


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

    public void readIncidenceMatrix(boolean verbose){
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
            if(verbose) {
                System.out.println("*** Backwards incidence matrix I-");
                printMatrix(prevIncidence);
                System.out.println();

                System.out.println("*** Forwards incidence matrix I+");
                printMatrix(postIncidence);
                System.out.println();

                incidence = postIncidence.subtract(prevIncidence);
                System.out.println("*** Inncidence matrix I");
                printMatrix(incidence);
                System.out.println();
            }else{
                System.out.println("Matrices de incidencia leidas correctamente.");
            }

        } catch (NullPointerException e) {
            System.out.println("No se pudo leer matriz de incidencia.");
//            e.printStackTrace();
        }
    }

    public void printMatrix(RealMatrix m){
        for(int i=0; i<m.getRowDimension(); i++){
            for(int j=0; j<m.getColumnDimension(); j++){
                System.out.print(String.format("%5d ",Math.round(m.getEntry(i,j))));
            }
            System.out.println();
        }
    }

    public void readMarking(boolean verbose){
        try {
            marking = new ArrayRealVector(placeNodesList.getLength());
            for (int i = 0; i < placeNodesList.getLength(); i++) {

                Node nNode = placeNodesList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String[] values_content = eElement.getElementsByTagName("value").item(1).getTextContent().split(",");
                    int mark = Integer.parseInt(values_content[values_content.length-1]);
                    marking.setEntry(i,mark);
                }
            }
            if(verbose) {
                System.out.println("*** Initial Marking M0");
                printVector(marking);
                System.out.println();
            }else{
                System.out.println("Marcado inicial leido correctamente.");
            }
        }catch (NullPointerException e){
            System.out.println("No se pudo leer marcado inicial.");
        }
    }

    private void printVector(RealVector v) {
        for(int i=0; i<v.getDimension(); i++){
            System.out.print(String.format("%5d ",Math.round(v.getEntry(i))));
        }
    }

    public void readInhibitions(boolean verbose){
        try {
            inhibition = new Array2DRowRealMatrix(placeNodesList.getLength(), transitionNodesList.getLength());
            for (int i = 0; i < arcNodesList.getLength(); i++) {

                Node nNode = arcNodesList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String elementId = eElement.getAttribute("id");
                    String sourceId = eElement.getAttribute("source");
                    String targetId = eElement.getAttribute("target");
                    String arc_type = eElement.getElementsByTagName("type").item(0).getAttributes().getNamedItem("value").getTextContent();

                    if(elementId.startsWith("P") && arc_type.equals("inhibitor")){
                        int placeIdx = getIndex(placeNodesList,sourceId);
                        int transitionIdx = getIndex(transitionNodesList,targetId);
                        inhibition.setEntry(placeIdx,transitionIdx,1);
                    }

                    if(elementId.startsWith("T") && arc_type.equals("inhibitor")){
                        int placeIdx = getIndex(placeNodesList,targetId);
                        int transitionIdx = getIndex(transitionNodesList,sourceId);
                        inhibition.setEntry(placeIdx,transitionIdx,1);
                    }
                }
            }
            if(verbose) {
                System.out.println("*** Inhibition matrix H");
                printMatrix(inhibition);
                System.out.println();
            }else{
                System.out.println("Matriz de inhibidores leida correctamente.");
            }

        }catch (NullPointerException e) {
            System.out.println("No se pudo leer matriz de inhibidores.");
        }
    }

    public void getTimeIntervals(){}//ToDo: TimeIntervals:  Implementar, reemplazar void por lo que corresponda.

    public void getPolicies(){} //ToDo: Policies:  Implementar, reemplazar void por lo que corresponda.



}

