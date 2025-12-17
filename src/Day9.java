import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {
    
    private static List<String> path = new ArrayList<>();
    
    private static String getNextInPath(String point) {
        int idx = path.indexOf(point);
        if (idx == path.size() - 1) {
            return path.getFirst();
        }
        return path.get(idx + 1);
    }
    
    private static String getMoveDirection(String point) {
        String nextPoint = getNextInPath(point);
        List<Long> pointNums = Arrays.stream(point.split(",")).map(Long::parseLong).toList();
        List<Long> nextPointNums = Arrays.stream(nextPoint.split(",")).map(Long::parseLong).toList();
        long pointX = pointNums.getFirst(); long pointY = pointNums.getLast();
        long nextX = nextPointNums.getFirst(); long nextY = nextPointNums.getLast();
        if (pointX == nextX) {
            return pointY < nextY ? "DOWN" : "UP";
        } else {
            return pointX < nextX ? "RIGHT" : "LEFT";
        }
    }
    
    private static boolean nextMoveIntersectsRect(String point, long leftmostX, long rightmostX, long topmostY, long bottommostY) {
        String nextPoint = getNextInPath(point);
        List<Long> pointNums = Arrays.stream(point.split(",")).map(Long::parseLong).toList();
        List<Long> nextPointNums = Arrays.stream(nextPoint.split(",")).map(Long::parseLong).toList();
        long pointX = pointNums.getFirst(); long pointY = pointNums.getLast();
        long nextX = nextPointNums.getFirst(); long nextY = nextPointNums.getLast();
        if (leftmostX < pointX && pointX < rightmostX) {
            return pointY < topmostY ? nextY >= bottommostY : nextY <= topmostY;
        } else if (topmostY < pointY && pointY < bottommostY) {
            return pointX < leftmostX ? nextX >= rightmostX : nextX <= leftmostX;
        }
        return false;
    }
    
    private static boolean isOnBottom(long leftmostX, long rightmostX, long bottommostY, long pointX, long pointY) {
        return bottommostY == pointY && leftmostX < pointX && pointX < rightmostX;
    }
    
    private static boolean isOnTop(long leftmostX, long rightmostX, long topmostY, long pointX, long pointY) {
        return topmostY == pointY && leftmostX < pointX && pointX < rightmostX;
    }

    private static boolean isOnRightSide(long rightmostX, long topmostY, long bottommostY, long pointX, long pointY) {
        return rightmostX == pointX && topmostY < pointY && pointY < bottommostY;
    }
    
    private static boolean isOnLeftSide(long leftmostX, long topmostY, long bottommostY, long pointX, long pointY) {
        return leftmostX == pointX && topmostY < pointY && pointY < bottommostY;
    }
    
    private static boolean isInsideRect(long leftmostX, long rightmostX, long topmostY, long bottommostY, long pointX, long pointY) {
        return leftmostX < pointX && pointX < rightmostX && topmostY < pointY && pointY < bottommostY;
    }
    
    private static boolean isInvalidPoint(String corner1, String corner2, String point) {
        List<Long> corner1Nums = Arrays.stream(corner1.split(",")).map(Long::parseLong).toList();
        List<Long> corner2Nums = Arrays.stream(corner2.split(",")).map(Long::parseLong).toList();
        List<Long> pointNums = Arrays.stream(point.split(",")).map(Long::parseLong).toList();
        long leftmostX = Math.min(corner1Nums.getFirst(), corner2Nums.getFirst());
        long rightmostX = Math.max(corner1Nums.getFirst(), corner2Nums.getFirst());
        long topmostY = Math.min(corner1Nums.getLast(), corner2Nums.getLast());
        long bottommostY = Math.max(corner1Nums.getLast(), corner2Nums.getLast());
        long pointX = pointNums.getFirst(); long pointY = pointNums.getLast();
        String nextMove = getMoveDirection(point);
        boolean onLeftSide = isOnLeftSide(leftmostX, topmostY, bottommostY, pointX, pointY);
        boolean onRightSide = isOnRightSide(rightmostX, topmostY, bottommostY, pointX, pointY);
        boolean onTop = isOnTop(leftmostX, rightmostX, topmostY, pointX, pointY);
        boolean onBottom = isOnBottom(leftmostX, rightmostX, bottommostY, pointX, pointY);
        if (isInsideRect(leftmostX, rightmostX, topmostY, bottommostY, pointX, pointY)) {
            return true;
        } else if ((onLeftSide && nextMove.equals("RIGHT"))
                || (onRightSide && nextMove.equals("LEFT"))
                || (onTop && nextMove.equals("DOWN"))
                || (onBottom && nextMove.equals("UP"))){
            return true;
        } else if (!onLeftSide && !onRightSide && !onTop && !onBottom) {
            return nextMoveIntersectsRect(point, leftmostX, rightmostX, topmostY, bottommostY);
        }
        return false;
    }
    
    private static boolean validateRect(String corner1, String corner2) {
        String nextPoint = getNextInPath(corner1);
        while (!nextPoint.equals(corner1)) {
            if (isInvalidPoint(corner1, corner2, nextPoint)) {
                return false;
            }
            nextPoint = getNextInPath(nextPoint);
        }
        return true;
    }
    
    private static double calcRectArea(String corner1, String corner2) {
        List<Long> corner1Num = Arrays.stream(corner1.split(",")).map(Long::parseLong).toList();
        List<Long> corner2Num = Arrays.stream(corner2.split(",")).map(Long::parseLong).toList();
        long x1 = corner1Num.getFirst(); long y1 = corner1Num.getLast();
        long x2 = corner2Num.getFirst(); long y2 = corner2Num.getLast();
        return (Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1);
    }
    
    private static double processPoints(List<String> points) {
        double maxArea = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (!validateRect(points.get(i), points.get(j))) {
                    continue;
                }
                double area = calcRectArea(points.get(i), points.get(j));
                maxArea = Math.max(area, maxArea);
            }
        }
        return maxArea;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        path = inputData.stream().toList();
        double maxArea = processPoints(inputData);
        System.out.println(maxArea);
    }
}
