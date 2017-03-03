package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segmentList;

    public FastCollinearPoints(Point[] points) {
        segmentList = new ArrayList<>();

        if (points == null) {
            throw new NullPointerException("Given points are null");
        }

        Point[] copies = points.clone();
        Arrays.sort(copies);

        if (hasDuplicate(copies)) {
            throw new IllegalArgumentException("Contains duplicated points");

        }

        for (int i = 0; i < copies.length - 3; i++) {
            Arrays.sort(copies, copies[i].slopeOrder());
            for (int cur = 0, first = 1, last = 2; last < copies.length; last++) {
                while (last < copies.length
                        && Double.compare(copies[cur].slopeTo(copies[first]), copies[cur].slopeTo(copies[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && copies[cur].compareTo(copies[first]) < 0) {
                    segmentList.add(new LineSegment(copies[cur], copies[last - 1]));
                }

                first = last;
            }
        }

    }

    public int numberOfSegments() {
        return segmentList.size();
    }

    public LineSegment[] segments() {
        return segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                return true;
            }
        }

        return false;
    }
}
