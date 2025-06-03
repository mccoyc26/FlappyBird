import java.io.*;
import java.util.ArrayList;

public class ScoreManager {
    private final String filename = "highscores.txt";
    private final int maxEntries = 5;

    private ArrayList<ScoreEntry> scores = new ArrayList<>();

    public static class ScoreEntry {
        public String name;
        public int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    public ScoreManager() {
        loadScores();
    }

    public void addScore(String name, int score) {
        scores.add(new ScoreEntry(name, score));

        // Sort scores in descending order
        scores.sort((a, b) -> b.score - a.score);

        // Keep only top few scores
        if (scores.size() > maxEntries) {
            while (scores.size() > maxEntries) {
                scores.remove(scores.size() - 1);
            }
        }

        saveScores();
    }

    public ArrayList<ScoreEntry> getScores() {
        return scores;
    }

    private void loadScores() {
        scores.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("No high score file found");
        }
    }

    private void saveScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (ScoreEntry entry : scores) {
                bw.write(entry.name + "," + entry.score);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
