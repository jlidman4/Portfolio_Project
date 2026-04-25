
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Class declaring the creation of a Team object, creates a representation for a
 * sports team.
 */
public final class Team {

    /**
     * Name of team.
     */
    private String name;
    /**
     * Points team has.
     */
    private int points;

    /**
     * Maps other teams in league and number of wins against a given team.
     */
    private Map<Team, Integer> wins;
    /**
     * Maps other teams in league and number of ties against a given team.
     */
    private Map<Team, Integer> ties;
    /**
     * Maps other teams in league with number of wins.
     */
    private Sequence<Team> opponents;
    /**
     * Maps other teams in league and number of losses against a given team.
     */
    private Map<Team, Integer> losses;

    /**
     * A given team's goal difference.
     */
    private int goalDifference;

    /**
     * Team constructor.
     *
     * @param name
     *            Name of team.
     * @param points
     *            Points team has.
     * @param wins
     *            {@code Map<Team, Integer>} map with head-to-head wins against
     *            other teams
     * @param ties
     *            {@code Map<Team, Integer>} map with head-to-head ties against
     *            other teams
     * @param losses
     *            {@code Map<Team, Integer>} map with head-to-head losses
     *            against other teams
     * @param goalDifference
     *            {@code int} representation of a team's goal difference
     * @param opponents
     *            {@code Sequence<Team>} of opponents
     */

    public Team(String name, int points, Map<Team, Integer> wins,
            Map<Team, Integer> ties, Map<Team, Integer> losses,
            int goalDifference, Sequence<Team> opponents) {
        this.name = name;
        this.points = points;
        this.wins = wins;
        this.ties = ties;
        this.losses = losses;
        this.goalDifference = goalDifference;
        this.opponents = opponents;
    }

    /**
     * Team constructor.
     *
     * @param name
     *            Name of team.
     * @param points
     *            Points team has.
     * @param opponents
     *            {@code Sequence<Team> } of opponents
     */
    public Team(String name, int points, Sequence<Team> opponents) {
        Map<Team, Integer> headToHeadWins = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadTies = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadLosses = new Map1L<Team, Integer>();
        for (int i = 0; i < opponents.length(); i++) {
            headToHeadWins.add(opponents.entry(i), 0);
            headToHeadTies.add(opponents.entry(i), 0);
            headToHeadLosses.add(opponents.entry(i), 0);
        }
        this.name = name;
        this.points = points;
        this.wins = headToHeadWins;
        this.ties = headToHeadTies;
        this.losses = headToHeadLosses;
        this.goalDifference = 0;
        this.opponents = opponents;
    }

    /**
     * Team constructor.
     *
     * @param name
     *            Name of team.
     * @param opponents
     *            {@code Sequence<Team>} of opponents
     */
    public Team(String name, Sequence<Team> opponents) {

        Map<Team, Integer> headToHeadWins = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadTies = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadLosses = new Map1L<Team, Integer>();
        for (int i = 0; i < opponents.length(); i++) {
            headToHeadWins.add(opponents.entry(i), 0);
            headToHeadTies.add(opponents.entry(i), 0);
            headToHeadLosses.add(opponents.entry(i), 0);
        }
        this.name = name;
        this.points = 0;
        this.wins = headToHeadWins;
        this.ties = headToHeadTies;
        this.losses = headToHeadLosses;
        this.goalDifference = 0;
        this.opponents = opponents;
    }

    /**
     * Team constructor.
     *
     * @param name
     *            Name of team.
     */
    public Team(String name) {

        Map<Team, Integer> headToHeadWins = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadTies = new Map1L<Team, Integer>();
        Map<Team, Integer> headToHeadLosses = new Map1L<Team, Integer>();
        Sequence<Team> newOpponents = new Sequence1L<>();
        this.name = name;
        this.points = 0;
        this.wins = headToHeadWins;
        this.ties = headToHeadTies;
        this.losses = headToHeadLosses;
        this.goalDifference = 0;
        this.opponents = newOpponents;
    }

    /**
     * Returns points team has.
     *
     * @return {@link #points}
     */
    public int points() {
        return this.points;
    }

    /**
     * Returns team name.
     *
     * @return {@link #name}
     */
    public String name() {
        return this.name;
    }

    /**
     * Returns team wins.
     *
     * @return {@link #wins}
     */
    public Map<Team, Integer> wins() {
        return this.wins;
    }

    /**
     * Returns team ties.
     *
     * @return {@link #ties}
     */
    public Map<Team, Integer> ties() {
        return this.ties;
    }

    /**
     * Returns team losses.
     *
     * @return {@link #losses}
     */
    public Map<Team, Integer> losses() {
        return this.losses;
    }

    /**
     * Returns team goal difference.
     *
     * @return {@link #goalDifference}
     */
    public int goalDifference() {
        return this.goalDifference;
    }

    /**
     * Returns {@code Sequence<Team>} representation of opponents.
     *
     * @return {@link #opponents}
     */
    public Sequence<Team> opponents() {
        return this.opponents;
    }

    /**
     * Class acting as a comparator for teams using a tie-breaker system
     * inspired by the Premier League - orders teams first by points, goal
     * difference, then head-to-head wins.
     */
    static final class CompareTeams implements Comparator<Team> {
        @Override
        public int compare(Team first, Team second) {
            assert first != null;
            assert second != null;
            int toReturn = 0;
            if (first.points() != second.points()) {
                toReturn = second.points() - first.points();
            } else if (first.goalDifference() != second.goalDifference()) {
                toReturn = second.goalDifference() - first.goalDifference();
            } else if (first.wins().value(second).intValue() != second.wins()
                    .value(first).intValue()) {
                toReturn = second.wins().value(first).intValue()
                        - first.wins().value(second).intValue();
            }
            return toReturn;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team other = (Team) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
