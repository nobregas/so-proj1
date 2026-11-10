package escalonador;

import processo.Processo;

import java.util.*;

public class EscalonadorPrioridade extends Escalonador {
  // calculo de tempo de espera falta. e desempate
    EscalonadorPrioridade() {
        super(new PriorityQueue<>(
                Comparator.comparingInt(Processo::getPrioridade).reversed()
                        .thenComparingInt(Processo::getInstanteChegada)
                        .thenComparingInt(Processo::getId)
        ));
    }

}
