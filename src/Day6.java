import java.util.*;

public class Day6 {

    private static long result = 0;
    
    private static long result2 = 0;

    private static void performOperations(Map<Integer, List<String>> parsedProblems) {
        List<Integer> keys = parsedProblems.keySet().stream().toList();
        for (int i = 0; i < parsedProblems.get(0).size(); i++) {
            String operator = parsedProblems.get(keys.getLast()).get(i);
            long problemResult = Long.parseLong(parsedProblems.get(keys.getFirst()).get(i));
            for (int j = 1; j < keys.size() - 1; j++) {
                long value = Long.parseLong(parsedProblems.get(j).get(i));
                switch (operator) {
                    case "*":
                        problemResult *= value;
                        break;
                    case "+":
                        problemResult += value;
                        break;
                }
            }
            result += problemResult;
        }
    }

    private static Map<Integer, List<String>> parseProblems(List<String> inputData) {
        Map<Integer, List<String>> parsedResult = new TreeMap<>();
        for (int i = 0; i < inputData.size(); i++) {
            String line = inputData.get(i);
            parsedResult.put(i, Arrays.stream(line.split("\s+")).toList());
        }
        return parsedResult;
    }

    private static void performOperations2(Map<Integer, List<String>> parsedProblems) {
        List<Integer> keys = parsedProblems.keySet().stream().toList();
        for (int problem : keys) {
            List<String> values = parsedProblems.get(problem);
            String operator = values.getLast();
            List<String> numbers = values.subList(0, values.size() - 1);
            long problemResult = Long.parseLong(numbers.getFirst());
            for (int j = 1; j < numbers.size(); j++) {
                long value = Long.parseLong(numbers.get(j));
                switch (operator) {
                    case "*":
                        problemResult *= value;
                        break;
                    case "+":
                        problemResult += value;
                        break;
                }
            }
            result2 += problemResult;
        }
    }
    
    private static Map<Integer, List<String>> parseProblems2(List<String> inputData) {
        List<List<String>> splitInput = inputData.stream().map(line -> Arrays.asList(line.split(""))).toList();
        int problemNum = 0;
        Map<Integer, List<String>> parsedProblems = new HashMap<>();
        String operator = "";
        for (int col = splitInput.getFirst().size() - 1; col >= 0; col--) {
            StringBuilder number = new StringBuilder();
            boolean allSpaces = true;
            for (int row = 0; row < inputData.size(); row++) {
                String val = splitInput.get(row).get(col);
                if (!val.equals(" ")) {
                    allSpaces = false;
                    if (val.equals("*") || val.equals("+")) {
                        operator = val;
                    } else {
                        number.append(splitInput.get(row).get(col));
                    }
                }
            }
            if (!parsedProblems.containsKey(problemNum)) {
                parsedProblems.put(problemNum, new ArrayList<>());
                parsedProblems.get(problemNum).add(number.toString());
            } else if (!allSpaces){
                parsedProblems.get(problemNum).add(number.toString());
            }

            if (allSpaces || col == 0) {
                parsedProblems.get(problemNum).add(operator);
                problemNum++;
            }
        }
        return parsedProblems;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        Map<Integer, List<String>> parsedProblems = parseProblems(inputData);
        Map<Integer, List<String>> parsedProblems2 = parseProblems2(inputData);
        performOperations(parsedProblems);
        performOperations2(parsedProblems2);
        System.out.println(result);
        System.out.println(result2);
    }
}
