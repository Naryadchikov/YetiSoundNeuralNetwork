/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[] inputData1 = {1.0f, 0.0f};
        float[] idealData1 = {1.0f};
        float[] inputData2 = {0.0f, 1.0f};
        float[] idealData2 = {1.0f};
        float[] inputData3 = {0.0f, 0.0f};
        float[] idealData3 = {0.0f};
        float[] inputData4 = {1.0f, 1.0f};
        float[] idealData4 = {0.0f};
        float[] resultData1;
        float[] resultData2;
        float[] resultData3;
        float[] resultData4;
        int lastEpoch = 1000;
        int counter;

        XoRNetwork.addLayer(new InputLayer(inputData1));
        XoRNetwork.addLayer(new HiddenLayer(13));
        XoRNetwork.addLayer(new HiddenLayer(12));
        XoRNetwork.addLayer(new HiddenLayer(11));
        XoRNetwork.addLayer(new HiddenLayer(10));
        XoRNetwork.addLayer(new OutputLayer(idealData1));

        XoRNetwork.start();

        for (int i = 0; i < lastEpoch; i++) {
            XoRNetwork.changeInputData(inputData1);
            XoRNetwork.changeIdealData(idealData1);
            counter = 0;
            while (XoRNetwork.meanSquaredError(idealData1) > 0.01) {
                XoRNetwork.train();
                counter++;
            }
            resultData1 = XoRNetwork.getResultData();
            System.out.println("Answer after " + counter + " iterations is: " + resultData1[0]);
            System.out.println("Ideal answer is: " + idealData1[0]);

            XoRNetwork.changeInputData(inputData2);
            XoRNetwork.changeIdealData(idealData2);
            counter = 0;
            while (XoRNetwork.meanSquaredError(idealData2) > 0.01) {
                XoRNetwork.train();
                counter++;
            }
            resultData2 = XoRNetwork.getResultData();
            System.out.println("Answer after " + counter + " iterations is: " + resultData2[0]);
            System.out.println("Ideal answer is: " + idealData2[0]);

            XoRNetwork.changeInputData(inputData3);
            XoRNetwork.changeIdealData(idealData3);
            counter = 0;
            while (XoRNetwork.meanSquaredError(idealData3) > 0.01) {
                XoRNetwork.train();
                counter++;
            }
            resultData3 = XoRNetwork.getResultData();
            System.out.println("Answer after " + counter + " iterations is: " + resultData3[0]);
            System.out.println("Ideal answer is: " + idealData3[0]);


            XoRNetwork.changeInputData(inputData4);
            XoRNetwork.changeIdealData(idealData4);
            counter = 0;
            while (XoRNetwork.meanSquaredError(idealData4) > 0.01) {
                XoRNetwork.train();
                counter++;
            }
            resultData4 = XoRNetwork.getResultData();
            System.out.println("Answer after " + counter + " iterations is: " + resultData4[0]);
            System.out.println("Ideal answer is: " + idealData4[0]);
        }

        XoRNetwork.changeInputData(inputData3);
        XoRNetwork.changeIdealData(idealData3);
        XoRNetwork.iteration();
        resultData3 = XoRNetwork.getResultData();
        System.out.println("Final result is: " + resultData3[0]);
        System.out.println("Ideal answer is: " + idealData3[0]);
    }
}