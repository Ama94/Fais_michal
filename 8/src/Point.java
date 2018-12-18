import java.util.Arrays;

public class Point {
    private final int DIMENSIONS;
    private final int[] position;;

    public Point(int dimensions) {
        DIMENSIONS = dimensions;
        position = new int[DIMENSIONS];
    }

    /**
     * Metoda ustawia pozycje dimention na wartoĹÄ value.
     *
     * @param dimention numer wspĂłĹrzÄdnej, ktĂłrej wartoĹÄ jest modyfikowana
     * @param value     wartoĹÄ wpisywana do wspĂłĹrzÄdnej dimention
     */
    public void setPosition(int dimention, int value) {
        position[dimention] = value;
    }

    /**
     * Metoda zwraca wspĂłĹrzÄdnÄ dimention.
     *
     * @param dimention numer wspĂłĹrzÄdnej, ktĂłrej wartoĹÄ jest odczytywana
     * @return wartoĹÄ wspĂłĹrzÄdnej
     */
    public int getPosition(int dimention) {
        return position[dimention];
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