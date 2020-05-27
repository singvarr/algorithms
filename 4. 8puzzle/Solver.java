import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> solution;
    private final boolean hasSolution;

    public Solver(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board shouldn't be null");
        }

        solution = new Stack<Board>();

        Board twin = board.twin();

        SearchNode searchNode = new SearchNode(board, null);
        SearchNode twinSearchNode = new SearchNode(twin, null);

        MinPQ<SearchNode> minPQ = initMinPQ(searchNode);
        MinPQ<SearchNode> twinMinPQ = initMinPQ(twinSearchNode);

        while (true) {
            searchNode = minPQ.delMin();
            twinSearchNode = twinMinPQ.delMin();

            if (!searchNode.board.isGoal() && !twinSearchNode.board.isGoal()) {
                addGameNodes(searchNode, minPQ);
                addGameNodes(twinSearchNode, twinMinPQ);
            } else  {
                break;
            }
        }

        hasSolution = searchNode.board.isGoal();

        if (hasSolution) {
            addSolutionBoards(searchNode);
        }
    }

    private void addSolutionBoards(SearchNode searchNode) {
        while (true) {
            solution.push(searchNode.board);

            if (searchNode.prevSearchNode != null) {
                searchNode = searchNode.prevSearchNode;
            } else {
                break;
            }

        }
    }

    private MinPQ<SearchNode> initMinPQ(SearchNode root) {
        MinPQ<SearchNode> minpq = new MinPQ<SearchNode>();
        minpq.insert(root);

        return minpq;
    }

    private void addGameNodes(SearchNode currentNode, MinPQ<SearchNode> minpq) {
        for (Board board: currentNode.board.neighbors()) {
            if (currentNode.prevSearchNode == null || !board.equals(currentNode.prevSearchNode.board)) {
                SearchNode node = new SearchNode(board, currentNode);
                minpq.insert(node);
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prevSearchNode;
        private final int moves;
        private final int manhattan;

        public SearchNode(Board board, SearchNode prevSearchNode) {
            this.board = board;
            this.prevSearchNode = prevSearchNode;
            this.manhattan = board.manhattan();
            this.moves = prevSearchNode == null ? 0 : prevSearchNode.moves + 1;
        }

        public int compareTo(SearchNode other) {
            int diff = this.manhattan + this.moves - (other.manhattan + other.moves);

            return diff == 0 ? (this.manhattan - other.manhattan) : diff;
        }
    }

    public boolean isSolvable() {
        return hasSolution;
    }

    public int moves() { return isSolvable() ? solution.size() - 1 : -1;  }

    public Iterable<Board> solution() { return isSolvable() ? solution : null; }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
        }
    }
}
