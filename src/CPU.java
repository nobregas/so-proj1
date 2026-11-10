import processo.Processo;

public class CPU {

    private Processo processo;

    public  CPU() {}

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public void processar(Processo p) {
        this.setProcesso(p);

        if (p != null) {
            p.decrementarInstrucao();
        }
    }

}
