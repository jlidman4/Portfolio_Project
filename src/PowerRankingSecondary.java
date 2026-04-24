import components.sequence.Sequence;

/**
 * Layered implementations of secondary methods for {@code PowerRanking}.
 */
public abstract class PowerRankingSecondary implements PowerRanking {

    /**
     * Returns rank of team in {@code this}.
     *
     * @param t
     *            Team whose rank is to be returned
     * @requires {@code this} /= {} and team is in {@code this}
     * @return rank of {@code t}, -1 if team is not found.
     */
    @Override
    public int getRank(Team t) {
        int rank = -1;
        Team.CompareTeams comparator = new Team.CompareTeams();
        for (int i = 0; i < this.getTeamList().length(); i++) {
            if (comparator.compare(t, this.getTeamList().entry(i)) == 0) {
                rank = i;
            }
        }
        return rank;
    }

    /**
     * Returns top n teams in {@code this}.
     *
     * @param n
     *            Teams to be returned
     * @requires n <= |this|
     * @return Sequence containing top n teams in {@code this}
     * @ensures |getTopN| = n and getTopN is subset of {@code this}
     */
    @Override
    public Sequence<Team> getTopN(int n) {
        Sequence<Team> newSequence = this.getTeamList().newInstance();
        int number = n;
        if (number > this.getTeamList().length()) {
            number = this.getTeamList().length();
        }
        for (int i = 0; i < number; i++) {
            newSequence.add(newSequence.length(), this.getTeamList().entry(i));
        }
        return newSequence;
    }

    /**
     * Returns highest ranked team in {@code this}.
     *
     * @requires {@code this} /= {}
     * @return highest ranked team
     * @ensures getHighestRanked = [highest ranked team in {@code this}]
     */
    @Override
    public Team getHighestRanked() {
        return this.getTeamList().entry(0);
    }

    /**
     * Returns lowest ranked team in {@code this}.
     *
     * @requires {@code this} /= {}
     * @return lowest ranked team
     * @ensures getLowestRanked = [lowest ranked team in {@code this}]
     */
    @Override
    public Team getLowestRanked() {
        return this.getTeamList().entry(this.getTeamList().length() - 1);
    }

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
    @Override
    public void setRank(Team t, int rank) {
        assert t != null : "Violation of: t is not null";
        assert 1 <= rank : "Violation of: rank less than 1";
        assert rank <= this.getTeamList()
                .length() : "Violation of: rank <= |this.getTeamList()|";

        int position = this.getRank(t);
        assert position != -1 : "Violation of: t exists in this.getTeamList()";
        Sequence<Team> newSequence = this.getTeamList();
        Team reAddedTeam = newSequence.remove(position);

        newSequence.add(rank - 1, reAddedTeam);
        this.setTeamList(newSequence);

    }

    /**
     * Bumps team up one rank.
     *
     * @param t
     *            Team whose rank is to be bumped up
     * @updates {@code this}
     * @requires {@code this} /= {} and team is in {@code this}
     * @ensures Team {@code t}'s rank += 1, if possible, else unchanged
     */
    @Override
    public void bumpUp(Team t) {
        assert t != null : "Violation of: t is not null";
        int position = this.getRank(t);
        assert position != -1 : "Violation of: t exists in this.getTeamList()";
        Sequence<Team> newSequence = this.getTeamList();
        if (position > 0) {
            newSequence.remove(position);
            newSequence.add(position - 1, t);
        }
        this.setTeamList(newSequence);

    }

    /**
     * Bumps team down one rank.
     *
     * @param t
     *            Team whose rank is to be bumped down
     * @updates {@code this}
     * @requires {@code this} /= {} and team is in {@code this}
     *
     * @ensures team rank -= 1 if possible, else unchanged
     */
    @Override
    public void bumpDown(Team t) {
        assert t != null : "Violation of: t is not null";
        int position = this.getRank(t);
        assert position != -1 : "Violation of: t exists in this.getTeamList()";
        Sequence<Team> newSequence = this.getTeamList();
        if (position < (this.getTeamList().length() - 1)) {
            Team removedTeam = newSequence.remove(position);
            newSequence.add(position + 1, removedTeam);
        }
        this.setTeamList(newSequence);
    }

