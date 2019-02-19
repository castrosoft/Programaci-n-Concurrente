package progconcurrente;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;

import static java.lang.Thread.sleep;

public class Main {

//    private static JFileChooser fileChooser = new JFileChooser();
//    private static int open_result;
    public static void main(String[] args) {

        XMLPetriNetReader petriNetReader = new XMLPetriNetReader();

        JFrame frame = new JFrame("Simulate your fantastic XML Petri Net in two steps!!! By: CastroDetkeTeam Inc.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        JFileChooser fileChooser = new JFileChooser("../");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PIPE File","xml"));

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setSize(800,40);

        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel pathLabel = new JLabel("Red: ");

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton selectBtn = new JButton("Seleccionar RdP");
        selectBtn.setToolTipText("<html>Permite seleccionar un archivo XML<br>generado por PIPE</html>");


        JButton startBtn = new JButton("Comenzar");
        startBtn.setToolTipText("Comienza la simulacion de la red.");
        JButton stopBtn = new JButton("Detener");
        stopBtn.setToolTipText("<html>Detiene la simulacion y reinicia la<br>red seleccionada.</html>");
        JCheckBox verbose = new JCheckBox("Verbose Mode");

        selectBtn.addActionListener(e -> {
            int open_result = fileChooser.showOpenDialog(null);
            if (open_result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                pathLabel.setText("Red: " + selectedFile.getName());
                System.out.println("*** Red seleccionada: " + selectedFile.getName());
                System.out.println("2) Para comenzar la simulacion, presione \"Comenzar\"");
                //TODO implementar creacion de objetos e inicializacion de simulacion.
                petriNetReader.setfXMLFile(selectedFile);


            }
        });

        stopBtn.addActionListener(e -> {
            //TODO Implementar parar simulacion.
        });

        startBtn.addActionListener(e -> {
            //TODO Implementar lanzamiento de simulacion.
            petriNetReader.readIncidenceMatrix(verbose.isSelected());
            petriNetReader.readInhibitions(verbose.isSelected());
            petriNetReader.readMarking(verbose.isSelected());
        });

//        controlPanel.setPreferredSize(new Dimension(startBtn.getWidth()+stopBtn.getWidth(),40));
        selectPanel.setPreferredSize(new Dimension(465,40));

        selectPanel.add(selectBtn);
        selectPanel.add(pathLabel);

        controlPanel.add(verbose);
        controlPanel.add(startBtn);
        controlPanel.add(stopBtn);


        selectPanel.setVisible(true);
        controlPanel.setVisible(true);

        mainPanel.add(selectPanel);
        mainPanel.add(controlPanel);

        frame.add( mainPanel, BorderLayout.NORTH );
        JTextArea ta = new JTextArea();
        ta.setEnabled(false);
        ta.setDisabledTextColor(Color.BLACK);
        PrintStream ps = new PrintStream(new TextAreaOutputStream(ta) );
        System.setOut( ps );
        System.setErr( ps );


        frame.add( new JScrollPane(ta));

        frame.setVisible(true);
        System.out.println("1) Por favor seleccione una red en formato XML realizada en PIPE.");
    }
}

