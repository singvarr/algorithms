import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private final int[] board;
    private final int size;
    private final Stack<Board> neighborBoardsStack;

    public Board(int[][] tiles) {
        size = tiles.length;

        int len = (int) Math.pow(size, 2) + 1;
        board = new int[len];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int index = y * size + x + 1;
                board[index] =  tiles[y][x];
            }
        }

        neighborBoardsStack = new Stack<Board>();
    }

    private int[] mapTo2DCoords(int i) {
        int[] coords = new int[2];

        coords[0] = i % size == 0 ? size - 1 : i % size - 1;
        coords[1] =  i > size ? (i - 1 - coords[0]) / size : 0;

        return coords;
    }

    private int getBlankTileIndex() {
        int result = 0;
        for (int i = 1; i < board.length; i++) {
            if (board[i] == 0) {
                result = i;
                break;
            }
        }

        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 1; i < board.length; i++) {
            s.append(String.format("%2d ", board[i]));
            if (i % size == 0) {
                s.append("\n");
            }
        }

        return s.toString();
    }

    public int dimension() {
        return size;
    }

    public int hamming() {
        int tilesInWrongPosition = 0;
        for (int i = 1; i < board.length; i++) {
            if (board[i] != i && board[i] != 0) {
                tilesInWrongPosition++;
            }
        }

        return tilesInWrongPosition;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 1; i < board.length; i++) {
            if (i != board[i] && board[i] != 0) {
                int[] targetCoords = mapTo2DCoords(board[i]);
                int[] pointCoords = mapTo2DCoords(i);

                int horizontal = Math.abs(pointCoords[0] - targetCoords[0]);
                int vertical = Math.abs(pointCoords[1] - targetCoords[1]);

                int pointDistance = vertical + horizontal;
                result += pointDistance;
            }
        }

        return result;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        else if (y == null || this.getClass() != y.getClass()) return false;

        Board otherBoard = (Board) y;
        if (this.dimension() != otherBoard.dimension()) return false;

        return Arrays.equals(this.board, otherBoard.board);
    }

    public Iterable<Board> neighbors()      {
        int blank = getBlankTileIndex();
        int[] blankCoords = mapTo2DCoords(blank);

        int left = blank - 1;
        int[] leftCoords = mapTo2DCoords(left);
        if (left >= 1 && leftCoords[1] == blankCoords[1]) {
            createNeighborBoard(left);
        }

        int right = blank + 1;
        int[] rightCoords = mapTo2DCoords(right);
        if (right <= board.length - 1 && rightCoords[1] == blankCoords[1]) {
            createNeighborBoard(right);
        }

        int topIndex = blank - size;
        if (topIndex >= 1) createNeighborBoard(topIndex);

        int bottomIndex = blank + size;
        if (bottomIndex <= board.length - 1) createNeighborBoard(bottomIndex);

        return neighborBoardsStack;
    }

    private void createNeighborBoard(int replaceIndex) {
        int[] copyTiles = swapTiles(getBlankTileIndex(), replaceIndex);

        Board neighbor = createBoard(copyTiles);
        neighborBoardsStack.push(neighbor);
    }

    private Board createBoard(int[] tiles) {
        int[][] neighborBoardTiles = new int[size][size];

        for (int i = 1; i < tiles.length; i++) {
            int[] coords = mapTo2DCoords(i);
            int x = coords[0];
            int y = coords[1];
            neighborBoardTiles[y][x] = tiles[i];
        }

        return new Board(neighborBoardTiles);
    }

    private int[] swapTiles(int a, int b) {
        int[] boardArr = Arrays.copyOf(board, board.length);

        int tmp = boardArr[a];
        boardArr[a] = boardArr[b];
        boardArr[b] = tmp;

        return boardArr;
    }

    public Board twin() {
        int a = 0;
        int b = 0;

        for (int i = 1; i < board.length; i++) {
            if (a != 0 && board[i] != 0) {
                b = i;
                break;
            }

            if (board[i] != 0 && a == 0) {
                a = i;
            }
        }

        int[] twinTiles = swapTiles(a, b);
        return createBoard(twinTiles);
    }
}
