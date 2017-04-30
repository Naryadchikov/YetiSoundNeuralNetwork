/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[] inputData = {1.0f, 0.0f};
        float[] idealData = {1.0f};

        XoRNetwork.addLayer(new InputLayer(inputData));
        XoRNetwork.addLayer(new HiddenLayer(3));
        XoRNetwork.addLayer(new OutputLayer(idealData));

        XoRNetwork.start();

        int counter = 0;

        while (XoRNetwork.meanSquaredError(idealData) > 0.00001) {
            XoRNetwork.train();
            counter++;
        }

        float[] resultData = XoRNetwork.getResultData();

        System.out.println("Answer after " + counter + " iterations is: " + resultData[0]);
        System.out.println("Ideal answer is: " + idealData[0]);
    }
}