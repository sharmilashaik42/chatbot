import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class EnhancedChatbot extends JFrame {

    private final JTextArea chatArea;
    private final JTextField inputField;
    private final JButton sendButton;

    private String userName = null;
    private final String botName = "ChatBot";
    private final Random random = new Random();

    public EnhancedChatbot() {
        setTitle("🤖 Professional AI Chatbot");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background panel
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("🤖 Professional AI Assistant", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(header, BorderLayout.NORTH);

        // Chat area
        chatArea = new JTextArea();
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(Color.WHITE);
        chatArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        inputPanel.setOpaque(false);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(new Color(255, 193, 7));
        sendButton.setForeground(Color.BLACK);
        sendButton.setFocusPainted(false);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Welcome message
        appendBotMessage("Hello! I’m your professional AI assistant. How can I help you today?");

        // Events
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());
    }

    // ---------- Chat Logic ----------
    private void processInput() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        appendUserMessage(userInput);
        inputField.setText("");

        if (userInput.equalsIgnoreCase("bye")) {
            appendBotMessage("Goodbye! Have a wonderful day 😊");
            return;
        }

        appendBotMessage(getResponse(userInput));
    }

    private String getResponse(String input) {
        String text = input.toLowerCase();

        if (text.startsWith("my name is")) {
            userName = input.substring(10).trim();
            return "Nice to meet you, " + userName + ". I’ll remember your name.";
        }

        if (userName != null && text.matches(".*\\b(hi|hello|hey)\\b.*")) {
            return "Hello " + userName + "! How may I assist you today?";
        }

        if (text.contains("time")) {
            return "Current time: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        }

        if (text.contains("date")) {
            return "Today's date: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
        }

        if (text.contains("joke")) {
            String[] jokes = {
                    "Why don’t programmers like nature? Too many bugs.",
                    "Why did Java break up with Python? Too many exceptions!",
                    "Why was the computer tired? It ran too many processes."
            };
            return jokes[random.nextInt(jokes.length)];
        }

        if (text.contains("help")) {
            return "I can chat with you, tell jokes, give time/date, and remember your name.";
        }

        String[] defaults = {
                "That’s interesting. Tell me more.",
                "I see. What would you like to discuss next?",
                "Could you please elaborate?",
                "Thanks for sharing. Go on.",
                "I’m listening 😊"
        };

        return defaults[random.nextInt(defaults.length)];
    }

    // ---------- UI Helpers ----------
    private void appendUserMessage(String msg) {
        chatArea.append("You: " + msg + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void appendBotMessage(String msg) {
        chatArea.append(botName + ": " + msg + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // ---------- Gradient Panel ----------
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(102, 126, 234),
                    0, getHeight(), new Color(118, 75, 162)
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedChatbot().setVisible(true);
        });
    }
}
