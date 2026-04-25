import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Multi-League Tracker.
 *
 * Demonstrates usage of {@code PowerRanking} component as part of the
 * representation of a larger structure. While the Allsvenskan simulator only
 * makes use of one PowerRanking object, this class makes use of multiple. Shows
 * that {@code PowerRanking} is suitable as a building block for larger
 * components.
 */
public final class MultiLeagueTracker {

    /**
     * Map from league name to league's {@code PowerRanking} object.
     */
    private Map<String, PowerRanking> leagues;

    /**
     * Constructs a new {@code MultiLeagueTracker} object.
     */
    public MultiLeagueTracker() {
        this.leagues = new Map1L<String, PowerRanking>();
    }

    /**
     * Adds a new league to {@code this}.
     *
     * @param name
     *            the league name
     * @requires name is not in this
     */
    public void addLeague(String name) {
        assert name != null : "Violation of: name is not null";
        assert !this.leagues.hasKey(name) : "Violation of: name is not in this";
        this.leagues.add(name, new PowerRankingOnSequence(name));
    }

    /**
     * Adds {@code team} to the league with {@code team} at the bottom of its
     * standings.
     *
     * @param leagueName
     *            the league to add the team to
     * @param team
     *            the team to add
     * @requires leagueName is in this and team is not in the named league
     */
    public void addTeamToLeague(String leagueName, Team team) {
        assert this.leagues
                .hasKey(leagueName) : "Violation of: leagueName is in this";
        PowerRanking pr = this.leagues.value(leagueName);
        pr.addTeam(team, pr.size() + 1);
    }

    /**
     * Reports the rank of {@code team} in the named league.
     *
     * @param leagueName
     *            league name
     * @param team
     *            the team whose rank is to be retrieved
     * @return the team's rank, or -1 if not present
     * @requires leagueName is in this
     */
    public int rankOf(String leagueName, Team team) {
        assert this.leagues
                .hasKey(leagueName) : "Violation of: leagueName is in this";
        PowerRanking pr = this.leagues.value(leagueName);
        if (!pr.hasTeam(team)) {
            return -1;
        }
        return pr.getRank(team);
    }

    /**
     * Returns set of league names that contain {@code team}.
     *
     * @param team
     *            team to look up
     * @return the names of all leagues in which team participates
     */
    public Set<String> leaguesContaining(Team team) {
        Set<String> result = new Set1L<String>();
        for (Map.Pair<String, PowerRanking> pair : this.leagues) {
            if (pair.value().hasTeam(team)) {
                result.add(pair.key());
            }
        }
        return result;
    }

    /**
     * Returns a map from each league name to current first-place team.
     *
     * @return map of league name to top team
     */
    public Map<String, Team> tableToppers() {
        Map<String, Team> result = new Map1L<String, Team>();
        for (Map.Pair<String, PowerRanking> pair : this.leagues) {
            if (pair.value().size() > 0) {
                result.add(pair.key(), pair.value().getHighestRanked());
            }
        }
        return result;
    }

    /**
     * Bumps {@code team} up one rank in named league.
     *
     * @param leagueName
     *            the league in which to bump
     * @param team
     *            the team to bump up
     * @requires leagueName is in this and team is in named league
     */
    public void promoteInLeague(String leagueName, Team team) {
        assert this.leagues
                .hasKey(leagueName) : "Violation of: leagueName is in this";
        this.leagues.value(leagueName).bumpUp(team);
    }

    /**
     * Bumps {@code team} down one rank in named league.
     *
     * @param leagueName
     *            the league in which to bump
     * @param team
     *            the team to bump down
     * @requires leagueName is in this and team is in named league
     */
    public void relegateInLeague(String leagueName, Team team) {
        assert this.leagues
                .hasKey(leagueName) : "Violation of: leagueName is in this";
        this.leagues.value(leagueName).bumpDown(team);
    }

    /**
     * Reports the number of leagues being tracked.
     *
     * @return number of leagues in this
     */
    public int leagueCount() {
        return this.leagues.size();
    }

    /**
     * Demo driver: builds a tracker with three leagues, runs a few operations,
     * and prints summary information.
     *
     * @param args
     *            command-line arguments (unused)
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        out.println("Multi-League Tracker");
        out.println();

        MultiLeagueTracker tracker = new MultiLeagueTracker();
        tracker.addLeague("Premier League");
        tracker.addLeague("La Liga");
        tracker.addLeague("Champions League");
        tracker.addTeamToLeague("Premier League", new Team("Manchester City"));
        tracker.addTeamToLeague("Premier League", new Team("Arsenal"));
        tracker.addTeamToLeague("Premier League", new Team("Liverpool"));
        tracker.addTeamToLeague("La Liga", new Team("Real Madrid"));
        tracker.addTeamToLeague("La Liga", new Team("Barcelona"));
        tracker.addTeamToLeague("La Liga", new Team("Atletico Madrid"));
        tracker.addTeamToLeague("Champions League",
                new Team("Manchester City"));
        tracker.addTeamToLeague("Champions League", new Team("Real Madrid"));
        tracker.addTeamToLeague("Champions League", new Team("Arsenal"));
        tracker.addTeamToLeague("Champions League", new Team("Barcelona"));
        tracker.promoteInLeague("La Liga", new Team("Barcelona"));
        tracker.relegateInLeague("Champions League", new Team("Real Madrid"));
        Set<String> cityLeagues = tracker
                .leaguesContaining(new Team("Manchester City"));
        out.println("Manchester City competes in:");
        for (String league : cityLeagues) {
            out.println("  - " + league + " (rank "
                    + tracker.rankOf(league, new Team("Manchester City"))
                    + ")");
        }
        out.println();
        out.println("Current table-toppers:");
        Map<String, Team> toppers = tracker.tableToppers();
        for (Map.Pair<String, Team> entry : toppers) {
            out.println("  " + entry.key() + ": " + entry.value().name());
        }
        out.println();

        out.println("Total leagues being tracked: " + tracker.leagueCount());

        out.close();
    }
}
