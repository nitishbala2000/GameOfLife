package main_package;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class World {

    private String name;
    private String author;
    private int width;
    private int height;
    private boolean[][] cells;
    private List<boolean[][]> worldCache;
    private int generation = 0;

    public World(String format) {

        String[] s = format.split(":");

        this.name = s[0];
        this.author = s[1];
        this.width = Integer.valueOf(s[2]);
        this.height = Integer.valueOf(s[3]);

        this.cells = new boolean[height][width];

        int startUpperCol = Integer.valueOf(s[4]);
        int startUpperRow = Integer.valueOf(s[4]);

        String[] cellDescriptions = s[6].split(" ");

        for (int row = 0; row < cellDescriptions.length; row ++) {

            for (int col = 0; col < cellDescriptions[row].length(); col++) {


                if (cellDescriptions[row].charAt(col) == '1') {
                    cells[row + startUpperRow][col + startUpperCol] = true;
                }
            }

        }

        this.worldCache = new ArrayList<>();
        worldCache.add(this.cells);

    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGeneration() {
        return generation;
    }

    public boolean getCell(int row, int col) {
        return cells[row][col];
    }

    private int countNeighbours(int row, int col) {
        int neighbours = 0;
        for (int i = row - 1; i <= row + 1; i++) {

            for (int j = col - 1; j <= col + 1; j++) {

                if (!(i == row && j == col)) {

                    if (i >= 0 && i < this.height && j >= 0  && j < this.width) {

                        if (this.cells[i][j]) {
                            neighbours += 1;
                        }

                    }

                }
            }
        }

        return neighbours;
    }

    public void advanceGeneration() {

        this.generation += 1;

        if (this.generation < worldCache.size()) {

            this.cells = worldCache.get(generation);

        } else{
            //Compute next world state using the current world state
            boolean[][] newWorld = new boolean[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {

                    int neighbours = countNeighbours(row, col);

                    if (this.cells[row][col]) {

                        if (neighbours <= 1 || neighbours >= 4) {
                            newWorld[row][col] = false;

                        } else {
                            newWorld[row][col] = true;
                        }

                    } else {

                        if (neighbours == 3) {
                            newWorld[row][col] = true;
                        }

                    }
                }
            }

            this.cells = newWorld;
            this.worldCache.add(newWorld);
        }

    }

    public void moveBackGeneration() {
        if (generation > 0) {
            generation -= 1;
            this.cells = this.worldCache.get(generation);
        }
    }

    @Override
    public String toString() {
        return this.name + " (" + this.author + ")";
    }

    public void print() {
        for (int row = 0; row < this.height; row++) {

            for (int col = 0; col < this.width; col++) {

                System.out.print((cells[row][col] ? "*" : "-") + " " );

            }

            System.out.println();
        }
    }

}
