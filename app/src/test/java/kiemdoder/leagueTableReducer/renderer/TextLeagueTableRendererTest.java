package kiemdoder.leagueTableReducer.renderer;

import kiemdoder.leagueTableReducer.LeagueTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextLeagueTableRendererTest {

    @Test
    void render() {
        final LeagueTable table = new LeagueTable();
        table.update("team1", 6);
        table.update("team2", 1);

        final String renderResult = new TextLeagueTableRenderer().render(table);
        assertEquals(renderResult, "1. team1, 6 pts\n\r2. team2, 1 pt");
    }
}
