
public class PriceList {
	
	private String typeOfTrader;
	private String nameOfTrader;
	private float transportCost;
	
	private int itemSize;
	private String unitSize;
	private String nameIngredient;
	private String typeIngredient;
	private float price;
	private int existingQuantity;
	
	public PriceList(String typeOfTrader, String nameOfTrader, float transportCost, int itemSize, String unitSize, String nameIngredient, String typeIngredient, float price, int existingQuantity){
		
		this.typeOfTrader = typeOfTrader;
		this.nameOfTrader = nameOfTrader;
		this.transportCost = transportCost;
		this.itemSize = itemSize;
		this.unitSize = unitSize;
		this.nameIngredient = nameIngredient;
		this.typeIngredient = typeIngredient;
		this.price = price;
		this.existingQuantity = existingQuantity;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + 
				"[typeOfTrader=" + typeOfTrader +
		           ",nameOfTrader=" + nameOfTrader + 
		           ",transportCost=" + transportCost +
		           ",itemSize=" + itemSize +
		           ",unitSize=" + unitSize +
		           ",nameIngredient=" + nameIngredient +
		           ",typeIngredient=" + typeIngredient +
		           ",price=" + price +
		           ",existingQuantity=" + existingQuantity + "]";
	}

}
