package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            Frontend ex = new Frontend();
        });
    }
}


//Old testing protocol for pre beta.
//Check text files for new testing protocol
/*
int[] b = {3, 6, 8, 12, 15};
Backend a = new Backend(15, b, "C:\\Users\\Enes\\Desktop\\win%.txt", 4, "Cb051bnc", 2000);
a.prepareLineUps();
System.out.println(Arrays.deepToString(a.lineuparray));
System.out.println("here");
a.prepareProbList4();
System.out.println(Arrays.deepToString(a.problist));
a.pageRankTransform();
System.out.println("here");
System.out.println(Arrays.deepToString(a.pagerankArray));
a.pageRank();
System.out.println("here");
System.out.println(Arrays.toString(a.lineuparray[124]));
System.out.println("here");
a.finaliseResults4();
*/