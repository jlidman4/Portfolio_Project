import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit test fixture for {@code PowerRanking}'s kernel methods, and those
 * overwritten from Standard.
 *
 * Tests are written against the {@code PowerRanking} interface.
 */
public class PowerRankingOnSequenceTest {

    /**
     * Constant representing the number one.
     */
    private final int zero = 0;
    /**
     * Constant representing the number one.
     */
    private final int one = 1;
    /**
     * Constant representing the number two.
     */
    private final int two = 2;
    /**
     * Constant representing the number three.
     */
    private final int three = 3;
    /**
     * Constant representing the number four.
     */
    private final int four = 4;

    /**
     * Returns a new instance of {@code PowerRanking} for testing.
     *
     * @return new, empty instance of PowerRanking
     */
    private PowerRanking constructorTest() {
        return new PowerRankingOnSequence();
    }

    /**
     * Returns a new instance of {@code PowerRanking} for testing with a given
     * league name {@code name}.
     *
     * @param name
     *            league name
     * @return new, empty instance of PowerRanking with given {@code name}
     */
    private PowerRanking constructorTestWithName(String name) {
        return new PowerRankingOnSequence(name);
    }

    /*
     * Constructor tests below
     */

    /**
     * Test for no argument constructor.
     */
    @Test
    public void testNoArgConstructor() {
        PowerRanking pr = this.constructorTest();
        assertEquals(this.zero, pr.size());
        assertEquals("", pr.getLeagueName());
    }

    /**
     * Test for no constructor taking only given league name.
     */
    @Test
    public void testNameConstructor() {
        PowerRanking pr = this.constructorTestWithName("Allsvenskan");
        assertEquals(this.zero, pr.size());
        assertEquals("Allsvenskan", pr.getLeagueName());
    }

    /**
     * Test for constructor while entering empty league name.
     */
    @Test
    public void testNameConstructorEmptyString() {
        PowerRanking pr = this.constructorTestWithName("");
        assertEquals(this.zero, pr.size());
        assertEquals("", pr.getLeagueName());
    }

    /*
     * Testing for kernel methods.
     */

    /**
     * Testing size on PowerRanking with no teams.
     */
    @Test
    public void testSizeEmpty() {
        PowerRanking pr = this.constructorTest();
        assertEquals(this.zero, pr.size());
    }

    /**
     * Testing size on PowerRanking with one team.
     */
    @Test
    public void testSizeOne() {
        PowerRanking pr = this.constructorTest();
        pr.addTeam(new Team("AIK"), this.one);
        assertEquals(this.one, pr.size());
    }

    /**
     * Testing size on PowerRanking with > 1 teams.
     */
    @Test
    public void testSizeMany() {
        PowerRanking pr = this.constructorTest();
        pr.addTeam(new Team("AIK"), this.one);
        pr.addTeam(new Team("Sirius"), this.two);
        pr.addTeam(new Team("Elfsborg"), this.three);
        pr.addTeam(new Team("Mjallby"), this.four);
        assertEquals(this.four, pr.size());
    }

    /* Test cases for addTeam() method. */

    /**
     * Testing addTeam() on PowerRanking with no teams.
     */
    @Test
    public void testAddTeamFirst() {
        PowerRanking pr = this.constructorTest();
        Team t = new Team("AIK");
        pr.addTeam(t, this.one);
        assertEquals(this.one, pr.size());
        assertEquals(t, pr.teamAtRank(this.one));
    }

