package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Starter {
    File pD;
    File dS;
    String accu;
    String matchup;
    String metaL;
    File dN;
    File mC;
    ArrayList<String> tR = null;
    int[] MCarray = {0, 2, 10, 50, 290, 1682, 9410, 50626};

    public Starter(File pD, File dS, File dN, File mC, String accu, String matchup, String metaL) throws FileNotFoundException {
        this.pD = pD;
        this.dS = dS;
        this.mC = mC;
        this.accu = accu;
        this.matchup = matchup;
        this.dN = dN;
        this.metaL = metaL;
        tR = startItUp();

    }

    private ArrayList<String> startItUp() throws FileNotFoundException {
        Scanner scanner = new Scanner(dS);
        ArrayList<Integer> k = new ArrayList<>();
        while (scanner.hasNextInt())
            k.add(scanner.nextInt());
        int[] listOfSep = new int[k.size()];
        for (int i = 0; i < k.size(); i++)
            listOfSep[i] = k.get(i);
        int sizeOf = listOfSep[listOfSep.length - 1];
        int sizeofLineup = 0;
        if (matchup.equals("Cb05ban"))
            sizeofLineup = 4;
        else if (matchup.equals("Cb03ban"))
            sizeofLineup = 3;
        int MCLevel = Integer.parseInt(accu);
        Backend a = new Backend(sizeOf, listOfSep, pD.getAbsolutePath(), sizeofLineup, matchup, MCLevel, dN.getAbsolutePath(), mC.getAbsolutePath(), Integer.parseInt(metaL));
        a.prepareMetaProbList();
        a.prepareLineUps();

        System.out.println(a.totalNumOfLineUps);
        JDialog popUp = new JDialog(new JFrame(), "Lineups prepared, now this will take a while.");
        popUp.setSize(500, 0);
        /*JLabel hh = new JLabel("Lineups prepared, now this will take a while.");
        popUp.add(hh);*/
        popUp.setVisible(true);
        //System.out.println(Arrays.deepToString(a.lineuparray));
        a.prepareProbList();
        // System.out.println(Arrays.deepToString(a.problist));
        a.pageRankTransform();
        //System.out.println(Arrays.deepToString(a.pagerankArray));
        a.pageRank();
        //System.out.println(Arrays.toString(a.lineuparray[124]));
        ArrayList<String> f = a.finaliseResults();
        return f;
    }

    public ArrayList<String> returnStuff() {
        return tR;
    }
}
