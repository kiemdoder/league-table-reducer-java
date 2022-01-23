package kiemdoder.leagueTableReducer;

public class TeamLeaguePoints {
    private final String team;
    private final int points;

    public TeamLeaguePoints(String team, int points) {
        this.team = team;
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }
}
