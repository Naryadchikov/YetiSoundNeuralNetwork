import music.AwesomeMusic;
import music.WaveFile;

/**
 * Класс-Launcher.
 */
public class Launcher {

    public static void main(String[] args) {
//        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
//        float[][] inputData = {{0.0f, 0.0f}, {0.0f, 1.0f}, {1.0f, 0.0f}, {1.0f, 1.0f}};
//        float[][] idealData = {{0.0f}, {1.0f}, {1.0f}, {0.0f}};
//        boolean isWithBias = true;
//        int lastEpoch = 100000;
//
//        XoRNetwork.addLayer(new InputLayer(isWithBias, inputData[0]));
//        XoRNetwork.addLayer(new HiddenLayer(isWithBias, 3));
//        XoRNetwork.addLayer(new OutputLayer(idealData[0]));
//
//        XoRNetwork.start();
//
//        for (int i = 0; i < lastEpoch; i++) {
//            for (int j = 0; j < idealData.length; j++) {
//                XoRNetwork.changeInputData(isWithBias, inputData[j]);
//                XoRNetwork.changeIdealData(idealData[j]);
//                XoRNetwork.train();
//            }
//        }
//
//        float[][] testData = {{0.0f, 0.0f}, {1.0f, 0.0f}};
//        float[][] idealTestResult = {{0.0f}, {1.0f}};
//        float[] resultData;
//
//        for (int i = 0; i < testData.length; i++) {
//            XoRNetwork.changeInputData(isWithBias, testData[i]);
//            XoRNetwork.changeIdealData(idealTestResult[i]);
//
//            resultData = XoRNetwork.getResultData();
//
//            System.out.println("Final result is: " + resultData[0]);
//            System.out.println("Ideal answer is: " + idealTestResult[i][0]);
//        }
        AwesomeMusic[] trainingSongs = new AwesomeMusic[1];
        String[] awesomeSongsPath = {"./bin/1.wav"};
        float[][] inputData = { {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
                                {0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f},
                                {1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f},
                                {1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
                                {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f},
                                {1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f},
                                {1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f},
                                {1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f},
                                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f} };

        for (int i = 0; i < trainingSongs.length; i++) {
            trainingSongs[i] = new AwesomeMusic(awesomeSongsPath[i]);
        }

        NeuralNetwork[] YetiSoundNetworks = new NeuralNetwork[trainingSongs[0].getFramesCount()];
        boolean isWithBias = true;

        for (int i = 0; i < YetiSoundNetworks.length; i++) {
            YetiSoundNetworks[i] = new NeuralNetwork(0.01f, 0.1f);
        }

        float[] resultData = new float[YetiSoundNetworks.length];
        int[] resultFrames = new int[YetiSoundNetworks.length];

        for (int i = 0; i < YetiSoundNetworks.length; i++) {
            float[][] idealData = new float[trainingSongs.length][1];
            int lastEpoch = 100;

            for (int j = 0; j < trainingSongs.length; j++) {
                idealData[j][0] = trainingSongs[j].getIdealData()[i];
                idealData[j][0] = idealData[j][0] / (Integer.MAX_VALUE / 2);
            }

            YetiSoundNetworks[i].addLayer(new InputLayer(isWithBias, inputData[0]));
            YetiSoundNetworks[i].addLayer(new HiddenLayer(isWithBias, 5));
            YetiSoundNetworks[i].addLayer(new HiddenLayer(isWithBias, 4));
            YetiSoundNetworks[i].addLayer(new HiddenLayer(isWithBias, 3));
            YetiSoundNetworks[i].addLayer(new OutputLayer(idealData[0]));

            YetiSoundNetworks[i].start();

            for (int j = 0; j < lastEpoch; j++) {
                for (int k = 0; k < idealData.length; k++) {
                    YetiSoundNetworks[i].changeInputData(isWithBias, inputData[k]);
                    YetiSoundNetworks[i].changeIdealData(idealData[k]);
                    YetiSoundNetworks[i].train();
                }
            }
            System.out.println("End training for " + i + " network.");

            YetiSoundNetworks[i].changeInputData(isWithBias, inputData[0]);
            resultData[i] = YetiSoundNetworks[i].getResultData()[0];
            resultFrames[i] = (int) (resultData[i] * (Integer.MAX_VALUE / 2));
        }

        WaveFile.saveWav(resultFrames, "./bin/AwesomeSong.wav");
    }
}