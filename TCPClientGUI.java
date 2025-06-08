import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TCPClientGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public TCPClientGUI() {
        setTitle("TCP Client Chat");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        new Thread(this::startClient).start();
    }

    private void startClient() {
        try {
            socket = new Socket("localhost", 5000);
            appendMessage("Connected to server.");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                appendMessage("Server: " + message);
                if (message.equalsIgnoreCase("bye")) {
                    appendMessage("Server disconnected.");
                    break;
                }
            }

            closeConnections();
        } catch (IOException e) {
            appendMessage("Error: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            appendMessage("Client: " + message);
            output.println(message);
            inputField.setText("");
            if (message.equalsIgnoreCase("bye")) {
                appendMessage("Client stopped.");
                closeConnections();
                System.exit(0);
            }
        }
    }

    private void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
        });
    }

    private void closeConnections() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            appendMessage("Error closing connections: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TCPClientGUI().setVisible(true);
        });
    }
}
