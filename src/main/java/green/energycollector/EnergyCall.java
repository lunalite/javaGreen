//package green.energycollector;
//
//import green.gui.controller.EnergyCallController;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class EnergyCall {
//    private String fileName;
//    private String ext1;
//    private String ext2;
//    private String dir;
//    private EnergyCallController energyController;
//
//    public EnergyCall() {
//        ext1 = ".java";
//        ext2 = ".class";
//        dir = "./src/javaGreen/";
//    }
//
//    public List obtainEnergyReport(String _folderInput, String _mainInput) throws IOException {
//        List javaEnergyConsumption = new ArrayList();
//        //TODO Find some classifications of assembly
//        //Call API. For now we send to github
//        //gitCall(_folderInput, _mainInput);
//
//        //After pushing to gitlab, we open a new socket connection to wait for report to be sent
////        javaEnergyConsumption = socketCall();
//
//        //Stdout to check java consumption
//        //TODO: To find out the amount of energy consumption for the particular java program alone.
//        Iterator<String> javaECItr = javaEnergyConsumption.iterator();
//        while (javaECItr.hasNext()) {
//            System.out.println(javaECItr.next());
//        }
//        return javaEnergyConsumption;
//    }
//
//
//
//
//
////    Task<> task = new Task<> {}
//
//
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getExt1() {
//        return ext1;
//    }
//
//    public void setExt1(String ext1) {
//        this.ext1 = ext1;
//    }
//
//    public String getExt2() {
//        return ext2;
//    }
//
//    public void setExt2(String ext2) {
//        this.ext2 = ext2;
//    }
//
//    public String getDir() {
//        return dir;
//    }
//
//    public void setDir(String dir) {
//        this.dir = dir;
//    }
//
//    public EnergyCallController getEnergyController() {
//        return energyController;
//    }
//
//    public void setEnergyController(EnergyCallController energyController) {
//        this.energyController = energyController;
//    }
//}