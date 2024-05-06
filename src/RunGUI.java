import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class RunGUI extends JFrame {
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";

    private JTextField startTextField;
    private JTextField endTextField;
    private JComboBox<String> algorithmComboBox;
    private JTextArea outputTextArea;

    public RunGUI() {
        // setTitle("Word Ladder Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        // Set background color
        Color backgroundColor = Color.decode("#4877E9");
        getContentPane().setBackground(backgroundColor);

        JLabel logoLabel = new JLabel(new ImageIcon("assets/logo.png"));
        add(logoLabel, BorderLayout.WEST);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 20, 0, 0); // Padding bottom 20
        inputPanel.setBackground(Color.decode("#4877E9"));

        JLabel startLabel = new JLabel("           Start Word:  ");
        startLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        startLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0); // Padding bottom 10
        inputPanel.add(startLabel, gbc);

        startTextField = new JTextField();
        startTextField.setPreferredSize(new Dimension(250, 35));
        startTextField.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 10, 10); // Padding left 20, padding bottom 10
        inputPanel.add(startTextField, gbc);

        JLabel endLabel = new JLabel("         End Word:  ");
        endLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        endLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0); // Padding bottom 10
        inputPanel.add(endLabel, gbc);

        endTextField = new JTextField();
        endTextField.setPreferredSize(new Dimension(250, 35));
        endTextField.setFont(new Font("Poppins", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 10, 0); // Padding left 20, padding bottom 10
        inputPanel.add(endTextField, gbc);
        
        JLabel algorithmLabel = new JLabel("Select Algorithm:    ");
        algorithmLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        algorithmLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4; // Span two columns
        gbc.insets = new Insets(20, 0, 0, 0); // Padding top 20
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(algorithmLabel, gbc);

        String[] algorithms = {"UCS", "Greedy Best First Search", "A*"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setPreferredSize(new Dimension(250, 30));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0); // Padding bottom 20
        inputPanel.add(algorithmComboBox, gbc);

        inputPanel.add(startLabel);
        inputPanel.add(startTextField);
        inputPanel.add(endLabel);
        inputPanel.add(endTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#4877E9")); // Warna latar belakang
        buttonPanel.setPreferredSize(new Dimension(400, 35));
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveWordLadder();
            }
        });
        buttonPanel.add(solveButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setPreferredSize(new Dimension(400, 180));
        resultPanel.setBackground(Color.decode("#4877E9")); // Warna latar belakang
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK), // Border dengan warna hitam
                new EmptyBorder(10, 50, 10, 50))); // Margin kiri 10px
        JLabel resultLabel = new JLabel("Result");
        resultLabel.setFont(new Font("Poppins", Font.BOLD, 25));
        resultLabel.setForeground(Color.WHITE);
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(logoLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER); // Tambahkan mainPanel ke area tengah frame
        add(resultPanel, BorderLayout.SOUTH);
    }

    private void solveWordLadder() {
        String startWord = startTextField.getText().toLowerCase();
        String endWord = endTextField.getText().toLowerCase();
        int choice = algorithmComboBox.getSelectedIndex() + 1;

        English dictionary = new English(); 
        if (!dictionary.isValidWord(startWord)) {
            // Kata tidak valid, tampilkan pesan kesalahan
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Kata awal tidak valid.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            });
            return; // Keluar dari metode jika kata tidak valid
        } else if (startWord.split("\\s+").length > 1) {
            // Kata tidak valid, tampilkan pesan kesalahan
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Tolong masukkan hanya satu kata.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            });
            return; // Keluar dari metode jika kata tidak valid
        }

        if (!dictionary.isValidWord(endWord)) {
            // Kata tidak valid, tampilkan pesan kesalahan
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Kata akhir tidak valid.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            });
            return; // Keluar dari metode jika kata tidak valid
        } else if (startWord.split("\\s+").length > 1) {
            // Kata tidak valid, tampilkan pesan kesalahan
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Tolong masukkan hanya satu kata.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            });
            return; // Keluar dari metode jika kata tidak valid
        }
    
        WordLadderSolver wordLadderSolver = new WordLadderSolver();

        List<Object> path = null;
        long startTime = System.currentTimeMillis();
        switch (choice) {
            case 1:
                path = wordLadderSolver.solveUCS(startWord, endWord, dictionary);
                break;
            case 2:
                path = wordLadderSolver.solveGreedyBestFirstSearch(startWord, endWord, dictionary);
                break;
            case 3:
                path = wordLadderSolver.solveAStar(startWord, endWord, dictionary);
                break;
            default:
                outputTextArea.append("Invalid algorithm choice.");
                return;
        }

        // Check if path is found
        if (path == null) {
            outputTextArea.append(ANSI_PURPLE + "Oops!! No path found!!\n");
            return;
        }

        long endTime = System.currentTimeMillis();
        int exploredNodes = (int) path.get(1);
        @SuppressWarnings("unchecked")
        List<String> pathList = (List<String>) path.get(0);
        Output.printWordLadder(pathList, outputTextArea);
        Output.printStepCount(exploredNodes, outputTextArea);
        Output.printExecutionTime(startTime, endTime, outputTextArea);
        // Perbarui tampilan
        revalidate();
        repaint();

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RunGUI gui = new RunGUI();
            gui.setVisible(true);
        });
    }
}
