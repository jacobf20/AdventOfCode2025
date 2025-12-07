import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static List<String> parseInputByLine(String filePath) throws Exception {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String nextLine;
        List<String> result = new ArrayList<>();

        while ( (nextLine = br.readLine()) != null ) {
            result.add(nextLine);
        }

        return result;
    }

    public static List<String> parseInputByComma(String filePath) throws Exception {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String fileContents = br.readLine();
        return Arrays.stream(fileContents.split(",")).toList();
    }
}
