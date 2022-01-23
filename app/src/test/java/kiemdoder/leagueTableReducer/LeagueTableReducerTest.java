package kiemdoder.leagueTableReducer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTableReducerTest {

    @Test
    void reduceMatchesToLeaguePositions() {
        final String matchesPlayed =
                """
                        team2 3, team4 3
                        team1 1, team three 0
                        team2 1, team three 1
                        team1 3, team4 1
                        team2 4, team5 0""";

        List<TeamLeaguePoints> leaguePositions = LeagueTableReducer.reduceMatchesToLeaguePositions(matchesPlayed).leaguePositions();
        assertEquals(leaguePositions.get(0).getTeam(), "team1");
        assertEquals(leaguePositions.get(0).getPoints(), 6);

        assertEquals(leaguePositions.get(1).getTeam(), "team2");
        assertEquals(leaguePositions.get(1).getPoints(), 5);

        assertEquals(leaguePositions.get(2).getTeam(), "team three");
        assertEquals(leaguePositions.get(2).getPoints(), 1);

        assertEquals(leaguePositions.get(3).getTeam(), "team4");
        assertEquals(leaguePositions.get(3).getPoints(), 1);

        assertEquals(leaguePositions.get(4).getTeam(), "team5");
        assertEquals(leaguePositions.get(4).getPoints(), 0);
    }

    @Test
    void commandLine() {
        String[] args = {"--matches", "test-files/matches.txt", "--output", "test-files/output/table.txt"};
        LeagueTableReducer.main(args);
    }
}
