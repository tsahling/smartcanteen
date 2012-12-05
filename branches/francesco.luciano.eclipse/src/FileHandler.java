import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileHandler {

	public List<HitList> readHitlist() {
		Vector<String[]> lines = new Vector<String[]>();
		List<HitList> hitlist = new ArrayList<HitList>();
		Integer mealPlacement = null;
		String mealName = null;

		try {
			CSVTokenizer csv = new CSVTokenizer(
					"/Users/frato/Desktop/hitliste.csv", ',');

			while (csv.hasMoreLines()) {
				lines.add(csv.nextLine());
			}
			for (int i = 0; i <= lines.size() - 1; i++) {

				mealPlacement = Integer.valueOf(lines.get(i)[0]);
				mealName = lines.get(i)[1];
				HitList oneHitListItem = new HitList(mealPlacement, mealName);
				hitlist.add(oneHitListItem);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hitlist;
	}

	public static List<PriceList> readPriceList() {
		
		Vector<String[]> lines = new Vector<String[]>();
		List<PriceList> pricelist = new ArrayList<PriceList>();
		String typeOfTrader = null;
		String nameOfTrader = null;
		String transportCost = null;
		Integer itemSize = null;
		String unitSize = null;
		String nameIngredient = null;
		String typeIngredient = null;
		String price = null;
		Integer existingQuantity = null;
		float priceFloat = 0;
		float transportCostFloat = 0;
		
		try {
			CSVTokenizer csv = new CSVTokenizer(
					"/Users/frato/Desktop/preisliste_1.csv", ',');
			while (csv.hasMoreLines()) {
				lines.add(csv.nextLine());
			}
			
			// CSV Header
			typeOfTrader = lines.get(0)[0];
			nameOfTrader = lines.get(0)[1];
			transportCost = lines.get(0)[2].replace(",", ".");
			transportCostFloat = new Float(transportCost);
			
			for (int i = 1; i <= lines.size() - 1; i++) {

					itemSize = Integer.valueOf(lines.get(i)[0]);
					unitSize = lines.get(i)[1];
					nameIngredient = lines.get(i)[2];
					typeIngredient = lines.get(i)[3];
					price = lines.get(i)[4].replace(",", ".");
					existingQuantity = Integer.valueOf(lines.get(i)[5]);
					priceFloat = new Float(price);
				
				
				
				
			PriceList onePriceListItem = new PriceList(typeOfTrader,
					nameOfTrader, transportCostFloat, itemSize,
					unitSize, nameIngredient, typeIngredient, priceFloat,
					existingQuantity);
			
			pricelist.add(onePriceListItem);
				
				
//				System.out.println(typeOfTrader+ " " +
//						nameOfTrader + " " + transportCostFloat+ " " +itemSize+ " " +
//						unitSize+ " " +nameIngredient+ " " +typeIngredient+ " " +priceFloat+ " " +
//						existingQuantity);
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return pricelist;
	}

	public static void main(String[] args) {
		
		readPriceList();

	}

}
