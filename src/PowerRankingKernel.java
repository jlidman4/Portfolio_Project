
public interface PowerRankingKernel {

    /**
     * Adds a team to {@code this}.
     *
     * @param t
     *            team to add to {@code this}.
     * @ensures t is in {@code this}
     */
    void addTeam(Team t);

    /**
     * Removes a team from {@code this}.
     *
     * @param t
     *            team to remove from {@code this}.
     * @requires this /= {}
     * @ensures t is not in {@code this}
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
     *            the element to be checked
     * @return true if element is in {@code this}
     * @ensures hasTeam = (t is in this)
     */
    boolean hasTeam(Team t);

}
