
/**
 * PowerRanking kernel component with primary methods. Ranks defined as integers from
 * 1 at highest and |this| at lowest.
 */
public interface PowerRankingKernel extends Standard<PowerRanking>, Iterable<Team> {

    /**
     * Adds a team to {@code this}.
     *
     * @param t
     *            team to add to {@code this}.
     * @updates this
     * @requires t is not in {@code this}
     * @ensures t is in {@code this} and 
     * [descending order of {@code this} is preserved]
     */
    void addTeam(Team t);

    /**
     * Removes a team from {@code this}.
     *
     * @param t
     *            team to remove from {@code this}.
     * @updates {@code this}
     * @requires {@code t} is in {@code this}
     * @ensures t is not in {@code this} and 
     * [descending order of {@code this} is preserved]
     * and |{@code this}| = |{#@code this}| - 1
     *
     */
    void removeTeam(Team t);

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

}
