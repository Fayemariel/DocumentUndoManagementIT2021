
package documentundomanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DocumentWindow extends JFrame {

    // Objects
    private Document document;
    private ActionStack actionStack;

    // GUI components
    private JTextArea txtDocument;
    private JTextArea txtHistory;
    private JTextField txtSearch;

    private JButton btnSave;
    private JButton btnUndo;
    private JButton btnClearText;
    private JButton btnClearHistory;
    private JButton btnSearch;

    // Constructor
    public DocumentWindow() {

        document = new Document();
        actionStack = new ActionStack();

        setTitle("Document Undo Management");
        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    // Create application interface
    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // HEADER
        JLabel title = new JLabel(
            "DOCUMENT UNDO MANAGEMENT",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(23, 36, 58));
        title.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        add(title, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // DOCUMENT EDITOR
        JPanel editorPanel = new JPanel(new BorderLayout(5, 5));

        JLabel editorLabel = new JLabel("Document Editor");
        editorLabel.setFont(new Font("Arial", Font.BOLD, 16));

        txtDocument = new JTextArea();
        txtDocument.setFont(new Font("Arial", Font.PLAIN, 15));
        txtDocument.setLineWrap(true);
        txtDocument.setWrapStyleWord(true);

        JScrollPane editorScroll = new JScrollPane(txtDocument);

        editorPanel.add(editorLabel, BorderLayout.NORTH);
        editorPanel.add(editorScroll, BorderLayout.CENTER);

        // EDITOR BUTTONS
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        btnSave = new JButton("Save Edit");
        btnUndo = new JButton("Undo");
        btnClearText = new JButton("Clear Text");

        btnSave.setBackground(new Color(37, 99, 235));
        btnSave.setForeground(Color.WHITE);

        btnUndo.setBackground(new Color(71, 85, 105));
        btnUndo.setForeground(Color.WHITE);

        btnClearText.setBackground(new Color(220, 38, 38));
        btnClearText.setForeground(Color.WHITE);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUndo);
        buttonPanel.add(btnClearText);

        // HISTORY PANEL
        JPanel historyPanel = new JPanel(new BorderLayout(5, 5));

        JLabel historyLabel =
            new JLabel("Search Recorded Actions");

        historyLabel.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        // SEARCH FIELD AND BUTTON
        txtSearch = new JTextField();
        btnSearch = new JButton("Search");

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        // HISTORY DISPLAY
        txtHistory = new JTextArea(8, 30);
        txtHistory.setEditable(false);
        txtHistory.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane historyScroll = new JScrollPane(txtHistory);

        // HISTORY TOP SECTION
        JPanel historyTopPanel = new JPanel(
            new BorderLayout(5, 5)
        );

        historyTopPanel.add(historyLabel, BorderLayout.NORTH);
        historyTopPanel.add(searchPanel, BorderLayout.SOUTH);

        // CLEAR HISTORY BUTTON
        JPanel clearHistoryPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        btnClearHistory = new JButton("Clear History");
        btnClearHistory.setBackground(new Color(220, 38, 38));
        btnClearHistory.setForeground(Color.WHITE);

        clearHistoryPanel.add(btnClearHistory);

        // Arrange history components
        historyPanel.add(historyTopPanel, BorderLayout.NORTH);
        historyPanel.add(historyScroll, BorderLayout.CENTER);
        historyPanel.add(clearHistoryPanel, BorderLayout.SOUTH);

        // COMBINE PANELS
        JPanel centerPanel = new JPanel(
            new BorderLayout(10, 10)
        );

        centerPanel.add(editorPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // BUTTON EVENTS

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEdit();
            }
        });

        btnUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undoEdit();
            }
        });

        btnClearText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearDocument();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchHistory();
            }
        });

        btnClearHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearActionHistory();
            }
        });

        updateHistory();
    }

    // SAVE EDIT
    private void saveEdit() {

        String oldText = document.getContent();
        String newText = txtDocument.getText();

        if (oldText.equals(newText)) {
            JOptionPane.showMessageDialog(
                this,
                "No new changes to save."
            );
            return;
        }

        TextEditAction action =
            new TextEditAction(oldText, newText);

        if (actionStack.push(action)) {

            document.setContent(newText);
            updateHistory();

            JOptionPane.showMessageDialog(
                this,
                "Edit saved successfully!"
            );

        } else {
            JOptionPane.showMessageDialog(
                this,
                "Cannot save. Stack or history is full."
            );
        }
    }

    // UNDO EDIT
    private void undoEdit() {

        DocumentAction action = actionStack.pop();

        if (action == null) {
            JOptionPane.showMessageDialog(
                this,
                "No saved edits to undo."
            );
            return;
        }

        document.setContent(action.undo());
        txtDocument.setText(document.getContent());

        updateHistory();

        JOptionPane.showMessageDialog(
            this,
            "Last edit successfully undone!"
        );
    }

    // CLEAR DOCUMENT TEXT
    private void clearDocument() {

        String oldText = document.getContent();

        if (oldText.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "The document is already empty."
            );
            return;
        }

        ClearDocumentAction action =
            new ClearDocumentAction(oldText);

        if (actionStack.push(action)) {

            document.clear();
            txtDocument.setText("");

            updateHistory();

            JOptionPane.showMessageDialog(
                this,
                "Document text cleared successfully!"
            );

        } else {
            JOptionPane.showMessageDialog(
                this,
                "Cannot clear. Stack or history is full."
            );
        }
    }

    // SEARCH HISTORY
    private void searchHistory() {

        String keyword = txtSearch.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a keyword."
            );
            return;
        }

        txtHistory.setText("");

        boolean found = false;

        for (int i = 0;
             i < actionStack.getHistoryCount();
             i++) {

            DocumentAction action =
                actionStack.getHistoryAction(i);

            String description = action.getDescription();
            String text = action.getNewText();

            if (description.toLowerCase().contains(
                    keyword.toLowerCase())
                    || text.toLowerCase().contains(
                    keyword.toLowerCase())) {

                txtHistory.append(
                    "Action: " + description + "\n"
                    + "Text: " + text + "\n"
                    + "-------------------------\n"
                );

                found = true;
            }
        }

        if (!found) {
            txtHistory.setText("No matching actions found.");
        }
    }

    // DISPLAY ALL HISTORY
    private void updateHistory() {

        txtHistory.setText("");

        for (int i = 0;
             i < actionStack.getHistoryCount();
             i++) {

            DocumentAction action =
                actionStack.getHistoryAction(i);

            txtHistory.append(
                (i + 1) + ". "
                + action.getDescription()
                + "\n"
            );
        }
    }

    // CLEAR ACTION HISTORY
    private void clearActionHistory() {

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete all recorded actions?",
            "Clear History",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            actionStack.clearHistory();

            updateHistory();

            txtSearch.setText("");

            JOptionPane.showMessageDialog(
                this,
                "Action history cleared successfully!"
            );
        }
    }
}
