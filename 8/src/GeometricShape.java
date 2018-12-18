import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;


class GeometricShape implements GeometricShapeInterface {
    //
    // Lista, ktora przetrzymuje wszystkie punktu. To na niej wykonywane sa wszystkie metody takie jak add, czy remove.
    ArrayList<Point> points;
    //
    // Lista, ktora przetrzymuje wszystkie punkty w klejnosci dodania.
    ArrayList<Point> chronologic_points;
    //
    // Zmienna przetrzymuje liczbe wymiarow pierwzego punktu. Ustawiana jest nie w konstruktorze, tylko przy peirwszym wywolaniu metody add.
    // Nowe punkty (po pierwszym) nie moga miec innej liczby wymiarow niz points_dimension.
    int points_dimensions;
    //
    // Kontruktor inicjuje zmienne klasy.
    GeometricShape() {
        points = new ArrayList<Point>();
        chronologic_points = new ArrayList<Point>();
        points_dimensions = 0;
    }
    /**
     * Metoda dodaje punkt. Punkt dodawany na koniec kolekcji.
     *
     * @param point dodawany punkt
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy nowododawany
     *                                          punkt posiada inną liczbę wymiarów
     *                                          niż te, które już wcześniej przed
     *                                          nim dodano. O poprawnej liczbie
     *                                          wymiarów decyduje <b>pierwszy</b>
     *                                          punkt dodany do kształtu.
     */
    public void add(Point point) throws WrongNumberOfDimensionsException {
        //
        // Sprawdzenie czy points posiada juz jakis punkt. Jesli nie, ustawiamy zmienna points_dimensions.
        if(this.points.size() == 0) this.points_dimensions = determine_point_dimensions(point);
            //
            // W drugim przypadku sprawdzamy, czy podany punkt zgadza sie z liczba wymiarow.
        else {
            int dimensions = determine_point_dimensions(point);
            //
            // Jesli wymiar sie nie zgadza - blad.
            if(dimensions != points_dimensions)
                throw new WrongNumberOfDimensionsException(points_dimensions, dimensions); // argumenty: expected dim, actual dimwrong_number;
        }
        //
        // dodanie punktu do tablicy (jesli tutaj jestesmy, to znaczy ze wszystko bylo ok).
        this.points.add(point);
        this.chronologic_points.add(point);
    } // end add()

    /**
     * Metoda usuwa punkt point, o ile taki istnieje. Jeśli w kolekcji punktów jest
     * więcej takich samych jak point, usuwany jest tylko pierwszy z nich.
     *
     * @param point punkt do usunięcia
     * @throws WrongArgumentException wyjątek zgłaszany gdy zlecane jest usunięcie
     *                                punktu, który nie należy do figury.
     */
    public void remove(Point point) throws WrongArgumentException {
        //
        // przeszukanie tablicy za podanym punktem
        for(int i = 0; i < this.points.size(); i++) {
            //
            // sprawdzenie czy ten punkt jest szukanym.
            if(this.points.get(i).equals(point)) {
                this.chronologic_points.remove(points.get(i));
                this.points.remove(i);
                return;
            }
        }
        //
        // Jesli tutaj doszlismy, to znaczy ze nie ma podanego punktu w tablicy - blad.
        throw new WrongArgumentException(point);
    }

    /**
     * Metoda dodaje punkt przed punktem beforePoint.
     *
     * @param point       dodawany punkt
     * @param beforePoint punkt, bezpośrednio przed którym nowy należy dodać
     * @throws WrongArgumentException           wyjątek zgłaszany, gdy jako
     *                                          beforePoint przekazany został punkt,
     *                                          który nie został wcześniej dodany do
     *                                          figury.
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
     *                                          wymiarów punktu point lub
     *                                          beforePoint nie jest zgodna z liczbą
     *                                          wymiarów punktów dodanych wcześniej
     *                                          do kształtu.
     */
    public void addBefore(Point point, Point beforePoint) throws WrongArgumentException, WrongNumberOfDimensionsException {
        //
        // sprawdzenie liczby wymiarow podanego punktu
        if(determine_point_dimensions(beforePoint) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(beforePoint) );
        if(determine_point_dimensions(point) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(point) );
        //
        // sprawdzenie czy w ogole sa jakies punkty
        if(this.points.size() == 0) throw new WrongArgumentException(beforePoint);
        //
        // szukanie punktu
        for(int i = 0; i < this.points.size(); i++) {
            if(this.points.get(i).equals(beforePoint)) {
                this.points.add(i, point);
                this.chronologic_points.add(point);
                return;
            }
        }
        throw new WrongArgumentException(beforePoint);
    }

