class Team {
    /**
     * Name of team.
     */
    private String name;
    /**
     * Points team has.
     */
    private Integer points;

    /**
     * Team constructor.
     *
     * @param name
     *            Name of team.
     * @param points
     *            Points team has.
     */
    public Team(String name, Integer points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Returns points team has.
     * 
     * @return this.points
     */
    public Integer points() {
        return this.points;
    }

    /**
     * Returns team name.
     * 
     * @return this.points
     */
    public String name() {
        return this.name;
    }
}
