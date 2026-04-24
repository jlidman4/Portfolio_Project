import components.sequence.Sequence;

public interface PowerRanking extends PowerRankingKernel {

    /**
     * Returns rank of team in {@code this}.
     *
     * @param t
     *            Team whose rank is to be returned
     * @requires t is in {@code this}
     * @return rank of {@code t}, -1 if team is not found.
     */
    int getRank(Team t);

    /**
     * Returns top {@code n} teams in {@code this}, 
     * in descending order.
     * @param n
     *            Teams to be returned
     * @requires 0 <= n and n <= |this|
     * @return Sequence containing top {@code n} teams in {@code this}
     * @ensures |getTopN| = n and 
     * getTopN is subset of {@code this} and 
     * [getTopN in descending order] and 
     * [all teams in returned Sequence<Team> are of higher ranks
     * than those below top {@code n} teams] 
     */
    Sequence<Team> getTopN(int n);

    /**
     * Returns highest ranked team in {@code this}.
     *
     * @requires {@code this} /= <>
     * @return highest ranked team in {@code this}
     * @ensures getHighestRanked = [highest ranked team in {@code this}]
     */
    Team getHighestRanked();

    /**
     * Returns lowest ranked team in {@code this}.
     *
     * @requires {@code this} /= <>
     * @return lowest ranked team
     * @ensures getLowestRanked is in this 
      * and getLowestRanked = [lowest ranked team in {@code this}]
     */
    Team getLowestRanked();

    /**
     * Sets rank of {@code t} in {@code this}, shifting 
     * other teams' ranks to keep all ranks within range 
     * [1, |this|].
     *
     * @param t
     *            Team whose rank is to be set
     * @param rank
     *            Rank of which team is to be set to
     * @updates {@code this}
     * @requires team is in {@code this} and 1 <= rank <= |{@code this}|
     * @ensures {@code t} in this and 
     * [for every team t' in this other than t, t' is in #this]  and
     *     |this| = |#this| 
     */
    void setRank(Team t, int rank);

    /**
     * Bumps team up one rank.
     *
     * @param t
     *            Team whose rank is to be bumped up
     * @updates {@code this}
     * @requires {@code this} /= <> and team is in {@code this}
     * @ensures Team {@code t}'s rank -= 1, if possible, else unchanged
     */
    void bumpUp(Team t);

    /**
     * Bumps team down one rank.
     *
     * @param t
     *            Team whose rank is to be bumped down
     * @updates {@code this}
     * @requires {@code this} /= <> and team is in {@code this}
     * @ensures team rank += 1 if possible, else unchanged
     */
    void bumpDown(Team t);

    /**
     * Returns Sequence<Team> representation of this.
     *
     * @requires this /= <>
     * @return Sequence<Team> representation of this.
     * @ensures |orderedList| = |this| and [orderedList is subset of this]
     * and [orderedList is in descending order]
     */
    Sequence<Team> orderedList();

}
