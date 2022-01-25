package kiemdoder.leagueTableReducer;

import kiemdoder.leagueTableReducer.renderer.LeagueTableRenderer;
import kiemdoder.leagueTableReducer.renderer.TextLeagueTableRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LeagueTableReducer {

    /**
     * Take the matches played in the matchesPlayed text and reduce it to a sorted league table.
     * @param matchesPlayed The text listing the matches played.
     */
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
        String usage = """
                ./bin/app <options>
                
                Options:
                --matches <matches-txt-file>
                --output <output-file>
                
                When --input is not specified the rover instructions will be read from stdin
                """;
        System.out.println(usage);
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

        String matches = "";
        if (inputFile.isPresent()) {
            final Path filePath = Paths.get(inputFile.get());
            try {
                matches = Files.readString(filePath);

            } catch (IOException e) {
                System.out.println("Could not read matches file: " + inputFile.get());
                return;
            }
        } else {
            // Read the matches data from stdin
            System.out.println("Reading matches played from stdin");
            byte[] buf = new byte[1024];
            try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                InputStream is = System.in;
                for (int len = is.read(buf); len >= 0; len = is.read(buf)) {
                    bos.write(buf, 0, len);
                }
                matches = bos.toString();
                if (matches.length() == 0) {
                    System.out.println("No input from stdin, nothing to do");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
