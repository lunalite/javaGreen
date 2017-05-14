package green.gui.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import green.config.Configuration;
import green.database.MongoConnect;
import green.energycollector.StatisticalCall;
import green.gui.controller.RootController;
import green.Util.ENERGY_LABEL;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report {
    private static final Logger logger = Logger.getLogger(Report.class.getName());
    private static double averageEnergy;
    private static List<Double> energyList;
    private static List<String> decompiledAmbly;
    private static Document reportDocument;
    private static boolean insertedDocument;
    private static MongoCollection mongoCollection;
    private static RootController rootController;

    public Report() {
        Report.averageEnergy = 0.0;
        Report.energyList = new ArrayList<>();
        Report.decompiledAmbly = null;
        Report.reportDocument = null;
        Report.insertedDocument = false;
        Report.mongoCollection = MongoConnect.getMongoClient().getDatabase(Configuration.dbDatabaseName)
                .getCollection(Configuration.dbCollectionName);
    }

    public static void insertReportToDatabase() {
        ObjectMapper mapper = new ObjectMapper();
        if (decompiledAmbly != null && reportDocument != null && !insertedDocument) {
            logger.info("Report inserted to database without energy");
            if (isInDatabase(reportDocument) == 0) {
                //Added into database a new record
                logger.info("Report doesn't exist. Creating...");
                getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_AVERAGE, 0));
                getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MAX, 0));
                getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MIN, 0));
                getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_NO, 0));
                mongoCollection.insertOne(reportDocument);
            } else {
                //Report exists in the database
                logger.info("Report exists in database. Adding to energy list in a moment...");
                mongoCollection.find(reportDocument).forEach((Block<Document>) document -> {
                    String json = document.toJson();
                    try {
                        DecompilationMongoModel dmm = mapper.readValue(json, DecompilationMongoModel.class);
                        List<Double> energyList = dmm.getEnergyList();
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_AVERAGE, dmm.getAverageEnergy()));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MAX, Collections.max(energyList)));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MIN, Collections.min(energyList)));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_NO, energyList.size()));
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, e.toString(), e);
                    } catch (NullPointerException e) {
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_AVERAGE, 0));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MAX, 0));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_MIN, 0));
                        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.C_NO, 0));
                    }
                });
            }
            insertedDocument = true;
        } else if (decompiledAmbly != null && averageEnergy != 0.0 && reportDocument != null && insertedDocument) {
            logger.info("Report inserted to database after adding energy");
            mongoCollection.find(reportDocument).forEach((Block<Document>) document -> {
                String json = document.toJson();
                try {
                    DecompilationMongoModel dmm = mapper.readValue(json, DecompilationMongoModel.class);
                    List<Double> dBEnergyList = dmm.getEnergyList();
                    if (dBEnergyList != null) {
                        for (Double e : dBEnergyList) {
                            energyList.add(e);
                        }
                    }
                    System.out.println(energyList.size());
                    Report.averageEnergy = StatisticalCall.obtainAveragePower(energyList);
                    //TODO: Resolve this issue why the energy doesn't show up
                    getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.O_AVERAGE, averageEnergy));
                    getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.O_MAX, Collections.max(energyList)));
                    getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.O_MIN, Collections.min(energyList)));
                    getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.O_NO, energyList.size()));
                    mongoCollection.findOneAndUpdate(reportDocument, new Document("$set", new Document("averageEnergy", averageEnergy)
                            .append("energyList", energyList)));
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.toString(), e);
                }
            });

        } else {
            logger.info("Failed to insert to database");
        }
    }

    public static int isInDatabase(Document searchParameters) {
        return (int) mongoCollection.count(searchParameters);
    }

    public static void setAverageEnergy(double averageEnergy) {
        Report.averageEnergy = averageEnergy;
    }

    public static void setEnergyList(List<Double> energyList) {
        Report.energyList = energyList;
    }

    public static List<String> getDecompiledAmbly() {
        return decompiledAmbly;
    }

    public static void setDecompiledAmbly(List<String> decompiledAmbly) {
        Report.decompiledAmbly = decompiledAmbly;
    }

    public static void setReportDocument(Document reportDocument) {
        Report.reportDocument = reportDocument;
    }

    public static double getAverageEnergy() {
        return averageEnergy;
    }

    public static RootController getRootController() {
        return rootController;
    }

    public static void setRootController(RootController rootController) {
        Report.rootController = rootController;
    }
}
