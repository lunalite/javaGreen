//https://github.com/eugenp/tutorials/tree/master/core-java/src/test/java/com/baeldung/java/nio2
//https://github.com/GoesToEleven/Java_NewCircle_training/blob/master/code/Networking/net/Nio2EchoServer.java

package green.energycollector;

import green.gui.controller.RootController;
import green.gui.model.EnergyModel;
import green.gui.model.Report;
import green.misc.ENERGY_LABEL;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkService {
    private static final Logger logger = Logger.getLogger(NetworkService.class.getName());
    private AsynchronousServerSocketChannel serverChannel;
    private Future<AsynchronousSocketChannel> acceptResult;
    private AsynchronousSocketChannel clientChannel;
    private List<String> energyConsumption;
    private RootController rootController;

    public NetworkService() {
        energyConsumption = new ArrayList<>();
        try {
            serverChannel = AsynchronousServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress(4444);
            logger.info("Accepting connections on " + hostAddress);
            serverChannel.bind(hostAddress);
            acceptResult = serverChannel.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Runnable serverTask = () -> {
            //TODO problem with this current socket
            try {
                clientChannel = acceptResult.get();
                if ((clientChannel != null) && (clientChannel.isOpen())) {
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(8192);
                        Future<Integer> readResult = clientChannel.read(buffer);
                        if (readResult.get() == -1) {
                            break;
                        }

                        buffer.flip();
                        String message = new String(buffer.array()).trim();
                        energyConsumption.add(message);
                        buffer.clear();

                    } // while()
                    clientChannel.close();
                    serverChannel.close();
                    logger.info("Client and server channel closing...");
                    energyStats();
                }
            } catch (InterruptedException | ExecutionException | IOException e) {
                logger.log(Level.SEVERE, e.toString(), e);
                e.printStackTrace();
            }

        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private void energyStats() {
        List<Double> energyList = new ArrayList<>();
        String lines[] = energyConsumption.get(0).split("\\r?\\n");
        Pattern p = Pattern.compile("\\d*.?\\d+(?= mW)");
        for (String x : lines) {
            Matcher m = p.matcher(x);
            if (m.find()) {
                energyList.add(Double.parseDouble(m.group(0)));
            }
        }
        double averageEnergy = StatisticalCall.obtainAveragePower(energyList);
        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.N_AVERAGE, averageEnergy));
        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.N_MAX, Collections.max(energyList)));
        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.N_MIN, Collections.min(energyList)));
        getRootController().getEnergyData().add(new EnergyModel(ENERGY_LABEL.N_NO, energyList.size()));
        Report.setAverageEnergy(averageEnergy);
        Report.setEnergyList(energyList);
        Report.insertReportToDatabase();
        getRootController().setIsEnergyObtained(true);
    }

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}