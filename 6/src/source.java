import java.util.Arrays;

public class source {
    public static void main(String[] args) {
        PathFinderInterface pathfinder;

        pathfinder = PathFinderEnum.LEFT_HAND_TRAFFIC;
        int[][] map = new int[][]{
                { 0, 0},
                { 0, 0}
        };
        pathfinder.setMap(map);
        System.out.println(Arrays.deepToString(((PathFinderEnum) pathfinder).map));
    }
}
