package green.gui.model;

import java.util.List;
import java.util.Map;

public class DecompilationMongoModel {
    private Map<String, String> _id;
    private List<DecompileModel> decompileModels;
    private Double averageEnergy;
    private List<Double> energyList;

    public DecompilationMongoModel() {
    }

    public DecompilationMongoModel(Map<String, String> _id, List<DecompileModel> decompileModels, Double averageEnergy, List<Double> energyList) {
        this._id = _id;
        this.decompileModels = decompileModels;
        this.averageEnergy = averageEnergy;
        this.energyList = energyList;
    }

    public Map<String, String> get_id() {
        return _id;
    }

    public void set_id(Map<String, String> _id) {
        this._id = _id;
    }

    public List<DecompileModel> getDecompileModels() {
        return decompileModels;
    }

    public void setDecompileModels(List<DecompileModel> decompileModels) {
        this.decompileModels = decompileModels;
    }

    public Double getAverageEnergy() {
        return averageEnergy;
    }

    public void setAverageEnergy(Double averageEnergy) {
        this.averageEnergy = averageEnergy;
    }

    public List<Double> getEnergyList() {
        return energyList;
    }

    public void setEnergyList(List<Double> energyList) {
        this.energyList = energyList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DecompilationMongoModel{");
        sb.append("_id=").append(_id);
        sb.append(", decompileModels='").append(decompileModels).append('\'');
        sb.append(", averageEnergy='").append(averageEnergy).append('\'');
        sb.append(", energyList='").append(energyList).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

