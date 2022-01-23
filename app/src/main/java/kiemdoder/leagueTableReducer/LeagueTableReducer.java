package kiemdoder.leagueTableReducer;

import kiemdoder.leagueTableReducer.renderer.LeagueTableRenderer;
import kiemdoder.leagueTableReducer.renderer.TextLeagueTableRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LeagueTableReducer {

    public static LeagueTable reduceMatchesToLeaguePositions(String matchesPlayed) {
        final LeagueTable table = new LeagueTable();

        TeamScore.parseMatches(matchesPlayed).forEach(score -> {
            final TeamScore team1 = score[0];
            final TeamScore team2 = score[1];
            table.update(team1.getTeam(), team1.leaguePointsFromMatchPlayedAgainst(team2));
            table.update(team2.getTeam(), team2.leaguePointsFromMatchPlayedAgainst(team1));
        });

        return table;
    }

    private static void printUsage() {
        System.out.println("Usage: league-table-reducer --matches <matches-txt-file>");
    }

    public static void main(String[] args) {
        // Read command line args
        Stack<String> argsStack = new Stack<>();
        List<String> reversedArgs = Arrays.asList(args);
        Collections.reverse(reversedArgs);
        for (String arg : reversedArgs) {
            argsStack.push(arg);
        }

        Optional<String> inputFile = Optional.empty();
        Optional<String> outputFile = Optional.empty();
        while (!argsStack.empty()) {
            String option = argsStack.pop();
            if (option.equals("--matches")) {
                if (argsStack.empty()) {
                    printUsage();
                    return;
                }
                String filePath = argsStack.pop();
                if (filePath.startsWith("--")) {
                    printUsage();
                    return;
                } else {
                    inputFile = Optional.of(filePath);
                }
            } else if (option.equals("--output")) {
                if (argsStack.empty()) {
                    printUsage();
                    return;
                }
                String filePath = argsStack.pop();
                if (filePath.startsWith("--")) {
                    printUsage();
                    return;
                } else {
                    outputFile = Optional.of(filePath);
                }
            } else {
                printUsage();
                return;
            }
        }

        final String matches;
        if (inputFile.isPresent()) {
            final Path filePath = Paths.get(inputFile.get());
            try {
                matches = Files.readString(filePath);

            } catch (IOException e) {
                System.out.println("Could not read matches file: " + inputFile.get());
                return;
            }
        } else {
            matches =
                    """
                            team2 3, team4 3
                            team1 1, team three 0
                            team2 1, team three 1
                            team1 3, team4 1
                            team2 4, team5 0""";

        }

        final LeagueTableRenderer renderer = new TextLeagueTableRenderer();
        String tableText = renderer.render(reduceMatchesToLeaguePositions(matches));
        if (outputFile.isPresent()) {
            try {
                Files.writeString(Paths.get(outputFile.get()), tableText);
            } catch (IOException e) {
                System.out.println("Could not write results to file: " + outputFile.get());
            }
        } else {
            System.out.println(tableText);
        }

    }
}
