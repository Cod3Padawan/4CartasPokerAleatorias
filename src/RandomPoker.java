import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RandomPoker {
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 300;
    private static final Color POKER_GREEN = new Color(40, 104, 51);
    private static final Color BACKGROUND_GREEN = new Color(39, 174, 96);

    private List<File> imageFiles;
    private JPanel panel;

    private JLabel counterLabel;
    private int counter;
    private int numClicks;

    public static void main(String[] args) {
        String folderPath = "/home/ji156mint/IdeaProjects/CartasPoker/src/cartas";
        RandomPoker randomPoker = new RandomPoker();
        randomPoker.imageFiles = randomPoker.loadImageFiles(folderPath);
        Collections.shuffle(randomPoker.imageFiles);

        JFrame frame = new JFrame("Random Poker");
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND_GREEN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        randomPoker.panel = randomPoker.createPanel();
        frame.add(randomPoker.panel);
        randomPoker.counter = 0;
        randomPoker.counterLabel = new JLabel("Pulsaciones: " + randomPoker.numClicks);
        randomPoker.counterLabel.setHorizontalAlignment(JLabel.CENTER);
        randomPoker.counterLabel.setVerticalAlignment(JLabel.CENTER);
        randomPoker.counterLabel.setFont(randomPoker.counterLabel.getFont().deriveFont(24f));
        frame.add(randomPoker.counterLabel, "North");

        JButton generateButton = new JButton("Generar nuevas cartas");//Con esto generamos nuevas cartas y cada click se suma al marcador
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomPoker.updatePanel();
                randomPoker.numClicks++;
                randomPoker.counterLabel.setText("Pulsaciones: " + randomPoker.numClicks);
            }
        });


        JButton resetButton = new JButton("Resetear contador");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomPoker.numClicks = 0; // resetea el contador de pulsaciones
                randomPoker.counterLabel.setText("Pulsaciones: " + randomPoker.numClicks); // actualiza la etiqueta del contador de pulsaciones
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_GREEN);
        buttonPanel.add(generateButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, "South");

        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setVisible(true);
    }

    private List<File> loadImageFiles(String folderPath) {
        Objects.requireNonNull(folderPath, "La ruta de la carpeta no puede ser nula");
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<File> imageFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".png") && !fileName.startsWith("volver")) {
                    imageFiles.add(file);
                }
            }
        }
        return imageFiles;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(POKER_GREEN);
        panel.setLayout(new GridLayout(2, 2));

        if (imageFiles.isEmpty()) {
            System.err.println("Error: la lista de imágenes está vacía.");
            return panel;
        }

        for (int i = 0; i < 4; i++) {
            File imageFile = imageFiles.get(i % imageFiles.size());
            ImageIcon imageIcon = loadImageIcon(imageFile);
            JLabel label = new JLabel(imageIcon);
            panel.add(label);
        }

        return panel;
    }


    private void updatePanel() {
        panel.removeAll();
        Collections.shuffle(imageFiles);

        for (int i = 0; i < 4; i++) {
            File imageFile = imageFiles.get(i % imageFiles.size());
            ImageIcon imageIcon = loadImageIcon(imageFile);
            JLabel label = new JLabel(imageIcon);
            panel.add(label);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void resetCounter() {
        int counter = 0;
        Label counterLabel = null;
        counterLabel.setText("Número de pulsaciones: " + counter);
    }



    private ImageIcon loadImageIcon(File imageFile) {
        try {
            return new ImageIcon(ImageIO.read(imageFile).getScaledInstance(
                    IMAGE_WIDTH, IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH));
        } catch (IOException e) {
            System.err.println("Error al leer archivo de imagen: " + imageFile);
            return null;
        }
    }
}