import java.lang.reflect.Array;
import java.util.*;

public class Day4 {

    private static int result = 0;
    private static Map<Integer, List<Integer>> rollsToBeRemoved = new HashMap<>();

    private static boolean isPaperRoll(String pos) {
        return pos.equals("@");
    }

    private static int countSurroundingRolls(List<ArrayList<String>> map, int row, int col) {
        int rollCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > map.size() - 1) {
                continue;
            }
            for (int j = col - 1; j <= col + 1; j++) {
                if (j < 0 || j > map.getFirst().size() - 1 || (i == row && j == col)) {
                    continue;
                }
                if (isPaperRoll(map.get(i).get(j))) {
                    rollCount += 1;
                }
            }
        }
        return rollCount;
    }

    private static boolean isForkliftReachable(List<ArrayList<String>> map, int row, int col) {
        String currentPos = map.get(row).get(col);
        if (!isPaperRoll(currentPos)) {
            return false;
        }
        if ((row == 0 && col == 0)
                || (row == 0 && col == map.getFirst().size() - 1)
                || (row == map.size() - 1 && col == 0)
                || (row == map.size() - 1 && col == map.getFirst().size() - 1)) {
            return true;
        }
        return countSurroundingRolls(map, row, col) < 4;
    }

    private static List<ArrayList<String>> constructMap(List<String> inputData) {
        List<ArrayList<String>> map = new ArrayList<>();
        for (String row : inputData) {
            ArrayList<String> splitRow = new ArrayList<>(Arrays.asList(row.split("")));
            map.add(splitRow);
        }
        return map;
    }

    private static void processRolls(List<ArrayList<String>> map) {
        for (int row = 0; row < map.size(); row++) {
            for (int col = 0; col < map.getFirst().size(); col++) {
                if (isForkliftReachable(map, row, col)) {
                    result += 1;
                    if (!rollsToBeRemoved.containsKey(row)) {
                        rollsToBeRemoved.put(row, new ArrayList<>());
                        rollsToBeRemoved.get(row).add(col);
                    } else {
                        rollsToBeRemoved.get(row).add(col);
                    }
                }
            }
        }
    }

    private static void removeRolls(List<ArrayList<String>> map) {
        for (int row : rollsToBeRemoved.keySet()) {
            List<Integer> cols = rollsToBeRemoved.get(row);
            for (int col : cols) {
                map.get(row).set(col, "x");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        List<ArrayList<String>> inputMap = constructMap(inputData);
        int resultBeforeProcess = -1;
        while (result > resultBeforeProcess) {
            resultBeforeProcess = result;
            processRolls(inputMap);
            removeRolls(inputMap);
            rollsToBeRemoved = new HashMap<>();
        }
        System.out.println(result);
    }
}
