import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TCPServerGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    public TCPServerGUI() {
        setTitle("TCP Server Chat");
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

        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
            appendMessage("Server started. Waiting for client...");

            clientSocket = serverSocket.accept();
            appendMessage("Client connected.");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Thread to listen for messages from client
            String message;
            while ((message = input.readLine()) != null) {
                appendMessage("Client: " + message);
                if (message.equalsIgnoreCase("bye")) {
                    appendMessage("Client disconnected.");
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
            appendMessage("Server: " + message);
            output.println(message);
            inputField.setText("");
            if (message.equalsIgnoreCase("bye")) {
                appendMessage("Server stopped.");
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
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            appendMessage("Error closing connections: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TCPServerGUI().setVisible(true);
        });
    }
}
