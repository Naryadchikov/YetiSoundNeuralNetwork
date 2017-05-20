/**
 * Класс-Launcher.
 */
import music.AwesomeMusic;
import music.WaveFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static music.WaveFile.*;

public class Launcher {
    int bitrate = 44100;

    public  float[] FORSASHENIAKA(String FileName){
        int DLINAPESNI = 90000000; // bitrate * t , где t - длина песни в секундах
        float [] tmp = new float[DLINAPESNI];

        System.out.println("Чтение данных из моно-файла:");
        WaveFile wf = null;
        try {
            wf = new WaveFile(new File(FileName)); //"./src/main/resources/testwav1.wav"
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < DLINAPESNI; i++) {
           tmp[i] =  wf.getSampleInt(i);
        }
        return tmp;
    }

    public static void main(String[] args) {
        createWave();

        NeuralNetwork XoRNetwork = new NeuralNetwork(0.7f, 0.3f);
        float[][] inputData = {{0.0f, 0.0f}, {0.0f, 1.0f}, {1.0f, 0.0f}, {1.0f, 1.0f}};
        float[][] idealData = {{0.0f}, {1.0f}, {1.0f}, {0.0f}};
        boolean isWithBias = true;
        int lastEpoch = 100000;

        XoRNetwork.addLayer(new InputLayer(isWithBias, inputData[0]));
        XoRNetwork.addLayer(new HiddenLayer(isWithBias, 3));
        XoRNetwork.addLayer(new OutputLayer(idealData[0]));

        XoRNetwork.start();

        for (int i = 0; i < lastEpoch; i++) {
            for (int j = 0; j < idealData.length; j++) {
                XoRNetwork.changeInputData(isWithBias, inputData[j]);
                XoRNetwork.changeIdealData(idealData[j]);
                XoRNetwork.train();
            }
        }

        float[][] testData = {{0.0f, 0.0f}, {1.0f, 0.0f}};
        float[][] idealTestResult = {{0.0f}, {1.0f}};
        float[] resultData;

        for (int i = 0; i < testData.length; i++) {
            XoRNetwork.changeInputData(isWithBias, testData[i]);
            XoRNetwork.changeIdealData(idealTestResult[i]);

            resultData = XoRNetwork.getResultData();

            System.out.println("Final result is: " + resultData[0]);
            System.out.println("Ideal answer is: " + idealTestResult[i][0]);
        }
    }
}