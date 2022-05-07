package com.general_hello.commands.Objects.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private final String emojiGrid;
    private final int x;
    private final int y;
    private final String emojiOccupied;
    public static HashMap<Integer, ArrayList<Grid>> gridsX = new HashMap<>();
    public static HashMap<Integer, ArrayList<Grid>> gridsY = new HashMap<>();

    public Grid(String emojiGrid, int x, int y, String emojiOccupied) {
        this.emojiGrid = emojiGrid;
        this.x = x;
        this.y = y;
        this.emojiOccupied = emojiOccupied;
        ArrayList<Grid> grids = new ArrayList<>();
        if (gridsX.containsKey(x)) {
            grids = gridsX.get(x);
        }
        grids.add(this);
        gridsX.put(x, grids);

        grids = new ArrayList<>();
        if (gridsY.containsKey(y)) {
            grids = gridsY.get(y);
        }
        grids.add(this);
        gridsY.put(y, grids);
    }

    public String getEmojiOccupied() {
        return emojiOccupied;
    }

    public String getEmojiGrid() {
        return emojiGrid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
