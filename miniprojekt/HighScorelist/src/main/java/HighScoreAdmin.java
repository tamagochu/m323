import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class HighScoreAdmin {
    private static final List<String> LEVELS = List.of("einfach", "mittel", "schwer", "genie");
    private static final int MAX_ENTRIES = 10;
    private static final String FILE_NAME = "highscores.csv";
    private final Map<String, List<HighScore>> scores;

    public HighScoreAdmin() {
        this.scores = loadScores();
    }

    public String addScore(String name, String level, int time) {
        level = level.toLowerCase();
        if (!LEVELS.contains(level)) return "Invalid level!";

        HighScore newScore = new HighScore(name, new Date().toString(), level, time);
        List<HighScore> updatedScores = Stream.concat(scores.getOrDefault(level, List.of()).stream(), Stream.of(newScore))
                .sorted(Comparator.comparingInt(s -> s.time))
                .limit(MAX_ENTRIES)
                .collect(Collectors.toUnmodifiableList());

        Map<String, List<HighScore>> newScores = new HashMap<>(scores);
        newScores.put(level, updatedScores);
        return updatedScores.contains(newScore)
                ? "Your rank: " + (updatedScores.indexOf(newScore) + 1) + "!"
                : "HighScore entries only better than " + updatedScores.get(MAX_ENTRIES - 1).time + " seconds";
    }

    public String getHighScores(String level) {
        level = level.toLowerCase();
        if (!LEVELS.contains(level)) return "Invalid level!";
        return scores.getOrDefault(level, List.of()).stream()
                .map(HighScore::toString)
                .collect(Collectors.joining("\n"));
    }

    public void saveScores() {
        String csvContent = scores.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .map(hs -> String.join(",", hs.name, hs.date, hs.level, String.valueOf(hs.time)))
                .collect(Collectors.joining("\n", "Name,Date,Level,Time\n", ""));
        try {
            Files.writeString(Paths.get(FILE_NAME), csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, List<HighScore>> loadScores() {
        try {
            return Files.lines(Paths.get(FILE_NAME))
                    .skip(1)
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 4)
                    .map(parts -> new HighScore(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])))
                    .collect(Collectors.groupingBy(hs -> hs.level,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    list -> list.stream()
                                            .sorted(Comparator.comparingInt(s -> s.time))
                                            .limit(MAX_ENTRIES)
                                            .collect(Collectors.toUnmodifiableList())
                            )));
        } catch (IOException e) {
            System.out.println("Keine gespeicherten Highscores gefunden.");
            return LEVELS.stream().collect(Collectors.toMap(Function.identity(), l -> List.of()));
        }
    }
}
