package green.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Misc {
    public static void printProcessFromProcessBuilder(InputStream _inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(_inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}



