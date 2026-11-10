package processo;

import java.util.Objects;

public class Processo {

    private static int lastID = 1;

    // Unico
    private final int id;

    private int quatidadeInstrucoes;

    // 1 - 5
    private int prioridade;

    private int instanteChegada = 0;

    private int instanteTermino = 0;

    private int instrucoesRestantes;

    public Processo(int quatidadeInstrucoes, int prioridade) throws Exception {
        this.id = lastID++;
        setQuantidadeInstrucoes(quatidadeInstrucoes);
        setPrioridade(prioridade);
        this.instrucoesRestantes = quatidadeInstrucoes;

    }

    public int getId() {
        return id;
    }

    public int getQuatidadeInstrucoes() {
        return quatidadeInstrucoes;
    }

    private void setQuantidadeInstrucoes(int quantidadeInstrucoes) {
        if (quantidadeInstrucoes <= 0) {
            throw new IllegalArgumentException("ERROR: a quantidade de instrucoes deve ser positiva ou maior que zero");
        }
        this.quatidadeInstrucoes = quantidadeInstrucoes;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        if (prioridade < 1) {
            throw new IllegalArgumentException("ERROR: prioridade de um processo deve ser no minimo 1");
        }
        if (prioridade > 5) {
            throw new IllegalArgumentException("ERROR: prioridade de um processo nao pode ser maior que 5");
        }

        this.prioridade = prioridade;
    }

    public int getInstanteChegada() {
        return instanteChegada;
    }

    public void setInstanteChegada(int instanteChegada) {
        if (instanteChegada < 0) {
            throw new IllegalArgumentException("ERROR: instante de chegada nao pode ser negativo");
        }
        this.instanteChegada = instanteChegada;
    }

    public int getInstanteTermino() {
        return instanteTermino;
    }

    public void setInstanteTermino(int instanteTermino) {
        if (instanteTermino <= 0) {
            throw new IllegalArgumentException("ERROR: instante de termino deve ser positivo");
        }
        this.instanteTermino = instanteTermino;
    }

    public int getInstrucoesRestantes() {
        return instrucoesRestantes;
    }

    public int decrementarInstrucao() {
        if (instrucoesRestantes > 0) {
            instrucoesRestantes--;
        }
        return instrucoesRestantes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Processo processo = (Processo) o;
        return id == processo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
