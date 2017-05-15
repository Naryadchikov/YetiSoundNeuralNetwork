/**
 * Скрытый нейронный слой.
 */
public class HiddenLayer implements Layer {

    private Neuron[] neurons;
    private boolean isWithBias;
    private int numberOfNeurons;
    private float[] deltas;
    private float[][] previousDeltaW;
    private boolean isTrainedBefore;

    public HiddenLayer(boolean isWithBias, int numberOfNeurons) {
        this.isWithBias = isWithBias;
        this.numberOfNeurons = numberOfNeurons;
        neurons = new Neuron[numberOfNeurons];
        deltas = new float[numberOfNeurons];
        isTrainedBefore = false;
    }

    /**
     * Соединяет данный слой с предыдущем.
     *
     * @param previousLayer Предыдущий слой.
     */
    @Override
    public void connect(Layer previousLayer) {
        int numberWithoutBias = (isWithBias) ? numberOfNeurons - 1 : numberOfNeurons;

        for (int i = 0; i < numberWithoutBias; i++) {
            Synapse[] inputSynapses = new Synapse[previousLayer.getNumberOfNeurons()];

            for (int j = 0; j < inputSynapses.length; j++) {
                inputSynapses[j] = previousLayer.getNeuron(j).getOutputSynapse((float) Math.random());
            }
            neurons[i] = new HiddenNeuron(inputSynapses);
        }

        if (isWithBias) {
            neurons[numberWithoutBias] = new HiddenNeuron(); // Создается нейрон смещения
        }
    }

    /**
     * Пересчитывает связи по Методу Обратного Распространения (МОР).
     *
     * @param nextLayer Следующий слой.
     * @param trainingSpeed Скорость обучения сети.
     * @param momentum Момент.
     */
    @Override
    public void recalculateConnection(Layer nextLayer, float trainingSpeed, float momentum) {
        float tmp = 0;

        if (!isTrainedBefore) {
            previousDeltaW = new float[numberOfNeurons][nextLayer.getNumberOfNeurons()];
        }

        for (int i = 0; i < nextLayer.getNumberOfNeurons(); i++) {
            try {
                for (Synapse synapse : nextLayer.getNeuron(i).getInputSynapses()) {
                    tmp += synapse.getWeight() * nextLayer.getDelta(i);
                }
            } catch (NullPointerException ignored) {
            }
        }

        for (int i = 0; i < numberOfNeurons; i++) {
            deltas[i] = (1 - neurons[i].getOutputData()) * neurons[i].getOutputData() * tmp;
            for (int j = 0; j < nextLayer.getNumberOfNeurons(); j++) {
                float gradW = nextLayer.getDelta(j) * neurons[i].getOutputData();
                float deltaW = trainingSpeed * gradW + (isTrainedBefore ? momentum * previousDeltaW[i][j] : 0);

                previousDeltaW[i][j] = deltaW;
                nextLayer.getNeuron(j).changeInputSynapse(i, deltaW);
            }
        }

        isTrainedBefore = true;
    }

    /**
     * Пересчитывает данные следующего слоя.
     *
     * @param nextLayer Следующий слой.
     */
    @Override
    public void recalculateNextLayer(Layer nextLayer) {
        for (int i = 0; i < numberOfNeurons; i++) {
            for (int j = 0; j < nextLayer.getNumberOfNeurons(); j++) {
                nextLayer.getNeuron(j).changeInputSynapseData(i, neurons[i].getOutputData());
            }
        }

        for (int i = 0; i < nextLayer.getNumberOfNeurons(); i++) {
            nextLayer.getNeuron(i).recalculateOutputData();
        }
    }

    /**
     * @return Количество нейронов в слое.
     */
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
     * Возвращает дельту нейрона согласно Методу Обратного Распространения (МОР).
     *
     * @param index Номер нейрона.
     * @return Дельта нейрона.
     */
    @Override
    public float getDelta(int index) {
        return deltas[index];
    }
}