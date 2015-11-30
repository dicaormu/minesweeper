package com.fr.mines;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {


    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final Logger logger = LoggerFactory.getLogger(Launcher.class);
        try {
            System.out.println("How many cells in x do you want? ->");
            final int x = Integer.parseInt(br.readLine().trim());
            System.out.println("How many cells in y do you want? ->");
            final int y = Integer.parseInt(br.readLine().trim());
            System.out.println("How many mines do you want? ->");
            final int mines = Integer.parseInt(br.readLine().trim());
            MinesGame minesGame = new MinesGame(x, y, mines);
            minesGame.play();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
