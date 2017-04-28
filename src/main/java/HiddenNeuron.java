/**
 * Скрытый нейрон.
 */
public class HiddenNeuron implements Neuron {

    private final boolean isBias;
    private Synapse[] inputSynapses;
    private float inputData;
    private float outputData;

    public HiddenNeuron(Synapse[] synapses) {
        isBias = false;
        this.inputSynapses = synapses;
        inputData = 0;
        for (Synapse synapse : inputSynapses) {
            inputData += synapse.getOutputData();
        }
        outputData = 1 / (1 + (float) Math.pow(Math.E, -inputData));
    }

    public HiddenNeuron() {
        isBias = true;
        inputSynapses = null;
        inputData = 1;
        outputData = 1;
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
     * @param weight Весовой коэффициент синапса.
     * @return Исходящий синапс.
     */
    @Override
    public Synapse getOutputSynapse(float weight) {
        return new Synapse(weight, outputData);
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
        if (!isBias) {
            inputData = 0;
            for (Synapse synapse : inputSynapses) {
                inputData += synapse.getOutputData();
            }
            outputData = 1 / (1 + (float) Math.pow(Math.E, -inputData));
        }
    }
}
