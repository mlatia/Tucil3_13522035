import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class WordLadderSolver {

    // Metode untuk mencari node-node tetangga yang berbeda satu huruf dengan kata tertentu
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

    static class QueueElementGreedyAStar implements Comparable<QueueElementGreedyAStar> {
        double heuristic_score;
        String word;
        public QueueElementGreedyAStar(double heuristic_score, String word) {
            this.heuristic_score = heuristic_score;
            this.word = word;
        }
        @Override
        public int compareTo(QueueElementGreedyAStar other) {
            return Double.compare(this.heuristic_score, other.heuristic_score);
        }
    }

    // Metode untuk menghitung estimasi biaya dari suatu kata ke tujuan
    private double heuristic(String currentWord, String endWord) {
        // Misalnya, kita menggunakan jumlah karakter yang berbeda antara kata saat ini dan kata akhir sebagai heuristik
        return countDifferences(currentWord, endWord);
    }

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

    // Method untuk menyelesaikan Word Ladder menggunakan algoritma UCS
    public List<Object> solveUCS(String startWord, String endWord, English dictionary) {
        // Inisialisasi PriorityQueue untuk UCS
        PriorityQueue<QueueElementUCS> queueUCS = new PriorityQueue<>();
        QueueElementUCS node = new QueueElementUCS(0, startWord);
        queueUCS.add(node);

        // Inisialisasi path dan cost
        Map<String, String> rawPath = new HashMap<>();
        Map<String, Double> costSoFar = new HashMap<>();
        costSoFar.put(startWord, 0.0);
        Set<String> explored = new HashSet<>();
        int exploredNodes = 0;

        while (!queueUCS.isEmpty()) {
            QueueElementUCS currentElement = queueUCS.poll(); // ambil simpul dengan biaya terendah dari priorityQueue
            String currentWord = currentElement.word;
            exploredNodes++;
            // Jika kata saat ini adalah kata akhir, maka pencarian selesai
            if (currentWord.equals(endWord)) {
                break;
            }

            // Tandai kata saat ini sebagai sudah dieksplorasi
            explored.add(currentWord);

            // Cari node-node tetangga yang berbeda satu huruf dengan kata(node) saat ini
            List<String> neighbors = findNeighbors(currentWord, dictionary);
            neighbors.sort(new Comparator<String>() {
                @Override
                public int compare(String word1, String word2) {
                    return word1.compareTo(word2);
                }
            });
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    // update cost, selalu 1 karena perbedaan hanya satu huruf
                    double newCost = costSoFar.get(currentWord) + 1;

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

        // Jika tidak ada path yang ditemukan, return null
        if (!costSoFar.containsKey(endWord)) {
            List<Object> result = new ArrayList<>();
            result.add(null);
            result.add(exploredNodes);
            return result;
        }

        // Rekonstruksi path dari startWord ke endWord
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        path.add(0, startWord);

        List<Object> result = new ArrayList<>();
        result.add(path);
        result.add(exploredNodes);
        return result;
    }

    public List<Object> solveGreedyBestFirstSearch(String startWord, String endWord, English dictionary) {
        // Inisialisasi path
        Map<String, String> rawPath = new HashMap<>();
        Set<String> explored = new HashSet<>();
        int exploredNodes = 0;
        
        rawPath.put(startWord, null);

       while(true){
        String currentWord = null;
        exploredNodes++;
        double minPriority = Double.MAX_VALUE;
        // pilih node dengan prioritas minimum yang belum dieksplorasi 
        for(String word : rawPath.keySet()){
            if(!explored.contains(word)){
                double priority = heuristic(word, endWord);
                if(priority < minPriority){
                    minPriority = priority;
                    currentWord = word;
                }
            }
        }

        // Jika kata saat ini adalah kata akhir, maka pencarian selesai
        if(currentWord == null){
            List<Object> result = new ArrayList<>();
            result.add(null);
            result.add(exploredNodes);
            return result;
        }
        if (currentWord != null && currentWord.equals(endWord)) {
            break;
        }
      
        // Tandai kata saat ini sebagai sudah dieksplorasi
        explored.add(currentWord);

        // Cari node-node tetangga yang berbeda satu huruf dengan kata saat ini
        List<String> neighbors = findNeighbors(currentWord, dictionary);
        // Urutkan tetangga berdasarkan abjad dan prioritas posisi
        neighbors.sort(new Comparator<String>() {
            @Override
            public int compare(String word1, String word2) {
                return word1.compareTo(word2);
            }
        });
        
        for (String neighbor : neighbors) {
            if (!explored.contains(neighbor)) {
                // Jika node tetangga belum pernah dikunjungi, tambahkan ke path
                if (!rawPath.containsKey(neighbor)) {
                    rawPath.put(neighbor, currentWord);
                }
            }
        }
       }

        // Rekonstruksi path dari startWord ke endWord
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        rawPath.clear();

        // Jika path ditemukan, tambahkan startWord ke path
        if (!path.get(0).equals(startWord)) {
            path.add(0, startWord);
        }
        // Tambahkan endWord ke path
        List<Object> result = new ArrayList<>();
        result.add(path);
        result.add(exploredNodes);
        return result;
    }

    // Metode untuk menyelesaikan Word Ladder menggunakan algoritma A*
    public List<Object> solveAStar(String startWord, String endWord, English dictionary) {
        // Inisialisasi antrian prioritas untuk A*
        PriorityQueue<QueueElementGreedyAStar> queueAStar = new PriorityQueue<>();
        QueueElementGreedyAStar node = new QueueElementGreedyAStar(heuristic(startWord, endWord), startWord);
        queueAStar.add(node);
        // Inisialisasi path dan cost
        Map<String, String> rawPath = new HashMap<>();
        Map<String, Double> costSoFar = new HashMap<>();
        costSoFar.put(startWord, 0.0);
        Set<String> explored = new HashSet<>();
        int exploredNodes = 0;
        while (!queueAStar.isEmpty()) {
            QueueElementGreedyAStar currentElement = queueAStar.poll(); // Ambil node dengan prioritas terendah dari antrian prioritas
            String currentWord = currentElement.word; 
            exploredNodes++; 
            // Jika kata saat ini adalah kata akhir, maka pencarian selesai
            if (currentWord.equals(endWord)) {
                break;
            }
            // Tandai kata saat ini sebagai sudah dieksplorasi
            explored.add(currentWord);
            // Cari node-node tetangga yang berbeda satu huruf dengan kata saat ini
            List<String> neighbors = findNeighbors(currentWord, dictionary);
            neighbors.sort(new Comparator<String>() {
                @Override
                public int compare(String word1, String word2) {
                    return word1.compareTo(word2);
                }
            });
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    // Hitung cost untuk mencapai tetangga ini
                    double newCost = costSoFar.get(currentWord) + 1; // cost selalu 1 karena perbedaan hanya satu huruf

                    // Jika node tetangga belum pernah dikunjungi atau cost lebih rendah, perbarui informasi
                    if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                        costSoFar.put(neighbor, newCost);
                        double priority = newCost + heuristic(neighbor, endWord);
                        QueueElementGreedyAStar newElement = new QueueElementGreedyAStar(priority, neighbor);
                        queueAStar.add(newElement);
                        rawPath.put(neighbor, currentWord);
                    }
                }
            }
        }
        // Jika tidak ada path yang ditemukan, kembalikan null
        if (!costSoFar.containsKey(endWord)) {
            List<Object> result = new ArrayList<>();
            result.add(null);
            result.add(exploredNodes);
            return result;
        }
        // Rekonstruksi path dari startWord ke endWord
        List<String> path = new ArrayList<>();
        String word = endWord;
        while (!word.equals(startWord)) {
            path.add(0, word);
            word = rawPath.get(word);
        }
        path.add(0, startWord);
        List<Object> result = new ArrayList<>();
        result.add(path);
        result.add(exploredNodes);
        return result;
    }
}