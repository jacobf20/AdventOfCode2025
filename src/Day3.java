import java.util.*;

public class Day3 {

    private static int result = 0;
    private static long result2 = 0;


    private static long findMaxJoltagePart2(String bank) {
        List<String> batteries = Arrays.stream(bank.split("")).toList();
        Map<Integer, String> maxBatteries = new TreeMap<>();
        int maxBatteryIdx = 0;
        for (int i = 11; i >= 0; i --) {
            int maxBattery = 0;
            for (int j = maxBatteryIdx; j < batteries.size() - i; j++) {
                int battery = Integer.parseInt(batteries.get(j));
                if (battery > maxBattery && !maxBatteries.containsKey(j)) {
                    maxBatteryIdx = j;
                    maxBattery = battery;
                }
            }
            maxBatteries.put(maxBatteryIdx, String.valueOf(maxBattery));
            maxBatteryIdx += 1;
        }
        StringBuilder maxJoltage = new StringBuilder();
        for (String maxBattery : maxBatteries.values()) {
            maxJoltage.append(maxBattery);
        }
        return Long.parseLong(maxJoltage.toString());
    }

    private static int findMaxJoltage(String bank) {
        List<String> batteries = Arrays.stream(bank.split("")).toList();
        int maxJoltage = 0;
        for (int i = 0; i < batteries.size(); i++) {
            String firstBattery = batteries.get(i);
            for (int j = i + 1; j < batteries.size(); j++) {
                String secondBattery = batteries.get(j);
                String joltage = firstBattery + secondBattery;
                int joltageVal = Integer.parseInt(joltage);
                if (joltageVal > maxJoltage) {
                    maxJoltage = joltageVal;
                }
            }
        }
        return maxJoltage;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        for (String bank : inputData) {
            result += findMaxJoltage(bank);
            result2 += findMaxJoltagePart2(bank);
        }
        System.out.println(result);
        System.out.println(result2);
    }
}
