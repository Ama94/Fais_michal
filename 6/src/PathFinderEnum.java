import java.util.*;
import java.util.function.Function;

 public enum PathFinderEnum implements PathFinderInterface {

    LEFT_HAND_TRAFFIC {

        protected double getp(Skret skret)
        {
            if (skret == Skret.LEWO)
                return med;
            else
                return high;
        }
    },
    RIGHT_HAND_TRAFFIC {

        protected double getp(Skret skret)
        {
            if (skret == Skret.PRAWO)
                return med;
            else
                return high;
        }
    };

    private static Double dot(Graf.Krawedz e) {
        return 2.;
    }

    private static Double dot2(Graf.Krawedz e) {
        return 0.;
    }

    private static Double dot3(Graf.Krawedz e) {
        return 0.;
    }

    public enum Skret {
        PRZOD,
        LEWO,
        PRAWO;
    }
    private class Graf {
        public class Wierz {
            Collection<Krawedz> krawedz;
            Kierunek ten;
             Position pos;


            Wierz(Position pos1, Kierunek ten)
            {
                krawedz = new ArrayList<Krawedz>();
                this.ten = ten;
                pos = pos1;


            }
        }
        private class Krawedz {
             Wierz to;
            double weight;
            Skret skret;
            Krawedz(Wierz _to, double _weight, Skret _turn)
            {
                weight = _weight;
                skret = _turn;
                to = _to;


            }
        }
        Map<Position, Map<Kierunek, Wierz>> lista_wierz = new HashMap<Position, Map<Kierunek, Wierz>>();

        Map<Wierz, Collection<Krawedz>> graf = new HashMap<Wierz, Collection<Krawedz>>();

        void stworz_wierz(Position pos1)
        {
            Map<Kierunek, Wierz> lista = lista_wierz.get(pos1);

                lista = new EnumMap<Kierunek, Wierz>(Kierunek.class);
                lista_wierz.put(pos1, lista);

            for (Kierunek kierunek : Kierunek.values()) {
                lista.put(kierunek, new Wierz(pos1, kierunek));
            }
        }
        void stworz_polaczenie(
                Position od, Kierunek _dir, Position to1)
        {
             Map<Kierunek, Wierz> wierzcholki = lista_wierz.get(od);
             Kierunek przych = _dir.right().right();
            Wierz cel = lista_wierz.get(to1).get(przych);
            wierzcholki.get(przych).krawedz.add(
                    new Krawedz(cel, od.weight, Skret.PRZOD));

            wierzcholki.get(przych.left())
                    .krawedz.add(new Krawedz(cel, od.weight, Skret.PRAWO));
            wierzcholki.get(przych.right())
                    .krawedz.add(new Krawedz(cel, od.weight, Skret.LEWO));
        }
        Map<Kierunek, Wierz> wez_wierzcholek(Position pos1)
        {
            return lista_wierz.get(pos1);
        }
        PositionInterface[] znajdz_droge(Position start1,
                                         Position koniec1,
                                         Function<Krawedz, Double> szerokosc)
        {

            Wierz poczatek_wierz = new Wierz(start1, null);
            Map<Kierunek, Wierz> poczatek_wierz1 = lista_wierz.get(start1);
            for (Wierz v : poczatek_wierz1.values()) {
                for (Krawedz e : v.krawedz) {
                    poczatek_wierz.krawedz.add(e);
                }
            }
             Map<Wierz, Double> dist = new HashMap<Wierz, Double>();
             Map<Wierz, Wierz> poprzedni = new HashMap<Wierz, Wierz>();
             Map<Wierz, Wierz> nastepny = new HashMap<Wierz, Wierz>();
             PriorityQueue<Wierz> queue = new PriorityQueue<>(Comparator.comparingDouble((Wierz mms) -> dist.getOrDefault(mms, Double.MAX_VALUE)));
            queue.offer(poczatek_wierz);
             dist.put(poczatek_wierz, dot2(null));


            Wierz koniec_wierzcholka = new Wierz(null, null);
            while (queue.size() != 0) {
                 Wierz obecny = queue.peek();
                 queue.poll();
                if (obecny.pos.equals(koniec1))
                    koniec_wierzcholka = obecny;

                for (Krawedz krawedz : obecny.krawedz) {
                     Wierz sasiad = krawedz.to;
                     double value = dist.get(obecny) + szerokosc.apply(krawedz)
                            + ((obecny.krawedz.size() > 1) ? getp1(krawedz.skret)
                            : dot2(null));

                    if (dist.getOrDefault(sasiad, Double.MAX_VALUE) > value) {
                        queue.offer(sasiad);
                        nastepny.put(obecny, sasiad);
                        poprzedni.put(sasiad, obecny);
                        dist.put(sasiad, value);

                    }
                }
            }
            Deque<Position> lista = new ArrayDeque<Position>();
            for (Wierz k = koniec_wierzcholka; k != null; k = poprzedni.get(k)) {
                lista.addFirst(k.pos);
            }
            return lista.toArray(new PositionInterface[2]);
        }
    }
    private enum Kierunek {

        ZACHOD {

            public Kierunek right()
            {
                return POLNOC;
            }

            public Kierunek left()
            {
                return POLUDNIE;
            }
        },
        POLUDNIE {

            public Kierunek right()
            {
                return ZACHOD;
            }

            public Kierunek left()
            {
                return WSCHOD;
            }
        },

        POLNOC {

            public Kierunek right()
            {
                return WSCHOD;
            }

            public Kierunek left()
            {
                return ZACHOD;
            }
        },
        WSCHOD {

            public Kierunek right()
            {
                return POLUDNIE;
            }

            public Kierunek left()
            {
                return POLNOC;
            }
        };

        abstract Kierunek left();
        abstract Kierunek right();

    }
     public double getp1(Skret skret)
     {

         return getp(skret);
     }

    private class Position implements PositionInterface {
        int col;
        int row;
        int weight;
        public Position(int kolumna1, int wiersz1, int szerokosc1)
        {
            weight = szerokosc1;
            col = kolumna1;
            row = wiersz1;

        }

        public Position(PositionInterface var)
        {
            this(var.getCol(), var.getRow(), 0);
        }

        public int getRow()
        {
            return row;
        }
        public int getCol()
        {
            return col;
        }


        private boolean przeszkoda()
        {
            return weight == 0;
        }

        public boolean equals(Object ob)
        {


            Position a = (Position) ob;
            if (col == a.col && row == a.row )
            {return true;}
            else {
                return false;
            }
        }

        public int hashCode()
        {

            return Objects.hash(col, row);
        }


    }

     protected abstract double getp(Skret skret);
    public Graf graf;
    public static double high =2.;
    public static double med = 1;




    public void setMap(int[][] mapa1)
    {
        graf = new Graf();
        int wysokosc = mapa1[2].length;
        int szerokosc = mapa1.length;

        Position[][] mapa = new Position[szerokosc][wysokosc];

        int kolumna =0;

        while(kolumna < szerokosc) {

            for (int wiersz = 0; wiersz < wysokosc; wiersz=wiersz+1) {
                Position obecnie = new Position(kolumna, wiersz, mapa1[kolumna][wiersz]);
                mapa[kolumna][wiersz] = obecnie;
                Position sasiad;
                graf.stworz_wierz(obecnie);
                if (obecnie.przeszkoda()){
                    continue;}

                if (!(sasiad = mapa[kolumna][wiersz - 1]).przeszkoda() && wiersz > 0) {
                    graf.stworz_polaczenie(
                            sasiad, Kierunek.POLNOC, obecnie);

                    graf.stworz_polaczenie(
                            obecnie, Kierunek.POLUDNIE, sasiad);

                }
                if (!(sasiad = mapa[kolumna - 1][wiersz]).przeszkoda() && kolumna > 0) {
                    graf.stworz_polaczenie(sasiad, Kierunek.WSCHOD, obecnie);
                    graf.stworz_polaczenie(obecnie, Kierunek.ZACHOD, sasiad);

                }

            }
            kolumna++;
        }
    }

    public PositionInterface[] getShortestRoute(
            PositionInterface begin, PositionInterface end)
    {
        return graf.znajdz_droge(
                new Position(begin), new Position(end), PathFinderEnum::dot);
    }

    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end) {
        return graf.znajdz_droge(
                new Position(begin), new Position(end), PathFinderEnum::dot2);
        }


    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end) {
        return graf.znajdz_droge(
                new Position(begin), new Position(end), PathFinderEnum::dot3);
    }
}
