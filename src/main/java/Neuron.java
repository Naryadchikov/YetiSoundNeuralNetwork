/**
 * Нейрон.
 */
public interface Neuron {

    /** @return Преобразованные данные. */
    float getOutputData();

    /** @return Все входящие в нейрон синапсы. */
    Synapse[] getInputSynapses();

    /**
     * @param weight Весовой коэффициент синапса.
     * @return Исходящий синапс.
     */
    Synapse getOutputSynapse(float weight);

    /**
     * Корректирует весовой коэффициент синапса.
     * @param index Номер входящего синапса.
     * @param deltaW Изменение весового коэффициента синапса.
     */
    void changeInputSynapse(int index, float deltaW);

    /** Пересчитывает выходные данные. */
    void recalculateOutputData();
}