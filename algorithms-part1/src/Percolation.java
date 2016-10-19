import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top = 0;
    private int bottom;
    private Integer[][] matrix;
    private int size;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        top = 0;
        bottom = n * n + 1;

        // initialization
        // 0, n-1
        matrix = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = null;
            }
        }
        size = n;

        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        row = row - 1;
        col = col - 1;

        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (matrix[row][col] == null) {
            matrix[row][col] = row * size + col + 1;
        }
        connectNeighbor(row, col);
    }

    // row and col are based on [0, n-1]
    private void connectNeighbor(int row, int col) {
        // up
        if (row > 0  && matrix[row-1][col] != null) {
            uf.union(matrix[row][col], matrix[row-1][col]);
        } else if (row == 0) {
            uf.union(matrix[row][col], top);
        }

        // down
        if (row + 1 < size && matrix[row+1][col] != null) {
            uf.union(matrix[row][col], matrix[row+1][col]);
        } else if (row == size -1) {
            uf.union(matrix[row][col], bottom);
        }
        // left
        if (col > 0 && matrix[row][col-1] != null) {
            uf.union(matrix[row][col], matrix[row][col-1]);
        }
        // right
        if (col + 1 < size && matrix[row][col+1] != null) {
            uf.union(matrix[row][col], matrix[row][col+1]);
        }
    }

    public boolean isOpen(int row, int col) {
        row -= 1;
        col -= 1;
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row][col] != null;
    }

    public boolean isFull(int row, int col) {
        // virtual top and bottom are connected and
        // this point is connected to it
        row -= 1;
        col -= 1;
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return uf.connected(top, bottom) && uf.connected(matrix[row][col], top);
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            String content = "";
            for (int j = 0; j < size; j++) {
                content += matrix[i][j] + " ";
            }
            System.out.println(content);
        }
    }

    public boolean connected(int row1, int col1, int row2, int col2) {
        row1 -= 1;
        col1 -= 1;
        row2 -= 1;
        col2 -= 1;

        return uf.connected(row1 * size + col1 + 1, row2 * size + col2 + 1);
    }
    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.display();
        System.out.println(p.percolates());

        p.open(1,1);
        p.open(2,1);
        p.open(3,1);
        p.open(4,1);
        p.open(5,1);
        p.open(6,1);
        p.display();
        System.out.println("after 1 - 6 " + p.percolates());

        p.open(7, 1);
        p.open(8, 1);
        p.open(9, 1);
        System.out.println("after 1 - 9 " + p.percolates());
        p.open(10, 1);

        System.out.println(p.percolates());
    }

}
