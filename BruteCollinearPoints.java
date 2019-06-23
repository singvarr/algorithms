import java.util.Arrays;

public class BruteCollinearPoints {
    private final double[] slopes;
    private final int slopesLimit = 3;
    private int segmentsLen;

    public BruteCollinearPoints(Point[] points) {
        validateInput(points);
        Point base = points[0];
        Arrays.sort(points, base.slopeOrder());

        slopes = new double[slopesLimit];
        for (int i = 1; i < slopes.length; i++) {
            slopes[i - 1] = points[0].slopeTo(points[i]);
        }
    }

    public int numberOfSegments() {
        return segmentsLen;
    }

    public LineSegment[] segments() {
        segmentsLen = 1;
        for (int i = 0; i < point.length; i++) {
            if (Arra)
        }
    }

    private void validateInput(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Invalid args");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("Invalid args");
            }
        }
    }
}
