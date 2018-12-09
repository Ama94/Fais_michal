import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class GeometricShape extends GeometricShapeInterface {

    List<Point> lista=new LinkedList<>();
    private int indeks=0;   //ilosc elementów
    List<Point> lista2=new LinkedList<>();

    public void add(Point point) {
        lista2=lista;
        lista.add(point);
        indeks++;

    }


    public boolean remove(Point point) {

        lista2=lista;
        if(indeks==0){return false;}
        for(int i=0;i<indeks;i++)
        {

            if(lista.get(i)==point)
            {
                lista.remove(i);
                indeks--;
                return true;
            }


        }
        return false;
    }


    public boolean addBefore(Point point, Point beforePoint) {

        lista2=lista;

        for(int i=0;i<indeks;i++){
            if(lista.get(i)==beforePoint){
                lista.add(i-1,point);
                indeks++;
                return true;
            }

        }
        return false;
    }


    public boolean addAfter(Point point, Point afterPoint) {

        lista2=lista;

        for(int i=indeks;i>0;i--){
            if(lista.get(i)==afterPoint){
                lista.add(i+1,point);
                indeks++;
                return true;
            }

        }
        return false;

    }


    public Point removeBefore(Point beforePoint) {

        lista2=lista;
        if(indeks==0){return null;}
        for(int i=0;i<indeks;i++){
            if(lista.get(i)==beforePoint){
                if(i==1) {return null;}
                Point ret=lista.get(i-1);
                lista.remove(i-1);
                indeks--;
                return ret;
            }

        }
        return null;
    }


    public Point removeAfter(Point afterPoint) {

        lista2=lista;
        if(indeks==0){return null;}
        for(int i=indeks;i>0;i--){
            if(lista.get(i)==afterPoint){
                Point ret=lista.get(i+1);
                lista.remove(i+1);
                indeks--;
                return ret;
            }

        }
        return null;
    }


    public boolean undo() {
        if(indeks>0){
        lista=lista2;
        return true; }
        else
        {return false;}
    }


    public boolean redo() {
    }


    public List<Point> get() {
        return lista;
    }

    public List<Point> getUniq() {
        for(int i=0;i<indeks;i++)
        {
            if(lista.get(i)==lista.get(i+1))
            {
                lista.remove(i+1);
                i--;
                indeks--;
            }
        }
        return lista;
    }

    public Map<Point, Integer> getPointsAsMap() {
    }
}

