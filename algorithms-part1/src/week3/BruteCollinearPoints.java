package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segmentList;

    public BruteCollinearPoints(Point[] points) {
        segmentList = new ArrayList<>();
        if (points == null) {
            throw new NullPointerException("Given points are null");
        }

        Point[] copies = points.clone();
        Arrays.sort(copies);

        if (hasDuplicate(copies)) {
            throw new IllegalArgumentException("Contains duplicated points");
        }

        for (int i = 0; i < copies.length-3; i++) {
            for (int j = i+1; j < copies.length-2; j++) {
                double slopeI2J = copies[i].slopeTo(copies[j]);
                for (int k = j+1; k < copies.length-1; k++) {
                    double slopeI2K = copies[i].slopeTo(copies[k]);

                    if (slopeI2J == slopeI2K) {
                        for (int m = k+1; m < copies.length; m++) {
                            double slopeI2M = copies[i].slopeTo(copies[m]);
                            if (slopeI2J == slopeI2M) {
                                segmentList.add(new LineSegment(copies[i], copies[m]));
                            }
                        }
                    }

                }
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
