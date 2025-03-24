import java.io.*;
import java.util.*;

public class HighScoreApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HighScoreAdmin admin = new HighScoreAdmin();

        while (true) {
            System.out.println("\n1. Neuer Highscore eintragen");
            System.out.println("2. Highscores anzeigen");
            System.out.println("3. Beenden & speichern");
            System.out.print("Auswahl: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Level (Einfach, Mittel, Schwer, Genie): ");
                String level = scanner.nextLine();
                System.out.print("Zeit in Sekunden: ");
                int time = Integer.parseInt(scanner.nextLine());
                System.out.println(admin.addScore(name, level, time));
            } else if (choice.equals("2")) {
                System.out.print("Welches Level anzeigen? (Einfach, Mittel, Schwer, Genie): ");
                String level = scanner.nextLine();
                System.out.println(admin.getHighScores(level));
            } else if (choice.equals("3")) {
                admin.saveScores();
                System.out.println("Highscores gespeichert. Programm beendet.");
                break;
            } else {
                System.out.println("Ungültige Auswahl!");
            }
        }
        scanner.close();
    }
}
