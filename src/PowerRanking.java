import components.sequence.Sequence;

public interface PowerRanking extends PowerRankingKernel {

    /**
     * Returns rank of team in {@code this}.
     *
     * @param t
     *            Team whose rank is to be returned
     * @requires this /= {} and team is in {@code this}
     * @return rank of team
     */
    int getRank(Team t);

    /**
     * Returns top n teams in {@code this}.
     *
     * @param n
     *            Teams to be returned
     * @requires n <= |this|
     * @return Sequence containing top n teams in {@code this}
     * @ensures |getTopN| = n and getTopN is subset of this
     */
    Sequence<Team> getTopN(int n);

    /**
     * Returns highest ranked team in {@code this}.
     *
     * @requires this /= {}
     * @return highest ranked team
     * @ensures getHighestRanked = [highest ranked team in this]
     */
    Team getHighestRanked();

    /**
     * Returns lowest ranked team in {@code this}.
     *
     * @requires this /= {}
     * @return lowest ranked team
     * @ensures getLowestRanked = [lowest ranked team in this]
     */
    Team getLowestRanked();

    /**
     * Returns rank of team in {@code this}.
     *
     * @param t
     *            Team whose rank is to be set
     * @param rank
     *            Rank of which team is to be set to
     * @updates this
     * @ensures [team t has rank 'rank' in this]
     * @requires this /= {} and team is in {@code this}
     */
    void setRank(Team t, int rank);

    /**
     * Bumps team up one rank.
     *
     * @param t
     *            Team whose rank is to be bumped up
     * @updates this
     * @requires this /= {} and team is in {@code this}
     * @ensures team rank += 1
     */
    void bumpUp(Team t);

    /**
     * Bumps team down one rank.
     *
     * @param t
     *            Team whose rank is to be bumped down
     * @updates this
     * @requires this /= {} and team is in {@code this}
     * @ensures team rank -= 1
     */
    void bumpDown(Team t);

    /**
     * Returns Sequence<Team> representation of this.
     *
     * @requires this /= {}
     * @return Sequence<Team> representation of this.
     * @ensures |orderedList| = |this| and orderedList is subset of this
     */
    Sequence<Team> orderedList();

}
