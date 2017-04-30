/**
 * Входной нейронный слой.
 */
public class InputLayer implements Layer {

    private final Neuron[] neurons;
    private int numberOfNeurons;
    private float[][] previousDeltaW;
    private boolean isTrainedBefore;

    public InputLayer(float[] data) {
        numberOfNeurons = data.length + 1;
        neurons = new Neuron[numberOfNeurons];
        isTrainedBefore = false;

        for (int i = 0; i < numberOfNeurons - 1; i++) {
            neurons[i] = new InputNeuron(data[i]);
        }
        neurons[numberOfNeurons - 1] = new InputNeuron(); // Создается нейрон смещения
    }

    /**
     * Входной слой первый, у него нету предыдущих.
     * @param previousLayer Предыдущий слой.
     */
    @Override
    public void connect(Layer previousLayer) {

    }

    /**
     * Пересчитывает связи по Методу Обратного Распространения (МОР).
     * @param nextLayer Следующий слой.
     * @param trainingSpeed Скорость обучения сети.
     * @param momentum Момент.
     */
    @Override
    public void recalculateConnection(Layer nextLayer, float trainingSpeed, float momentum) {
        if (!isTrainedBefore) {
            previousDeltaW = new float[numberOfNeurons][nextLayer.getNumberOfNeurons()];
        }

        for (int i = 0; i < numberOfNeurons; i++) {
            for (int j = 0; j < nextLayer.getNumberOfNeurons(); j++) {
                float gradW = nextLayer.getDelta(j) * neurons[i].getOutputData();
                float deltaW = trainingSpeed * gradW + (isTrainedBefore ? momentum * previousDeltaW[i][j] : 0);

                previousDeltaW[i][j] = deltaW;
                nextLayer.getNeuron(j).changeInputSynapse(i, deltaW);
            }
        }

        isTrainedBefore = true;
    }

    /** Входной слой не меняется. */
    @Override
    public void recalculateLayer() {

    }

    /** Пересчитывает данные слоя с изменением входных данных синапсов. */
    @Override
    public void recalculateLayerWithSynapses(Layer previousLayer) {

    }

    /** @return Количество нейронов в слое. */
    @Override
    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    /**
     * @param index Номер нейрона.
     * @return Нейрон.
     */
    @Override
    public Neuron getNeuron(int index) {
        return neurons[index];
    }

    /**
     * Дельта входного нейрона равна нулю.
     * @param index Номер нейрона.
     * @return 0.
     */
    @Override
    public float getDelta(int index) {
        return 0;
    }
}