import java.util.Scanner;
import java.util.List;
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    
    // @Override
    // public void start(Stage primaryStage) throws Exception {
    //     Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    //     primaryStage.setTitle("Word Ladder");
    //     primaryStage.setScene(new Scene(root, 300, 275));
    //     primaryStage.show();
    // }

    public static void main(String[] args) {
        // launch(args);
        Scanner scanner = new Scanner(System.in);
        English dictionary = new English();
        WordLadderSolver wordLadderSolver = new WordLadderSolver();

        String startWord;
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

        List<String> path = null;
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
        // Periksa apakah jalur ditemukan atau tidak
        if (path == null) {
            System.out.println(ANSI_PURPLE + "Ooppss!! Tidak ada jalur yang ditemukan!!" + ANSI_RESET);
        }

        long endTime = System.currentTimeMillis();
        Output.printWordLadder(path);
        Output.printStepCount(path.size()); // Kurangi 1 karena jumlah langkah sama dengan jumlah node dikunjungi minus 1
        Output.printExecutionTime(startTime, endTime);

        System.out.print("Apakah Anda ingin keluar? (Y/N): ");
        String exitChoice = scanner.nextLine().toUpperCase();
        if (exitChoice.equals("Y")) {
            return;
        }else{
            System.out.println("");
            main(args);
        }
    }
}  