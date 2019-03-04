package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Backend {
    double[][] problist;
    double[][] lineupproblist;
    int[] interruptionList;
    int[][] lineuparray;
    double[][] pagerankArray;
    int sizeOfLineup;
    String matchType;
    int totalNumOfLineUps;
    int sizeOf;
    int MCLevel;
    double pagerank[];
    String[] deckNamesArray;
    ArrayList<ArrayList<Integer>> checker;
    int[] metaIndicator;
    int metaLevel;

    public Backend(int sizeOf, int[] intList, String location, int sizeOfLineup, String matchType, int MCLevel, String deckNames, String metaIndicator, int metaLevel) {
        this.sizeOf = sizeOf;
        this.metaLevel = metaLevel;
        this.metaIndicator = new int[sizeOf];
        checker = new ArrayList<>();
        problist = new double[sizeOf][sizeOf];
        File file = new File(location);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int row = 0; row < sizeOf; row++) {
            for (int i = 0; i < sizeOf; i++) {
                problist[row][i] = scanner.nextDouble();
            }
        }
        file = new File(metaIndicator);
        scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int row = 0; row < sizeOf; row++) {
            this.metaIndicator[row] = scanner.nextInt();
        }
        interruptionList = new int[intList.length];
        for (int i = 0; i < intList.length; i++) {
            interruptionList[i] = intList[i];
        }
        this.sizeOfLineup = sizeOfLineup;
        this.matchType = matchType;
        totalNumOfLineUps = 0;
        lineuparray = new int[20000][sizeOfLineup];
        this.MCLevel = MCLevel;
        deckNamesArray = new String[sizeOf];
        File file1 = new File(deckNames);
        Scanner scanner1 = null;
        try {
            scanner1 = new Scanner(file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < sizeOf; i++) {
            deckNamesArray[i] = scanner1.nextLine();
        }
    }

    public void prepareMetaProbList() {
        if (metaLevel > 0) {
            for (int row = 0; row < sizeOf; row++) {
                for (int i = row; i < sizeOf; i++) {
                    if (metaIndicator[row] != metaIndicator[i]) {
                        //System.out.println(problist[row][i]);
                        problist[row][i] = sigmoidFixer(problist[row][i], metaLevel);
                        problist[i][row] = 1 - problist[row][i];
                        //System.out.println(problist[row][i]);
                    }
                }
            }
        }
    }

    public double sigmoidFixer(double x, double level) {
        double eCon = Math.pow(Math.E, (-x + 0.5) * level);
        return 1 / (1 + eCon);
    }

    public void prepareLineUps() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int i = 0; i < interruptionList.length; i++) {
            a.add(i);
        }
        recPreparer(a);
    }

    public void recPreparer(ArrayList<Integer> a) {
        if (a.size() == sizeOfLineup) {
            generateLineups(a);
        } else {
            for (int i = 0; i < a.size(); i++) {
                ArrayList<Integer> b = new ArrayList(a);
                b.remove(i);
                recPreparer(b);
            }
        }
    }

    public void generateLineups(ArrayList<Integer> a) {
        if (sizeOfLineup == 4)
            generateLineups4(a);
        else if (sizeOfLineup == 3)
            generateLineups3(a);
    }

    private void generateLineups3(ArrayList<Integer> a) {
        if (checker.contains(a)) {

        } else if (a.get(0) == 0) {
            checker.add(a);
            for (int i = 0; i < interruptionList[0]; i++) {
                for (int j = interruptionList[a.get(1) - 1]; j < interruptionList[a.get(1)]; j++) {
                    for (int k = interruptionList[a.get(2) - 1]; k < interruptionList[a.get(2)]; k++) {
                        //System.out.println(i + " " + j + " " + k);
                        lineuparray[totalNumOfLineUps][0] = i;
                        lineuparray[totalNumOfLineUps][1] = j;
                        lineuparray[totalNumOfLineUps][2] = k;
                        //System.out.println(deckNamesArray[lineuparray[totalNumOfLineUps][0]] + ", " + deckNamesArray[lineuparray[totalNumOfLineUps][1]] + ", " + deckNamesArray[lineuparray[totalNumOfLineUps][2]]);
                        totalNumOfLineUps += 1;
                    }
                }
            }
        } else {
            checker.add(a);
            for (int i = interruptionList[a.get(0) - 1]; i < interruptionList[a.get(0)]; i++) {
                for (int j = interruptionList[a.get(1) - 1]; j < interruptionList[a.get(1)]; j++) {
                    for (int k = interruptionList[a.get(2) - 1]; k < interruptionList[a.get(2)]; k++) {

                        // System.out.println(i + " " + j + " " + k);
                        lineuparray[totalNumOfLineUps][0] = i;
                        lineuparray[totalNumOfLineUps][1] = j;
                        lineuparray[totalNumOfLineUps][2] = k;
                        //System.out.println(deckNamesArray[lineuparray[totalNumOfLineUps][0]] + ", " + deckNamesArray[lineuparray[totalNumOfLineUps][1]] + ", " + deckNamesArray[lineuparray[totalNumOfLineUps][2]]);

                        totalNumOfLineUps += 1;
                    }
                }
            }
        }

    }

    public void generateLineups4(ArrayList<Integer> a) {
        if (checker.contains(a)) {

        }
        if (a.get(0) == 0) {

            checker.add(a);
            for (int i = 0; i < interruptionList[0]; i++) {
                for (int j = interruptionList[a.get(1) - 1]; j < interruptionList[a.get(1)]; j++) {
                    for (int k = interruptionList[a.get(2) - 1]; k < interruptionList[a.get(2)]; k++) {
                        for (int l = interruptionList[a.get(3) - 1]; l < interruptionList[a.get(3)]; l++) {
                            lineuparray[totalNumOfLineUps][0] = i;
                            lineuparray[totalNumOfLineUps][1] = j;
                            lineuparray[totalNumOfLineUps][2] = k;
                            lineuparray[totalNumOfLineUps][3] = l;
                            totalNumOfLineUps += 1;
                        }
                    }
                }
            }
        } else {

            checker.add(a);
            for (int i = interruptionList[a.get(0) - 1]; i < interruptionList[a.get(0)]; i++) {
                for (int j = interruptionList[a.get(1) - 1]; j < interruptionList[a.get(1)]; j++) {
                    for (int k = interruptionList[a.get(2) - 1]; k < interruptionList[a.get(2)]; k++) {
                        for (int l = interruptionList[a.get(3) - 1]; l < interruptionList[a.get(3)]; l++) {
                            lineuparray[totalNumOfLineUps][0] = i;
                            lineuparray[totalNumOfLineUps][1] = j;
                            lineuparray[totalNumOfLineUps][2] = k;
                            lineuparray[totalNumOfLineUps][3] = l;
                            totalNumOfLineUps += 1;
                        }
                    }
                }
            }
        }

    }

    public void prepareProbList() {
        if (sizeOfLineup == 4)
            prepareProbList4();
        else if (sizeOfLineup == 3)
            prepareProbList3();
    }

    private void prepareProbList3() {
        lineupproblist = new double[totalNumOfLineUps][totalNumOfLineUps];
        for (int i = 0; i < totalNumOfLineUps; i++) {
            for (int j = i; j < totalNumOfLineUps; j++) {
                if (i == j)
                    lineupproblist[i][j] = 0.5;
                else {
                    double a = 0.5;
                    ArrayList<Integer> l1 = new ArrayList<Integer>();
                    l1.add(lineuparray[i][0]);
                    l1.add(lineuparray[i][1]);
                    l1.add(lineuparray[i][2]);
                    ArrayList<Integer> l2 = new ArrayList<Integer>();
                    l2.add(lineuparray[j][0]);
                    l2.add(lineuparray[j][1]);
                    l2.add(lineuparray[j][2]);
                    if (matchType.equals("Cb03ban"))
                        a = conquest_1b_nocoin(l1, l2);
                    lineupproblist[i][j] = a;
                    lineupproblist[j][i] = 1 - a;

                    //System.out.println(i);
                }
            }
        }
    }

    public void prepareProbList4() {
        lineupproblist = new double[totalNumOfLineUps][totalNumOfLineUps];
        for (int i = 0; i < totalNumOfLineUps; i++) {
            for (int j = i; j < totalNumOfLineUps; j++) {
                if (i == j)
                    lineupproblist[i][j] = 0.5;
                else {
                    double a = 0.5;
                    ArrayList<Integer> l1 = new ArrayList<Integer>();
                    l1.add(lineuparray[i][0]);
                    l1.add(lineuparray[i][1]);
                    l1.add(lineuparray[i][2]);
                    l1.add(lineuparray[i][3]);
                    ArrayList<Integer> l2 = new ArrayList<Integer>();
                    l2.add(lineuparray[j][0]);
                    l2.add(lineuparray[j][1]);
                    l2.add(lineuparray[j][2]);
                    l2.add(lineuparray[j][3]);
                    if (matchType.equals("Cb05ban"))
                        a = conquest_1b_nocoin(l1, l2);
                    lineupproblist[i][j] = a;
                    lineupproblist[j][i] = 1 - a;
                }
            }

        }
    }

    //Working code but not in use.
    public double conquest_1b_coin(ArrayList<Integer> a, ArrayList<Integer> b) {
        Integer toBanA = new Integer(0);
        Integer toBanB = new Integer(0);
        double winrateA = 0.0;
        double winrateB = 0.0;
        for (int numA : a) {
            double winrate = 0.0;
            for (int numB : b)
                winrate += (problist[numA][numB] + 1 - problist[numB][numA]) / 2;
            // System.out.println(winrate);
            if (winrateA < winrate / sizeOfLineup) {
                toBanA = numA;
                winrateA = winrate / sizeOfLineup;
            }
        }

        //  System.out.println("============");
        for (int numB : b) {
            double winrate = 0.0;
            for (int numA : a)
                winrate += (problist[numB][numA] + 1 - problist[numA][numB]) / 2;
            // System.out.println(winrate);
            if (winrateB < winrate / sizeOfLineup) {
                toBanB = numB;
                winrateB = winrate / sizeOfLineup;
            }
        }
        /*
        System.out.println(toBanA);
        System.out.println(a);
        System.out.println(toBanB);
        System.out.println(b);
        System.out.println("============");
        a.remove(toBanA);
        b.remove(toBanB);
        */
        int MC = 2500; //DONT LOWER IT TOO MUCH
        Random rand = new Random();
        //System.out.println(b);
        ArrayList<Integer> aC = new ArrayList<Integer>();
        ArrayList<Integer> bC = new ArrayList<Integer>();
        int aPick;
        int bPick;
        int wincount = 0;
        for (int i = 0; i < MC; i++) {
            boolean coin = rand.nextBoolean();
            for (Integer foo : a) {
                aC.add(foo);
            }
            for (Integer foo : b) {
                bC.add(foo);
            }
            while (!aC.isEmpty() && !bC.isEmpty()) {
                if (coin) {
                    aPick = rand.nextInt(aC.size());
                    bPick = rand.nextInt(bC.size());
                    //System.out.println( problist[aC.get(aPick)][bC.get(bPick)]);
                    if (Math.random() < problist[aC.get(aPick)][bC.get(bPick)])
                        aC.remove(aC.get(aPick));
                    else
                        bC.remove(bC.get(bPick));
                    coin = !coin;
                } else {
                    aPick = rand.nextInt(aC.size());
                    bPick = rand.nextInt(bC.size());
                    //System.out.println( problist[aC.get(aPick)][bC.get(bPick)]);
                    if (Math.random() < 1 - problist[bC.get(bPick)][aC.get(aPick)])
                        aC.remove(aC.get(aPick));
                    else
                        bC.remove(bC.get(bPick));
                    coin = !coin;
                }

            }
            if (aC.isEmpty()) {
                wincount += 1;
                bC.clear();
            } else
                aC.clear();
        }
        //System.out.println(((double)wincount/(double)MC));
        return ((double) wincount / (double) MC);

    }

    public double conquest_1b_nocoin(ArrayList<Integer> a, ArrayList<Integer> b) {
        Integer toBanA = new Integer(0);
        Integer toBanB = new Integer(0);
        double winrateA = 0.0;
        double winrateB = 0.0;
        double winrate = 0.0;
        for (int numA : a) {
            winrate = 0.0;
            for (int numB : b)
                winrate += problist[numA][numB];
            if (winrateA < winrate / a.size()) {
                toBanA = numA;
                winrateA = winrate / a.size();
            }
        }
        for (int numB : b) {
            winrate = 0.0;
            for (int numA : a)
                winrate += problist[numB][numA];
            if (winrateB < winrate / a.size()) {
                toBanB = numB;
                winrateB = winrate / a.size();
            }
        }
        a.remove(toBanA);
        b.remove(toBanB);
        Random rand = new Random();
        //System.out.println(b);
        ArrayList<Integer> aC = new ArrayList<Integer>();
        ArrayList<Integer> bC = new ArrayList<Integer>();
        int aPick;
        int bPick;
        int wincount = 0;
        for (int i = 0; i < MCLevel; i++) {
            for (Integer foo : a) {
                aC.add(foo);
            }
            for (Integer foo : b) {
                bC.add(foo);
            }
            while (!aC.isEmpty() && !bC.isEmpty()) {
                aPick = rand.nextInt(aC.size());
                bPick = rand.nextInt(bC.size());
                //System.out.println( problist[aC.get(aPick)][bC.get(bPick)]);
                if (Math.random() < problist[aC.get(aPick)][bC.get(bPick)])
                    aC.remove(aC.get(aPick));
                else
                    bC.remove(bC.get(bPick));
            }
            if (aC.isEmpty()) {
                wincount += 1;
                bC.clear();
            } else
                aC.clear();
        }
        //System.out.println(((double)wincount/(double)MC));
        return ((double) wincount / (double) MCLevel);
    }

    public double LHS_b05_1b_nocoin() {
        return 0;
    }

    public void pageRankTransform() {
        pagerankArray = new double[totalNumOfLineUps][totalNumOfLineUps];
/*        for (int i = 0; i < totalNumOfLineUps; i++) {
            for (int j = i; j < totalNumOfLineUps; j++) {
                if (lineupproblist[i][j] < 0.5 - drawlevel) {
                    pagerankArray[i][j] = 1;
                    pagerankArray[j][i] = 0;
                } else if (lineupproblist[j][i] < 0.5 - drawlevel) {
                    pagerankArray[j][i] = 1;
                    pagerankArray[i][j] = 0;
                } else {
                    pagerankArray[i][j] = 1;
                    pagerankArray[j][i] = 1;
                }

            }
        }
        */
        for (int i = 0; i < totalNumOfLineUps; i++) {
            double a = 0;
            for (int j = 0; j < totalNumOfLineUps; j++) {
                a += lineupproblist[j][i];
            }
            //System.out.println(a);
            for (int j = 0; j < totalNumOfLineUps; j++) {
                lineupproblist[j][i] = lineupproblist[j][i] / a;
                /*System.out.print(lineupproblist[j][i]);
                System.out.print(" ");*/
            }
            //System.out.println();
        }
    }

    public void pageRank() {

        double InitialPageRank;
        double OutgoingLinks = 0;
        double DampingFactor = 0.60;
        double TempPageRank[] = new double[10000];

        int ExternalNodeNumber;
        int InternalNodeNumber;
        int k; // For Traversing
        int ITERATION_STEP = 1;

        InitialPageRank = 1.0 / totalNumOfLineUps;
        pagerank = new double[10000];
        for (k = 0; k < totalNumOfLineUps; k++) {
            pagerank[k] = InitialPageRank;
        }

        while (ITERATION_STEP <= 10) // Iterations
        {
            //System.out.println(Arrays.toString(pagerank));
            // Store the PageRank for All Nodes in Temporary Array
            for (k = 0; k < totalNumOfLineUps; k++) {
                TempPageRank[k] = this.pagerank[k];
                this.pagerank[k] = 0;
            }

            for (InternalNodeNumber = 0; InternalNodeNumber < totalNumOfLineUps; InternalNodeNumber++) {
                for (ExternalNodeNumber = 0; ExternalNodeNumber < totalNumOfLineUps; ExternalNodeNumber++) {
                    this.pagerank[InternalNodeNumber] += TempPageRank[ExternalNodeNumber] * lineupproblist[InternalNodeNumber][ExternalNodeNumber];
                    //System.out.println(pagerankArray[InternalNodeNumber][ExternalNodeNumber]);
                }
                //System.out.println(this.pagerank[InternalNodeNumber]);
            }
            ITERATION_STEP = ITERATION_STEP + 1;
        }

// Add the Damping Factor to PageRank
        for (k = 0; k < totalNumOfLineUps; k++) {
            this.pagerank[k] = (1 - DampingFactor) + DampingFactor * this.pagerank[k];
        }
    }

    public ArrayList<String> finaliseResults() {
        if (sizeOfLineup == 4)
            return finaliseResults4();
        else if (sizeOfLineup == 3)
            return finaliseResults3();
        return null;
    }

    private ArrayList<String> finaliseResults3() {
        String[] strings = new String[totalNumOfLineUps];
        System.out.println(lineuparray[1].length);
        for (int i = 0; i < totalNumOfLineUps; i++) {
            strings[i] = deckNamesArray[lineuparray[i][0]] + ", " + deckNamesArray[lineuparray[i][1]] + ", " + deckNamesArray[lineuparray[i][2]];
        }
        Map<Double, String> map = new TreeMap<Double, String>();
        for (int i = 0; i < totalNumOfLineUps; i++) {
            map.put(pagerank[i], strings[i]);
        }
        //System.out.println(map);
        ArrayList<String> aa = new ArrayList<String>(map.values());
        return aa;
    }

    public ArrayList<String> finaliseResults4() {
        String[] strings = new String[totalNumOfLineUps];
        System.out.println(lineuparray[1].length);
        for (int i = 0; i < totalNumOfLineUps; i++) {
            strings[i] = deckNamesArray[lineuparray[i][0]] + ", " + deckNamesArray[lineuparray[i][1]] + ", " + deckNamesArray[lineuparray[i][2]] + ", " + deckNamesArray[lineuparray[i][3]];
        }
        Map<Double, String> map = new TreeMap<Double, String>();
        for (int i = 0; i < totalNumOfLineUps; i++) {
            map.put(pagerank[i], strings[i]);
        }
        //System.out.println(map);
        ArrayList<String> aa = new ArrayList<String>(map.values());
        return aa;
    }

}
