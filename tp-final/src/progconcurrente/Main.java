package progconcurrente;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("XML Petri Net Parser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel pathLabel = new JLabel("Red: ");
        JButton selectBtn = new JButton("Seleccionar RdP");
        pathLabel.setSize(550, 30);
        selectBtn.setSize(50, 30);

        panel.add(selectBtn);
        panel.add(pathLabel);
        panel.setVisible(true);

        frame.add(panel, BorderLayout.NORTH);
        JTextArea ta = new JTextArea();
        PrintStream ps = new PrintStream(new TextAreaOutputStream(ta));
        System.setOut(ps);
        System.setErr(ps);


        frame.add(new JScrollPane(ta));

        frame.setVisible(true);

        final JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(panel);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathLabel.setText("Red: " + selectedFile.getName());
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            try {
                GetDataXML g1 = new GetDataXML(selectedFile.getAbsoluteFile());
                g1.getDataXML();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}

