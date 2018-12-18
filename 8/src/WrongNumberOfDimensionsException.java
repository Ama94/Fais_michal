/**
 * Klasa reprezentuje wyjÄtek zgĹaszany gdy uĹźyta liczba wymiarĂłw nie zgadza siÄ
 * z oczekiwanÄ.
 */
public class WrongNumberOfDimensionsException extends Exception {
    /**
     * Oczekiwana liczba wymiarĂłw
     */
    private final int expectedDimensions;
    /**
     * Faktycznie uĹźyta liczba wymiarĂłw, niezgodna z oczekiwanÄ
     */
    private final int actualDimensions;

    /**
     * Metoda zwraca oczekiwanÄ liczbÄ wymiarĂłw.
     *
     * @return oczekiwana liczba wymiarĂłw.
     */
    public int getExpectedDimensions() {
        return expectedDimensions;
    }

    /**
     * Metoda zwraca faktycznie uĹźytÄ liczbÄ wymiarĂłw.
     *
     * @return uĹźyta liczba wymiarĂłw.
     */
    public int getActualDimensions() {
        return actualDimensions;
    }

    public WrongNumberOfDimensionsException(int expectedDimensions, int actualDimensions) {
        this.expectedDimensions = expectedDimensions;
        this.actualDimensions = actualDimensions;
    }

}