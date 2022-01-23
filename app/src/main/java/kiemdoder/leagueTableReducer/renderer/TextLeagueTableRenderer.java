package kiemdoder.leagueTableReducer.renderer;

import kiemdoder.leagueTableReducer.LeagueTable;
import kiemdoder.leagueTableReducer.TeamLeaguePoints;

import java.util.ArrayList;
import java.util.List;

public class TextLeagueTableRenderer implements LeagueTableRenderer {
    @Override
    public String render(LeagueTable leagueTable) {
        List<String> leaguePositions = new ArrayList<>();
        int position = 1;
        for(TeamLeaguePoints points : leagueTable.leaguePositions()) {
            leaguePositions.add(position + ". " + points.getTeam() + ", " + points.getPoints() + (points.getPoints() == 1 ? " pt" : " pts"));
            position++;
        }

        return String.join("\n\r", leaguePositions);
    }
}
