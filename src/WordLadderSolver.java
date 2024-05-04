import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class WordLadderSolver {
    // Kelas untuk merepresentasikan elemen dalam antrian prioritas untuk algoritma UCS
    static class QueueElementUCS implements Comparable<QueueElementUCS> {
        double final_score;
        String word;

        public QueueElementUCS(double final_score, String word) {
            this.final_score = final_score;
            this.word = word;
        }

        @Override
        public int compareTo(QueueElementUCS other) {
            return Double.compare(this.final_score, other.final_score);
        }
    }

    // Metode untuk menyelesaikan Word Ladder menggunakan algoritma UCS
    public List<String> solveUCS(String startWord, String endWord, English dictionary) {
        // Inisialisasi antrian prioritas untuk UCS
        PriorityQueue<QueueElementUCS> queueUCS = new PriorityQueue<>();
        QueueElementUCS node = new QueueElementUCS(0, startWord);
        queueUCS.add(node);

        // Inisialisasi jalur awal dan biaya sejauh ini
        Map<String, String> rawPath = new HashMap<>();
        Map<String, Double> costSoFar = new HashMap<>();
        costSoFar.put(startWord, 0.0);
        Set<String> explored = new HashSet<>();

        // Loop utama untuk melakukan pencarian
        while (!queueUCS.isEmpty()) {
            QueueElementUCS currentElement = queueUCS.poll();
            String currentWord = currentElement.word;

            // Jika kata saat ini adalah kata akhir, maka pencarian selesai
            if (currentWord.equals(endWord)) {
                break;
            }

            // Tandai kata saat ini sebagai sudah dieksplorasi
            explored.add(currentWord);

            // Cari kata-kata tetangga yang berbeda satu huruf dengan kata saat ini
            List<String> neighbors = findNeighbors(currentWord, dictionary);
            // System.out.println(neighbors);
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    // Hitung biaya baru untuk mencapai tetangga ini
                    double newCost = costSoFar.get(currentWord) + 1; // Biaya selalu 1 karena perbedaan hanya satu huruf

                    // Jika tetangga belum pernah dikunjungi atau biaya baru lebih rendah, perbarui informasi
                    if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                        costSoFar.put(neighbor, newCost);
                        double priority = newCost;
                        QueueElementUCS newElement = new QueueElementUCS(priority, neighbor);
                        queueUCS.add(newElement);
                        rawPath.put(neighbor, currentWord);
                    }
                }
            }
        }

        // Jika tidak ada jalur yang ditemukan, kembalikan null
        if (!costSoFar.containsKey(endWord)) {
            return null;
        }

        // Rekonstruksi jalur dari kata awal ke kata akhir
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        path.add(0, startWord);

        return path;
    }

    // Metode untuk mencari kata-kata tetangga yang berbeda satu huruf dengan kata tertentu
    private List<String> findNeighbors(String word, English dictionary) {
        List<String> neighbors = new ArrayList<>();
        // Iterasi melalui semua kata dalam kamus
        for (String candidate : dictionary.getWords()) {
            // Jika panjangnya sama dengan kata saat ini dan berbeda satu huruf, tambahkan sebagai tetangga
            if (candidate.length() == word.length() && countDifferences(candidate, word) == 1) {
                neighbors.add(candidate);
            }
        }
        return neighbors;
    }

    // Metode untuk menghitung jumlah perbedaan antara dua kata
    private int countDifferences(String word1, String word2) {
        int count = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    static class QueueElementGreedyBFS implements Comparable<QueueElementGreedyBFS> {
        double heuristic_score;
        String word;

        public QueueElementGreedyBFS(double heuristic_score, String word) {
            this.heuristic_score = heuristic_score;
            this.word = word;
        }

        @Override
        public int compareTo(QueueElementGreedyBFS other) {
            return Double.compare(this.heuristic_score, other.heuristic_score);
        }
    }

    public List<String> solveGreedyBestFirstSearch(String startWord, String endWord, English dictionary) {
        // Inisialisasi antrian prioritas untuk Greedy BFS
        PriorityQueue<QueueElementGreedyBFS> queueGreedyBFS = new PriorityQueue<>();
        QueueElementGreedyBFS node = new QueueElementGreedyBFS(heuristic(startWord, endWord), startWord);
        queueGreedyBFS.add(node);

        // Inisialisasi jalur awal
        Map<String, String> rawPath = new HashMap<>();
        Set<String> explored = new HashSet<>();

        // Loop utama untuk melakukan pencarian
        while (!queueGreedyBFS.isEmpty()) {
            QueueElementGreedyBFS currentElement = queueGreedyBFS.poll();
            String currentWord = currentElement.word;

            // Jika kata saat ini adalah kata akhir, maka pencarian selesai
            if (currentWord.equals(endWord)) {
                break;
            }

            // Tandai kata saat ini sebagai sudah dieksplorasi
            explored.add(currentWord);

            // Cari kata-kata tetangga yang berbeda satu huruf dengan kata saat ini
            List<String> neighbors = findNeighbors(currentWord, dictionary);
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    double priority = heuristic(neighbor, endWord);
                    QueueElementGreedyBFS newElement = new QueueElementGreedyBFS(priority, neighbor);
                    queueGreedyBFS.add(newElement);
                    rawPath.put(neighbor, currentWord);
                }
            }
        }

        // Jika tidak ada jalur yang ditemukan, kembalikan null
        if (!rawPath.containsKey(endWord)) {
            return null;
        }

        // Rekonstruksi jalur dari kata awal ke kata akhir
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        path.add(0, startWord);

        return path;
    }

    // Metode untuk menyelesaikan Word Ladder menggunakan algoritma A*
    public List<String> solveAStar(String startWord, String endWord, English dictionary) {
        // Inisialisasi antrian prioritas untuk A*
        PriorityQueue<QueueElementGreedyBFS> queueAStar = new PriorityQueue<>();
        QueueElementGreedyBFS node = new QueueElementGreedyBFS(heuristic(startWord, endWord), startWord);
        queueAStar.add(node);

        // Inisialisasi jalur awal dan biaya sejauh ini
        Map<String, String> rawPath = new HashMap<>();
        Map<String, Double> costSoFar = new HashMap<>();
        costSoFar.put(startWord, 0.0);
        Set<String> explored = new HashSet<>();

        // Loop utama untuk melakukan pencarian
        while (!queueAStar.isEmpty()) {
            QueueElementGreedyBFS currentElement = queueAStar.poll();
            String currentWord = currentElement.word;

            // Jika kata saat ini adalah kata akhir, maka pencarian selesai
            if (currentWord.equals(endWord)) {
                break;
            }

            // Tandai kata saat ini sebagai sudah dieksplorasi
            explored.add(currentWord);

            // Cari kata-kata tetangga yang berbeda satu huruf dengan kata saat ini
            List<String> neighbors = findNeighbors(currentWord, dictionary);
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    // Hitung biaya baru untuk mencapai tetangga ini
                    double newCost = costSoFar.get(currentWord) + 1; // Biaya selalu 1 karena perbedaan hanya satu huruf

                    // Jika tetangga belum pernah dikunjungi atau biaya baru lebih rendah, perbarui informasi
                    if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                        costSoFar.put(neighbor, newCost);
                        double priority = newCost + heuristic(neighbor, endWord);
                        QueueElementGreedyBFS newElement = new QueueElementGreedyBFS(priority, neighbor);
                        queueAStar.add(newElement);
                        rawPath.put(neighbor, currentWord);
                    }
                }
            }
        }

        // Jika tidak ada jalur yang ditemukan, kembalikan null
        if (!costSoFar.containsKey(endWord)) {
            return null;
        }

        // Rekonstruksi jalur dari kata awal ke kata akhir
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        path.add(0, startWord);
        
        return path;
    }

    // Metode untuk menghitung estimasi biaya dari suatu kata ke tujuan
    private double heuristic(String currentWord, String endWord) {
        // Misalnya, kita menggunakan jumlah karakter yang berbeda antara kata saat ini dan kata akhir sebagai heuristik
        return countDifferences(currentWord, endWord);
    }

    // Metode untuk menyimpan explorasi kata
}