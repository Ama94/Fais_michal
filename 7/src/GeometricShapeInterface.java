import java.util.List;
import java.util.Map;

public interface GeometricShapeInterface {
    /**
     * Metoda dodaje punkt. Punkt dodawany jest na koniec kolekcji.
     *
     * @param point dodawany punkt
     */
    public void add(Point point);

    /**
     * Metoda usuwa punkt, o ile taki istnieje. JeĹli w kolekcji punktĂłw jest wiÄcej
     * takich samych jak point, usuwany jest tylko pierwszy z nich.
     *
     * @param point punkt do usuniÄcia
     * @return true - punkt istniaĹ w kolekcji i zostaĹ usuniÄty, false - takiego
     *         punktu nie byĹo i w zwiÄzku z tym nie zostaĹ usuniÄty.
     */
    public boolean remove(Point point);

    /**
     * Metoda dodaje punkt przed punktem beforePoint.
     *
     * @param point       dodawany punkt
     * @param beforePoint punkt, bezpoĹrednio przed ktĂłrym nowy naleĹźy dodaÄ
     * @return true - punkt odniesienia istniaĹ i dodano nowy punkt prawidĹowo,
     *         false - wskazanego punktu odniesienia nie byĹo, dodanie nowego punktu
     *         nie byĹo moĹźliwe.
     */
    public boolean addBefore(Point point, Point beforePoint);

    /**
     * Metoda dodaje punkt za punktem afterPoint.
     *
     * @param point      dodawany punkt
     * @param afterPoint punkt, bezpoĹrednio za ktĂłrym nowy naleĹźy dodaÄ
     * @return true - punkt odniesienia istniaĹ i dodano nowy punkt prawidĹowo,
     *         false - wskazanego punktu odniesienia nie byĹo, dodanie nowego punktu
     *         nie byĹo moĹźliwe.
     */
    public boolean addAfter(Point point, Point afterPoint);

    /**
     * Metoda usuwa punkt przed punktem beforePoint.
     *
     * @param beforePoint punkt istniejÄcy bezpoĹrednio przed beforePoint naleĹźy
     *                    usunÄÄ.
     * @return Gdy punkt odniesienia istniaĹ oraz istniaĹ punkt do usuniÄcia,
     *         zwracana jest referencja do usuniÄtego punktu. Gdy punkt odniesienia
     *         lub punkt przed nim nie istniaĹ zwracany jest NULL.
     */
    public Point removeBefore(Point beforePoint);

    /**
     * Metoda usuwa punkt za punktem afterPoint.
     *
     * @param afterPoint punkt istniejÄcy bezpoĹrednio po afterPoint naleĹźy usunÄÄ.
     * @return Gdy punkt odniesienia istniaĹ oraz istniaĹ punkt do usuniÄcia,
     *         zwracana jest referencja do usuniÄtego punktu. Gdy punkt odniesienia
     *         lub punkt za nim nie istniaĹ zwracany jest NULL.
     */
    public Point removeAfter(Point afterPoint);

    /**
     * Metoda usuwa skutek ostatniego polecenia add, remove, addAfter, addBefore,
     * removeAfter lub removeBefore. Poprzez wielokrotne wykonywanie metody undo()
     * moĹźliwe jest usuniÄcie wczeĹniejszych poleceĹ. UsuniÄciu podlegajÄ wyĹÄcznie
     * polecenia poprawnie zrealizowane, tych, ktĂłre nie zmieniĹy stanu listy
     * punktĂłw nie naleĹźy zapamietywaÄ i uwzglÄdniaÄ w dziaĹaniu undo.
     *
     * @return true - polecenie undo przywrĂłciĹo poprzedni stan obiektu. false -
     *         brak poleceĹ do usuniÄcia, undo nie zmieniĹo stanu obiektu.
     */
    public boolean undo();

    /**
     * Metoda przywraca stan systemu sprzed wykonania polecenia undo, ktĂłre
     * bezpoĹrednio poprzedza wykonanie redo (uwaga: operacje odczytu stanu obiektu
     * mogÄ pojawiÄ siÄ pomiÄdzy undo a redo, podobnie jak inne operacje, ktĂłre nie
     * zmieniÄ stanu obiektu czyli np. bĹÄde zlecnie addBefore)
     *
     * @return true - poprawnie przywrĂłcono zmianÄ, ktĂłrÄ usuniÄto wczeĹnie za
     *         pomocÄ undo, false - bezpoĹrednio przed redo nie byĹo undo i
     *         polecenie nie mogĹo zadziaĹaÄ prawidĹowo.
     */
    public boolean redo();

    /**
     * Metoda zwraca aktualnÄ listÄ wszystkich punktĂłw.
     *
     * @return lista punktĂłw
     */
    public List<Point> get();

    /**
     * Metoda zwraca listÄ punktĂłw bez kolejnych powtĂłrzeĹ. KolejnoĹÄ punktĂłw w tej
     * kolekcji ma byÄ taka sama, jak w przypadku list zwracanej za pomocÄ metody
     * get jednakĹźe bez powtĂłrzeĹ. JeĹli kolejnoĹÄ punktĂłw zwracana za pomocÄ get to
     * np. P1, P2, P2, P3, P2, P1, P1, P1, P4 to metoda getUniq ma zwrĂłciÄ: P1, P2,
     * P3, P2, P1 i P4, czyli usuwane sÄ wszystkie kolejne wystÄpienia danego
     * punktu. PowtĂłrzenie punktu ma miejsce wtedy, gdy P1.equals(P2)=true. Uwaga:
     * metoda zwraca listÄ (nie zbiĂłr) punktĂłw, wciÄĹź jeden punkt moĹźe wystÄpiÄ
     * wielokrotnie w wyniku.
     *
     * @return kolekcja punktĂłw bez powtĂłrzeĹ.
     */
    public List<Point> getUniq();

    /**
     * Metoda zwraca mapÄ liczby wystÄpieĹ punktĂłw. Jako klucz w mapie wystÄpuje
     * punkt, jako wartoĹÄ liczba wystÄpieĹ tego punktu w budowanym ksztaĹcie.
     *
     * @return mapa liczby wystÄpien punktĂłw.
     */
    public Map<Point, Integer> getPointsAsMap();

}