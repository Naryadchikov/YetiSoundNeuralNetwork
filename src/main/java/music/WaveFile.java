package music;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Класс работы с Wave-файлом.
 */
public class WaveFile {
    //задание начальных условий
    static int bitrate = 44100;
    static float halfInt = Integer.MAX_VALUE / 2;
    static double halfPi  = Math.PI / 2;
    static float rate = 2 * (float)Math.PI / bitrate;
    static float eps  = 1f;

    //создание массива нот
    static float[] notes    = {261.626f, 293.665f, 329.628f, 349.228f, 391.995f, 440.000f, 493.883f};   //до ре ми фа соль ля си
    static int  [] intNotes = {     262,      294,      330,      350,      392,      440,      484};
    static int [] foundNotes = new int[notes.length];

    public  static int [] shiftNotes(int pos, int [] tmpNotes){
        int [] shiftedNotes = new int[tmpNotes.length - 1];
        for (int i = 0; i < shiftedNotes.length; i++)
            if (i >= pos)
                shiftedNotes[i] = tmpNotes[i+1];
            else
                shiftedNotes[i] = tmpNotes[i];

        return shiftedNotes;
    }

    public static boolean find(int [] tmpNotes,int sum, int k ) {
        for (int i = 0; i < tmpNotes.length; i++) {
            if (sum - tmpNotes[i] > eps ) {
                int[] shiftedNotes = shiftNotes(i, tmpNotes);
                int kk = k + 1;
                if ( find(shiftedNotes, sum - tmpNotes[i], kk) ) {
                    foundNotes[k] = tmpNotes[i];
                    return true;
                }
            } else if (Math.abs(sum - tmpNotes[i]) < eps) {
                foundNotes[k] = tmpNotes[i];
                return true;
            }
        }
        return false;
    }

    public  static int [] getFoundNotes(){
        return foundNotes;
    }
    public static int [] calculateNotes(int t){
        int [] tmp = new int [notes.length];
        for (int i = 0; i < notes.length; i++){
            tmp[i] =  (int) Math.round(halfInt * (Math.sin(rate * intNotes[i] * t)));
        }
        return tmp;
    }

    public static int [] deCalculateNotes(int t, int [] means){
        int [] tmp = new int [notes.length];
        for (int i = 0; i < notes.length; i++){
            // tmp[i] =  (int) Math.round(halfInt * (Math.sin(rate * intNotes[i] * t)));
            tmp[i] =  (int) Math.round(1/(rate * t) * (Math.asin(means[i] / halfInt)));
        }
        return tmp;
    }

