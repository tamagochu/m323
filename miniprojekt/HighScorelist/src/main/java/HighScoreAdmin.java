import java.io.*;
import java.util.*;

class HighScoreAdmin {
    private static final List<String> LEVELS = Arrays.asList("einfach", "mittel", "schwer", "genie");
    private static final int MAX_ENTRIES = 10;
    private static final String FILE_NAME = "highscores.csv";
    private Map<String, List<HighScore>> scores = new HashMap<>();

    public HighScoreAdmin() {
        for (String level : LEVELS) {
            scores.put(level, new ArrayList<>());
        }
        loadScores();
    }

    public String addScore(String name, String level, int time) {
        level = level.toLowerCase();
        if (!scores.containsKey(level)) return "Invalid level!";

        String date = new Date().toString();
        HighScore newScore = new HighScore(name, date, level, time);
        List<HighScore> levelScores = scores.get(level);
        levelScores.add(newScore);
        levelScores.sort(Comparator.comparingInt(s -> s.time));

        if (levelScores.size() > MAX_ENTRIES) {
            levelScores.remove(MAX_ENTRIES);
        }

        int rank = levelScores.indexOf(newScore) + 1;
        return rank <= MAX_ENTRIES ? "Your rank: " + rank + "!" : "HighScore entries only better than " + levelScores.get(MAX_ENTRIES - 1).time + " seconds";
    }

    public String getHighScores(String level) {
        level = level.toLowerCase();
        if (!scores.containsKey(level)) return "Invalid level!";
        StringBuilder sb = new StringBuilder();
        for (HighScore hs : scores.get(level)) {
            sb.append(hs.toString()).append("\n");
        }
        return sb.toString();
    }

    public void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("Name,Date,Level,Time");
            for (String level : LEVELS) {
                for (HighScore hs : scores.get(level)) {
                    writer.println(hs.name + "," + hs.date + "," + hs.level + "," + hs.time);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    scores.get(parts[2]).add(new HighScore(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
            for (String level : LEVELS) {
                scores.get(level).sort(Comparator.comparingInt(s -> s.time));
                if (scores.get(level).size() > MAX_ENTRIES) {
                    scores.get(level).subList(MAX_ENTRIES, scores.get(level).size()).clear();
                }
            }
        } catch (IOException e) {
            System.out.println("Keine gespeicherten Highscores gefunden.");
        }
    }
}