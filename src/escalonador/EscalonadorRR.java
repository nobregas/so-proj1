package escalonador;

import processo.Processo;

import java.util.ArrayDeque;

public class EscalonadorRR extends Escalonador {

    // quantum 2, 5 ou 10.
    private final int QUANTUM;
    private int CONTADOR = 0;

    public EscalonadorRR(int quantum) {
        super(new ArrayDeque<>());
        this.QUANTUM = quantum;
    }

    @Override
    public Processo proximo(Processo atual) {
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
