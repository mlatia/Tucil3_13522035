import java.awt.Font;
import java.util.List;
import javax.swing.JTextArea;

public class Output {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Method to print Word Ladder path to JTextArea
    public static void printWordLadder(List<String> path, JTextArea outputTextArea) {
        outputTextArea.setFont(new Font("Poppins", Font.PLAIN, 16));
        if (path == null) {
            outputTextArea.append("\nPath tidak ditemukan.\n");
        } else {
            outputTextArea.append("\nWord Ladder Path: ");
            if (path.isEmpty()) {
                outputTextArea.append("Path tidak ditemukan..\n");
            } else {
                for (String word : path) {
                    if(path.indexOf(word) == path.size()-1) {
                        outputTextArea.append(word);
                    } else {
                        outputTextArea.append(word + "-->");
                    }
                }
            }
        }
        outputTextArea.append("\nPanjang path : " + path.size() + "\n");
    }
    // Method to print Word Ladder path using CLI
    public static void printWordLadderCLI(List<String> path) {
        if (path == null) {
            System.out.println("\nPath tidak ditemukan..\n");
        } else {
            System.out.println("\nWord Ladder Path:\n");
            if (path.isEmpty()) {
                System.out.println("Path tidak ditemukan..\n");
            } else {
                for (String word : path) {
                    System.out.println(word);
                }
            }
        }
    }
    // Methid to print the Banyak node yang dikunjungi using CLI   
    public static void printStepCountCLI(int exploredNodes) {
        System.out.println("Banyak node yang dikunjungi: " + exploredNodes);
    }
    // Method to print the Banyak node yang dikunjungi to JTextArea
    public static void printStepCount(int exploredNodes, JTextArea outputTextArea) {
        outputTextArea.setFont(new Font("Poppins", Font.PLAIN, 16));
        outputTextArea.append("Banyak node yang dikunjungi: " + exploredNodes + "\n");
    }

    // Method to print Waktu Eksekusi to JTextArea
    public static void printExecutionTime(long startTime, long endTime, JTextArea outputTextArea) {
        outputTextArea.setFont(new Font("Poppins", Font.PLAIN, 16));
        double executionTime = (double) (endTime - startTime) / 1000.0;
        String formattedExecutionTime = String.format("%.8f", (double) executionTime);
        outputTextArea.append("Waktu Eksekusi: " + formattedExecutionTime + " detik\n");
    }

    // Method to print Waktu Eksekusi using CLI
    public static void printExecutionTimeCLI(long startTime, long endTime) {
        double executionTime = (double) (endTime - startTime) / 1000.0;
        String formattedExecutionTime = String.format("%.8f", (double) executionTime);
        System.out.println("Waktu Eksekusi: " + formattedExecutionTime + " detik\n");
    }
}
