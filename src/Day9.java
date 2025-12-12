import java.util.Arrays;
import java.util.List;

public class Day9 {
    
    private static double calcRectArea(String corner1, String corner2) {
        List<Long> corner1Num = Arrays.stream(corner1.split(",")).map(Long::parseLong).toList();
        List<Long> corner2Num = Arrays.stream(corner2.split(",")).map(Long::parseLong).toList();
        long x1 = corner1Num.getFirst();
        long y1 = corner1Num.getLast();
        long x2 = corner2Num.getFirst();
        long y2 = corner2Num.getLast();
        return (x1 - x2 + 1) * (y1 - y2 + 1);
    }
    
    private static double processPoints(List<String> points) {
        double maxArea = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i == j) {
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
        double maxArea = processPoints(inputData);
        System.out.println(maxArea);
    }
}
