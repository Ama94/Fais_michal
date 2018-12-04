

public interface PathFinderInterface {
    /**
     * Metoda ustawia mapÄ miasta.
     *
     * @param map dwuwymiarowa tablica reprezentujÄca mapÄ drĂłg w mieĹcie
     */
    public void setMap(int[][] map);

    /**
     * Metoda zwraca tablicÄ poĹoĹźeĹ na mapie, ktĂłre reprezentujÄ najkrĂłtszÄ trasÄ
     * pomiÄdzy poĹoĹźeniem begin a poĹoĹźeniem end. Trasa najkrĂłtsza to trasa
     * przechodzÄca przez moĹźliwie najmniej poĹoĹźeĹ poĹrednich. W przypadku, gdy
     * istniejÄ trasy o identycznej dĹugoĹci o wyborze decyduje preferowany kierunek
     * ruchu na skrzyĹźowaniach. Trasa jako pozycje skrajne zawiera poĹoĹźenia begin i
     * end.
     *
     * @param begin poĹoĹźenie startowe
     * @param end   poĹoĹźenie koĹcowe
     *
     * @return najkrĂłtsza trasa od begin do end
     */
    public PositionInterface[] getShortestRoute(PositionInterface begin, PositionInterface end);

    /**
     * Metoda zwraca tablicÄ poĹoĹźeĹ na mapie, ktĂłre reprezentujÄ najĹatwiejszÄ
     * trasÄ pomiÄdzy poĹoĹźeniem begin a poĹoĹźeniem end. O wyborze trasy decyduje
     * preferowany kierunek ruchu na skrzyĹźowaniach. Trasa jako pozycje skrajne
     * zawiera poĹoĹźenia begin i end.
     *
     * @param begin poĹoĹźenie startowe
     * @param end   poĹoĹźenie koĹcowe
     * @return najĹatwiejsza trasa od begin do end
     */
    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end);

    /**
     * Metoda zwraca tablicÄ poĹoĹźeĹ tablicy, ktĂłre reprezentujÄ najszybszÄ trasÄ
     * pomiÄdzy poĹoĹźeniem begin a poĹoĹźeniem end. Trasa najszybsza to trasa
     * przechodzÄca przez poĹoĹźenia, ktĂłrych suma wartoĹci w mapie jest najmniejsza.
     * W przypadku, gdy istniejÄ trasy o identycznym czasie przejazdu o wyborze
     * decyduje preferowany kierunek ruchu na skrzyĹźowaniach. Trasa jako pozycje
     * skrajne zawiera poĹoĹźenia begin i end.
     *
     * @param begin poĹoĹźenie startowe
     * @param end   poĹoĹźenie koĹcowe
     * @return najszybsza trasa od begin do end
     */
    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end);
}