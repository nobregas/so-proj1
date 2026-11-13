package escalonador;

import processo.Processo;

import java.util.*;

public class EscalonadorSJF extends Escalonador {
    public EscalonadorSJF() {
        super(new PriorityQueue<>(
                Comparator.comparingInt(Processo::getQuatidadeInstrucoes)
                        .thenComparingInt(Processo::getInstanteChegada)
                        .thenComparingInt(Processo::getId)
        ));
    }

}
