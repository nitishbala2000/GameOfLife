package main_package;

import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private World world = null;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (this.world == null) {
            // Paint the background white
            return;
        } else{
            g.setColor(Color.black);
            g.drawString("Generation " +world.getGeneration(), 6, this.getHeight() - 6);
            int squareWidth = Math.min(this.getWidth() / world.getWidth(), this.getHeight() / world.getHeight());
            int x = 0;
            int y = 0;
            for (int row = 0; row < world.getHeight(); row++) {

                for (int col = 0; col < world.getWidth(); col++) {

                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x, y, squareWidth, squareWidth);

                    if (world.getCell(row, col)) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x , y , squareWidth , squareWidth );
                    }

                    x += squareWidth;
                }

                x = 0;
                y += squareWidth;

            }



        }

    }

    public void display(World w) {

        this.world = w;
        repaint();
    }
}
