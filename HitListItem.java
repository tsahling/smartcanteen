/**
 * 
 * @author Franceso Luciano
 */
public class HitListItem {

    private String name;
    private int rank;

    /**
     * 
     * @param name
     * @param rank
     */
    public HitListItem(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int hashCode() {
 
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
