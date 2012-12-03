
public class PriceList {
	
	private String artHaendler;
	private String nameHaendler;
	private float transportKosten;
	
	private int groesseGebinde;
	private String mengenAngabe;
	private String nameLebensmittel;
	private String art;
	private float preis;
	private int vorhandeneMenge;
	
	public PriceList(String artHaendler,String nameHaendler,float transportKosten,int groesseGebinde,String mengenAngabe,String nameLebensmittel,String art, float preis,int vorhandeneMenge){
		
		this.artHaendler = artHaendler;
		this.nameHaendler = nameHaendler;
		this.transportKosten = transportKosten;
		this.groesseGebinde = groesseGebinde;
		this.mengenAngabe = mengenAngabe;
		this.nameLebensmittel = nameLebensmittel;
		this.preis = preis;
		this.vorhandeneMenge = vorhandeneMenge;
		this.art = art;
	}

}
