package music;

import java.util.ArrayList;
import java.util.Arrays;

public class AwesomeMusic {

    private ArrayList<Float> frames;

    public AwesomeMusic(float[] frames) {
        this.frames = new ArrayList<Float>();
        for (Float frame : frames) {
            this.frames.add(frame);
        }
    }

    public int getAmountOfFrames() {
        return frames.size();
    }

    public float getIdealData(int index) {
        return frames.get(index);
    }
}