    /**
     * Metoda dodaje punkt za punktem afterPoint.
     *
     * @param point      dodawany punkt
     * @param afterPoint punkt, bezpośrednio za którym nowy należy dodać
     * @throws WrongArgumentException           wyjątek zgłaszany, gdy jako
     *                                          afterPoint przekazany został punkt,
     *                                          który nie został wcześniej dodany do
     *                                          figury.
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
     *                                          wymiarów punktu point lub afterPoint
     *                                          nie jest zgodna z liczbą wymiarów
     *                                          punktów dodanych wcześniej do
     *                                          kształtu.
     */
    public void addAfter(Point point, Point afterPoint) throws WrongNumberOfDimensionsException, WrongArgumentException {
        //
        // sprawdzenie liczby wymiarow podanego punktu
        if(determine_point_dimensions(afterPoint) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(afterPoint) );
        if(determine_point_dimensions(point) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(point) );
        //
        // sprawdzenie czy w ogole sa jakies punkty
        if(this.points.size() == 0) throw new WrongArgumentException( afterPoint );
        //
        // szukanie punktu
        for(int i = this.points.size() - 1; i >= 0; i--) {
            if(this.points.get(i).equals(afterPoint)) {
                this.points.add(i+1, point);
                this.chronologic_points.add(point);
                return;
            }
        }
        throw new WrongArgumentException( afterPoint );
    }
    /**
     * Metoda usuwa punkt przed punktem beforePoint.
     *
     * @param beforePoint punkt istniejący bezpośrednio przed beforePoint należy
     *                    usunąć.
     * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
     *         zwracana jest referencja do usuniętego punktu.
     * @throws NoSuchPointException             wyjątek zgłaszany, gdy punkt
     *                                          beforePoint jest pierwszym z punktów
     *                                          figury i innego punktu przed nim nie
     *                                          ma.
     * @throws WrongArgumentException           wyjątek zgłaszany, gdy punkt
     *                                          beforePoint nie został wcześniej
     *                                          dodany do figury.
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
     *                                          wymiarów punktu beforePoint nie jest
     *                                          zgodna z liczbą wymiarów punktów
     *                                          dodanych wcześniej do kształtu.
     */
    public Point removeBefore(Point beforePoint) throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException {
        //
        // sprawdzenie czy sa jakies punkty w liscie
        if(this.points.size() == 0) throw new WrongArgumentException( beforePoint );
        //
        // sprawdzenie czy to nie jest pierwszy punkt w liscie.
        if(beforePoint.equals(this.points.get(0))) throw new NoSuchPointException( beforePoint );
        //
        // Sprawdzenie czy liczba wymiarow sie zgadza
        if(determine_point_dimensions(beforePoint) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(beforePoint) );
        //
        // szukanie beforePoint w liscie.
        for(int i = 0; i < this.points.size(); i++) {
            //
            // sprawdzenie czy to szukany punkt
            if(this.points.get(i).equals(beforePoint)) {
                //
                // usuwanie z chronologicznej listy
                this.chronologic_points.remove(this.points.get(i-1));
                //
                // ArrayList.remove() returns the element that was removed.
                return this.points.remove(i-1); // usuwanko
            }
        }
        //
        // punkt nie zostal odnaleziony na liscie.
        throw new WrongArgumentException( beforePoint );
    }

    /**
     * Metoda usuwa punkt za punktem afterPoint.
     *
     * @param afterPoint punkt istniejący bezpośrednio po afterPoint należy usunąć.
     * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
     *         zwracana jest referencja do usuniętego punktu.
     * @throws NoSuchPointException             wyjątek zgłaszany, gdy punkt
     *                                          afterPoint jest ostatnim z punktów
     *                                          figury i innego punktu za nim nie
     *                                          ma.
     * @throws WrongArgumentException           wyjątek zgłaszany, gdy punkt
     *                                          afterPoint nie został wcześniej
     *                                          dodany do figury.
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
     *                                          wymiarów punktu afterPoint nie jest
     *                                          zgodna z liczbą wymiarów punktów
     *                                          dodanych wcześniej do kształtu.
     */

    public Point removeAfter(Point afterPoint) throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException {
        //
        // sprawdzenie czy sa jakies punkty w liscie
        if(this.points.size() == 0) throw new WrongArgumentException( afterPoint );
        //
        // sprawdzenie czy to nie jest ostatni punkt w liscie.
        if(afterPoint.equals(this.points.get(this.points.size()-1))) throw new NoSuchPointException( afterPoint );
        //
        // Sprawdzenie czy liczba wymiarow sie zgadza
        if(determine_point_dimensions(afterPoint) != points_dimensions)
            throw new WrongNumberOfDimensionsException( points_dimensions, determine_point_dimensions(afterPoint));
        //
        // szukanie afterPoint w liscie.
        for(int i = this.points.size() -1; i > 0; i--) {
            //
            // sprawdzenie czy to szukany punkt
            if(this.points.get(i).equals(afterPoint)) {
                //
                // usuwanie punktu z chronologicznej listy
                this.chronologic_points.remove(this.points.get(i+	1));
                //
                // ArrayList.remove() returns the element that was removed.
                return this.points.remove(i+1); // usuwanko
            }
        }
        //
        // punkt nie zostal odnaleziony na liscie.
        throw new WrongArgumentException( afterPoint );
    }

    /**
     * Metoda zwraca aktualną listę wszystkich punktów.
     *
     * @return lista punktów
     */
    public List<Point> get() {
        return this.points;
    }

