package green.gui.model;

import green.Util.ENERGY_LABEL;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;

public class EnergyModel {
    private StringProperty label;
    private DoubleProperty energy;
    private StringProperty energyRounding;

    public EnergyModel(ENERGY_LABEL label, double energy) {
        this.label = new SimpleStringProperty(label.toString());
        this.energy = new SimpleDoubleProperty(energy);
        this.energyRounding = new SimpleStringProperty(new DecimalFormat("#.#").format(energy));
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public double getEnergy() {
        return energy.get();
    }

    public DoubleProperty energyProperty() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy.set(energy);
    }

    public String getEnergyRounding() {
        return energyRounding.get();
    }

    public StringProperty energyRoundingProperty() {
        return energyRounding;
    }

    public void setEnergyRounding(String energyRounding) {
        this.energyRounding.set(energyRounding);
    }
}
