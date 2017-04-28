/**
 * Выходной нейрон.
 */
public class OutputNeuron implements Neuron {

    private Synapse[] inputSynapses;
    private float inputData;
    private float outputData;

    public OutputNeuron(Synapse[] synapses) {
        this.inputSynapses = synapses;
        inputData = 0;
        for (Synapse synapse : inputSynapses) {
            inputData += synapse.getOutputData();
        }
        outputData = 1 / (1 + (float) Math.pow(Math.E, -inputData));
    }

    /** @return Преобразованные данные. */
    @Override
    public float getOutputData() {
        return outputData;
    }

    /** @return Все входящие в нейрон синапсы. */
    @Override
    public Synapse[] getInputSynapses() {
        return inputSynapses;
    }

    /**
     * У выходного нейрона нет выходных синапсов.
     * @param weight Весовой коэффициент синапса.
     * @return null.
     */
    @Override
    public Synapse getOutputSynapse(float weight) {
        return null;
    }

    /**
     * Корректирует весовой коэффициент синапса.
     * @param index Номер входящего синапса.
     * @param deltaW Изменение весового коэффициента синапса.
     */
    @Override
    public void changeInputSynapse(int index, float deltaW) {
        float newW = inputSynapses[index].getWeight() + deltaW;

        inputSynapses[index].setWeight(newW);
    }

    /** Пересчитывает выходные данные. */
    @Override
    public void recalculateOutputData() {
        inputData = 0;
        for (Synapse synapse : inputSynapses) {
            inputData += synapse.getOutputData();
        }
        outputData = 1 / (1 + (float) Math.pow(Math.E, -inputData));
    }
}