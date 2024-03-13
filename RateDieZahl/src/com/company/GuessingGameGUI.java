package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.concurrent.ThreadLocalRandom;

public class GuessingGameGUI extends JFrame {
    private int myNumber = ThreadLocalRandom.current().nextInt(0, 101);
    private int count = 0;

    // GUI-Komponenten
    private JTextField guessField;
    private JButton guessButton;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JLabel attemptLabel;

    public GuessingGameGUI() {
        super("Zahlenraten Spiel");
        setLayout(new BorderLayout(10, 10)); // Hauptlayout

        // Prompt-Label
        promptLabel = new JLabel("Rate die geheime Zahl zwischen 0 und 100");
        promptLabel.setHorizontalAlignment(JLabel.CENTER);
        add(promptLabel, BorderLayout.NORTH);

        // Eingabepanel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        guessField = new JTextField(10);
        inputPanel.add(guessField);
        guessButton = new JButton("Rate!");
        guessButton.setEnabled(false); // Button deaktiviert
        inputPanel.add(guessButton);
        add(inputPanel, BorderLayout.CENTER);

        // Unterpanel für Feedback und Versuche
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Feedback-Label
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(feedbackLabel);

        // Versuche-Label
        attemptLabel = new JLabel("Versuche: 0");
        attemptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(attemptLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Listener für den Rate-Button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAGuess();
            }
        });

        // Listener für das Textfeld, um Enter zu erkennen
        guessField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String text = guessField.getText();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && isValidGuess(text)) {
                    makeAGuess();
                }
            }
        });

        // Listener für das Textfeld, um den Zustand des Rate-Buttons zu aktualisieren
        guessField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            public void insertUpdate(DocumentEvent e) {
                update();
            }
            public void update() {
                String text = guessField.getText();
                guessButton.setEnabled(isValidGuess(text));
            }
        });
    }

    // Validierungsmethode
    private boolean isValidGuess(String text) {
        try {
            int guess = Integer.parseInt(text.trim());
            return guess >= 0 && guess <= 100; // Prüft, ob die Zahl im gültigen Bereich ist
        } catch (NumberFormatException e) {
            return false; // Keine gültige Zahl
        }
    }

    // Methode zum Verarbeiten von Rateversuchen
    private void makeAGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText().trim());
            guess(guess);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Bitte eine gültige Zahl eingeben.");
        }
    }

    // Logik für das Raten
    private void guess(int number) {
        count++;
        if (number == myNumber) {
            feedbackLabel.setText("<html>Richtig geraten!</html>");
            attemptLabel.setText("Du hast " + count + " Versuche gebraucht.");
            guessButton.setEnabled(false); // Deaktiviert den Button nach dem Gewinnen
        } else if (number < myNumber) {
            feedbackLabel.setText("Zahl ist zu klein.");
        } else {
            feedbackLabel.setText("Zahl ist zu groß.");
        }
        attemptLabel.setText("Versuche: " + count);
    }

    public static void main(String[] args) {
        GuessingGameGUI frame = new GuessingGameGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Fenstergröße angepasst
        frame.setVisible(true);
    }
}
