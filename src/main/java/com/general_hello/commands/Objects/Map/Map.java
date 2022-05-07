package com.general_hello.commands.Objects.Map;

import java.util.ArrayList;

public class Map {
    public static String buildMap(Grid currentGrid) {
        int x = currentGrid.getX();
        int y = currentGrid.getY();
        int count = 0;
        if (x-2<1) {
            x = 3;
        }

        if (y-2<1) {
            y = 3;
        }

        if (x+3 > 16) {
            x = 13;
        }

        if (y + 3 > 16) {
            y = 13;
        }
        int currentX = currentGrid.getX();
        int currentY = currentGrid.getY();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**Current location:** (")
                .append(currentX).append(", ").append(currentY).append(")\n");
        int newx;
        int newy = y - 2;
        while (count < 5) {
            ArrayList<Grid> yGrids = Grid.gridsY.get(newy);
            int yolobolo = 0;
            newx = x-2;
            while (yolobolo < 5) {
                if (currentX==newx && currentY == newy) {
                    stringBuilder.append(yGrids.get(newx-1).getEmojiOccupied());
                } else {
                    stringBuilder.append(yGrids.get(newx-1).getEmojiGrid());
                }
                yolobolo++;
                newx++;
            }
            stringBuilder.append("\n");
            count++;
            newy++;
        }

        return stringBuilder.toString();
    }

    public static void initializeMap() {
        new Grid(":red_square:", 1, 1, ":red_circle:");
        new Grid(":red_square:", 1, 2, ":red_circle:");
        new Grid(":red_square:", 1, 3, ":red_circle:");
        new Grid(":red_square:", 1, 4, ":red_circle:");
        new Grid(":red_square:", 1, 5, ":red_circle:");
        new Grid(":red_square:", 1, 6, ":red_circle:");
        new Grid(":red_square:", 1, 7, ":red_circle:");
        new Grid(":red_square:", 1, 8, ":red_circle:");
        new Grid(":red_square:", 1, 9, ":red_circle:");
        new Grid(":red_square:", 1, 10, ":red_circle:");
        new Grid(":red_square:", 1, 11, ":red_circle:");
        new Grid(":red_square:", 1, 12, ":red_circle:");
        new Grid(":red_square:", 1, 13, ":red_circle:");
        new Grid(":red_square:", 1, 14, ":red_circle:");
        new Grid(":red_square:", 1, 15, ":red_circle:");
        new Grid(":red_square:", 2, 1, ":red_circle:");
        new Grid(":red_square:", 2, 2, ":red_circle:");
        new Grid(":red_square:", 2, 3, ":red_circle:");
        new Grid(":red_square:", 2, 4, ":red_circle:");
        new Grid(":red_square:", 2, 5, ":red_circle:");
        new Grid(":red_square:", 2, 6, ":red_circle:");
        new Grid(":red_square:", 2, 7, ":red_circle:");
        new Grid(":red_square:", 2, 8, ":red_circle:");
        new Grid(":red_square:", 2, 9, ":red_circle:");
        new Grid(":red_square:", 2, 10, ":red_circle:");
        new Grid(":red_square:", 2, 11, ":red_circle:");
        new Grid(":red_square:", 2, 12, ":red_circle:");
        new Grid(":red_square:", 2, 13, ":red_circle:");
        new Grid(":red_square:", 2, 14, ":red_circle:");
        new Grid(":red_square:", 2, 15, ":red_circle:");
        new Grid(":red_square:", 3, 1, ":red_circle:");
        new Grid(":red_square:", 3, 2, ":red_circle:");
        new Grid(":red_square:", 3, 3, ":red_circle:");
        new Grid(":red_square:", 3, 4, ":red_circle:");
        new Grid(":red_square:", 3, 5, ":red_circle:");
        new Grid(":red_square:", 3, 6, ":red_circle:");
        new Grid(":red_square:", 3, 7, ":red_circle:");
        new Grid(":red_square:", 3, 8, ":red_circle:");
        new Grid(":red_square:", 3, 9, ":red_circle:");
        new Grid(":red_square:", 3, 10, ":red_circle:");
        new Grid(":red_square:", 3, 11, ":red_circle:");
        new Grid(":red_square:", 3, 12, ":red_circle:");
        new Grid(":red_square:", 3, 13, ":red_circle:");
        new Grid(":red_square:", 3, 14, ":red_circle:");
        new Grid(":red_square:", 3, 15, ":red_circle:");
        new Grid(":red_square:", 4, 1, ":red_circle:");
        new Grid(":red_square:", 4, 2, ":red_circle:");
        new Grid(":red_square:", 4, 3, ":red_circle:");
        new Grid(":red_square:", 4, 4, ":red_circle:");
        new Grid(":red_square:", 4, 5, ":red_circle:");
        new Grid(":red_square:", 4, 6, ":red_circle:");
        new Grid(":red_square:", 4, 7, ":red_circle:");
        new Grid(":red_square:", 4, 8, ":red_circle:");
        new Grid(":red_square:", 4, 9, ":red_circle:");
        new Grid(":red_square:", 4, 10, ":red_circle:");
        new Grid(":red_square:", 4, 11, ":red_circle:");
        new Grid(":red_square:", 4, 12, ":red_circle:");
        new Grid(":red_square:", 4, 13, ":red_circle:");
        new Grid(":red_square:", 4, 14, ":red_circle:");
        new Grid(":red_square:", 4, 15, ":red_circle:");
        new Grid(":red_square:", 5, 1, ":red_circle:");
        new Grid(":red_square:", 5, 2, ":red_circle:");
        new Grid(":red_square:", 5, 3, ":red_circle:");
        new Grid(":red_square:", 5, 4, ":red_circle:");
        new Grid(":red_square:", 5, 5, ":red_circle:");
        new Grid(":red_square:", 5, 6, ":red_circle:");
        new Grid(":red_square:", 5, 7, ":red_circle:");
        new Grid(":red_square:", 5, 8, ":red_circle:");
        new Grid(":red_square:", 5, 9, ":red_circle:");
        new Grid(":red_square:", 5, 10, ":red_circle:");
        new Grid(":red_square:", 5, 11, ":red_circle:");
        new Grid(":red_square:", 5, 12, ":red_circle:");
        new Grid(":red_square:", 5, 13, ":red_circle:");
        new Grid(":red_square:", 5, 14, ":red_circle:");
        new Grid(":red_square:", 5, 15, ":red_circle:");
        new Grid(":red_square:", 6, 1, ":red_circle:");
        new Grid(":red_square:", 6, 2, ":red_circle:");
        new Grid(":red_square:", 6, 3, ":red_circle:");
        new Grid(":red_square:", 6, 4, ":red_circle:");
        new Grid(":red_square:", 6, 5, ":red_circle:");
        new Grid(":red_square:", 6, 6, ":red_circle:");
        new Grid(":red_square:", 6, 7, ":red_circle:");
        new Grid(":red_square:", 6, 8, ":red_circle:");
        new Grid(":red_square:", 6, 9, ":red_circle:");
        new Grid(":red_square:", 6, 10, ":red_circle:");
        new Grid(":red_square:", 6, 11, ":red_circle:");
        new Grid(":red_square:", 6, 12, ":red_circle:");
        new Grid(":red_square:", 6, 13, ":red_circle:");
        new Grid(":red_square:", 6, 14, ":red_circle:");
        new Grid(":red_square:", 6, 15, ":red_circle:");
        new Grid(":red_square:", 7, 1, ":red_circle:");
        new Grid(":red_square:", 7, 2, ":red_circle:");
        new Grid(":red_square:", 7, 3, ":red_circle:");
        new Grid(":red_square:", 7, 4, ":red_circle:");
        new Grid(":red_square:", 7, 5, ":red_circle:");
        new Grid(":red_square:", 7, 6, ":red_circle:");
        new Grid(":red_square:", 7, 7, ":red_circle:");
        new Grid(":red_square:", 7, 8, ":red_circle:");
        new Grid(":red_square:", 7, 9, ":red_circle:");
        new Grid(":red_square:", 7, 10, ":red_circle:");
        new Grid(":red_square:", 7, 11, ":red_circle:");
        new Grid(":red_square:", 7, 12, ":red_circle:");
        new Grid(":red_square:", 7, 13, ":red_circle:");
        new Grid(":red_square:", 7, 14, ":red_circle:");
        new Grid(":red_square:", 7, 15, ":red_circle:");
        new Grid(":red_square:", 8, 1, ":red_circle:");
        new Grid(":red_square:", 8, 2, ":red_circle:");
        new Grid(":red_square:", 8, 3, ":red_circle:");
        new Grid(":red_square:", 8, 4, ":red_circle:");
        new Grid(":red_square:", 8, 5, ":red_circle:");
        new Grid(":red_square:", 8, 6, ":red_circle:");
        new Grid(":red_square:", 8, 7, ":red_circle:");
        new Grid(":red_square:", 8, 8, ":red_circle:");
        new Grid(":red_square:", 8, 9, ":red_circle:");
        new Grid(":red_square:", 8, 10, ":red_circle:");
        new Grid(":red_square:", 8, 11, ":red_circle:");
        new Grid(":red_square:", 8, 12, ":red_circle:");
        new Grid(":red_square:", 8, 13, ":red_circle:");
        new Grid(":red_square:", 8, 14, ":red_circle:");
        new Grid(":red_square:", 8, 15, ":red_circle:");
        new Grid(":red_square:", 9, 1, ":red_circle:");
        new Grid(":red_square:", 9, 2, ":red_circle:");
        new Grid(":red_square:", 9, 3, ":red_circle:");
        new Grid(":red_square:", 9, 4, ":red_circle:");
        new Grid(":red_square:", 9, 5, ":red_circle:");
        new Grid(":red_square:", 9, 6, ":red_circle:");
        new Grid(":red_square:", 9, 7, ":red_circle:");
        new Grid(":red_square:", 9, 8, ":red_circle:");
        new Grid(":red_square:", 9, 9, ":red_circle:");
        new Grid(":red_square:", 9, 10, ":red_circle:");
        new Grid(":red_square:", 9, 11, ":red_circle:");
        new Grid(":red_square:", 9, 12, ":red_circle:");
        new Grid(":red_square:", 9, 13, ":red_circle:");
        new Grid(":red_square:", 9, 14, ":red_circle:");
        new Grid(":red_square:", 9, 15, ":red_circle:");
        new Grid(":red_square:", 10, 1, ":red_circle:");
        new Grid(":red_square:", 10, 2, ":red_circle:");
        new Grid(":red_square:", 10, 3, ":red_circle:");
        new Grid(":red_square:", 10, 4, ":red_circle:");
        new Grid(":red_square:", 10, 5, ":red_circle:");
        new Grid(":red_square:", 10, 6, ":red_circle:");
        new Grid(":red_square:", 10, 7, ":red_circle:");
        new Grid(":red_square:", 10, 8, ":red_circle:");
        new Grid(":red_square:", 10, 9, ":red_circle:");
        new Grid(":red_square:", 10, 10, ":red_circle:");
        new Grid(":red_square:", 10, 11, ":red_circle:");
        new Grid(":red_square:", 10, 12, ":red_circle:");
        new Grid(":red_square:", 10, 13, ":red_circle:");
        new Grid(":red_square:", 10, 14, ":red_circle:");
        new Grid(":red_square:", 10, 15, ":red_circle:");
        new Grid(":red_square:", 11, 1, ":red_circle:");
        new Grid(":red_square:", 11, 2, ":red_circle:");
        new Grid(":red_square:", 11, 3, ":red_circle:");
        new Grid(":red_square:", 11, 4, ":red_circle:");
        new Grid(":red_square:", 11, 5, ":red_circle:");
        new Grid(":red_square:", 11, 6, ":red_circle:");
        new Grid(":red_square:", 11, 7, ":red_circle:");
        new Grid(":red_square:", 11, 8, ":red_circle:");
        new Grid(":red_square:", 11, 9, ":red_circle:");
        new Grid(":red_square:", 11, 10, ":red_circle:");
        new Grid(":red_square:", 11, 11, ":red_circle:");
        new Grid(":red_square:", 11, 12, ":red_circle:");
        new Grid(":red_square:", 11, 13, ":red_circle:");
        new Grid(":red_square:", 11, 14, ":red_circle:");
        new Grid(":red_square:", 11, 15, ":red_circle:");
        new Grid(":red_square:", 12, 1, ":red_circle:");
        new Grid(":red_square:", 12, 2, ":red_circle:");
        new Grid(":red_square:", 12, 3, ":red_circle:");
        new Grid(":red_square:", 12, 4, ":red_circle:");
        new Grid(":red_square:", 12, 5, ":red_circle:");
        new Grid(":red_square:", 12, 6, ":red_circle:");
        new Grid(":red_square:", 12, 7, ":red_circle:");
        new Grid(":red_square:", 12, 8, ":red_circle:");
        new Grid(":red_square:", 12, 9, ":red_circle:");
        new Grid(":red_square:", 12, 10, ":red_circle:");
        new Grid(":red_square:", 12, 11, ":red_circle:");
        new Grid(":red_square:", 12, 12, ":red_circle:");
        new Grid(":red_square:", 12, 13, ":red_circle:");
        new Grid(":red_square:", 12, 14, ":red_circle:");
        new Grid(":red_square:", 12, 15, ":red_circle:");
        new Grid(":red_square:", 13, 1, ":red_circle:");
        new Grid(":red_square:", 13, 2, ":red_circle:");
        new Grid(":red_square:", 13, 3, ":red_circle:");
        new Grid(":red_square:", 13, 4, ":red_circle:");
        new Grid(":red_square:", 13, 5, ":red_circle:");
        new Grid(":red_square:", 13, 6, ":red_circle:");
        new Grid(":red_square:", 13, 7, ":red_circle:");
        new Grid(":red_square:", 13, 8, ":red_circle:");
        new Grid(":red_square:", 13, 9, ":red_circle:");
        new Grid(":red_square:", 13, 10, ":red_circle:");
        new Grid(":red_square:", 13, 11, ":red_circle:");
        new Grid(":red_square:", 13, 12, ":red_circle:");
        new Grid(":red_square:", 13, 13, ":red_circle:");
        new Grid(":red_square:", 13, 14, ":red_circle:");
        new Grid(":red_square:", 13, 15, ":red_circle:");
        new Grid(":red_square:", 14, 1, ":red_circle:");
        new Grid(":red_square:", 14, 2, ":red_circle:");
        new Grid(":red_square:", 14, 3, ":red_circle:");
        new Grid(":red_square:", 14, 4, ":red_circle:");
        new Grid(":red_square:", 14, 5, ":red_circle:");
        new Grid(":red_square:", 14, 6, ":red_circle:");
        new Grid(":red_square:", 14, 7, ":red_circle:");
        new Grid(":red_square:", 14, 8, ":red_circle:");
        new Grid(":red_square:", 14, 9, ":red_circle:");
        new Grid(":red_square:", 14, 10, ":red_circle:");
        new Grid(":red_square:", 14, 11, ":red_circle:");
        new Grid(":red_square:", 14, 12, ":red_circle:");
        new Grid(":red_square:", 14, 13, ":red_circle:");
        new Grid(":red_square:", 14, 14, ":red_circle:");
        new Grid(":red_square:", 14, 15, ":red_circle:");
        new Grid(":red_square:", 15, 1, ":red_circle:");
        new Grid(":red_square:", 15, 2, ":red_circle:");
        new Grid(":red_square:", 15, 3, ":red_circle:");
        new Grid(":red_square:", 15, 4, ":red_circle:");
        new Grid(":red_square:", 15, 5, ":red_circle:");
        new Grid(":red_square:", 15, 6, ":red_circle:");
        new Grid(":red_square:", 15, 7, ":red_circle:");
        new Grid(":red_square:", 15, 8, ":red_circle:");
        new Grid(":red_square:", 15, 9, ":red_circle:");
        new Grid(":red_square:", 15, 10, ":red_circle:");
        new Grid(":red_square:", 15, 11, ":red_circle:");
        new Grid(":red_square:", 15, 12, ":red_circle:");
        new Grid(":red_square:", 15, 13, ":red_circle:");
        new Grid(":red_square:", 15, 14, ":red_circle:");
        new Grid(":red_square:", 15, 15, ":red_circle:");
    }

    public static Grid getGridFromLocation(int x, int y) {
        return Grid.gridsX.get(x).get(y - 1);
    }
}
