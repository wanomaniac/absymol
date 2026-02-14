package com.wano.abysmol;

import java.util.Random;

public class EvitabilityState {
    public long seed = 21;
    public float severity = 0.25f;   // 0.0 to 10
    public long ticks;

    public Random rng;

    EvitabilityState(){
        init();
    }

    public static int clamp8(int val) {
        return Math.max(0, Math.min(255, val));
    }

    private void init() {
        this.rng = new Random(seed);
    }

    public int nextSigned(int range) {
        seed ^= seed << 13;
        seed ^= seed >>> 7;
        seed ^= seed << 17;
        int val = (int)(seed & 0xFFFFFFFFL);
        return (val % (2 * range + 1)) - range;
    }

    public  float driftFloat(float value, float maxPercent) {
        float factor = 1.0f + (nextSigned(1000) / 1000f) * maxPercent;
        return value * factor;
    }
}