    /**
     * Testing addTeam() on PowerRanking with one team to front, testing that
     * the team in previous version of PowerRanking shifts down.
     */
    @Test
    public void testAddTeamFront() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.one);
        assertEquals(this.two, pr.size());
        assertEquals(b, pr.teamAtRank(this.one));
        assertEquals(a, pr.teamAtRank(this.two));
    }

    /**
     * Testing addTeam() on PowerRanking with one team, testing that the team in
     * previous version of PowerRanking stays at rank 1.
     */
    @Test
    public void testAddTeamEnd() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        assertEquals(a, pr.teamAtRank(this.one));
        assertEquals(b, pr.teamAtRank(this.two));
    }

    /**
     * Testing addTeam() on PowerRanking with multiple teams to middle, testing
     * that the previous Team with rank 1 stays at rank 1 and that the previous
     * last place Team stays in last place.
     */
    @Test
    public void testAddTeamMiddle() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        Team c = new Team("Elfsborg");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        pr.addTeam(c, this.two);
        assertEquals(a, pr.teamAtRank(this.one));
        assertEquals(c, pr.teamAtRank(this.two));
        assertEquals(b, pr.teamAtRank(this.three));
    }

    /* Test cases for removeTeam() method. */

    /**
     * Testing removeTeam() on PowerRanking with one team, testing that size()
     * returns zero after removal.
     */
    @Test
    public void testRemoveTeamOnlyOne() {
        PowerRanking pr = this.constructorTest();
        Team t = new Team("AIK");
        pr.addTeam(t, this.one);
        Team removed = pr.removeTeam(this.one);
        assertEquals(t, removed);
        assertEquals(this.zero, pr.size());
    }

    /**
     * Testing removeTeam() on PowerRanking with two teams, testing that size()
     * returns one after removal of first place team and second place team is
     * now of rank one.
     */
    @Test
    public void testRemoveTeamFirst() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        Team removed = pr.removeTeam(this.one);
        assertEquals(a, removed);
        assertEquals(this.one, pr.size());
        assertEquals(b, pr.teamAtRank(this.one));
    }

    /**
     * Testing removeTeam() on PowerRanking with two teams, testing that size()
     * returns one after removal of second place team and second place team is
     * now of rank one.
     */
    @Test
    public void testRemoveTeamLast() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        Team removed = pr.removeTeam(this.two);
        assertEquals(b, removed);
        assertEquals(this.one, pr.size());
        assertEquals(a, pr.teamAtRank(this.one));
    }

    /**
     * Testing removeTeam() on PowerRanking with three teams, testing that
     * size() returns two after removal of middle team, team of rank one stays
     * at rank one and team at last place stays at last place.
     */
    @Test
    public void testRemoveTeamMiddle() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        Team b = new Team("Sirius");
        Team c = new Team("Elfsborg");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        pr.addTeam(c, this.three);
        Team removed = pr.removeTeam(this.two);
        assertEquals(b, removed);
        assertEquals(this.two, pr.size());
        assertEquals(a, pr.teamAtRank(this.one));
        assertEquals(c, pr.teamAtRank(this.two));
    }

    /* Test cases for teamAtRank() method. */

    /**
     * Testing teamAtRank() on PowerRanking with one team, testing that size()
     * returns one after teamAtRank retrieval of team, team of rank one stays at
     * rank one.
     */
    @Test
    public void testTeamAtRankFirst() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        pr.addTeam(a, this.one);
        assertEquals(a, pr.teamAtRank(this.one));
        assertEquals(this.one, pr.size());
    }

    /**
     * Testing teamAtRank() on PowerRanking with three teams, testing that
     * size() returns three after retrieval of team, team of rank one stays at
     * rank one.
     */
    @Test
    public void testTeamAtRankLast() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("Arsenal");
        Team b = new Team("Brighton");
        Team c = new Team("Chelsea");
        pr.addTeam(a, this.one);
        pr.addTeam(b, this.two);
        pr.addTeam(c, this.three);
        assertEquals(c, pr.teamAtRank(this.three));
        assertEquals(this.three, pr.size());
    }

    /* Test cases for hasTeam() method. */

    /**
     * Testing hasTeam() on PowerRanking with no teams, testing that hasTeam()
     * returns false.
     */
    @Test
    public void testHasTeamEmpty() {
        PowerRanking pr = this.constructorTest();
        assertFalse(pr.hasTeam(new Team("AIK")));
    }

    /**
     * Testing hasTeam() on PowerRanking with one team, testing that hasTeam()
     * returns true when attempting to retrieve a Team in set.
     */
    @Test
    public void testHasTeamPresent() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        pr.addTeam(a, this.one);
        assertTrue(pr.hasTeam(a));
    }

    /**
     * Testing hasTeam() on PowerRanking with one team, testing that hasTeam()
     * returns false when attempting to retrieve a Team not in set.
     */
    @Test
    public void testHasTeamAbsent() {
        PowerRanking pr = this.constructorTest();
        pr.addTeam(new Team("AIK"), this.one);
        assertFalse(pr.hasTeam(new Team("Mjallby")));
    }

    /**
     * Testing hasTeam() on PowerRanking with no teams, testing that hasTeam()
     * returns false when attempting to retrieve a Team not in set that has been
     * removed.
     */
    @Test
    public void testHasTeamAfterRemoval() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        pr.addTeam(a, this.one);
        pr.removeTeam(this.one);
        assertFalse(pr.hasTeam(a));
    }

    /* Test cases for leagueName getter and setter methods. */
    /**
     * Testing getLeagueName() on PowerRanking initialized to "". Testing proper
     * retrieval of empty string.
     */
    @Test
    public void testGetLeagueNameDefault() {
        PowerRanking pr = this.constructorTest();
        assertEquals("", pr.getLeagueName());
    }

    /**
     * Testing getLeagueName() on PowerRanking initialized to "Allsvenskan" via
     * constructor. Testing proper retrieval of leagueName "Allsvenskan".
     */
    @Test
    public void testGetLeagueNameAfterConstruction() {
        PowerRanking pr = this.constructorTestWithName("Allsvenskan");
        assertEquals("Allsvenskan", pr.getLeagueName());
    }

    /**
     * Testing setLeagueName() on PowerRanking initialized to "Tippligaen" via
     * setLeagueName().
     */
    @Test
    public void testSetLeagueName() {
        PowerRanking pr = this.constructorTest();
        pr.setLeagueName("Tippligaen");
        assertEquals("Tippligaen", pr.getLeagueName());
    }

    /**
     * Testing setLeagueName() on PowerRanking initialized to "Serie A" via
     * constructor, testing if setLeagueName("Ligue une") properly changes name
     * to "Ligue une".
     */
    @Test
    public void testSetLeagueNameOverwrite() {
        PowerRanking pr = this.constructorTestWithName("Serie A");
        pr.setLeagueName("Ligue une");
        assertEquals("Ligue une", pr.getLeagueName());
    }

    /**
     * Testing setLeagueName() on PowerRanking initialized to "MLS" via
     * constructor, testing if setLeagueName("") properly changes name to empty
     * string.
     */
    @Test
    public void testSetLeagueNameToEmpty() {
        PowerRanking pr = this.constructorTestWithName("MLS");
        pr.setLeagueName("");
        assertEquals("", pr.getLeagueName());
    }

    /*
     * Standard method tests
     */

    /* Tests for newInstance() */

    /**
     * Testing that newInstance() produces empty PowerRanking.
     */
    @Test
    public void testNewInstanceProducesEmpty() {
        PowerRanking pr = this.constructorTestWithName("Allsvenskan");
        pr.addTeam(new Team("AIK"), this.one);
        PowerRanking fresh = pr.newInstance();
        assertEquals(this.zero, fresh.size());
        assertEquals("", fresh.getLeagueName());
    }

    /**
     * Testing that newInstance() does not modify original PowerRanking object
     * that it is called on.
     */
    @Test
    public void testNewInstanceDoesNotAffectOriginal() {
        PowerRanking pr = this.constructorTest();
        Team a = new Team("AIK");
        pr.addTeam(a, this.one);
        pr.newInstance();
        assertEquals(this.one, pr.size());
        assertEquals(a, pr.teamAtRank(this.one));
    }

    /* Tests for clear(). */

    /**
     * Testing that clear() clears empty PowerRanking.
     */
    @Test
    public void testClearEmpty() {
        PowerRanking pr = this.constructorTest();
        pr.clear();
        assertEquals(this.zero, pr.size());
        assertEquals("", pr.getLeagueName());
    }

    /**
     * Testing that clear() clears non-empty PowerRanking.
     */
    @Test
    public void testClearNonEmpty() {
        PowerRanking pr = this.constructorTestWithName("Allsvenskan");
        pr.addTeam(new Team("AIK"), this.one);
        pr.addTeam(new Team("Elfsborg"), this.two);
        pr.clear();
        assertEquals(this.zero, pr.size());
        assertEquals("", pr.getLeagueName());
    }

    /* Tests for transferFrom() */

    /**
     * Testing transferring from empty, ensuring that current PowerRanking is
     * now empty.
     */
    @Test
    public void testTransferFromEmpty() {
        PowerRanking dest = this.constructorTestWithName("Old");
        dest.addTeam(new Team("Arsenal"), this.one);
        PowerRanking source = this.constructorTest();
        dest.transferFrom(source);
        assertEquals(this.zero, dest.size());
        assertEquals("", dest.getLeagueName());
        assertEquals(this.zero, source.size());
    }

    /**
     * Testing transferring from non-empty, ensuring that current PowerRanking
     * changed accordingly.
     */
    @Test
    public void testTransferFromNonEmpty() {
        PowerRanking dest = this.constructorTest();
        PowerRanking source = this.constructorTestWithName("Allsvenskan");
        Team a = new Team("AIK");
        Team b = new Team("Elfsborg");
        source.addTeam(a, this.one);
        source.addTeam(b, this.two);
        dest.transferFrom(source);
        assertEquals(this.two, dest.size());
        assertEquals("Allsvenskan", dest.getLeagueName());
        assertEquals(a, dest.teamAtRank(this.one));
        assertEquals(b, dest.teamAtRank(this.two));
        assertEquals(this.zero, source.size());
        assertEquals("", source.getLeagueName());
    }
}
