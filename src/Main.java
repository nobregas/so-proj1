import escalonador.Escalonador;
import escalonador.EscalonadorFCFS;
import processo.GeradorProcessos;
import processo.Processo;

// atua como uma especie de kernel simplificado
public class Main {
    public static void main(String[] args) throws Exception {
        int tick = 0;

        final Escalonador FCFS = new EscalonadorFCFS();

        Despachador despachador = new Despachador(FCFS);

        while (tick < 200) {
            var novoProcesso = GeradorProcessos.gerar(tick);

            if (novoProcesso != null) {
                FCFS.adicionar(novoProcesso);
            }

            despachador.chamarProcessamento();

            var processoAtual = despachador.getCpu().getProcesso();

            if (processoAtual == null) {
                System.out.println("Tick " + tick + " | CPU ociosa");
            } else {
                if (processoAtual.getInstrucoesRestantes() == 0) {
                    processoAtual.setInstanteTermino(tick + 1);
                }

                System.out.println(
                        "Tick " + tick
                                + " | Processo " + processoAtual.getId()
                                + " | Instrucoes Restantes " + processoAtual.getInstrucoesRestantes()
                                + " | Chegada " + processoAtual.getInstanteChegada()
                                + " | Termino " + processoAtual.getInstanteTermino()
                );

            }

            tick++;
        }

        int somaEspera = 0;
        var concluidos = FCFS.getConcluidos();

        for (Processo processo : concluidos) {
            int espera = processo.getInstanteTermino()
                    - processo.getInstanteChegada()
                    - processo.getInstanteChegada();

            somaEspera += espera;

            System.out.println(
                    "Processo " + processo.getId()
                    + " | Tempo de Espera: " + espera
            );
        }

        if (!concluidos.isEmpty()) {
            double mediaEspera = (double) somaEspera / concluidos.size();
            System.out.println("Tempo de medio de espera: " + mediaEspera);
        } else {
            System.out.println("Nenhum processo foi concluido");
        }

    }

}
