package de.veloper.remote;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;


import static org.kordamp.ikonli.materialdesign2.MaterialDesignE.EMAIL_RECEIVE_OUTLINE;
import static org.kordamp.ikonli.materialdesign2.MaterialDesignE.EMAIL_SEND_OUTLINE;
import static org.kordamp.ikonli.materialdesign2.MaterialDesignL.LOGOUT;

public class MiniMailStart extends JFrame {


    public MiniMailStart(String title) throws HeadlessException {
        super(title);
        JPanel guiPanel = createButtonPanel();
        add(guiPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(472,78);
        setResizable(false);
        setLocationRelativeTo(null);

    }
    private JPanel createButtonPanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(0,3));


        JButton postausgang = createButton(EMAIL_SEND_OUTLINE,
                "senden",
                "Öffnet den Posteingang und synchronisiert diesen.",
                "Gesendete E-Mails");
        postausgang.addActionListener(e -> empfangen());

        JButton posteingang = createButton(EMAIL_RECEIVE_OUTLINE,
                "empfangen",
                "Öffnet die gesendeten E-Mails.",
                "Empfangene E-Mails");
        posteingang.addActionListener(e-> senden());

        JButton ausloggen = createButton(LOGOUT,
                "ende",
                "User ausloggen und Programm beenden.",
                "Programm beenden");
        ausloggen.addActionListener(e -> beenden());
        tempPanel.add(posteingang);
        tempPanel.add(postausgang);
        tempPanel.add(ausloggen);

        pack();
        return tempPanel;
    }


    private JButton createButton(Ikon iconEnum, String actionCommand, String toolTipText, String text) {
        JButton tempButton = new JButton();
        FontIcon fontIcon = createFontIcon(iconEnum);
        tempButton.setIcon(fontIcon);
        tempButton.setActionCommand(actionCommand);
        tempButton.setToolTipText(toolTipText);
        tempButton.setText(text);
        tempButton.setBorderPainted(false);
        return tempButton;
    }

    private FontIcon createFontIcon(Ikon name) {
        FontIcon tempIcon = new FontIcon();
        tempIcon.setIkon(name);
        tempIcon.setIconSize(25);

        return tempIcon;
    }

    private void senden() {
        new Senden();
    }

    private void empfangen() {
        new Empfangen();
    }

    private void beenden() {
        System.exit(0);
    }
}
