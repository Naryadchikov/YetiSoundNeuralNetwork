/**
 * Синапс.
 */
public class Synapse {

    private float weight;
    private float inputData;

    public Synapse(float inputData) {
        weight = (float)Math.random();
        this.inputData = inputData;
    }

    public Synapse(float weight, float inputData) {
        this.weight = weight;
        this.inputData = inputData;
    }

    /** @return Весовой коэффициент синапса. */
    public float getWeight() {
        return weight;
    }

    /**
     * Установить новое значение весового коэффициента.
     * @param weight Весовой коэффициент синапса.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /** @return Преобразованные данные. */
    public float getOutputData() {
        return inputData * weight;
    }
}