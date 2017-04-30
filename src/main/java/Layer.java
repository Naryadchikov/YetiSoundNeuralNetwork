/**
 * Нейронный слой.
 */
public interface Layer {

    /**
     * Соединяет данный слой с предыдущем.
     * @param previousLayer Предыдущий слой.
     */
    void connect(Layer previousLayer);

    /**
     * Пересчитывает связи по Методу Обратного Распространения (МОР).
     * @param nextLayer Следующий слой.
     * @param trainingSpeed Скорость обучения сети.
     * @param momentum Момент.
     */
    void recalculateConnection(Layer nextLayer, float trainingSpeed, float momentum);

    /**
     * Пересчитывает данные следующего слоя.
     * @param nextLayer Следующий слой.
     */
    void recalculateNextLayer(Layer nextLayer);

    /** @return Количество нейронов в слое. */
    int getNumberOfNeurons();

    /**
     * @param index Номер нейрона.
     * @return Нейрон.
     */
    Neuron getNeuron(int index);

    /**
     * Возвращает дельту нейрона согласно Методу Обратного Распространения (МОР).
     * @param index Номер нейрона.
     * @return Дельта нейрона.
     */
    float getDelta(int index);
}