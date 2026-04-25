
import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Demonstrates usage of the {@code PowerRanking} component. This simulator
 * initializes Allsvenskan standings and prompts the user for results each week
 * over a set number of weeks. Then, it applies changes to the league standings
 * based on those results. Finally, the season's top-N standings are printed
 * upon the season's end.
 *
 * Highlights use of kernel methods ({@code addTeam}, {@code removeTeam},
 * {@code teamAtRank}, {@code size}) along with secondary methods
 * ({@code bumpUp}, {@code bumpDown}, {@code getTopN}, {@code getRank},
 * {@code getHighestRanked}).
 */
public final class AllsvenskanSimulator {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private AllsvenskanSimulator() {
    }

    /**
     * Sets up an initial Allsvenskan ranking with a fixed amount of teams.
     *
     * @return a populated {@code PowerRanking} representing the starting
     *         standings
     */
    private static PowerRanking buildInitialStandings() {
        PowerRanking pr = new PowerRankingOnSequence("Allsvenskan");

        pr.addTeam(new Team("AIK"), 1);
        pr.addTeam(new Team("Djurgarden"), 2);
        pr.addTeam(new Team("Hammarby"), 3);
        pr.addTeam(new Team("Sirius"), 4);
        pr.addTeam(new Team("Elfsborg"), 5);
        pr.addTeam(new Team("Brommapojkarna"), 6);
        pr.addTeam(new Team("Degerfors"), 7);
        pr.addTeam(new Team("Mjallby"), 8);

        return pr;
    }

    /**
     * Prints the full standings to {@code out} in order of rank.
     *
     * @param pr
     *            the ranking to print
     * @param out
     *            the output writer
     */
    private static void printStandings(PowerRanking pr, SimpleWriter out) {
        out.println();
        out.println(pr.getLeagueName() + " Standings");
        for (int rank = 1; rank <= pr.size(); rank++) {
            out.println(rank + ". " + pr.teamAtRank(rank).name());
        }
        out.println();
    }

    /**
     * Prints the top {@code n} teams from {@code pr} to {@code out}.
     *
     * @param pr
     *            {@code PowerRanking} given by user
     * @param n
     *            number of top teams to print
     * @param out
     *            the output writer
     */
    private static void printTopN(PowerRanking pr, int n, SimpleWriter out) {
        Sequence<Team> top = pr.getTopN(n);
        out.println("Top " + n + " teams:");
        for (int i = 0; i < top.length(); i++) {
            out.println("  " + (i + 1) + ". " + top.entry(i).name());
        }
        out.println();
    }

    /**
     * Loop that bumps teams up or down based on user input.
     *
     * @param pr
     *            {@code PowerRanking} to update
     * @param in
     *            input reader
     * @param out
     *            output writer
     */
    private static void runMatchWeek(PowerRanking pr, SimpleReader in,
            SimpleWriter out) {
        out.println("Enter results. Format: (team-name) (up|down)");
        out.println("Enter 'done' to finish week.");
        out.println();

        boolean done = false;
        while (!done) {
            out.print("> ");
            String line = in.nextLine().trim();

            if (line.equalsIgnoreCase("done")) {
                done = true;
            } else {
                int splitAt = line.lastIndexOf(' ');
                if (splitAt < 0) {
                    out.println("  (invalid input)");
                } else {
                    String teamName = line.substring(0, splitAt).trim();
                    String direction = line.substring(splitAt + 1).trim();
                    Team t = new Team(teamName);

                    if (!pr.hasTeam(t)) {
                        out.println("  '" + teamName + "' not in Allsvenskan");
                    } else if (direction.equalsIgnoreCase("up")) {
                        pr.bumpUp(t);
                        out.println("  " + teamName + " moved up to "
                                + pr.getRank(t));
                    } else if (direction.equalsIgnoreCase("down")) {
                        pr.bumpDown(t);
                        out.println("  " + teamName + " moved down to "
                                + pr.getRank(t));
                    } else {
                        out.println("  (use 'up' or 'down')");
                    }
                }
            }
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Allsvenskan Season Simulator");
        out.println();
        PowerRanking standings = buildInitialStandings();
        out.println("Initializing standings:");
        printStandings(standings, out);
        out.println("Match Week 1");
        runMatchWeek(standings, in, out);

        out.println("Current standings:");
        printStandings(standings, out);

        out.println("Top four teams:");
        printTopN(standings, 4, out);
        out.println("Current highest ranked: "
                + standings.getHighestRanked().name());
        out.println(
                "Current lowest ranked: " + standings.getLowestRanked().name());

        in.close();
        out.close();
    }
}
