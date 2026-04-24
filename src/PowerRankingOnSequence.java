import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code PowerRanking} represented as a {@code Sequence<Team>} of teams in
 * descending rank order, paired with a {@code String} league name.
 *
 * @convention this.teamList /= null and this.leagueName /= null and for all i,
 *             j: integer where (0 <= i < j < |this.teamList|)
 *             (this.teamList.entry(i) compares >= this.teamList.entry(j)) and
 *             [no duplicate teams in this.teamList]
 *
 * @correspondence this = (teams: this.teamList, name: this.leagueName) where
 *                 the team at this.teamList.entry(i) has rank i + 1
 */
final class PowerRankingOnSequence extends PowerRankingSecondary {
    /**
     * {@code Sequence<Team>} representation for {@code this}.
     */
    private Sequence<Team> teamList;

    /**
     * Name of league associated with {@code this}.
     */
    private String leagueName;

    /**
     * Constructor for PowerRankingOnSequence.
     *
     * @param teamList
     *            {@code Sequence<Team>} representation for {@code this}.
     *
     * @param name
     *            Name of league associated with {@code this}.
     */
    PowerRankingOnSequence(Sequence<Team> teamList, String name) {
        this.teamList = teamList;
        this.leagueName = name;
    }

    /**
     * Creates new representation of {@code this}.
     */
    private void createNewRep() {
        this.teamList = new Sequence1L<Team>();
        this.leagueName = "";
    }

    /**
     * Constructor for PowerRankingOnSequence.
     *
     * @param teamList
     *            {@code Sequence<Team>} representation for {@code this}.
     *
     */
    PowerRankingOnSequence(Sequence<Team> teamList) {
        this.teamList = teamList;
        this.leagueName = "";
    }

    /**
     * Constructor for PowerRankingOnSequence.
     *
     * @param name
     *            Name of league associated with {@code this}.
     */
    PowerRankingOnSequence(String name) {
        this.createNewRep();
        this.leagueName = name;
    }

    /**
     * Constructor for PowerRankingOnSequence.
     */
    PowerRankingOnSequence() {
        this.createNewRep();
    }

    @Override
    public void addTeam(Team t, int rank) {
        this.teamList.add(rank - 1, t);
    }

    @Override
    public Team removeTeam(int rank) {
        Team removed = this.teamList.remove(rank - 1);
        return removed;
    }

    @Override
    public String getLeagueName() {
        return this.leagueName;
    }

    @Override
    public void setLeagueName(String name) {
        this.leagueName = name;
    }

    @Override
    public int size() {
        return this.teamList.length();
    }

    @Override
    public boolean hasTeam(Team t) {
        for (int i = 0; i < this.teamList.length(); i++) {
            if (this.teamList.entry(i).equals(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void transferFrom(PowerRanking source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        boolean assertCheck = source instanceof PowerRankingOnSequence;
        assert assertCheck : "Violation of: source"
                + " is of dynamic type PowerRankingOnSequence";

        PowerRankingOnSequence localSource = (PowerRankingOnSequence) source;
        this.teamList = localSource.teamList;
        this.leagueName = localSource.leagueName;
        localSource.createNewRep();
    }

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public PowerRanking newInstance() {
        return new PowerRankingOnSequence();

    }

    @Override
    public Team teamAtRank(int rank) {
        return this.teamList.entry(rank - 1);
    }

}
