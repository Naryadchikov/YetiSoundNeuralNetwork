package music;

import java.io.File;

/**
 * Класс, содержащий идеальные данные для тренировки сети.
 */
public class AwesomeMusic {

    private WaveFile waveFile;
    private int framesCount;

    public AwesomeMusic(String FileName) {
        try {
            waveFile = new WaveFile(new File(FileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        framesCount = new Long(waveFile.getFramesCount()).intValue();
    }

    /**
     * Возвращает массив идеальных данных для тренировки сети.
     * @return Массив идеальных данных.
     */
    public float[] getIdealData() {
        float[] idealData = new float[framesCount];

        if (waveFile != null) {
            for (int i = 0; i < framesCount; i++) {
                idealData[i] = waveFile.getSampleInt(i);
            }
        }

        return idealData;
    }

    /**
     * Возвращает количество кадров в песне
     * @return Количество кадров в песне.
     */
    public int getFramesCount() {
        return framesCount;
    }
}