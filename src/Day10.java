import java.util.*;

public class Day10 {
    
    private static final Map<String, Integer> memo = new HashMap<>();
    private static int result;
    
    private static List<String> getLightPossibilities(String goalLights) {
        int length = goalLights.length();
        double combinations = Math.pow(2, length);
        List<String> lightsCombos = new ArrayList<>();
        for (int i = 1; i < combinations; i++) {
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(i));
            while (binary.length() < length) {
                binary.insert(0, "0");
            }
            StringBuilder lights = new StringBuilder();
            List<String> binarySplit = Arrays.stream(binary.toString().split("")).toList();
            for (String digit : binarySplit) {
                if (digit.equals("1")) {
                    lights.append("#");
                } else {
                    lights.append(".");
                }
            }
            lightsCombos.add(lights.toString());
        }
        lightsCombos.remove(goalLights);
        lightsCombos.add(goalLights);
        return lightsCombos;
    }
    
    private static int minIgnoreNull(int a, int b) {
        if (a == -1) {
            return b;
        } else if (b == -1) {
            return a;
        }
        return Math.min(a, b);
    }
    
    private static int minimumButtonPresses(String lights, List<String> buttons) {
        if (memo.containsKey(lights)) {
            return memo.get(lights);
        }
        
        String baseLights = ".".repeat(lights.length());
        memo.put(baseLights, 0);
        for (String button : buttons) {
            memo.put(pressButtons(baseLights, button), 1);
        }
        
        while (!memo.containsKey(lights)) {
            for (String lightCombo : getLightPossibilities(lights)) {
                if (memo.containsKey(lightCombo)) {
                    continue;
                }
                for (String button : buttons) {
                    String subproblem = pressButtons(lightCombo, button);
                    if (!memo.containsKey(lightCombo) && !memo.containsKey(subproblem)) {
                        continue;
                    }
                    memo.put(lightCombo, minIgnoreNull(memo.getOrDefault(lightCombo, -1), memo.getOrDefault(subproblem, -2) + 1));
                }
            }
        }
        return memo.getOrDefault(lights, Integer.MAX_VALUE);
    }
    
    private static String pressButtons(String currentLights, String buttons) {
        List<Integer> buttonNums = Arrays.stream(buttons.split(",")).map(Integer::parseInt).toList();
        String[] splitLights = currentLights.split("");
        for (int num : buttonNums) {
            if (splitLights[num].equals(".")) {
                splitLights[num] = "#";
            } else {
                splitLights[num] = ".";
            }
        }
        return String.join("", splitLights);
    }
    
    private static List<String> getButtons (String inputLine) {
        List<String> buttons = new ArrayList<>();
        int start; int end;
        int i = 0;
        while (i < inputLine.length()) {
            start = inputLine.indexOf("(", i);
            end = inputLine.indexOf(")", i);
            if (start < 0 || end < 0) {
                break;
            }
            buttons.add(inputLine.substring(start + 1, end));
            i = end + 1;
        }
        return buttons;
    }
    
    private static String getLightConfig (String inputLine) {
        int start = inputLine.indexOf("[");
        int end = inputLine.indexOf("]");
        return inputLine.substring(start + 1, end);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        for (String line : inputData) {
            memo.clear();
            result += minimumButtonPresses(getLightConfig(line), getButtons(line));
        }
        System.out.println(result);
    }
}
