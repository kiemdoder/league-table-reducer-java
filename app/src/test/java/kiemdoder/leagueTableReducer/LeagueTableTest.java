package kiemdoder.leagueTableReducer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTableTest {

    @Test
    void updateTable() {
        LeagueTable table = new LeagueTable();
        table.update("team1", 3);
        table.update("team1", 3);
        table.update("team2", 1);

        List<TeamLeaguePoints> leaguePositions = table.leaguePositions();
        assertEquals(leaguePositions.size(), 2);

        assertEquals(leaguePositions.get(0).getTeam(), "team1");
        assertEquals(leaguePositions.get(0).getPoints(), 6);

        assertEquals(leaguePositions.get(1).getTeam(), "team2");
        assertEquals(leaguePositions.get(1).getPoints(), 1);
    }

    @Test
    void leaguePositions() {
        LeagueTable table = new LeagueTable();
        table.update("team2", 10);
        table.update("team1", 20);
        table.update("team3", 5);

        List<TeamLeaguePoints> leaguePositions = table.leaguePositions();
        assertEquals(leaguePositions.size(), 3);

        assertEquals(leaguePositions.get(0).getTeam(), "team1");
        assertEquals(leaguePositions.get(0).getPoints(), 20);

        assertEquals(leaguePositions.get(1).getTeam(), "team2");
        assertEquals(leaguePositions.get(1).getPoints(), 10);

        assertEquals(leaguePositions.get(2).getTeam(), "team3");
        assertEquals(leaguePositions.get(2).getPoints(), 5);
    }
}
