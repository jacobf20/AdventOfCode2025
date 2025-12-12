import java.util.*;

public class Day8 {
    
    private static List<List<Integer>> circuits = new ArrayList<>();
    private static Map<Integer, List<Integer>> directConnections = new HashMap<>();
    
    private static int getBoxId(List<String> box) {
        return box.hashCode();
    }
    
    private static boolean areDirectlyConnected(List<String> box1, List<String> box2) {
        int box1Id = getBoxId(box1);
        int box2Id = getBoxId(box2);
        return directConnections.getOrDefault(box1Id, new ArrayList<>()).contains(box2Id) || directConnections.getOrDefault(box2Id, new ArrayList<>()).contains(box1Id);
    }
    
    private static int getAssignedCircuit(List<String> box) {
        int boxId = getBoxId(box);
        for (int i = 0; i < circuits.size(); i++) {
            if (circuits.get(i).contains(boxId)) {
                return i;
            }
        }
        return -1;
    }
    
    private static double calculateDistance(List<String> box1, List<String> box2) {
        List<Integer> box1Coordinates = box1.stream().map(Integer::parseInt).toList();
        List<Integer> box2Coordinates = box2.stream().map(Integer::parseInt).toList();
        int xDiff = box1Coordinates.getFirst() - box2Coordinates.getFirst();
        int yDiff = box1Coordinates.get(1) - box2Coordinates.get(1);
        int zDiff = box1Coordinates.getLast() - box2Coordinates.getLast();
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2));
    }
    
    private static List<List<String>> getClosestBoxes(List<List<String>> boxes) {
        double shortestDistance = Double.MAX_VALUE;
        List<List<String>> closestBoxes = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            List<String> box1 = boxes.get(i);
            for (int j = 0; j < boxes.size(); j++) {
                List<String> box2 = boxes.get(j);
                if (i == j || areDirectlyConnected(box1, box2)) {
                    continue;
                }
                double dist = calculateDistance(box1, box2);
                if (dist < shortestDistance) {
                    closestBoxes.clear();
                    closestBoxes.add(box1);
                    closestBoxes.add(box2);
                    shortestDistance = dist;
                }
            }
        }
        return closestBoxes;
    }
    
    private static boolean checkDone(List<List<String>> boxes) {
        int boxesInCircuits = 0;
        for (List<Integer> circuit : circuits) {
            boxesInCircuits += circuit.size();
        }
        return boxes.size() == boxesInCircuits;
    }
    
    private static void processBoxes(List<List<String>> boxes) {
        List<List<String>> lastConnectedBoxes = new ArrayList<>();
        while (!checkDone(boxes)) {
            List<List<String>> closestBoxes = getClosestBoxes(boxes);
            int box1Assignment = getAssignedCircuit(closestBoxes.getFirst());
            int box2Assignment = getAssignedCircuit(closestBoxes.getLast());
            int box1Id = getBoxId(closestBoxes.getFirst());
            int box2Id = getBoxId(closestBoxes.getLast());
            if (box1Assignment == -1 && box2Assignment == -1) {
                List<Integer> newCircuit = new ArrayList<>();
                newCircuit.add(box1Id);
                newCircuit.add(box2Id);
                circuits.add(newCircuit);
            } else if (box1Assignment != -1 && box2Assignment == -1) {
                circuits.get(box1Assignment).add(box2Id);
            } else if (box1Assignment == -1) {
                circuits.get(box2Assignment).add(box1Id);
            } else if (box1Assignment != box2Assignment) {
                circuits.get(box1Assignment).addAll(circuits.get(box2Assignment));
                circuits.remove(circuits.get(box2Assignment));
            }
            if (!directConnections.containsKey(box1Id)) {
                directConnections.put(box1Id, new ArrayList<>());
                directConnections.get(box1Id).add(box2Id);
            } else {
                directConnections.get(box1Id).add(box2Id);
            }
            lastConnectedBoxes.clear();
            lastConnectedBoxes.add(closestBoxes.getFirst());
            lastConnectedBoxes.add(closestBoxes.getLast());
        }
        int x1 = Integer.parseInt(lastConnectedBoxes.getFirst().getFirst());
        int x2 = Integer.parseInt(lastConnectedBoxes.getLast().getFirst());
        System.out.println(x1 * x2);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Not enough arguments.");
        }
        String filePath = args[0];
        List<String> inputData = Utils.parseInputByLine(filePath);
        List<List<String>> boxPositions = inputData.stream().map(row -> Arrays.asList(row.split(","))).toList();
        processBoxes(boxPositions);
//        circuits.sort((o1, o2) -> o2.size() - o1.size());
//        System.out.println(circuits.getFirst().size() * circuits.get(1).size() * circuits.get(2).size());
    }
}
