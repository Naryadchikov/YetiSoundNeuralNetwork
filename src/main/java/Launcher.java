/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[][] inputData = { {0.0f, 0.0f}, {0.0f, 1.0f}, {1.0f, 0.0f}, {1.0f, 1.0f} };
        float[][] idealData = { {0.0f}, {1.0f}, {1.0f}, {0.0f} };
        int lastEpoch = 100000;

        XoRNetwork.addLayer(new InputLayer(inputData[0]));
        XoRNetwork.addLayer(new HiddenLayer(3));
        XoRNetwork.addLayer(new OutputLayer(idealData[0]));

        XoRNetwork.start();

        for (int i = 0; i < lastEpoch; i++) {
            for (int j = 0; j < idealData.length; j++) {
                XoRNetwork.changeInputData(inputData[j]);
                XoRNetwork.changeIdealData(idealData[j]);
                XoRNetwork.train();
            }
        }

        float[][] testData = { {0.0f, 0.0f}, {1.0f, 0.0f} };
        float[][] idealTestResult = { {0.0f}, {1.0f} };
        float[] resultData;

        for (int i = 0; i < testData.length; i++) {
            XoRNetwork.changeInputData(testData[i]);
            XoRNetwork.changeIdealData(idealTestResult[i]);

            resultData = XoRNetwork.getResultData();

            System.out.println("Final result is: " + resultData[0]);
            System.out.println("Ideal answer is: " + idealTestResult[i][0]);
        }
    }
}