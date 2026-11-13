package escalonador;

import processo.Processo;

import java.util.*;

public class EscalonadorPrioridade extends Escalonador {
    public EscalonadorPrioridade() {
        super(new PriorityQueue<>(
                Comparator.comparingInt(Processo::getPrioridade).reversed()
                        .thenComparingInt(Processo::getInstanteChegada)
                        .thenComparingInt(Processo::getId)
        ));
    }

}
