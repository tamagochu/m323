import java.util.Objects;

final class HighScore {
    final String name;
    final String date;
    final String level;
    final int time;

    public HighScore(String name, String date, String level, int time) {
        this.name = name;
        this.date = date;
        this.level = level;
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " - " + time + "s on " + date;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HighScore other)) return false;
        return name.equals(other.name)
                && date.equals(other.date)
                && level.equals(other.level)
                && time == other.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, level, time);
    }
}
