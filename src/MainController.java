// import javafx.fxml.FXML;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Label;
import java.util.List;

public class MainController {

    // @FXML
    // private TextField startInput;

    // @FXML
    // private TextField endInput;

    // @FXML
    // private Label box;

    // private English dictionary;
    // private WordLadderSolver wordLadderSolver;

    // public MainController() {
    //     dictionary = new English();
    //     wordLadderSolver = new WordLadderSolver(dictionary);
    // }

    // @FXML
    // private void solveWordLadder() {
    //     String startWord = startInput.getText().toLowerCase();
    //     String endWord = endInput.getText().toLowerCase();

    //     List<String> path = null;
    //     switch (choice) {
    //         case 1:
    //             path = wordLadderSolver.solveUCS(startWord, endWord);
    //             break;
    //         case 2:
    //             path = wordLadderSolver.solveGreedyBestFirstSearch(startWord, endWord);
    //             break;
    //         case 3:
    //             path = wordLadderSolver.solveAStar(startWord, endWord);
    //             break;
    //         default:
    //             box.setText("Pilihan algoritma tidak valid.");
    //             return;
    //     }

    //     if (path != null) {
    //         StringBuilder result = new StringBuilder();
    //         for (String word : path) {
    //             result.append(word).append(" -> ");
    //         }
    //         box.setText(result.toString());
    //     } else {
    //         box.setText("Tidak ada solusi ditemukan.");
    //     }
    // }
}
