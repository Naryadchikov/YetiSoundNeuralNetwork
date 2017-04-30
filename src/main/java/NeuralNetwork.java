import java.util.ArrayList;
import java.util.List;

/**
 * Нейронная сеть.
 */
public class NeuralNetwork {

    private List<Layer> layers;
    private float trainingSpeed;
    private float momentum;

    public NeuralNetwork(float trainingSpeed, float momentum) {
        layers = new ArrayList<Layer>();
        this.trainingSpeed = trainingSpeed;
        this.momentum = momentum;
    }

    /**
     * Добавляет новый слой в сеть.
     * @param layer Слой.
     */
    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    /** Первая итерация. */
    public void start() {
        for (int i = 0; i < layers.size() - 1; i++) {
            layers.get(i + 1).connect(layers.get(i));
        }
    }

    /** Одна итерация. */
    private void iteration() {
        for (int i = 0; i < layers.size() - 1; i++) {
            layers.get(i).recalculateNextLayer(layers.get(i + 1));
        }
    }

    /** Одна тренировочная итерация по Методу Обратного Распространения (МОР). */
    public void train() {
        for (int i = layers.size() - 2; i >= 0; i--) {
            layers.get(i).recalculateConnection(layers.get(i + 1), trainingSpeed, momentum);
        }
        iteration();
    }

    /**
     * Считает ошибку полученного результата по MSE.
     * @param idealData Идеальный ответ.
     * @return Ошибку по MSE.
     * @throws UnsupportedOperationException Если размера массива идеальных данных не совпадает
     *                                       с количемтвом нейронов в выходном слое.
     */
    public float meanSquaredError(float[] idealData) {
        int numberOfNeurons = layers.get(layers.size() - 1).getNumberOfNeurons();
        float error = 0;

        if (idealData.length != numberOfNeurons) {
            throw new UnsupportedOperationException();
        }

        for (int i = 0; i < numberOfNeurons; i++) {
            error += Math.pow(idealData[i] - layers.get(layers.size() - 1).getNeuron(i).getOutputData(), 2);
        }
        error /= numberOfNeurons;

        return error;
    }

    /** @return Результат работы нейронной сети. */
    public float[] getResultData() {
        int numberOfNeurons = layers.get(layers.size() - 1).getNumberOfNeurons();
        float[] resultData = new float[numberOfNeurons];

        for (int i = 0; i < numberOfNeurons; i++) {
            resultData[i] = layers.get(layers.size() - 1).getNeuron(i).getOutputData();
        }

        return resultData;
    }

    /**
     * Меняет входные данные.
     * @param newInputData Новые входные данные.
     * @throws UnsupportedOperationException Если размера массива новых входных данных не совпадает
     *                                       с количемтвом нейронов в входном слое.
     */
    public void changeInputData(float[] newInputData) {
        if (newInputData.length != layers.get(0).getNumberOfNeurons() - 1) {
            throw new UnsupportedOperationException();
        }

        List<Layer> tmp = new ArrayList<Layer>();

        layers.remove(0);
        tmp.add(new InputLayer(newInputData));
        tmp.addAll(layers);
        layers = tmp;

        iteration();
    }

    /**
     * Меняет идеальные данные выходного слоя на новые.
     * @param newIdealData Новые идеальные данные.
     * @throws UnsupportedOperationException Если размера массива новых идеальных данных не совпадает
     *                                       с количемтвом нейронов в выходном слое.
     */
    public void changeIdealData(float[] newIdealData) {
        int numberOfNeurons = layers.get(layers.size() - 1).getNumberOfNeurons();
        int layersNumber = layers.size();

        if (newIdealData.length != numberOfNeurons) {
            throw new UnsupportedOperationException();
        }

        OutputLayer newOutputLayer = (OutputLayer) layers.get(layersNumber - 1);

        newOutputLayer.changeIdealData(newIdealData);
        layers.remove(layersNumber - 1);
        layers.add(newOutputLayer);
    }
}