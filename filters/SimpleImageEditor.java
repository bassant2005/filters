import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class SimpleImageEditor extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;

    public SimpleImageEditor() {
        super("Java Image Editor");

        // UI Setup
        JButton openButton = new JButton("Open");
        JButton bwButton = new JButton("Black & White");
        JButton grayButton = new JButton("Grayscale");
        JButton saveButton = new JButton("Save");

        imageLabel = new JLabel();

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.add(openButton);
        panel.add(bwButton);
        panel.add(grayButton);
        panel.add(saveButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        // Actions
        openButton.addActionListener(e -> openImage());
        bwButton.addActionListener(e -> {
            if (image != null) {
                applyBlackAndWhite();
                imageLabel.setIcon(new ImageIcon(image));
            }
        });
        grayButton.addActionListener(e -> {
            if (image != null) {
                applyGrayscale();
                imageLabel.setIcon(new ImageIcon(image));
            }
        });
        saveButton.addActionListener(e -> saveImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    private void openImage() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(chooser.getSelectedFile());
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                showError("Failed to load image.");
            }
        }
    }

    private void saveImage() {
        if (image == null) return;

        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(image, "png", chooser.getSelectedFile());
            } catch (IOException ex) {
                showError("Failed to save image.");
            }
        }
    }

    private void applyGrayscale() {
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color gray = new Color(avg, avg, avg);
                image.setRGB(x, y, gray.getRGB());
            }
        }
    }

    private void applyBlackAndWhite() {
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color bw = (avg > 127) ? Color.WHITE : Color.BLACK;
                image.setRGB(x, y, bw.getRGB());
            }
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleImageEditor::new);
    }
}
