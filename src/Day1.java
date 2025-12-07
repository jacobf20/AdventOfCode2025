import java.util.List;

public class Day1 {

    private static int result = 0;

    private static int turnDial(int currentPos, String move) {
        String direction = move.substring(0, 1);
        int rotations = Integer.parseInt(move.substring(1));
        int change = direction.equals("L") ? -1 : 1;
        int nextPos = currentPos;
        for (int i = 0; i < rotations; i++) {
            nextPos = nextPos + change;
            if (nextPos == -1) {
                nextPos = 99;
            } else if (nextPos == 100) {
                nextPos = 0;
            }
            if (nextPos == 0 && i < rotations - 1) {
                result += 1;
            }
        }
        return nextPos;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        int dialPos = 50;
        for (String move : inputData) {
            dialPos = turnDial(dialPos, move);
            if (dialPos == 0) {
                result += 1;
            }
        }
        System.out.println(result);
    }
}
