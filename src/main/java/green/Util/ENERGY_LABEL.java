package green.Util;

/**
 * C_AVERAGE: Cached average
 * N_AVERAGE: New average
 * O_AVERAGE: Overall average
 */
public enum ENERGY_LABEL {
    C_AVERAGE("CACHED_AVG"),
    C_MIN("CACHED_MIN"),
    C_MAX("CACHED_MAX"),
    C_NO("CACHED_QTY"),
    N_AVERAGE("NEW_AVG"),
    N_MIN("NEW_MIN"),
    N_MAX("NEW_MAX"),
    N_NO("NEW_QTY"),
    O_AVERAGE("OVERALL_AVG"),
    O_MIN("OVERALL_MIN"),
    O_MAX("OVERALL_MAX"),
    O_NO("OVERALL_QTY");

    private String labelName;

    ENERGY_LABEL(String s) {
        this.labelName = s;
    }

    @Override
    public String toString() {
        return this.labelName;
    }
}