    /**
     * Metoda zwraca zbiór punktów czyli kolekcję punktów bez powtórzeń. Kolejność
     * punktów w tej kolekcji nie ma znaczenia. Powtórzenie punktu ma miejsce wtedy,
     * gdy P1.equals(P2)=true.
     *
     * @return kolekcja punktów bez powtórzeń.
     */
    public Set<Point> getSetOfPoints() {
        //
        // inicjacja kolekcji
        Set<Point> set_of_points = new HashSet<>();
        //
        // Sprawdzenie, czy lista punktow ma zawartosc
        if(this.points.size() == 0) return set_of_points;
        //
        // Dodanie do kolekcji pierwszego elementu z listy (pierwszy na pewno nie powtarza się wcześniej.)
        set_of_points.add(this.points.get(0));
        //
        // Sprawdzenie, czy lista ma 1 punkt
        if(this.points.size() == 1) return set_of_points;
        //
        // Zapelnienie kolekcji
        for(int i = 0; i < points.size(); i++) {
            boolean unique = true;
            //
            // sprawdzenie czy wartosc juz wystapila (trzeba po kolei sprawdzac, bo musimy porownywac punkty za pomoca metody Point.equals()).
            for(Point p: set_of_points) {
                //
                // jesli znalezlismy taki sam punkt w kolekcji
                if(points.get(i).equals(p)) {
                    unique = false;
                    break;
                }
            }
            //
            // jesli obiekt jest unikatowy :) to go dodajemy do kolekcji
            if(unique) set_of_points.add(points.get(i));
        } // koniec uzupelniania kolekcji
        return set_of_points;
    } // koniec metody getSetOfPoints

    /**
     * Metoda zwraca obiekt typu Optional zawierający (o ile istnieje) punkt,
     * którego współrzędne przekazywane są na liście positions. Jeśli istnieje wiele
     * punktów o podanych wspołrzędnych zwracany jest punkt, który został dodany
     * jako ostatni. Metoda nigdy nie może zakończyć się zwróceniem null, jeśli
     * punktu o podanych współrzędnych nie ma, należy zwrócić pusty obiekt Optional.
     *
     * @param positions lista współrzędnych
     * @return obiekt Optional zawierający (o ile istnieje) punkt, o przekazanych za
     *         pomocą positions współrzędnych, w przeciwnym wypadku pusty obiekt
     *         Optional.
     * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy rozmiar listy
     *                                          jest niezgodny z liczbą wymiarów
     *                                          punktów należących do kształtu.
     */
    public Optional<Point> getByPosition(List<Integer> positions) throws WrongNumberOfDimensionsException {
        //
        // sprawdzenie poprawnosci argumentu
        if(positions.size() > 3)
            throw new WrongNumberOfDimensionsException( points_dimensions, positions.size() );
        //
        // tworzenie nowego punktu na podstawie podanych wspolrzednych
        Point point_to_find = new Point(positions.size());
        //
        // Ustawienie wspolrzednych utworzonego punktu.
        for(int i = 0; i < positions.size(); i++) {
            point_to_find.setPosition(i, positions.get(i));
        }
        //
        // Przeszukanie tablicy points za punktem point_to_find
        for(int k = this.chronologic_points.size() - 1 ; k >= 0; k--) {
            //
            // Sprawdzenie czy to szukany punkt i zwrócenie go zapakowanego w Optional.
            if( point_to_find.equals(this.chronologic_points.get(k)) ) return Optional.of( this.chronologic_points.get(k) );
        }
        //
        // Jesli tutaj doszlismy, to nie ma podanego punktu w tablicy points, zwracamy Optional z null.
        return Optional.empty();
    }

    /**
     * Prywatna metoda zwraca ilość wymiarów podanego punktu.
     * Jeśli podany został błędny lub pusty punkt, zwraca 0.
     *
     * @param p obiekt Point dla ktorego nalezy policzyc liczbe wymiarow
     * @return liczba wymiarow punktu p.
     **/
    private int determine_point_dimensions(Point p) {
        //
        // Zmienna bedzie liczyc wymiary.
        int dimension = 0;
        //
        // Liczenie wymiarow polega na sprawdzaniu wartosci kazdego kolejnego.
        // W koncu sprawdzimy wartosc dla wymiaru ktory nie istnieje i skonczy sie to zwroceniem bledu (ArrayIndexOutOfBoundsException)
        // Patrz metodę Point.getPosition()
        try {
            //
            // nieskonczona petla, ale zakladam, ze punkt ma skonczona liczbe wymiarow ;]
            for(int i=dimension;i<=3 ; i++) {
                //
                // sprawdzenie wartosci dla tego wymiaru. Jesli nie istnieje - wywali blad.
                p.getPosition(i);
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            //
            // zmniejszamy o 1 liczbe wymiarow, bo dla wymiaru dimension wywala blad, zatem punkt ma o 1 mniej wymiar.
            dimension--;
            return dimension;
        }
        return dimension;
    }
}

