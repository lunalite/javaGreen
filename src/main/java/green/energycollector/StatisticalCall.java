package green.energycollector;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticalCall {
    private static final Logger logger = Logger.getLogger(StatisticalCall.class.getName());

    public static double obtainAveragePower(List _parameters) {
        double avgPower;
        double total = 0.0;
        int i = 0;
        Iterator<Double> paraItr = _parameters.iterator();
        while (paraItr.hasNext()) {
            total += paraItr.next();
            i++;
        }
        try {
            avgPower = total / i;
            logger.info("Average energy calculated: " + avgPower);
            return avgPower;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString(), e);
            return 0.0;
        }
    }
}
