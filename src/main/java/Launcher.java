/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.0001f, 0.05f);
        float[][] inputData = {{0.2f, 0.1f}, {0.3f, 0.5f}, {0.1f, 0.0f}, {0.5f, 0.5f}, {0.2f, 0.4f}, {0.2f, 0.3f}};
        float[][] idealData = {{0.3f}, {0.8f}, {0.1f}, {0.9f}, {0.6f}, {0.5f}};
        int lastEpoch = 10000;
        int trainingSets = 6;

        XoRNetwork.addLayer(new InputLayer(inputData[0]));
        XoRNetwork.addLayer(new HiddenLayer(5));
        XoRNetwork.addLayer(new HiddenLayer(4));
        XoRNetwork.addLayer(new HiddenLayer(3));
        XoRNetwork.addLayer(new OutputLayer(idealData[0]));

        XoRNetwork.start();

        for (int i = 0; i < lastEpoch; i++) {
            for (int j = 0; j < trainingSets; j++) {
                XoRNetwork.changeInputData(inputData[j]);
                XoRNetwork.changeIdealData(idealData[j]);
                XoRNetwork.train();
            }
        }

        float[] testData1 = {0.3f, 0.4f};
        float[] idealTestResult1 = {0.7f};

        XoRNetwork.changeInputData(testData1);
        XoRNetwork.changeIdealData(idealTestResult1);
        XoRNetwork.iteration();

        float[] resultData1 = XoRNetwork.getResultData();

        System.out.println("Final result is: " + resultData1[0]);
        System.out.println("Ideal answer is: " + idealTestResult1[0]);

        float[] testData2 = {0.2f, 0.2f};
        float[] idealTestResult2 = {0.4f};

        XoRNetwork.changeInputData(testData2);
        XoRNetwork.changeIdealData(idealTestResult2);
        XoRNetwork.iteration();

        float[] resultData2 = XoRNetwork.getResultData();

        System.out.println("Final result is: " + resultData2[0]);
        System.out.println("Ideal answer is: " + idealTestResult2[0]);
    }
}