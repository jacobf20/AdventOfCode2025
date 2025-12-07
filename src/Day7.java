import java.util.*;

public class Day7 {
    
    private static Set<Integer> beamCols = new HashSet<>();
    private static Map<Integer, Long> beamTimelines = new HashMap<>();
    
    private static int splitCount = 0;
    private static long timelineCount = 0;
    
    private static int maxCol = 0;
    
    private static void startProcess(String firstRow) {
        beamCols.add(firstRow.indexOf("S"));
    }
    
    private static void handleSplit(int col) {
        beamCols.remove(col);
        long valToAdd = beamTimelines.get(col) != null ? beamTimelines.get(col) : 1;
        splitCount += 1;
        if (col == 0) {
            beamCols.add(1);
            if (beamTimelines.containsKey(1)) {
                beamTimelines.put(1, beamTimelines.get(1) + valToAdd);
            } else {
                beamTimelines.put(1, valToAdd);
            }
        } else if (col == maxCol) {
            beamCols.add(maxCol - 1);
            if (beamTimelines.containsKey(maxCol - 1)) {
                beamTimelines.put(maxCol - 1, beamTimelines.get(maxCol - 1) + valToAdd);
            } else {
                beamTimelines.put(maxCol - 1, valToAdd);
            }
        } else {
            beamCols.add(col - 1);
            beamCols.add(col + 1);
            if (beamTimelines.containsKey(col - 1)) {
                beamTimelines.put(col - 1, beamTimelines.get(col - 1) + valToAdd);
            } else {
                beamTimelines.put(col - 1, valToAdd);
            }
            if (beamTimelines.containsKey(col + 1)) {
                beamTimelines.put(col + 1, beamTimelines.get(col + 1) + valToAdd);
            } else {
                beamTimelines.put(col + 1, valToAdd);
            }
        }
    }
    
    private static void processRow(String row) {
        List<Integer> beamsToSplit = new ArrayList<>();
        beamCols.forEach(col -> {
            if (row.charAt(col) == '^') {
                beamsToSplit.add(col);
            }
        });
        beamsToSplit.forEach(Day7::handleSplit);
        beamsToSplit.forEach(col -> beamTimelines.remove(col));
    }
    
    private static void countTimelines() {
        for (long timelines : beamTimelines.values()) {
            timelineCount += timelines;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        maxCol = inputData.getFirst().length() - 1;
        startProcess(inputData.getFirst());
        for (int i = 1; i < inputData.size(); i++) {
            String row = inputData.get(i);
            processRow(row);
        }
        countTimelines();
        System.out.println(splitCount);
        System.out.println(timelineCount);
    }
}
