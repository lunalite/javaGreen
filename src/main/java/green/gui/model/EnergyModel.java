package green.gui.model;

import green.misc.ENERGY_LABEL;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EnergyModel {
    private StringProperty label;
    private DoubleProperty energy;

    public EnergyModel(ENERGY_LABEL label, double energy) {
        this.label = new SimpleStringProperty(label.toString());
        this.energy = new SimpleDoubleProperty(energy);
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
}
