import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileHandler {

	public List<HitList> readHitlist() {
		Vector<String[]> lines = new Vector<String[]>();
		List<HitList> hitliste = new ArrayList<HitList>();

		try {
			CSVTokenizer csv = new CSVTokenizer(
					"/Users/frato/Desktop/hitliste.csv", ',');

			while (csv.hasMoreLines()) {
				lines.add(csv.nextLine());
			}
			for (int i = 0; i <= lines.size() - 1; i++) {

				Integer id = Integer.valueOf(lines.get(i)[0]);
				String nameOfMeal = lines.get(i)[1];
				HitList oneHitListItem = new HitList(id, nameOfMeal);
				hitliste.add(oneHitListItem);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hitliste;
	}

	public static void main(String[] args) {

		Vector<String[]> lines = new Vector<String[]>();
		List<PriceList> preisliste = new ArrayList<PriceList>();

		try {
			CSVTokenizer csv = new CSVTokenizer(
					"/Users/frato/Desktop/preisliste_1.csv", ',');
			while (csv.hasMoreLines()) {
				lines.add(csv.nextLine());
			}

			String artDesHaendler = lines.get(0)[0];
			String nameDesHaendler = lines.get(0)[1];
			String transportKosten = lines.get(0)[2].replace(",", ".");
			float transportKostenFloat = new Float(transportKosten);
			for (int i = 1; i <= lines.size() - 1; i++) {

				Integer groesseGebinde = Integer.valueOf(lines.get(i)[0]);
				String massEinheit = lines.get(i)[1];
				String nameDerZutat = lines.get(i)[2];
				String artDerZutat = lines.get(i)[3];
				String preis = lines.get(i)[4].replace(",", ".");
				Integer vorhandeneMenge = Integer.valueOf(lines.get(i)[5]);
				float preisFloat = new Float(preis);

				PriceList onePriceListItem = new PriceList(artDesHaendler,
						nameDesHaendler, transportKostenFloat, groesseGebinde,
						massEinheit, nameDerZutat, artDerZutat, preisFloat,
						vorhandeneMenge);
				preisliste.add(onePriceListItem);
			}
			System.out.println(preisliste.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
