import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose input method: (1 = CLI, 2 = GUI , 3 = Exit)");
        if (scanner.hasNextInt()) {
            int inputChoice = scanner.nextInt();
            if (inputChoice == 1) {
                // Input via CLI
                runCLI();
            } else if (inputChoice == 2) {
                // Input via GUI
                RunGUI();
            } else if (inputChoice == 3) {
                return;
            } else {
                System.out.println("Invalid input method choice.");
                main(args);
            }
        } else {
            String invalidInput = scanner.next(); // Ambil input yang tidak valid
            System.out.println("Invalid input method choice: " + invalidInput + ". Please enter a valid integer.");
            main(args);
        }
    }


    public static void runCLI() {
        Scanner scanner = new Scanner(System.in);
        English dictionary = new English();
        WordLadderSolver wordLadderSolver = new WordLadderSolver();

        String startWord;
        System.out.println(ANSI_GREEN+"******************************************************************");
        System.out.println("********* Selamat bermain di program Word Ladder Solver! *********");
        System.out.println("******************************************************************" + ANSI_RESET);

        System.out.print("Masukkan kata awal (start word): ");
        startWord = scanner.nextLine().toLowerCase();
        
        // Validasi kata awal
        while (!dictionary.isValidWord(startWord) || startWord.split("\\s+").length > 1) {
            if (!dictionary.isValidWord(startWord)) {
                System.out.println(ANSI_RED + "Kata awal tidak valid." + ANSI_RESET);
            } else if (startWord.split("\\s+").length > 1) {
                System.out.println(ANSI_RED + "Tolong masukkan hanya satu kata." + ANSI_RESET);
            }
            System.out.print("Masukkan kata awal (start word): ");
            startWord = scanner.nextLine().toLowerCase();
        }
        
        String endWord;
        System.out.print("Masukkan kata akhir (end word): ");
        endWord = scanner.nextLine().toLowerCase();
        
        // Validasi kata akhir
        while (!dictionary.isValidWord(endWord) || endWord.length() != startWord.length() || endWord.split("\\s+").length > 1) {
            if (!dictionary.isValidWord(endWord)) {
                System.out.println(ANSI_RED + "Kata akhir tidak valid." + ANSI_RESET);
            } else if (endWord.length() != startWord.length()) {
                System.out.println(ANSI_RED + "Tolong masukkan kata yang memiliki panjang yang sama dengan kata awal." + ANSI_RESET);
            } 
            System.out.print("Masukkan kata akhir (end word): ");
            endWord = scanner.nextLine().toLowerCase();
        }

        System.out.print("Pilih algoritma (1 = UCS, 2 = Greedy Best First Search, 3 = A*): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Membersihkan karakter newline

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
                System.out.println("Pilihan algoritma tidak valid.");
                return;
        }
        long endTime = System.currentTimeMillis();

        @SuppressWarnings("unchecked")
        List<String> pathList =(List<String>) path.get(0);
        int exploredNodes = (int) path.get(1);
        System.out.println("Path: " + pathList);

        Output.printWordLadderCLI(pathList);
        Output.printStepCountCLI(exploredNodes); // Kurangi 1 karena jumlah langkah sama dengan jumlah node dikunjungi minus 1
        Output.printExecutionTimeCLI(startTime, endTime);
        NextOROut();
    }

    public static void NextOROut(){
        System.out.println("Apakah Anda ingin keluar? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String exitChoice = scanner.nextLine().toUpperCase();
        if (exitChoice.equals("Y")) {
            System.out.println(ANSI_GREEN+"Terima kasih telah menggunakan program Word Ladder Solver!"+ANSI_RESET);
            return;
        } else if (exitChoice.equals("N")) {
            runCLI();
        } else {
            System.out.println(ANSI_RED+"Pilihan tidak valid."+ANSI_RESET);
        }
    }

    public static void RunGUI() { // Ubah "RunGUI()" menjadi "RunGUI()"
        SwingUtilities.invokeLater(() -> {
            RunGUI gui = new RunGUI();
            gui.setVisible(true);
        });
    }
}