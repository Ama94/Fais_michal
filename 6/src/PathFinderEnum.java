import java.util.Objects;
import java.util.Stack;

public enum PathFinderEnum implements PathFinderInterface {

    LEFT_HAND_TRAFFIC,
    RIGHT_HAND_TRAFFIC;
    public int[][] map = null;
    private Position currentPosition;
    Stack<Position> stack = new Stack<Position>();
    protected int len;
    private class Position implements  PositionInterface {
        private final int col;
        private final int row;

        Position( int col, int row ) {
            this.col = col;
            this.row = row;
        }

        Position( PositionInterface pi ) {
            this.col = pi.getCol();
            this.row = pi.getRow();
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getCol() {
            return col;
        }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PMO_Test.Position position = (PMO_Test.Position) o;
        return col == position.col &&
                row == position.row;
    }*/

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }
        @Override
        public String toString() {
            return "Position{" +
                    "col=" + col +
                    ", row=" + row +
                    '}';
        }
    }



    @java.lang.Override
    public void setMap(int[][] map) {
        this.map = map;
    }

    @java.lang.Override
    public PositionInterface[] getShortestRoute(PositionInterface begin, PositionInterface end) {
        currentPosition = new Position(begin);
        stack.push(currentPosition);
        //int temp = 1;
        len = 0;
        Traffic(1, end);
        PositionInterface[] result = new PositionInterface[len + 1];
        while(len >= 0){
            result[len--] = stack.pop();
        }
        return result;
        //return new PositionInterface[0];
    }

    @java.lang.Override
    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end) {
        return new PositionInterface[0];
    }

    @java.lang.Override
    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end) {
        return new PositionInterface[0];
    }


    private int Traffic(int traffic, PositionInterface end){
        if(currentPosition.getCol() == end.getCol() && currentPosition.getRow() == end.getRow())
            return 0;
        //POLNOC
        if (traffic == 1){
            if(currentPosition.row + 1 <= map[currentPosition.col].length && map[currentPosition.col][currentPosition.row + 1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row + 1);
                stack.push(currentPosition);
                len++;
                return Traffic(1,end);

            }
            if(currentPosition.col + 1 <= map.length && map[currentPosition.col + 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row);
                stack.push(currentPosition);
                len++;
                return Traffic(2,end);
            }
            if(currentPosition.col - 1 >= 0 && map[currentPosition.col - 1 ][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1 , currentPosition.row );
                stack.push(currentPosition);
                len++;
                return Traffic(4,end);
            }
        }
        //WSCHOD
        if (traffic == 2){
            if(currentPosition.col + 1 <= map.length && map[currentPosition.col + 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row );
                stack.push(currentPosition);
                len++;
                return Traffic(2,end);
            }
            if(currentPosition.row - 1 >= 0 && map[currentPosition.col][currentPosition.row - 1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row - 1);
                stack.push(currentPosition);
                len++;
                return Traffic(3,end);
            }
            if(currentPosition.row + 1 <= map[currentPosition.col].length && map[currentPosition.col][currentPosition.row + 1] != 0) {
                currentPosition = new Position(currentPosition.col , currentPosition.row + 1);
                stack.push(currentPosition);
                len++;
                return Traffic(1,end);
            }
        }
        //POLODNIE
        if (traffic == 3){
            if(currentPosition.row - 1 >= 0 && map[currentPosition.col][currentPosition.row -1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row - 1);
                stack.push(currentPosition);
                len++;
                return Traffic(3,end);
            }
            if(currentPosition.col - 1 >= 0 && map[currentPosition.col -1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1, currentPosition.row);
                stack.push(currentPosition);
                len++;
                return Traffic(4,end);
            }
            if(currentPosition.row + 1 <= map[currentPosition.col].length && map[currentPosition.col + 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col + 1 , currentPosition.row);
                stack.push(currentPosition);
                len++;
                return Traffic(2,end);
            }
        }
        //ZACHOD
        if (traffic == 4){
            if(currentPosition.col - 1 >= 0&& map[currentPosition.col - 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1, currentPosition.row);
                stack.push(currentPosition);
                len++;
                return Traffic(4,end);
            }
            if(currentPosition.row + 1 <= map[currentPosition.col].length && map[currentPosition.col][currentPosition.row +1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row + 1);
                stack.push(currentPosition);
                len++;
                return Traffic(1,end);
            }
            if(currentPosition.col + 1 <= map.length && map[currentPosition.col + 1][currentPosition.row ] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row);
                stack.push(currentPosition);
                len++;
                return Traffic(2,end);
            }
        }
        return traffic;
    }

}