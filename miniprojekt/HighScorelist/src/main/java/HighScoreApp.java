import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HighScoreApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HighScoreAdmin admin = new HighScoreAdmin();
        runMenu(scanner, admin);
        scanner.close();
    }

    private static void runMenu(Scanner scanner, HighScoreAdmin admin) {
        Function<Void, Void> loop = new Function<Void, Void>() {
            public Void apply(Void unused) {
                displayMenu();
                String choice = scanner.nextLine();

                Optional<Void> result = switch (choice) {
                    case "1" -> Optional.of(handleAddScore(scanner, admin));
                    case "2" -> Optional.of(handleDisplayScores(scanner, admin));
                    case "3" -> handleExit(admin);
                    default -> {
                        System.out.println("Ungültige Auswahl!");
                        yield Optional.of(this.apply(null));
                    }
                };

                return result.orElse(null);
            }

            private void displayMenu() {
                System.out.println("\n1. Neuer Highscore eintragen");
                System.out.println("2. Highscores anzeigen");
                System.out.println("3. Beenden & speichern");
                System.out.print("Auswahl: ");
            }

            private Void handleAddScore(Scanner scanner, HighScoreAdmin admin) {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Level (Einfach, Mittel, Schwer, Genie): ");
                String level = scanner.nextLine();
                System.out.print("Zeit in Sekunden: ");
                int time = Integer.parseInt(scanner.nextLine());
                System.out.println(admin.addScore(name, level, time));
                return this.apply(null);
            }

            private Void handleDisplayScores(Scanner scanner, HighScoreAdmin admin) {
                System.out.print("Welches Level anzeigen? (Einfach, Mittel, Schwer, Genie): ");
                String level = scanner.nextLine();
                System.out.println(admin.getHighScores(level));
                return this.apply(null);
            }

            private Optional<Void> handleExit(HighScoreAdmin admin) {
                admin.saveScores();
                System.out.println("Highscores gespeichert. Programm beendet.");
                return Optional.empty();
            }
        };

        loop.apply(null);
    }
}
