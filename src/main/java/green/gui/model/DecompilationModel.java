package green.gui.model;

import java.util.List;

public class DecompilationModel {
    private List<DecompileModel> decompileModels;

    public DecompilationModel(){}

    public DecompilationModel(List<DecompileModel> decompileModels) {
        this.decompileModels = decompileModels;
    }

    public List<DecompileModel> getDecompileModels() {
        return decompileModels;
    }

    public void setDecompileModels(List<DecompileModel> decompileModels) {
        this.decompileModels = decompileModels;
    }
}
