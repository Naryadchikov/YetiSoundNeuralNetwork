/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[] inputData = {1.0f, 0.0f};
        float[] idealData = {1.0f};

        XoRNetwork.addLayer(new InputLayer(inputData));
        XoRNetwork.addLayer(new HiddenLayer(2));
        XoRNetwork.addLayer(new OutputLayer(idealData));

        XoRNetwork.start();

        for (int i = 0; i < 100000; i++) {
            XoRNetwork.train();
        }

        float[] resultData = XoRNetwork.getResultData();

        System.out.println(resultData[0]);
    }
}