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

    /** Одна тренировочная итерация по Методу Обратного Распространения (МОР). */
    public void train() {
        for (int i = layers.size() - 2; i >= 0; i--) {
            layers.get(i).recalculateConnection(layers.get(i + 1), trainingSpeed, momentum);
        }

        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).recalculateLayer();
        }
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
}