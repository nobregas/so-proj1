package escalonador;

import processo.Processo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Escalonador {
    protected final List<Processo> concluidos = new ArrayList<>();
    protected Queue<Processo> aProcessar;

    public Escalonador(Queue<Processo> aProcessar) {
        this.aProcessar = aProcessar;
    }

    public void adicionar(Processo p) {
        aProcessar.add(p);
    }

    public Processo proximo(Processo atual) {
        if (atual != null) {
            if (atual.getInstrucoesRestantes() != 0) {
                return atual;
            }
            concluidos.add(atual);
        }

        return aProcessar.poll();
    }

    public List<Processo> getConcluidos() {
        return concluidos;
    }

    public Queue<Processo> getAProcessar() {
        return aProcessar;
    }
}
