class HighScore {
    String name;
    String date;
    String level;
    int time;

    public HighScore(String name, String date, String level, int time) {
        this.name = name;
        this.date = date;
        this.level = level;
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " (" + date + ") - " + level + ": " + time + " sec";
    }
}