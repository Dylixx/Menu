package fr.iutfbleau.SAE;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * La classe <code>PieChartPanel</code> dessine le camembert
 *  
 * @version 1.1
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */

public class PieChartPanel extends JPanel {
     /**
     *  Couleur de l'action correct
     */
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.GREEN;

     /**
     *  tableau des noms des slices du camembert
     */
    private ArrayList<String> labels;

     /**
     *  tableau des valeurs représentées par les slices
     */
    private ArrayList<Integer> values;

    /**
     *  définit quelle slice possède la couleur verte
     */
    private int highlightIndex;

    /**
     *  tableau des couleurs de chaque slice
     */
    private Color[] sliceColors;


    /**
     * Constructeur destiné à remplir les variables qui seront transformées en camembert
     */
    public PieChartPanel(ArrayList<String> labels, ArrayList<Integer> values, int highlightIndex) {
        this.labels = labels;
        this.values = values;
        this.highlightIndex = highlightIndex;
        this.sliceColors = generateSliceColors();
    }

    /**
   * Dessine les slices 
   *
   * @param g graphique
   */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

	int total = (int) getTotalValues();
        int startAngle = 0;

        for (int i = 0; i < values.size(); i++) {
            double arcAngle = calculateArcAngle(i, total);
            int roundedArcAngle = (int) Math.round(arcAngle);

            g.setColor(sliceColors[i]);
            fillPieSlice(g2d, startAngle, roundedArcAngle);
            startAngle += roundedArcAngle;
            if (i == values.size() - 1 && startAngle < 360) {
                roundedArcAngle = 360 - startAngle;
                fillPieSlice(g2d, startAngle, roundedArcAngle);
            }
        }

        drawSliceLabels(g);
    }

    /**
   * Calcul les angles des slices
   *
   * @param index valeur dans le tableau values
   * @param total somme des valeurs
   * 
   * @return la valeur de l'angle
   */
    private double calculateArcAngle(int index, double total) {
        return ((double) values.get(index)) / total * 360.0;
    }

    /**
   * Colorie la slice
   * 
   * @param g2d la slice
   * @param startAngle position de la slice sur le camembert
   * @param arcAngle valeur de l'angle
   *
   * @return la nouvelle direction
   */
    private void fillPieSlice(Graphics2D g2d, int startAngle, int arcAngle) {
        g2d.fill(new Arc2D.Double(10, 10, getWidth() - 20, getHeight() - 20, startAngle, arcAngle, Arc2D.PIE));
    }

    /**
   * Positionne le label dans la slice associée
   *
   * @param g la slice
   */
    private void drawSliceLabels(Graphics g) {
        int total = (int) getTotalValues();
        int startAngle = 0;

        for (int i = 0; i < values.size(); i++) {
            double arcAngle = calculateArcAngle(i, total);
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int labelX = calculateLabelX(centerX, startAngle, arcAngle);
            int labelY = calculateLabelY(centerY, startAngle, arcAngle);

            g.setColor(Color.BLACK);
            g.drawString(labels.get(i), labelX, labelY);

            startAngle += arcAngle;
        }
    }

    /**
   * Calcul la position X du label dans la slice   
   * 
   * @param centerX centre X de la slice
   * @param startAngle position de la slice
   * @param arcAngle valeur de l'angle
   * 
   * @return position X du label
   */
    private int calculateLabelX(int centerX, int startAngle, double arcAngle) {
        return (int) (centerX + 0.6 * (getWidth() / 2) * Math.cos(Math.toRadians(startAngle + arcAngle / 2)));
    }

    /**
   * Calcul la position Y du label dans la slice   
   * 
   * @param centerY centre Y de la slice
   * @param startAngle position de la slice
   * @param arcAngle valeur de l'angle
   * 
   * @return position Y du label
   */
    private int calculateLabelY(int centerY, int startAngle, double arcAngle) {
        return (int) (centerY - 0.6 * (getHeight() / 2) * Math.sin(Math.toRadians(startAngle + arcAngle / 2)));
    }

     /**
   * Calcul la somme des valeurs du tableau values   
   * 
   * @return somme des valeurs
   */
    private double getTotalValues() {
        return values.stream().mapToDouble(Integer::doubleValue).sum();
    }

    /**
   * Génère une couleur pour chaque slice   
   * 
   * @return la couleur de la slice
   */
    private Color[] generateSliceColors() {
        Color[] colors = new Color[values.size()];
        Color oldColor = DEFAULT_HIGHLIGHT_COLOR;

        for (int i = 0; i < values.size(); i++) {
            colors[i] = (i == highlightIndex) ? DEFAULT_HIGHLIGHT_COLOR : getRandomColor(oldColor);
            oldColor = colors[i];
        }
        return colors;
    }

    /**
   * Génère une couleur différente des autres  
   * 
   * @param oldColor ancienne couleur afin d'en faire une différente
   * 
   * @return le couleur générée
   */
    private Color getRandomColor(Color oldColor) {
        Color newColor;
        do {
            newColor = new Color((int) (Math.random() * 256),(int) (Math.random() * 256) , (int) (Math.random() * 256));
        } while (estNuanceDeVert(newColor) || isColorClose(newColor, oldColor));
        return newColor;
    }

    /**
   * Compare deux couleurs   
   * 
   * @param color1 première couleur comparée
   * @param color2 deuxième couleur comparée
   * 
   * @return true si proche
   * @return false si éloignée
   */
    private static boolean isColorClose(Color color1, Color color2) {
        int threshold = 200;
        int redDiff = Math.abs(color1.getRed() - color2.getRed());
        int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());
        int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
        return redDiff + blueDiff + greenDiff < threshold;
    }

    /**
   * Vérifie si la couleur est verte (afin d'éviter les problèmes)   
   * 
   * @param couleur la couleur analysée
   * 
   * @return true si proche d'un vert
   * @return false si éloignée d'un vert
   */
    private static boolean estNuanceDeVert(Color couleur) {
        int rouge = couleur.getRed();
        int vert = couleur.getGreen();
        int bleu = couleur.getBlue();
        int seuil = 50;
        return (vert - rouge > seuil) && (vert - bleu > seuil) && (vert > seuil);
    }
}
