import escalonador.Escalonador;
import processo.Processo;

import java.util.Objects;

public class Despachador {

    private Escalonador escalonador;
    private CPU cpu;

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

        this.cpu.processar(proximoProcesso);
    }

    public Processo proximoProcesso(Processo atual) {
        return this.escalonador.proximo(atual);
    }
}
