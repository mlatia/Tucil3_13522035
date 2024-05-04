import java.util.List;

public class Output {
    // Metode untuk mencetak jalur Word Ladder ke layar
    public static void printWordLadder(List<String> path) {
        if (path == null) {
            System.out.println("\nTidak ada jalur yang ditemukan.");
        } else {
            System.out.println("\nJalur Word Ladder:");
            for (String word : path) {
                System.out.println(word);
            }
        }
    }

    // Metode untuk mencetak jumlah langkah yang diperlukan
    public static void printStepCount(int exploredNodes) {
        System.out.println("Banyaknya node yang dikunjungi: " + exploredNodes);
    }

    // Metode untuk mencetak waktu eksekusi
    public static void printExecutionTime(long startTime, long endTime) {
        long executionTime = endTime - startTime;
        System.out.println("Waktu eksekusi: " + executionTime + " milidetik");
    }
}
