package de.osjava.smartcanteen.data.item;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.osjava.smartcanteen.data.Ingredient;
import de.osjava.smartcanteen.datatype.Amount;
import de.osjava.smartcanteen.helper.NumberHelper;

/**
 * Die Klasse {@link PriceListItem} stellt eine Preislistenposition dar. Sie
 * enthält einen Verweis auf das jeweilige Lebensmittel ({@link Ingredient}) den
 * Preis und die verfügbare Menge.
 * 
 * @author Francesco Luciano
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
     * Multipliziert die verfügbare Menge einer Zutat mit der Größe des Gebindes.
     * 
     * @return Einen {@link Amount} mit dem Produkt aus verfügbarer Menge und Größe des Gebindes
     */
    public Amount multiplyAvailableQuantityWithSize() {
        return new Amount(NumberHelper.multiply(this.availableQuantityOfIngredient, this.size.getValue()),
                this.size.getUnit());
    }

    /**
     * 
     * @param quantity
     * @return
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
     * 
     * @return
     */
    private BigDecimal calculatePriceForOneUnitOfSize() {
        return NumberHelper.divide(this.price.getValue(), this.size.getValue());
    }

    /**
     * Dividiert die übergebene {@link Amount} mit der Größe des Gebindes. Das Ergebnis wird auf keine Nachkommastelle
     * aufgerundet, da die tatsächliche Anzahl an benötigten Gebinden herausgefunden werden soll.
     * 
     * Beispiel: Quantity -> 15.050 GRM -> 15.05 => 16 Gebinde
     * 
     * @param quantity
     * @return
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
     * 
     * @param quantity
     * @return
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
     * Methode um die Größe des Gebindes zu setzen.
     * 
     * @param size Die zu setzende Größe des Gebindes
     */
    public void setSize(Amount size) {
        this.size = size;
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
     * Methode um ein Gebinde zu setzen.
     * 
     * @param Das zu setzende Gebinde
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
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
     * Methode um den Preis eines Gebindest zu setzen
     * 
     * @param price Der zu setzende Preis des Gebindes
     */
    public void setPrice(Amount price) {
        this.price = price;
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
     * Diese Methode gibt den HashCode-Wert für das Objekt zurück, von dem die
     * Methode aufgerufen wurde.
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
     * Diese Methode prüft, ob das übergebene Objekt gleich dem Objekt ist, von
     * dem die Methode aufgerufen wurde.
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