    public static void saveWav(int[] saveSamples, String pathName){
        WaveFile wf = null;
        try {
            wf = new WaveFile(4, bitrate, 1, saveSamples);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (wf != null) {
                wf.saveFile(new File(pathName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wf != null) {
            System.out.println("Продолжительность моно-файла: " + wf.getDurationTime() + " сек.");
        }
    }

    public void createWave() {
        int[] samples = new int[3000000]; // в моно - 44100 шаг на 1 секунду
        int step = 40 * 6615;
        int window = 1000000;

        System.out.println("Создание моно-файла...");

        for (int i = 0; i < step + 10; i++) {
            int iw = i % window;

            if ((iw >= 0) && (iw <= step)) {
                //samples[i]  = (int) Math.round(halfInt * (Math.sin(rate * 330 * (i % (cons2 * 330 ) + 1))));
                // samples[i] = (int) Math.round(halfInt * (Math.sin((rate * intNotes[2] * i) % halfPi )));
                samples[i] = (int) Math.round(halfInt * (Math.sin((rate * intNotes[2] * (i))  )));
                //samples[i] += (int) Math.round(halfInt * (Math.sin(rate * 262 * (i % (cons2 * 262 ) + 1))));
                // samples[i] += (int) Math.round(halfInt * (Math.sin((rate * intNotes[0] * i) % halfPi)));
                samples[i] += (int) Math.round(halfInt * (Math.sin((rate * intNotes[0] * (i)) )));
            }
        }

        saveWav(samples,"./src/main/resources/testwav2.wav");
    }

    private final int NOT_SPECIFIED = -1;
    private int sampleSize = NOT_SPECIFIED;
    private long framesCount = NOT_SPECIFIED;
    private byte[] data = null; // массив байт представляющий аудио-данные
    private AudioInputStream ais = null;
    private AudioFormat af = null;

    /**
     * Создает объект из указанного wave-файла
     *
     * @param file Wave-файл
     * @throws UnsupportedAudioFileException Проблема с музыкальным файлом
     * @throws IOException Проблема с файла
     */
    public WaveFile(File file) throws UnsupportedAudioFileException, IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        // получаем поток с аудио-данными
        ais = AudioSystem.getAudioInputStream(file);

        // получаем информацию о формате
        af = ais.getFormat();

        // количество кадров в файле
        framesCount = ais.getFrameLength();

        // размер сэмпла в байтах
        sampleSize = af.getSampleSizeInBits() / 8;

        // размер данных в байтах
        long dataLength = framesCount * af.getSampleSizeInBits() * af.getChannels() / 8;

        // читаем в память все данные из файла разом
        data = new byte[(int) dataLength];
        ais.read(data);
    }

    /**
     * Создает объект из массива целых чисел
     *
     * @param sampleSize Количество байт занимаемых сэмплом
     * @param sampleRate Частота
     * @param channels Количество каналов
     * @param samples Массив значений (данные)
     * @throws Exception если размер сэмпла меньше, чем необходимо для хранения переменной типа int
     */
    public WaveFile(int sampleSize, float sampleRate, int channels, int[] samples) throws Exception {
        int INT_SIZE = 4;

        if (sampleSize < INT_SIZE) {
            throw new Exception("sample size < int size");
        }

        this.sampleSize = sampleSize;
        this.af = new AudioFormat(sampleRate, sampleSize * 8, channels, true, false);
        this.data = new byte[samples.length * sampleSize];

        // заполнение данных
        for (int i = 0; i < samples.length; i++) {
            setSampleInt(i, samples[i]);
        }

        framesCount = data.length / (sampleSize * af.getChannels());
        ais = new AudioInputStream(new ByteArrayInputStream(data), af, framesCount);
    }

    /**
     * Возвращает формат аудио-данных
     *
     * @return формат
     */
    public AudioFormat getAudioFormat() {
        return af;
    }

    /**
     * Возвращает копию массива байт представляющих
     * данные wave-файла
     *
     * @return массив байт
     */
    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    /**
     * Возвращает количество байт которое занимает
     * один сэмпл
     *
     * @return размер сэмпла
     */
    public int getSampleSize() {
        return sampleSize;
    }

    /**
     * Возвращает продолжительность сигнала в секундах
     *
     * @return продолжительность сигнала
     */
    public double getDurationTime() {
        return getFramesCount() / getAudioFormat().getFrameRate();
    }

    /**
     * Возвращает количество фреймов (кадров) в файле
     *
     * @return количество фреймов
     */
    public long getFramesCount() {
        return framesCount;
    }

    /**
     * Сохраняет объект WaveFile в стандартный файл формата WAVE
     *
     * @param file Файл, в который будет сохранен результат.
     * @throws IOException Проблема с записью файла
     */
    public void saveFile(File file) throws IOException {
        AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(data),
            af, framesCount), AudioFileFormat.Type.WAVE, file);
    }

    /**
     * Возвращает значение сэмпла по порядковому номеру. Если данные
     * записаны в 2 канала, то необходимо учитывать, что сэмплы левого и
     * правого канала чередуются. Например, сэмпл под номером один это
     * первый сэмпл левого канала, сэмпл номер два это первый сэмпл правого
     * канала, сэмпл номер три это второй сэмпл левого канала и т.д..
     *
     * @param sampleNumber Номер сэмпла, начиная с 0
     * @return значение сэмпла
     */
    public int getSampleInt(int sampleNumber) {
        if (sampleNumber < 0 || sampleNumber >= data.length / sampleSize) {
            throw new IllegalArgumentException("sample number is can't be < 0 or >= data.length/" + sampleSize);
        }

        // массив байт для представления сэмпла
        // (в данном случае целого числа)
        byte[] sampleBytes = new byte[sampleSize];

        // читаем из данных байты которые соответствуют
        // указанному номеру сэмпла
        System.arraycopy(data, sampleNumber * sampleSize, sampleBytes, 0, sampleSize);

        // преобразуем байты в целое число и возвращаем
        return ByteBuffer.wrap(sampleBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Устанавливает значение сэмпла
     *
     * @param sampleNumber Номер сэмпла
     * @param sampleValue Значение сэмпла
     */
    public void setSampleInt(int sampleNumber, int sampleValue) {
        // представляем целое число в виде массива байт
        byte[] sampleBytes = ByteBuffer.allocate(sampleSize).order(ByteOrder.LITTLE_ENDIAN).putInt(sampleValue).array();

        // последовательно записываем полученные байты
        // в место, которое соответствует указанному
        // номеру сэмпла
        System.arraycopy(sampleBytes, 0, data, sampleNumber * sampleSize, sampleSize);
    }
}