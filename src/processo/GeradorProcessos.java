package processo;

import java.util.Random;

public class GeradorProcessos {

    private static final Random random = new Random();

    static final int CHANCE_GERAR = 5;

    public static Processo gerar(int tick) throws Exception {
        if (tick < 0) {
            throw new IllegalArgumentException("Error: tick invalido");
        }

        Processo processo = null;

        if (tick == 0) {
            // garante 1 tick sempre gera processo
            processo = novoProcesso(tick);
        } else {
            if(random.nextInt(100) < CHANCE_GERAR) {
                processo = novoProcesso(tick);
            }
        }

        return processo;
    }

    private static Processo novoProcesso(int tick) throws Exception {
        var processo = new Processo(random.nextInt(41) + 10,
                random.nextInt(5) + 1);
        processo.setInstanteChegada(tick);

        return processo;
    }
}
