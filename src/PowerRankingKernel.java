import components.sequence.Sequence;
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
     * @ensures t is in {@code this}
     */
    void addTeam(Team t);

    /**
     * Getter for {@code Sequence<Team>} representation of {@code this}.
     *
     * @return {@code Sequence<Team>} representation of {@code this}
     */
    Sequence<Team> getTeamList();

    /**
     * Setter for {@code Sequence<Team>} representation of {@code this}.
     *
     * @param teamList
     *            New representation for {@code Sequence<Team>} representation
     *            of {@code this}.
     */
    void setTeamList(Sequence<Team> teamList);

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
     * Removes a team from {@code this}.
     *
     * @param t
     *            team to remove from {@code this}
     * @requires {@code this} /= {}
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
