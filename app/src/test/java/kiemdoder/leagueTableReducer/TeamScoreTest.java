package kiemdoder.leagueTableReducer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamScoreTest {

    @Test
    void leaguePoints() {
        //win
        assertEquals(
                new TeamScore("team1", 3).leaguePointsFromMatchPlayedAgainst(new TeamScore("team2", 1)),
                3,
                "Expected a win to yield 3 league points");

        //draw
        assertEquals(
                new TeamScore("team1", 1).leaguePointsFromMatchPlayedAgainst(new TeamScore("team2", 1)),
                1,
                "Expected a draw to yield 1 league points");

        //lost
        assertEquals(
                new TeamScore("team1", 1).leaguePointsFromMatchPlayedAgainst(new TeamScore("team2", 3)),
                0,
                "Expected a lost game to yield 0 league points");
    }

    @Test
    void parse() {
        final String teamName = "SpantasticFC";
        TeamScore teamScore = TeamScore.parse(teamName + " 3");
        assertEquals(teamScore.getTeam(), teamName);
        assertEquals(teamScore.getScore(), 3);

        // Test team name containing spaces
        TeamScore teamScore2 = TeamScore.parse("AC Spantastic 2");
        assertEquals(teamScore2.getTeam(), "AC Spantastic");
        assertEquals(teamScore2.getScore(), 2);
    }

    @Test
    void parseMatch() {
        TeamScore[] matchScores = TeamScore.parseMatch("team1 1, team2 2");
        assertEquals(matchScores[0].getTeam(), "team1");
        assertEquals(matchScores[0].getScore(), 1);
        assertEquals(matchScores[1].getTeam(), "team2");
        assertEquals(matchScores[1].getScore(), 2);
    }

    @Test
    void parseMatches() {
        final String team1Name = "Team1";
        final String team2Name = "Team2";
        final String team3Name = "Team3";
        final String team4Name = "Team4";

        final String games = team1Name + " 1," + team2Name + " 2\n" + team3Name + " 3," + team4Name + " 4";
        for (Object s : games.lines().toArray()) {
            System.out.println(s);
        }
        TeamScore[][] matches = TeamScore.parseMatches(games).toArray(TeamScore[][]::new);
        //assert first match
        assertEquals(matches[0][0].getTeam(), team1Name);
        assertEquals(matches[0][0].getScore(), 1);
        assertEquals(matches[0][1].getTeam(), team2Name);
        assertEquals(matches[0][1].getScore(), 2);

        //assert second match
        assertEquals(matches[1][0].getTeam(), team3Name);
        assertEquals(matches[1][0].getScore(), 3);
        assertEquals(matches[1][1].getTeam(), team4Name);
        assertEquals(matches[1][1].getScore(), 4);
    }
}
