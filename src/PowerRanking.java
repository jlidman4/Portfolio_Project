import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Proof of concept.
 */
public class PowerRanking {
    /**
     * Representation of {@code this}.
     */
    private Team[] ranking;
    /**
     * Number of teams in {@code this}.
     */
    private int teams;

    /**
     * Constructor.
     *
     * @param teams
     *            Number of teams in {@code this}
     */
    public PowerRanking(int teams) {
        this.teams = teams;
        this.ranking = new Team[teams];
    }

    /**
     * No-argument constructor.
     */
    public PowerRanking() {
        this.teams = 0;
        this.ranking = new Team[this.teams];
    }

    /**
     * Adds a team to {@code this}.
     *
     * @param t
     *            team to add to {@code this}.
     * @ensures t is in {@code this}
     */
    public void addTeam(Team t) {

        Team[] tempArr = new Team[this.teams + 1];
        boolean added = false;
        boolean equality = false;
        if (this.teams == 0) {
            this.ranking = new Team[1];
            this.ranking[0] = t;
            this.teams++;
            return;
        }
        for (int i = 0; i < this.teams; i++) {
            equality = (this.ranking[i].points() < t.points());
            if (!added && !equality) {
                tempArr[i] = this.ranking[i];
            } else if (!added && equality) {
                tempArr[i] = t;
            } else {
                tempArr[i] = this.ranking[i - 1];
            }
        }

        if (!added) {
            tempArr[this.teams] = t;
            this.ranking = tempArr;
        } else {
            this.ranking = tempArr;
        }
        this.teams++;

    }

    /**
     * Removes a team from {@code this}.
     *
     * @param t
     *            team to remove from {@code this}.
     * @requires this /= {}
     * @ensures t is not in {@code this}
     *
     */
    public void removeTeam(Team t) {
        if (this.teams == 0) {
            return;
        }
        Team[] tempArr = new Team[this.teams - 1];
        int j = 0;
        for (int i = 0; i < this.teams; i++) {
            boolean equality = (this.ranking[i].name().equals(t.name()));
            if (!equality) {
                tempArr[j] = this.ranking[i];
                j++;
            }
        }
        this.ranking = new Team[this.teams - 1];
        for (int i = 0; i < (this.teams - 1); i++) {
            this.ranking[i] = tempArr[i];
        }
        this.teams--;
    }

    /**
     * Reports if t is in {@code this}.
     *
     * @param t
     *            team to check if in {@code this}.
     * @return team is in {@code this}
     */
    public boolean hasTeam(Team t) {
        boolean fnd = false;
        for (Team team : this.ranking) {
            if (team.name().equals(t.name())) {
                fnd = !fnd;
            }
        }
        return fnd;
    }

    /**
     * Returns highest ranked team in {@code this}.
     *
     * @requires this /= {}
     * @return team is in {@code this}
     */
    public Team getHighestRanked() {
        return this.ranking[0];
    }

    /**
     * Returns cardinality of {@code this}.
     *
     * @return |this|
     */
    public int size() {
        return this.teams;
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        PowerRanking pr = new PowerRanking();

        Team aik = new Team("AIK", 95);
        Team dif = new Team("Djurgården", 2);
        Team hif = new Team("Hammarby", 2);
        SimpleWriter out = new SimpleWriter1L();
        out.println("testing component");

        out.println("adding teams");
        pr.addTeam(aik);
        pr.addTeam(dif);
        pr.addTeam(hif);

        out.println("size: " + pr.size());

        out.println("\nHighest Ranked Team: " + pr.getHighestRanked().name());

        out.println(
                "testing if ranking contains the most beautiful team in the world (aik) "
                        + pr.hasTeam(aik));

        out.println("\nremoving djurgården");
        pr.removeTeam(dif);

        out.println("size after removal: " + pr.size());
        out.println(
                "testing if dif was properly removed or not, false means it was removed: "
                        + pr.hasTeam(dif));

        out.close();
    }
}
