package de.osjava.smartcanteen.data.item;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie enthält einen Verweis auf das jeweilige
 * Lebensmittel ({@link Ingredient}) den Preis und die verfügbare Menge.
 * 
 * @author Tim Sahling
 */
public class PriceListItem {

    private Amount size;
    private Ingredient ingredient;
    private Amount price;
    private BigDecimal availableQuantityOfIngredient;

    /**
     * Standardkonstruktor mit verschiedenen Werten.
     * 
     * @param size Größe und Einheit des Gebindes
     * @param ingredient Name und Typ des Gebindes
     * @param price Preis des Gebindes
     * @param availableQuantityOfIngredient Anzahl vorhandener Gebinde
     */
    public PriceListItem(Amount size, Ingredient ingredient, Amount price, BigDecimal availableQuantityOfIngredient) {
        this.size = size;
        this.ingredient = ingredient;
        this.price = price;
        this.availableQuantityOfIngredient = availableQuantityOfIngredient;
    }

    /**
     * Multipliziert die verfügbare {@link Amount} einer {@link Ingredient} mit der Größe des Gebindes.
     * 
     * @return Eine {@link Amount} mit dem Produkt aus verfügbarer Menge und Größe des Gebindes
     */
    public Amount multiplyAvailableQuantityWithSize() {
        return new Amount(NumberHelper.multiply(this.availableQuantityOfIngredient, this.size.getValue()),
                this.size.getUnit());
    }

    /**
     * Berechnet den Preis für eine {@link Amount}.
     * 
     * @param quantity Die {@link Amount}, für die der Preis berechnet werden soll
     * @return Der berechnete Preis für die übergebene {@link Amount}
     */
    public BigDecimal calculatePriceForQuantity(Amount quantity) {
        BigDecimal result = null;

        if (validateSizeAndQuantity(quantity)) {

            if (this.size.getUnit().equals(quantity.getUnit())) {
                result = NumberHelper.multiply(calculatePriceForOneUnitOfSize(), quantity.getValue());
            }
        }

        return result;
    }

    /**
     * Berechnet den Preis des Gebindes für eine Einheit.
     * 
     * @return Der berechnete Preis für eine Einheit des Gebindes
     */
    public BigDecimal calculatePriceForOneUnitOfSize() {
        return NumberHelper.divide(this.price.getValue(), this.size.getValue());
    }

    /**
     * Dividiert die übergebene {@link Amount} mit der Größe des Gebindes. Das Ergebnis wird auf keine Nachkommastelle
     * aufgerundet, da die tatsächliche Anzahl an benötigten Gebinden herausgefunden werden soll.
     * 
     * Beispiel: Menge -> 15.050 GRM -> 15.05 => 16 Gebinde
     * 
     * @param quantity Die {@link Amount}, die mit der Größe des Gebindes dividiert werden soll
     * @return Ergebnis der Division aus {@link Amount} und Größe des Gebindes
     */
    public BigDecimal divideQuantityWithSize(Amount quantity) {
        BigDecimal result = null;

        if (validateSizeAndQuantity(quantity)) {

            if (this.size.getUnit().equals(quantity.getUnit())) {
                result = NumberHelper.divide(quantity.getValue(), this.size.getValue()).setScale(0, RoundingMode.UP);
            }
        }

        return result;
    }

    /**
     * Validiert die Größe des Gebindes und eine übergebene {@link Amount}.
     * 
     * @param quantity Die {@link Amount}, die überprüft werden soll
     * @return wahr/falsch, je nachdem ob die Größe des Gebindes und der übergebene {@link Amount} valide sind
     */
    private boolean validateSizeAndQuantity(Amount quantity) {
        return this.size != null && this.size.getUnit() != null && this.size.getValue() != null && quantity != null && quantity
                .getUnit() != null && quantity.getValue() != null;
    }

    /**
     * Methode um die Größe des Gebindes abzufragen.
     * 
     * @return Die Größe des Gebindes
     */
    public Amount getSize() {
        return size;
    }

    /**
     * Methode das Gebinde abzufragen.
     * 
     * @return Das Gebinde
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Methode um den Preis des Gebindes abzufragen
     * 
     * @return Der Preis des Gebindes
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Methode um die verfügbare Menge des Gebindes abzufragen.
     * 
     * @return Die verfügbare Menge des Gebindes
     */
    public BigDecimal getAvailableQuantityOfIngredient() {
        return availableQuantityOfIngredient;
    }

    /**
     * Methode um die verfügbare Menge des Gebindes zu setzen
     * 
     * @param availableQuantityOfIngredient Die zu setzende verfügbare Menge des Gebindes
     */
    public void setAvailableQuantityOfIngredient(BigDecimal availableQuantityOfIngredient) {
        this.availableQuantityOfIngredient = availableQuantityOfIngredient;
    }

    /**
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die Methode aufgerufen wurde.
     * 
     * @return Der HashCode-Wert des Objekts als int-Representation
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((availableQuantityOfIngredient == null) ? 0 : availableQuantityOfIngredient
                .hashCode());
        result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    /**
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von dem die Methode aufgerufen wurde.
     * 
     * @return wahr/falsch, je nachdem ob zu vergleichende Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PriceListItem other = (PriceListItem) obj;
        if (availableQuantityOfIngredient == null) {
            if (other.availableQuantityOfIngredient != null)
                return false;
        }
        else if (!availableQuantityOfIngredient.equals(other.availableQuantityOfIngredient))
            return false;
        if (ingredient == null) {
            if (other.ingredient != null)
                return false;
        }
        else if (!ingredient.equals(other.ingredient))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        }
        else if (!price.equals(other.price))
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        }
        else if (!size.equals(other.size))
            return false;
        return true;
    }

    /**
     * Erstellt die String-Representation des Objekts {@link PriceListItem}.
     * 
     * @return Die String-Representation von {@link PriceListItem}
     */
    @Override
    public String toString() {
        return "PriceListItem [size=" + size + ", ingredient=" + ingredient + ", price=" + price + ", availableQuantityOfIngredient=" + availableQuantityOfIngredient + "]";
    }
}
