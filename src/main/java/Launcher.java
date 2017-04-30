/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[] inputData1 = {1.0f, 0.0f};
        float[] idealData1 = {1.0f};

        XoRNetwork.addLayer(new InputLayer(inputData1));
        XoRNetwork.addLayer(new HiddenLayer(13));
        XoRNetwork.addLayer(new HiddenLayer(12));
        XoRNetwork.addLayer(new HiddenLayer(11));
        XoRNetwork.addLayer(new HiddenLayer(10));
        XoRNetwork.addLayer(new OutputLayer(idealData1));

        XoRNetwork.start();

        int counter = 0;

        while (XoRNetwork.meanSquaredError(idealData1) > 0.00001) {
            XoRNetwork.train();
            counter++;
        }

        float[] resultData1 = XoRNetwork.getResultData();

        System.out.println("Answer after " + counter + " iterations is: " + resultData1[0]);
        System.out.println("Ideal answer is: " + idealData1[0]);

        float[] inputData2 = {0.0f, 0.0f};
        float[] idealData2 = {0.0f};

        XoRNetwork.changeInputData(inputData2);
        XoRNetwork.changeIdealData(idealData2);
        counter = 0;
        while (XoRNetwork.meanSquaredError(idealData2) > 0.00001) {
            XoRNetwork.train();
            counter++;
        }

        float[] resultData2 = XoRNetwork.getResultData();

        System.out.println("Answer after " + counter + " iterations is: " + resultData2[0]);
        System.out.println("Ideal answer is: " + idealData2[0]);

        float[] inputData3 = {0.0f, 1.0f};
        float[] idealData3 = {1.0f};

        XoRNetwork.changeInputData(inputData3);
        XoRNetwork.changeIdealData(idealData3);
        counter = 0;
        while (XoRNetwork.meanSquaredError(idealData3) > 0.00001) {
            XoRNetwork.train();
            counter++;
        }

        float[] resultData3 = XoRNetwork.getResultData();

        System.out.println("Answer after " + counter + " iterations is: " + resultData3[0]);
        System.out.println("Ideal answer is: " + idealData3[0]);

        float[] inputData4 = {1.0f, 1.0f};
        float[] idealData4 = {0.0f};

        XoRNetwork.changeInputData(inputData4);
        XoRNetwork.changeIdealData(idealData4);
        counter = 0;
        while (XoRNetwork.meanSquaredError(idealData4) > 0.00001) {
            XoRNetwork.train();
            counter++;
        }

        float[] resultData4 = XoRNetwork.getResultData();

        System.out.println("Answer after " + counter + " iterations is: " + resultData4[0]);
        System.out.println("Ideal answer is: " + idealData4[0]);
    }
}