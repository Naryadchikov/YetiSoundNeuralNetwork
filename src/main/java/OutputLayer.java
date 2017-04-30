/**
 * Выходной нейронный слой.
 */
public class OutputLayer implements Layer {

    private Neuron[] neurons;
    private int numberOfNeurons;
    private float[] idealData;

    public OutputLayer(float[] idealData) {
        numberOfNeurons = idealData.length;
        this.idealData = idealData;
        neurons = new Neuron[numberOfNeurons];
    }

    /**
     * Соединяет данный слой с предыдущем.
     * @param previousLayer Предыдущий слой.
     */
    @Override
    public void connect(Layer previousLayer) {
        for (int i = 0; i < numberOfNeurons; i++) {
            Synapse[] inputSynapses = new Synapse[previousLayer.getNumberOfNeurons()];

            for (int j = 0; j < inputSynapses.length; j++) {
                inputSynapses[j] = previousLayer.getNeuron(j).getOutputSynapse((float) Math.random());
            }
            neurons[i] = new OutputNeuron(inputSynapses);
        }
    }

    /**
     * У выходного слоя нет выходных синапсов.
     * @param nextLayer Следующий слой.
     * @param trainingSpeed Скорость обучения сети.
     * @param momentum Момент.
     */
    @Override
    public void recalculateConnection(Layer nextLayer, float trainingSpeed, float momentum) {

    }

    /** Пересчитывает данные слоя. */
    @Override
    public void recalculateLayer() {
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i].recalculateOutputData();
        }
    }

    /** Пересчитывает данные слоя с изменением входных данных синапсов. */
    @Override
    public void recalculateLayerWithSynapses(Layer previousLayer) {
        for (int i = 0; i < numberOfNeurons; i++) {
            Synapse[] oldSynapses = neurons[i].getInputSynapses();

            for (int j = 0; j < oldSynapses.length; j++) {
                oldSynapses[j] = new Synapse(previousLayer.getNeuron(j).getOutputData(), oldSynapses[j].getWeight());
            }
            neurons[i].recalculateInputSynapsesData(oldSynapses);
        }
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
     * Возвращает дельту нейрона согласно Методу Обратного Распространения (МОР).
     * @param index Номер нейрона.
     * @return Дельта нейрона.
     */
    @Override
    public float getDelta(int index) {
        float outputData = neurons[index].getOutputData();

        return (idealData[index] - outputData) * (1 - outputData) * outputData;
    }

    /**
     * Меняет идеальные данные нейронов слоя на новые.
     * @param newIdealData Новые идеальные данные.
     */
    public void changeIdealData(float[] newIdealData) {
       idealData = newIdealData;
    }
}