package green.energycollector;

import green.misc.Misc;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecompileService extends Service<List> {
    private String folderInput;
    private String mainInput;

    public DecompileService(String _folderInput, String _mainInput) {
        this.folderInput = _folderInput;
        this.mainInput = _mainInput;
    }

    @Override
    protected Task createTask() {
        return new Task<List>() {
            protected List call() throws Exception {
                ProcessBuilder pb = new ProcessBuilder("/bin/bash", "javaDecompile.sh");
                Map<String, String> env = pb.environment();
                env.put("FOLDER_INPUT", getFolderInput());
                env.put("MAIN_INPUT", getMainInput());
                pb.directory(new File("./src/main/java/green/energycollector/"));
                Process p = pb.start();
                p.waitFor();

                File decompiledFile = new File("./src/javaGreen/" + getFolderInput() + "/build/decompiledMain");
                List<String> decompiledAmbly = FileUtils.readLines(decompiledFile, "utf-8");
//                EnergyCall.printProcess(p.getInputStream());
                //TODO Find some classifications of assembly

                //Call API. For now we send to github
                gitCall(getFolderInput(), getMainInput());

                return decompiledAmbly;
            }
        };
    }

    public static void gitCall(String _folderInput, String _mainInput) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "gitCall.sh");
        Map<String, String> env = pb.environment();
        env.put("FOLDER_INPUT", _folderInput);
        env.put("MAIN_INPUT", _mainInput);
        pb.directory(new File("./src/main/java/green/energycollector/"));
        Process process = pb.start();
//        Misc.printProcessFromProcessBuilder(process.getInputStream());
    }

    public String getFolderInput() {
        return folderInput;
    }

    public String getMainInput() {
        return mainInput;
    }
}