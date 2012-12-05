
public class HitList {
	
	private int mealPlacement;
	private String mealName;
	
	public HitList (int id, String mealName) {
		this.mealPlacement = id;
		this.mealName = mealName;
	}
	
	
	 @Override
	  public String toString()
	  {
	    return getClass().getName() +
	           "[mealPlacement=" + mealPlacement +
	           ",mealName=" + mealName + "]";
	  }
	

}
