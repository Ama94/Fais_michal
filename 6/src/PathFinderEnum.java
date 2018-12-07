import java.util.Objects;
import java.util.Stack;

public enum PathFinderEnum implements PathFinderInterface {

    LEFT_HAND_TRAFFIC,
    RIGHT_HAND_TRAFFIC;
    public int[][] map = null;
    private Position currentPosition;
    Stack<Position> stack = new Stack<Position>();

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
        int[] vector = {1,0,1,-1};
        stack.push(currentPosition);




        return new PositionInterface[0];
    }

    @java.lang.Override
    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end) {
        return new PositionInterface[0];
    }

    @java.lang.Override
    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end) {
        return new PositionInterface[0];
    }


    private int Traffic(int traffic){
        //POLNOC
        if (traffic == 1){
            if(map[currentPosition.col][currentPosition.row + 1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row + 1);
                stack.push(currentPosition);
                return 1;
            }
            if(map[currentPosition.col + 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row);
                stack.push(currentPosition);
                return 2;
            }
            if(map[currentPosition.col - 1 ][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1 , currentPosition.row );
                stack.push(currentPosition);
                return 4;
            }
        }
        //WSCHOD
        if (traffic == 2){
            if(map[currentPosition.col + 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row );
                stack.push(currentPosition);
                return 2;
            }
            if(map[currentPosition.col][currentPosition.row - 1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row - 1);
                stack.push(currentPosition);
                return 3;
            }
            if(map[currentPosition.col][currentPosition.row + 1] != 0) {
                currentPosition = new Position(currentPosition.col , currentPosition.row + 1);
                stack.push(currentPosition);
                return 1;
            }
        }
        //POLODNIE
        if (traffic == 3){
            if(map[currentPosition.col][currentPosition.row -1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row - 1);
                stack.push(currentPosition);
                return 3;
            }
            if(map[currentPosition.col -1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1, currentPosition.row);
                stack.push(currentPosition);
                return 4;
            }
            if(map[currentPosition.col ][currentPosition.row + 1] != 0) {
                currentPosition = new Position(currentPosition.col , currentPosition.row + 1);
                stack.push(currentPosition);
                return 1;
            }
        }
        //ZACHOD
        if (traffic == 4){
            if(map[currentPosition.col - 1][currentPosition.row] != 0) {
                currentPosition = new Position(currentPosition.col - 1, currentPosition.row);
                stack.push(currentPosition);
                return 4;
            }
            if(map[currentPosition.col][currentPosition.row +1] != 0) {
                currentPosition = new Position(currentPosition.col, currentPosition.row + 1);
                stack.push(currentPosition);
                return 1;
            }
            if(map[currentPosition.col + 1][currentPosition.row ] != 0) {
                currentPosition = new Position(currentPosition.col + 1, currentPosition.row);
                stack.push(currentPosition);
                return 2;
            }
        }
    }


}