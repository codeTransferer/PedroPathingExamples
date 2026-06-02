package org.firstinspires.ftc.teamcode.pedroPathing;


public class TileAddons  {
    private static double xMax=0;
    private static double xMin=0;
    private static double yMax=0;
    private static double yMin=0;
    private static boolean flipped = false;
    private static boolean flippedX = false;
    private static boolean flippedY = false;

    // Variable Sets/Gets
    public static void setXMax(double newMax) {
        xMax = newMax;
    }
    public static void setYMax(double newMax) {
        yMax = newMax;
    }
    public static void setXMin(double newMax) {
        xMin = newMax;
    }
    public static void setYMin(double newMax) {
        yMin = newMax;
    }
    public static void flipXandY() {
        flipped = !flipped;
    }
    public static void flipX() {
        flippedX = !flippedX;
    }
    public static void flipY() {
        flippedY = !flippedY;
    }

    public static double getYMin() {
        return yMin;
    }

    public static double getYMax() {
        return yMax;
    }

    public static double getXMin() {
        return xMin;
    }

    public static double getXMax() {
        return xMax;
    }

    public static void setXRange(double newMin, double newMax) {
        xMin = newMin;
        xMax = newMax;
    }

    public static void setYRange(double newMin, double newMax) {
        yMin = newMin;
        yMax = newMax;
    }

    public static double convertToX(double tiles) {
        if (tiles > 6 || tiles < 0) {
            return -1;
        }
        if (flipped) {
            if (flippedX) {
                return (yMax - ((tiles * ((yMax - yMin) / 6)) + yMin));
            } else {
                return ((tiles * ((yMax - yMin) / 6)) + yMin);
            }
        } else {
            if (flippedX) {
                return (xMax - ((tiles * ((xMax - xMin)/6)) + xMin));
            } else {
                return ((tiles * ((xMax - xMin)/6)) + xMin);
            }
        }
    }

    public static double convertToY(double tiles) {
        if (tiles > 6 || tiles < 0) {
            return -1;
        }
        if (!flipped) {
            if (flippedY) {
                return (yMax - ((tiles * ((yMax - yMin) / 6)) + yMin));
            } else {
                return ((tiles * ((yMax - yMin) / 6)) + yMin);
            }
        } else {
            if (flippedY) {
                return (xMax - ((tiles * ((xMax - xMin)/6)) + xMin));
            } else {
                return ((tiles * ((xMax - xMin)/6)) + xMin);
            }
        }
    }
}

