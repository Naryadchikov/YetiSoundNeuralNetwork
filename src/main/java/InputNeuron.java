/**
 * Входной нейрон.
 */
public class InputNeuron implements Neuron {

    private float outputData;

    public InputNeuron(float outputData) {
        this.outputData = outputData;
    }

    /** Создается нейрон смещения */
    public InputNeuron() {
        outputData = 1;
    }

    /** @return Преобразованные данные. */
    @Override
    public float getOutputData() {
        return outputData;
    }

    /** @return null, т.к. у входного нейрона нет входящих синапсов. */
    @Override
    public Synapse[] getInputSynapses() {
        return null;
    }

    /**
     * @param weight Весовой коэффициент синапса.
     * @return Исходящий синапс.
     */
    @Override
    public Synapse getOutputSynapse(float weight) {
        return new Synapse(weight, outputData);
    }

    /** У входного нейрона нет входящих синапсов. */
    @Override
    public void changeInputSynapse(int index, float deltaW) {

    }

    /** Выходные данные у входного нейрона постоянны. */
    @Override
    public void recalculateOutputData() {

    }

    /** Пересчитывает входные данные нейронов */
    @Override
    public void recalculateInputSynapsesData(Synapse[] newInputSynapses) {

    }
}