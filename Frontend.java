package com.company;

import javafx.beans.property.adapter.JavaBeanFloatProperty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

import static jdk.nashorn.internal.objects.NativeError.getStackTrace;

public class Frontend extends JFrame implements ActionListener {
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JFileChooser probDecks;
    private JButton getprobDecksButton;
    private JFileChooser deckSeparators;
    private JButton getdeckSeparatorsButton;
    private JRadioButton Cb05ban;
    private ButtonGroup matchupGroup;
    private JPanel matchRadioPanel;
    private JRadioButton lowAcc;
    private JRadioButton midAcc;
    private JRadioButton highAcc;
    private ButtonGroup accuracyGroup;
    private JPanel accRadioPanel;
    private JButton submitter;
    private File pD;
    private File dS;
    private File dN;
    private File mC;
    private JFileChooser deckNamesChooser;
    private JButton deckNamesChooserButton;
    private JRadioButton Cb03ban;
    private JFileChooser metaChooser;
    private JButton metaChooserButton;
    private JPanel metaRadioPanel;
    private JRadioButton noMeta;
    private JRadioButton lowMeta;
    private JRadioButton highMeta;
    private ButtonGroup metaGroup;


    public Frontend() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("LineUp Calculator");
        mainFrame.setSize(800, 300);
        mainFrame.setLayout(new GridLayout(2, 4));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        //titleLabel = new JLabel("<html>Welcome to the LineUp Calculator.<br/>Please consult the attached document for a guide or contact me. Good luck in your tournaments!<br/>Please insert the following data:</html>", JLabel.CENTER);
        deckNamesChooser = new JFileChooser();
        deckNamesChooserButton = new JButton("Get the deck names file");
        deckNamesChooserButton.addActionListener(this);
        metaChooser = new JFileChooser();
        metaChooserButton = new JButton("Get the meta file");
        metaChooserButton.addActionListener(this);
        probDecks = new JFileChooser();
        getprobDecksButton = new JButton("Get the probability file");
        getprobDecksButton.addActionListener(this);
        metaRadioPanel = new JPanel(new GridLayout(0, 1));
        noMeta = new JRadioButton("No Meta");
        noMeta.setActionCommand("0");
        noMeta.addActionListener(this);
        lowMeta = new JRadioButton("Low Meta");
        lowMeta.setActionCommand("8");
        lowMeta.addActionListener(this);
        highMeta = new JRadioButton("High Meta");
        highMeta.setActionCommand("10");
        highMeta.addActionListener(this);
        metaRadioPanel.add(noMeta);
        metaRadioPanel.add(lowMeta);
        metaRadioPanel.add(highMeta);
        metaGroup = new ButtonGroup();
        metaGroup.add(noMeta);
        metaGroup.add(lowMeta);
        metaGroup.add(highMeta);
        accuracyGroup = new ButtonGroup();
        lowAcc = new JRadioButton("Low accuracy");
        lowAcc.setActionCommand("50");
        lowAcc.addActionListener(this);
        midAcc = new JRadioButton("Medium accuracy(Recommended)");
        midAcc.setActionCommand("200");
        midAcc.addActionListener(this);
        highAcc = new JRadioButton("High accuracy");
        highAcc.setActionCommand("500");
        highAcc.addActionListener(this);
        accuracyGroup.add(lowAcc);
        accuracyGroup.add(midAcc);
        accuracyGroup.add(highAcc);
        accRadioPanel = new JPanel(new GridLayout(0, 1));
        accRadioPanel.add(lowAcc);
        accRadioPanel.add(midAcc);
        accRadioPanel.add(highAcc);
        deckSeparators = new JFileChooser();
        getdeckSeparatorsButton = new JButton("Get the faction splitting file");
        getdeckSeparatorsButton.addActionListener(this);
        Cb05ban = new JRadioButton("Conquest Best of 5 with 1 ban");
        Cb05ban.setActionCommand("Cb05ban");
        Cb03ban = new JRadioButton("Conquest Best of 3 with 1 ban");
        Cb03ban.setActionCommand("Cb03ban");
        matchupGroup = new ButtonGroup();
        matchupGroup.add(Cb05ban);
        matchupGroup.add(Cb03ban);
        matchRadioPanel = new JPanel(new GridLayout(0, 1));
        matchRadioPanel.add(Cb05ban);
        matchRadioPanel.add(Cb03ban);
        submitter = new JButton("Submit data");
        submitter.setActionCommand("submit");
        submitter.addActionListener(this);
        mainFrame.add(getprobDecksButton);
        mainFrame.add(getdeckSeparatorsButton);
        mainFrame.add(deckNamesChooserButton);
        mainFrame.add(metaChooserButton);
        mainFrame.add(accRadioPanel);
        mainFrame.add(matchRadioPanel);
        mainFrame.add(metaRadioPanel);
        mainFrame.add(submitter);
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if ("submit".equals(e.getActionCommand())) {
            try {
                String accu = accuracyGroup.getSelection().getActionCommand();
                String matchup = matchupGroup.getSelection().getActionCommand();
                String metaL = metaGroup.getSelection().getActionCommand();
                long startTime = System.currentTimeMillis();
                Starter start = new Starter(pD, dS, dN, mC, accu, matchup, metaL);

                ArrayList<String> g = start.returnStuff();
                JFrame popUp = new JFrame();
                popUp.setSize(400, 300);
                popUp.setLayout(new GridLayout(20, 1));
                for (int i = 0; i < Math.min(g.size(), 20); i++) {
                    popUp.add(new JLabel("Nr." + (i + 1) + ": " + g.get(g.size() - i - 1)));
                }
                popUp.setVisible(true);
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println(elapsedTime);
            } catch (FileNotFoundException e5) {
                JFrame popUp = new JFrame();
                popUp.setSize(100, 100);
                /*StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                System.out.println(sw.toString().replace("at","</br>"));
                popUp.add(new JTextArea(sw.toString()));*/
                popUp.add(new JLabel("File not found"));
                popUp.setVisible(true);
            } catch (Exception e5) {
                JFrame popUp = new JFrame();
                popUp.setSize(100, 100);
                popUp.add(new JLabel("There was a problem"));
                e5.printStackTrace();
                popUp.setVisible(true);
            }
        } else if (e.getSource() == getprobDecksButton) {
            int returnVal = probDecks.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                pD = probDecks.getSelectedFile();
            }
        } else if (e.getSource() == getdeckSeparatorsButton) {
            int returnVal1 = deckSeparators.showOpenDialog(this);
            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                dS = deckSeparators.getSelectedFile();
            }
        } else if (e.getSource() == deckNamesChooserButton) {
            int returnVal2 = deckNamesChooser.showOpenDialog(this);
            if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                dN = deckNamesChooser.getSelectedFile();
            }
        } else if (e.getSource() == metaChooserButton) {
            int returnVal3 = metaChooser.showOpenDialog(this);
            if (returnVal3 == JFileChooser.APPROVE_OPTION) {
                mC = metaChooser.getSelectedFile();
            }
        }
    }
}