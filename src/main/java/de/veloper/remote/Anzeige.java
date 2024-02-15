package de.veloper.remote;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

import static org.kordamp.ikonli.material2.Material2OutlinedAL.CANCEL;
import static org.kordamp.ikonli.material2.Material2OutlinedAL.FORWARD_TO_INBOX;
import static org.kordamp.ikonli.material2.Material2OutlinedMZ.REPLY;


public class Anzeige extends JDialog {
    //für die Eingabefelder
    private JTextField empfaengerFeld, betreffFeld;
    private JTextArea inhaltFeld;
    //für die Schaltflächen
    private JButton cancelBtn, antwortenBtn, weiterleitenBtn;
    private JPanel replyBtnPanel, forwardBtnPanel, cancelBtnPanel;
    private final Boolean replyBtnShow;

    //die innere Klasse für den ActionListener


    // AUFGABE 2 EINSENDEAUFGABE JAVA17D
    // antworten und weiterleiten Methoden sind abgewandelt von empfangenInstanz.getTabelle()stellen() für das Anzeigen
    // einer empfangenen Nachricht

    //der Konstruktor
    public Anzeige(JFrame parent, boolean modal, String ID, String empfaenger, String betreff, String inhalt, Boolean replyBtnBool) {
        super(parent, modal);
        this.replyBtnShow = replyBtnBool;
        setTitle("Anzeige");
        //die Oberfläche erstellen
        initGui(ID, empfaenger, betreff, inhalt);
        // Standardoperationen setzen
        // hier den Dialog ausblenden und löschen
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initGui(String iD, String empfaenger, String betreff, String inhalt) {
        setLayout(new BorderLayout());

        JPanel oben = createTopPanel(empfaenger, betreff);
        add(oben, BorderLayout.NORTH);
        inhaltFeld = initTextArea(inhalt);
        //das Feld setzen wir in ein Scrollpane
        JScrollPane scroll = createScrollPane(inhaltFeld);
        add(scroll, BorderLayout.CENTER);
        //die Felder können nicht bearbeitet werden
        empfaengerFeld.setEditable(false);
        betreffFeld.setEditable(false);
        inhaltFeld.setEditable(false);
        //die Schaltfläche
        add(createBottomPanel(), BorderLayout.SOUTH);
        //anzeigen
        setSize(600, 300);
        setVisible(true);
    }
    private JTextArea initTextArea(String inhalt) {
        inhaltFeld = new JTextArea(inhalt);
        inhaltFeld.setLineWrap(true);

        return inhaltFeld;
    }
    private JScrollPane createScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }
    private JPanel createTopPanel(String empfaenger, String betreff) {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(0, 2));
        tempPanel.add(new JLabel("Empfänger: "));
        empfaengerFeld = new JTextField(empfaenger);
        tempPanel.add(empfaengerFeld);
        tempPanel.add(new JLabel("Betreff: "));
        betreffFeld = new JTextField(betreff);
        tempPanel.add(betreffFeld);
        return tempPanel;
    }

    private JPanel createBottomPanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(0,3));
        cancelBtn = createButton(CANCEL, "ok", "Abbrechen");
        cancelBtn.addActionListener(e -> dispose());

        cancelBtnPanel = createPanel(cancelBtn);
        tempPanel.add(cancelBtnPanel);
        antwortenBtn = createButton(REPLY, "antworten", "Antworten");
        antwortenBtn.addActionListener(e -> antworten());

        replyBtnPanel = createPanel(antwortenBtn);
        // hiermit wird geprüft, ob es sich um eine gesendete oder empfangene
        // bei gesendeten E-Mails wird die boolean variable auf false gesetzt, sonst true
        if (replyBtnShow)
            tempPanel.add(replyBtnPanel);
        weiterleitenBtn = createButton(FORWARD_TO_INBOX, "weiterleiten", "Weiterleiten");
        weiterleitenBtn.addActionListener(e -> weiterleiten());

        forwardBtnPanel = createPanel(weiterleitenBtn);
        tempPanel.add(forwardBtnPanel);
        return tempPanel;
    }
    private JPanel createPanel(JButton panelButton) {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(panelButton, BorderLayout.NORTH);
        return tempPanel;
    }
    private JButton createButton(Ikon iconEnum, String actionCommand, String buttonText) {
        JButton tempButton = new JButton();
        FontIcon tempIcon = new FontIcon();
        tempIcon.setIkon(iconEnum);
        tempIcon.setIconSize(25);
        tempButton.setIcon(tempIcon);
        tempButton.setActionCommand(actionCommand);
        tempButton.setText(buttonText);
        return tempButton;
    }

    public void antworten() {
        String empfaenger = empfaengerFeld.getText();
        String betreff = "AW: " + betreffFeld.getText();
        String orgInhalt = inhaltFeld.getText();
        String inhalt = "\n\n------- Text der ursprünglichen Nachricht --------\n" + orgInhalt;
        new NeueNachricht(new JFrame(), true, empfaenger, betreff, inhalt);

    }
    public void weiterleiten() {
        String betreff = "WG: " + betreffFeld.getText();
        String orgInhalt = inhaltFeld.getText();
        String inhalt = "\n\n------- Text der ursprünglichen Nachricht --------\n" + orgInhalt;
        new NeueNachricht(new JFrame(), true, betreff, inhalt);
    }


}
