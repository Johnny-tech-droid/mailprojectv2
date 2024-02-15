package de.veloper.remote;

import jakarta.mail.*;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

import static org.kordamp.ikonli.material2.Material2OutlinedAL.LOGIN;


/**
 * Diese Klasse repräsentiert das Login-Fenster der MiniMail-Anwendung.
 */

public class MiniMailLogin extends JDialog {
    // die Variablen für die Logindaten
    private JTextField benutzerFeld;
    private JPasswordField passwortFeld;
    // die eigenschaften für die Verbindung mit dem Server
    private Properties smtpProperties;
    private JLabel infoLabel;

    /**
     * Konstruktor für das MiniMail-Login-Fenster.
     * Initialisiert die GUI und setzt die Benutzerdaten.
     */
    public MiniMailLogin() {
        initGui();
        Constants.setBENUTZERNAME(benutzerFeld.getText());
        Constants.setPASSWORT(new String(passwortFeld.getPassword()));

    }

    /**
     * Initialisiert die grafische Benutzeroberfläche des Login-Fensters.
     */
    private void initGui() {

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);
        add(infoLabelPanel(), BorderLayout.NORTH);
        JPanel inputPanel = createInputPanel();

        add(inputPanel, BorderLayout.CENTER);
        JPanel loginBtnPanel = createLoginButtonPanel();
        add(loginBtnPanel, BorderLayout.SOUTH);
        setTitle("MiniMail Login");
        setSize(380, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationByPlatform(true);
        setVisible(true);


        setResizable(false);

    }
    private JPanel infoLabelPanel() {
        JPanel tempPanel = new JPanel();
        infoLabel = new JLabel("");
        infoLabel.setForeground(Color.RED);
        tempPanel.add(infoLabel);
        return tempPanel;

    }

    /**
     * Erstellt das Panel für die Benutzereingabe.
     *
     * @return Das erstellte Panel.
     */
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();

        GridLayout gridLayout = new GridLayout(0,2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);

        inputPanel.setLayout(gridLayout);

        JLabel benutzerLabel = new JLabel("Benutzer:");
        benutzerFeld = new JTextField();
        benutzerFeld.setColumns(20);

        JLabel passwortLabel = new JLabel("Passwort:");
        passwortFeld = new JPasswordField();
        passwortFeld.setColumns(20);

        inputPanel.add(benutzerLabel);
        inputPanel.add(benutzerFeld);
        inputPanel.add(passwortLabel);
        inputPanel.add(passwortFeld);
        inputPanel.setSize(100,110);
        return inputPanel;
    }


    /**
     * Authentifiziert den Benutzer über die SMTP-Verbindung.
     *
     * @return True, wenn die Authentifizierung erfolgreich ist, sonst False.
     */
    public boolean authenticate() {
        setSmtpProperties();
        // Verbindung zum E-Mail-Server für die Authentifizierung der eingegebenen Nutzerdaten
        // angepasst für die Verbindung mit Outlook.com
        Session session = Session.getInstance(smtpProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(benutzerFeld.getText(), new String(passwortFeld.getPassword()));
            }
        });
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Setzt die SMTP-Verbindungseigenschaften.
     */
    private void setSmtpProperties() {

        String smtpServer = Constants.getSmtpServer();
        String smtpPort = Constants.getSmtpPort();
        smtpProperties = createProperties(smtpServer, smtpPort);
    }

    /**
     * Erstellt die Eigenschaften für die Verbindung.
     *
     * @param host Hostname des Servers.
     * @param port Port des Servers.
     * @return Die erstellten Eigenschaften.
     */
    private Properties createProperties(String host, String port) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        return properties;
    }

    private JButton createLoginButton() {
        JButton tempButton = new JButton();
        FontIcon tempIcon = new FontIcon();
        tempIcon.setIkon(LOGIN);
        tempIcon.setIconSize(25);
        tempButton.setIcon(tempIcon);

        tempButton.setToolTipText("Jetzt einloggen");
        return tempButton;
    }

    private JPanel createLoginButtonPanel() {
        JPanel tempPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(0,3);
        tempPanel.setLayout(gridLayout);
        JButton loginBtn = createLoginButton();
        loginBtn.addActionListener(e -> checkForAuthentication());

        tempPanel.add(new JLabel());
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        tempPanel.add(loginBtn);

        return tempPanel;
    }

    private void checkForAuthentication() {
        if (authenticate()) {
            new MiniMailStart("MiniMail Start");
            Constants.setBENUTZERNAME(benutzerFeld.getText());
            Constants.setPASSWORT(new String(passwortFeld.getPassword()));
            dispose();
        } else {
            System.err.println("FAILURE");
            infoLabel.setText("Eingabe ungültig.\n E-Mail-Adresse oder Passwort falsch.");
        }
    }
}
