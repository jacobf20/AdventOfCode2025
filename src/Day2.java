import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    private static long result = 0;

    private static List<Integer> getFactors(int num) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i < num; i++) {
            if (num % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }

    private static boolean validateId(String id) {
        List<Integer> factors = getFactors(id.length());
        for (int i : factors) {
            String firstPart = id.substring(0, i);
            int stop = i + i;
            boolean matching = true;
            for (int start = i; start < id.length(); start += i) {
                String nextPart = id.substring(start, stop);
                if (!firstPart.equals(nextPart)) {
                    matching = false;
                    break;
                }
                stop += i;
            }
            if (matching) {
                return false;
            }
        }
        return true;
    }

    private static void checkIds(String idString) {
        String[] ids = idString.split("-");
        long firstId = Long.parseLong(ids[0]);
        long endId = Long.parseLong(ids[1]);
        for (long i = firstId; i <= endId; i++) {
            String id = String.valueOf(i);
            String firstHalf = id.substring(0, id.length() / 2);
            String secondHalf = id.substring(id.length() / 2);
            if (!validateId(id)) {
                result += Long.parseLong(id);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByComma(filePath);
        inputData.forEach(Day2::checkIds);
        System.out.println(result);
    }
}
