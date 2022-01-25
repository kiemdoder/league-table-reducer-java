package kiemdoder.leagueTableReducer;

import java.util.*;

public class LeagueTable {
    private final Map<String, Integer> state = new HashMap<>();

    /**
     * Update the league table by adding points to a team
     */
    public void update(String team, int points) {
        if (state.containsKey(team)) {
            final int currentPoints = state.get(team);
            state.put(team, currentPoints + points);
        } else {
            state.put(team, points);
        }
    }

    /**
     * Return the teams league positions from highest to lowest.
     */
    public List<TeamLeaguePoints> leaguePositions() {
        final ArrayList<TeamLeaguePoints> leaguePositionsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : state.entrySet()) {
            leaguePositionsList.add(new TeamLeaguePoints(entry.getKey(), entry.getValue()));
        }

        //Sort table by points
        leaguePositionsList.sort(Comparator.comparing(TeamLeaguePoints::getPoints).reversed());

        return leaguePositionsList;
    }
}
