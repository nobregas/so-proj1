import escalonador.Escalonador;
import processo.Processo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Despachador {

    private Escalonador escalonador;
    private CPU cpu;

    private final List<Processo> ordemExecucao = new ArrayList<>();
    private Processo ultimoExecutado;

    public Despachador(Escalonador escalonador) {
        this.escalonador = escalonador;
        this.cpu = new CPU();
    }

    public Escalonador getEscalonador() {
        return escalonador;
    }

    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public void chamarProcessamento() {
        var processoAtual = this.cpu.getProcesso();

        var proximoProcesso = this.proximoProcesso(processoAtual);
        if (!Objects.equals(proximoProcesso, processoAtual)) {
            this.cpu.setProcesso(null);
        }

        if (proximoProcesso != null && !proximoProcesso.equals(this.ultimoExecutado)) {
            this.ordemExecucao.add(proximoProcesso);
            this.ultimoExecutado = proximoProcesso;
        }

        this.cpu.processar(proximoProcesso);
    }

    public List<Processo> getOrdemExecucao() {
        return ordemExecucao;
    }

    public int getTrocas() {
        return Math.max(0, ordemExecucao.size() - 1);
    }

    public Processo proximoProcesso(Processo atual) {
        return this.escalonador.proximo(atual);
    }
}
