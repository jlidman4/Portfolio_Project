import components.standard.Standard;

/**
 * PowerRanking kernel component with primary methods.
 */
public interface PowerRankingKernel extends Standard<PowerRanking> {

    /**
     * Adds a team to {@code this}.
     *
     * @param t
     *            team to add to {@code this}.
     * @param rank
     *            rank within {@code this} in which the team shall be placed
     * @updates this
     * @requires t is not in {@code this} and 1 <= {@code position} <=
     *           |#{@code this}|
     * @ensures t is in {@code this} and [descending order of {@code this} is
     *          preserved]
     */
    void addTeam(Team t, int rank);

    /**
     * Getter for league name of {@code this}.
     *
     * @return league name of {@code this}
     */
    String getLeagueName();

    /**
     * Setter for {@code String} representation of {@code this}.
     *
     * @param name
     *            New representation for {@code String} representation of
     *            {@code this}.
     *
     */
    void setLeagueName(String name);

    /**
     * Removes a team from {@code this} at rank {@code rank}.
     *
     * @param rank
     *            rank of team to remove from {@code this}.
     * @updates {@code this}
     * @return Team within {@code this} at rank {@code rank}
     * @requires 0 <= {@code rank} <= |#{@code this}|
     * @ensures t is not in {@code this} and [descending order of {@code this}
     *          is preserved] and |{@code this}| = |#{@code this}| - 1
     *
     */
    Team removeTeam(int rank);

    /**
     * Reports size of {@code this}.
     *
     * @return the size of {@code this}
     * @ensures size = |this|
     */
    int size();

    /**
     * Reports whether {@code t} is in {@code this}.
     *
     * @param t
     *            the team to be checked for
     * @return true if {@code t} is in {@code this}
     * @ensures hasTeam = (t is in this)
     */
    boolean hasTeam(Team t);

    /**
     * Returns a team from {@code this} at rank {@code rank} - 1.
     *
     * @param rank
     *            rank of team to return from {@code this}.
     * @updates {@code this}
     * @return Team within {@code this} at rank {@code rank}
     * @requires 0 <= {@code rank} <= |#{@code this}|
     * @ensures t is in {@code this} and [descending order of {@code this} is
     *          preserved] and |{@code this}| = |#{@code this}|
     *
     */
    Team teamAtRank(int rank);

}
