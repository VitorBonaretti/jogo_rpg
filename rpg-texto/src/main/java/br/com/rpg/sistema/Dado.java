package br.com.rpg.sistema;

import java.util.Random;

public class Dado {
    private static final Random RANDOM = new Random();

    public static int rolar(int lados) {
        if (lados <= 0) throw new IllegalArgumentException("lados deve ser > 0");
        return RANDOM.nextInt(lados) + 1;
    }

    public static int rolarPercentual() { 
        return rolar(100); 
    }
    
}
