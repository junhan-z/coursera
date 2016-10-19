import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top = 0;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF uf;
    private byte[] site;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        // assign top with 0, bottom with n^2 + 1, n^2 + 2 in total
        top = 0;
        bottom = n * n + 1;
        size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        site = new byte[n * n];
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        site[convertIndex(row, col)] = 1;
        connectNeighbors(row, col);
    }

    // row and col are based on [0, n-1]
    private void connectNeighbors(int row, int col) {
        int curIndex = convertIndex(row, col);
        // up
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(curIndex, convertIndex(row - 1, col));
        } else if (row == 1) {
            uf.union(curIndex, top);
        }

        // down
        if (row < size && isOpen(row + 1, col)) {
            uf.union(curIndex, convertIndex(row + 1, col));
        } else if (row == size) {
            uf.union(curIndex, bottom);
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(curIndex, convertIndex(row, col - 1));
        }
        // right
        if (col < size && isOpen(row, col + 1)) {
            uf.union(curIndex, convertIndex(row, col+1));
        }
    }

    public boolean isOpen(int row, int col) {
        isInBound(row, col);
        return site[convertIndex(row, col)] != 0;
    }

    public boolean isFull(int row, int col) {
        // virtual top and bottom are connected and
        // this point is connected to it
        if (!isOpen(row, col)) return false;

        int index = convertIndex(row, col);
        if (site[index] == 2) return true;
        boolean result = uf.connected(top, bottom) && uf.connected(index, top);
        if (result) site[index] = 2;

        return result;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private boolean isInBound(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IndexOutOfBoundsException(String.format("row %d col %d", row, col));
        }
        return true;
    }

    private int convertIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    public void display() {
        for (int i = 1; i <= size; i++) {
            String content = "";
            for (int j = 1; j <= size; j++) {
                content += site[convertIndex(i, j)] + " ";
            }
            System.out.println(content);
        }
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
