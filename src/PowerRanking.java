
import components.sequence.Sequence;

/**
 *
 * PowerRanking component with secondary methods.
 */

public interface PowerRanking extends PowerRankingKernel {

    /**
     * Returns rank of team in {@code this}.
     *
     * @param t
     *            Team whose rank is to be returned
     * @requires {@code this} /= {} and team is in {@code this}
     * @return rank of {@code t}, -1 if team is not found.
     */
    int getRank(Team t);

    /**
     * Returns top n teams in {@code this}.
     *
     * @param n
     *            Teams to be returned
     * @requires n <= |this|
     * @return Sequence containing top n teams in {@code this}
     * @ensures |getTopN| = n and getTopN is subset of {@code this}
     */
    Sequence<Team> getTopN(int n);

    /**
     * Returns highest ranked team in {@code this}.
     *
     * @requires {@code this} /= {}
     * @return highest ranked team
     * @ensures getHighestRanked = [highest ranked team in {@code this}]
     */
    Team getHighestRanked();

    /**
     * Returns lowest ranked team in {@code this}.
     *
     * @requires {@code this} /= {}
     * @return lowest ranked team
     * @ensures getLowestRanked = [lowest ranked team in {@code this}]
     */
    Team getLowestRanked();

    /**
     * Sets rank of {@code t} in {@code this}.
     *
     * @param t
     *            Team whose rank is to be set
     * @param rank
     *            Rank of which team is to be set to
     * @updates {@code this}
     * @ensures [team {@code t} has rank 'rank' in this]
     * @requires {@code this} /= {} and team is in {@code this} and 1 <= rank <=
     *           |{@code this}|
     */
    void setRank(Team t, int rank);

    /**
     * Bumps team up one rank.
     *
     * @param t
     *            Team whose rank is to be bumped up
     * @updates {@code this}
     * @requires {@code this} /= {} and team is in {@code this}
     * @ensures Team {@code t}'s rank += 1, if possible, else unchanged
     */
    void bumpUp(Team t);

    /**
     * Bumps team down one rank.
     *
     * @param t
     *            Team whose rank is to be bumped down
     * @updates {@code this}
     * @requires {@code this} /= {} and team is in {@code this}
     * @ensures team rank -= 1 if possible, else unchanged
     */
    void bumpDown(Team t);

    /**
     * Sorts {@code this} in descending rank order.
     *
     *
     * @requires this /= {}
     * @ensures |this| = |#this| and this is a permutation of #this in
     *          descending rank order
     */
    void orderedList();

}
