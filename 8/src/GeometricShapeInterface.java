import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GeometricShapeInterface {
    /**
     * Metoda dodaje punkt. Punkt dodawany na koniec kolekcji.
     *
     * @param point dodawany punkt
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy nowododawany
     *                                          punkt posiada innÄ liczbÄ wymiarĂłw
     *                                          niĹź te, ktĂłre juĹź wczeĹniej przed
     *                                          nim dodano. O poprawnej liczbie
     *                                          wymiarĂłw decyduje <b>pierwszy</b>
     *                                          punkt dodany do ksztaĹtu.
     */
    public void add(Point point) throws WrongNumberOfDimensionsException;

    /**
     * Metoda usuwa punkt point, o ile taki istnieje. JeĹli w kolekcji punktĂłw jest
     * wiÄcej takich samych jak point, usuwany jest tylko pierwszy z nich.
     *
     * @param point punkt do usuniÄcia
     * @throws WrongArgumentException wyjÄtek zgĹaszany gdy zlecane jest usuniÄcie
     *                                punktu, ktĂłry nie naleĹźy do figury.
     */
    public void remove(Point point) throws WrongArgumentException;

    /**
     * Metoda dodaje punkt przed punktem beforePoint.
     *
     * @param point       dodawany punkt
     * @param beforePoint punkt, bezpoĹrednio przed ktĂłrym nowy naleĹźy dodaÄ
     * @throws WrongArgumentException           wyjÄtek zgĹaszany, gdy jako
     *                                          beforePoint przekazany zostaĹ punkt,
     *                                          ktĂłry nie zostaĹ wczeĹniej dodany do
     *                                          figury.
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy liczba
     *                                          wymiarĂłw punktu point lub
     *                                          beforePoint nie jest zgodna z liczbÄ
     *                                          wymiarĂłw punktĂłw dodanych wczeĹniej
     *                                          do ksztaĹtu.
     */
    public void addBefore(Point point, Point beforePoint)
            throws WrongArgumentException, WrongNumberOfDimensionsException;

    /**
     * Metoda dodaje punkt za punktem afterPoint.
     *
     * @param point      dodawany punkt
     * @param afterPoint punkt, bezpoĹrednio za ktĂłrym nowy naleĹźy dodaÄ
     * @throws WrongArgumentException           wyjÄtek zgĹaszany, gdy jako
     *                                          afterPoint przekazany zostaĹ punkt,
     *                                          ktĂłry nie zostaĹ wczeĹniej dodany do
     *                                          figury.
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy liczba
     *                                          wymiarĂłw punktu point lub afterPoint
     *                                          nie jest zgodna z liczbÄ wymiarĂłw
     *                                          punktĂłw dodanych wczeĹniej do
     *                                          ksztaĹtu.
     */
    public void addAfter(Point point, Point afterPoint)
            throws WrongNumberOfDimensionsException, WrongArgumentException;

    /**
     * Metoda usuwa punkt przed punktem beforePoint.
     *
     * @param beforePoint punkt istniejÄcy bezpoĹrednio przed beforePoint naleĹźy
     *                    usunÄÄ.
     * @return Gdy punkt odniesienia istniaĹ oraz istniaĹ punkt do usuniÄcia,
     *         zwracana jest referencja do usuniÄtego punktu.
     * @throws NoSuchPointException             wyjÄtek zgĹaszany, gdy punkt
     *                                          beforePoint jest pierwszym z punktĂłw
     *                                          figury i innego punktu przed nim nie
     *                                          ma.
     * @throws WrongArgumentException           wyjÄtek zgĹaszany, gdy punkt
     *                                          beforePoint nie zostaĹ wczeĹniej
     *                                          dodany do figury.
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy liczba
     *                                          wymiarĂłw punktu beforePoint nie jest
     *                                          zgodna z liczbÄ wymiarĂłw punktĂłw
     *                                          dodanych wczeĹniej do ksztaĹtu.
     */
    public Point removeBefore(Point beforePoint)
            throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException;

    /**
     * Metoda usuwa punkt za punktem afterPoint.
     *
     * @param afterPoint punkt istniejÄcy bezpoĹrednio po afterPoint naleĹźy usunÄÄ.
     * @return Gdy punkt odniesienia istniaĹ oraz istniaĹ punkt do usuniÄcia,
     *         zwracana jest referencja do usuniÄtego punktu.
     * @throws NoSuchPointException             wyjÄtek zgĹaszany, gdy punkt
     *                                          afterPoint jest ostatnim z punktĂłw
     *                                          figury i innego punktu za nim nie
     *                                          ma.
     * @throws WrongArgumentException           wyjÄtek zgĹaszany, gdy punkt
     *                                          afterPoint nie zostaĹ wczeĹniej
     *                                          dodany do figury.
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy liczba
     *                                          wymiarĂłw punktu afterPoint nie jest
     *                                          zgodna z liczbÄ wymiarĂłw punktĂłw
     *                                          dodanych wczeĹniej do ksztaĹtu.
     */

    public Point removeAfter(Point afterPoint)
            throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException;

    /**
     * Metoda zwraca aktualnÄ listÄ wszystkich punktĂłw.
     *
     * @return lista punktĂłw
     */
    public List<Point> get();

    /**
     * Metoda zwraca zbiĂłr punktĂłw czyli kolekcjÄ punktĂłw bez powtĂłrzeĹ. KolejnoĹÄ
     * punktĂłw w tej kolekcji nie ma znaczenia. PowtĂłrzenie punktu ma miejsce wtedy,
     * gdy P1.equals(P2)=true.
     *
     * @return kolekcja punktĂłw bez powtĂłrzeĹ.
     */
    public Set<Point> getSetOfPoints();

    /**
     * Metoda zwraca obiekt typu Optional zawierajÄcy (o ile istnieje) punkt,
     * ktĂłrego wspĂłĹrzÄdne przekazywane sÄ na liĹcie positions. JeĹli istnieje wiele
     * punktĂłw o podanych wspoĹrzÄdnych zwracany jest punkt, ktĂłry zostaĹ dodany
     * jako ostatni. Metoda nigdy nie moĹźe zakoĹczyÄ siÄ zwrĂłceniem null, jeĹli
     * punktu o podanych wspĂłĹrzÄdnych nie ma, naleĹźy zwrĂłciÄ pusty obiekt Optional.
     *
     * @param positions lista wspĂłĹrzÄdnych
     * @return obiekt Optional zawierajÄcy (o ile istnieje) punkt, o przekazanych za
     *         pomocÄ positions wspĂłĹrzÄdnych, w przeciwnym wypadku pusty obiekt
     *         Optional.
     * @throws WrongNumberOfDimensionsException wyjÄtek zgĹaszany, gdy rozmiar listy
     *                                          jest niezgodny z liczbÄ wymiarĂłw
     *                                          punktĂłw naleĹźÄcych do ksztaĹtu.
     */
    public Optional<Point> getByPosition(List<Integer> positions) throws WrongNumberOfDimensionsException;
}