    /**
     * Helper for {@link #sort(Sequence, int, int)} in implementing mergesort.
     *
     * @param list
     *            {@code Sequence<Team>} list to be sorted.
     * @param l
     *            leftmost index in {@code list}
     * @param m
     *            middle index in {@code list}
     * @param r
     *            rightmost index in {@code list}
     */
    private void merge(Sequence<Team> list, int l, int m, int r) {
        Team.CompareTeams comparator = new Team.CompareTeams();

        int n1 = (m - l + 1);
        int n2 = r - m;

        Sequence<Team> left = list.newInstance();
        Sequence<Team> right = list.newInstance();

        for (int i = 0; i < n1; i++) {
            left.add(i, list.entry(l + i));
        }

        for (int j = 0; j < n2; j++) {
            right.add(j, list.entry(m + 1 + j));
        }
        Sequence<Team> mergedSequence = list.newInstance();
        int i = 0, j = 0;

        while (i < n1 && j < n2) {
            if (comparator.compare(left.entry(i), right.entry(j)) <= 0) {
                mergedSequence.add(mergedSequence.length(), left.entry(i));
                i++;
            } else {
                mergedSequence.add(mergedSequence.length(), right.entry(j));
                j++;
            }
        }

        while (i < n1) {
            mergedSequence.add(mergedSequence.length(), left.entry(i));
            i++;
        }

        while (j < n2) {
            mergedSequence.add(mergedSequence.length(), right.entry(j));
            j++;
        }
        for (int k = 0; k <= r - l; k++) {
            list.remove(l);
        }
        for (int k = 0; k < mergedSequence.length(); k++) {
            list.add(l + k, mergedSequence.entry(k));
        }

    }

    /**
     * Sorts {@code list} using mergesort.
     *
     * @param list
     *            Sequence<Team> representation of {@code this}
     * @param l
     *            leftmost value in {@code list}
     * @param r
     *            rightmost value in {@code list}
     */
    private void sort(Sequence<Team> list, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            this.sort(list, l, m);
            this.sort(list, m + 1, r);
            this.merge(list, l, m, r);
        }
    }

    /**
     * Sorts {@code this} in descending rank order.
     *
     *
     * @requires this /= {}
     * @ensures |this| = |#this| and and this is a permutation of #this in
     *          descending rank order
     */

    @Override
    public void orderedList() {
        Sequence<Team> newSequence = this.getTeamList();

        if (this.getTeamList().length() > 1) {
            this.sort(newSequence, 0, newSequence.length() - 1);
        }
        this.setTeamList(newSequence);
    }

    /**
     * Returns string representation of {@code rank}.
     *
     * @param rank
     * @return String representation of {@code rank}
     * @requires ({@code rank}/10) == 0
     */
    private static String stringConvertHelper(int rank) {
        final int ten = 10;
        assert (rank / ten == 0) : "Violation of: rank/10 != 0";

        final int one = 1;
        final int two = 2;
        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int six = 6;
        final int seven = 7;
        final int eight = 8;

        if (rank == 0) {
            return "0";
        } else if (rank == one) {
            return "1";
        } else if (rank == two) {
            return "2";
        } else if (rank == three) {
            return "3";
        } else if (rank == four) {
            return "4";
        } else if (rank == five) {
            return "5";
        } else if (rank == six) {
            return "6";
        } else if (rank == seven) {
            return "7";
        } else if (rank == eight) {
            return "8";
        }
        return "9";

    }

    /**
     * Modifies {@code sb}, returning a String representation of {@code rank}.
     *
     * @param rank
     *            rank of specified team
     * @param sb
     *            string builder to be modified
     */
    private static void createStringFromRank(int rank, StringBuilder sb) {
        final int ten = 10;
        if (rank >= ten) {
            createStringFromRank(rank / ten, sb);
        }
        sb.append(stringConvertHelper(rank % ten));
    }

    /**
     * Returns header for String representation of {@code this}.
     *
     * @return header for String representation of {@code this}.
     */
    private String writeHeader() {
        return (this.getLeagueName()
                + " Standings\n --------------------------\n");
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(this.writeHeader());
        for (int i = 0; i < this.getTeamList().length(); i++) {
            StringBuilder sb = new StringBuilder();
            createStringFromRank(i + 1, sb);
            String rank = sb.toString();
            message.append(
                    rank + ": " + this.getTeamList().entry(i).name() + "\n");
        }
        return message.toString();
    }

    @Override
    public boolean equals(Object obj) {
        boolean areEqual = true;

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PowerRanking)) {
            return false;
        }
        PowerRanking ranking = (PowerRanking) obj;
        if (this.getTeamList().length() != ranking.getTeamList().length()) {
            return false;
        }
        Team.CompareTeams comparator = new Team.CompareTeams();
        for (int i = 0; i < ranking.getTeamList().length(); i++) {
            if (!(comparator.compare(ranking.getTeamList().entry(i),
                    this.getTeamList().entry(i)) == 0)) {
                return false;
            }
        }
        return areEqual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int hashNumber = 17;
        int hash = hashNumber;
        Sequence<Team> list = this.getTeamList();
        for (int i = 0; i < list.length(); i++) {
            hash = prime * hash + list.entry(i).hashCode();
        }
        return hash;
    }

}
