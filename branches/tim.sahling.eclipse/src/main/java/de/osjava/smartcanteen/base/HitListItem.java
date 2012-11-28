package de.osjava.smartcanteen.base;

public class HitListItem {

    private String name;
    private int rank;

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
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + rank;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HitListItem other = (HitListItem) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HitListItem [name=" + name + ", rank=" + rank + "]";
    }

}
