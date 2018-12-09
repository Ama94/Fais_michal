import java.util.Arrays;

public class Point {
    private static final int DIMENSIONS = 3;
    private int[] position = new int[DIMENSIONS];

    /**
     * Metoda zwraca liczbÄ wymiarĂłw obsĹugiwanych przez obiekt Point.
     *
     * @return liczba obsĹugiwanych wymiarĂłw.
     */
    public int getNumberOfDimensions() {
        return DIMENSIONS;
    }

    /**
     * Metoda ustawia pozycje dimension na wartoĹÄ value.
     *
     * @param dimension numer wspĂłĹrzÄdnej, ktĂłrej wartoĹÄ jest modyfikowana
     * @param value     wartoĹÄ wpisywana do wspĂłĹrzÄdnej dimension
     */
    public void setPosition(int dimension, int value) {
        position[dimension] = value;
    }

    /**
     * Metoda zwraca wspĂłĹrzÄdnÄ dimension.
     *
     * @param dimension numer wspĂłĹrzÄdnej, ktĂłrej wartoĹÄ jest odczytywana
     * @return wartoĹÄ wspĂłĹrzÄdnej
     */
    public int getPosition(int dimension) {
        return position[dimension];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;
        return Arrays.equals(position, point.position);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(position);
    }
}
