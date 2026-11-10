package escalonador;

import java.util.ArrayDeque;

public class EscalonadorRR extends Escalonador {

    // quantum 2, 5 ou 10.
    private final int QUANTUM;
    private int CONTADOR = 0;

    EscalonadorRR(int quantum) {
        super(new ArrayDeque<>());
        this.QUANTUM = quantum;
    }

    // Chamado a cada tick (apos cada instrucao executada). - ajustar
    @Override
    public processo.Processo proximo(processo.Processo atual) {
        if (atual == null) {
            CONTADOR = 0;
            return aProcessar.poll();
        }
        if (atual.getInstrucoesRestantes() == 0) {
            concluidos.add(atual);
        } else if (++CONTADOR < QUANTUM) {
            return atual;
        } else {
            adicionar(atual);
        }
        CONTADOR = 0;
        return aProcessar.poll();
    }
}
