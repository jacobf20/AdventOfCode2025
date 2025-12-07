import java.util.*;

public class Day5 {

    private static long result = 0;

    private static long result2 = 0;

    private static long findRangeGaps(Map<Long, Long> freshRanges) {
        List<Long> keys = freshRanges.keySet().stream().toList();
        Long firstStart = keys.getFirst();
        Long firstEnd = freshRanges.get(firstStart);
        long rangeGaps = 0;
        for (int i = 1; i < keys.size(); i++) {
            Long nextStart = keys.get(i);
            Long nextEnd = freshRanges.get(nextStart);
            if (firstEnd < nextStart) {
                rangeGaps += (nextStart - 1) - firstEnd;
            }
            if (nextEnd > firstEnd) {
                firstEnd = nextEnd;
            }
        }
        return rangeGaps;
    }

    private static void countFreshIds(Map<Long, Long> freshRanges) {
        List<Long> keys = freshRanges.keySet().stream().toList();
        Long firstStart = keys.getFirst();
        Long lastEnd = freshRanges.get(keys.getLast());
        long rangeGaps = findRangeGaps(freshRanges);
        result2 = (lastEnd + 1) - firstStart - rangeGaps;
    }

    private static void countFreshIngredients(List<String> ingredients, Map<Long, Long> freshRanges) {
        for (String ingredient : ingredients) {
            long ingredientVal = Long.parseLong(ingredient);
            for (Long start : freshRanges.keySet()) {
                if (ingredientVal >= start && ingredientVal <= freshRanges.get(start)) {
                    result += 1;
                    break;
                }
            }
        }
    }

    private static Map<Long, Long> getFreshRanges(List<String> inputData) {
        Map<Long, Long> freshRanges = new TreeMap<>();
        String row = inputData.removeFirst();
        while (!row.isEmpty()) {
            List<String> rangeVals = Arrays.asList(row.split("-"));
            long start = Long.parseLong(rangeVals.getFirst());
            long end = Long.parseLong(rangeVals.getLast());
            if (freshRanges.containsKey(start)) {
                if (freshRanges.get(start) < end) {
                    freshRanges.put(start, end);
                }
            } else {
                freshRanges.put(start, end);
            }
            row = inputData.removeFirst();
        }
        return freshRanges;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        Map<Long, Long> freshRanges = getFreshRanges(inputData);
        countFreshIngredients(inputData, freshRanges);
        countFreshIds(freshRanges);
        System.out.println(result);
        System.out.println(result2);
    }
}
