package kiemdoder.leagueTableReducer;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The score that a team got for a match.
 */
public class TeamScore {
    private final String team;

    public String getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "TeamScore{" +
                "team='" + team + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamScore teamScore = (TeamScore) o;
        return score == teamScore.score && Objects.equals(team, teamScore.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, score);
    }

    private final int score;

    public TeamScore(String team, int score) {
        this.team = team;
        this.score = score;
    }

    /**
     * Return the league points for a match played by this team and the specified opponents.
     */
    public int leaguePointsFromMatchPlayedAgainst(TeamScore opponentsScore) {
        if (score > opponentsScore.score) {
            return 3;
        }
        if (score == opponentsScore.score) {
            return 1;
        }
        return 0;
    }

    /**
     * Create a TeamScore from its text representation.
     */
    public static TeamScore parse(String teamScoreStr) {
        final Pattern teamScorePattern = Pattern.compile("([A-Za-z\\s\\d]*)\\s(\\d+)");
        final Matcher matcher = teamScorePattern.matcher(teamScoreStr.trim());

        matcher.matches();
        final String team = matcher.group(1);
        final int score = Integer.parseInt(matcher.group(2));

        return new TeamScore(team, score);
    }

    /**
     * Parse a match played to produce a TeamScore for the two teams that played against each other.
     * @param match A string in the format "<team1> <team1-score>, <team2> <team2-score>"
     * @return A TeamScore array with 2 elements for the 2 teams.
     */
    public static TeamScore[] parseMatch(String match) {
        return Arrays.stream(match.split(","))
                .map(TeamScore::parse)
                .toArray(TeamScore[]::new);
    }

    /**
     * Parse a list of matches played as a stream of pairs of TeamScore objects.
     * @param matchesPlayed A string in the format "<team1> <team1-score>, <team2> <team2-score> \n "<team3> <team3-score>, <team4> <team4-score> \n ..."
     * @return A stream of pairs of TeamScore objects.
     */
    public static Stream<TeamScore[]> parseMatches(String matchesPlayed) {
        return matchesPlayed.lines().map(TeamScore::parseMatch);
    }

}
