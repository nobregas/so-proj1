import escalonador.Escalonador;
import escalonador.EscalonadorFCFS;
import escalonador.EscalonadorPrioridade;
import escalonador.EscalonadorRR;
import escalonador.EscalonadorSJF;
import processo.GeradorProcessos;
import processo.Processo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final int TICKS_CHEGADA = 200;

    static final int SLEEP_MS = 50;

    public static void main(String[] args) throws Exception {
        List<Processo> processos = new ArrayList<>();

        for (int tick = 0; tick < TICKS_CHEGADA; tick++) {
            var novoProcesso = GeradorProcessos.gerar(tick);

            if (novoProcesso != null) {
                processos.add(novoProcesso);
            }
        }

        listarProcessos(processos);

        Scanner scanner = new Scanner(System.in);
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println();
            System.out.println("=== MENU ===");
            System.out.println("1 - FCFS");
            System.out.println("2 - SJF");
            System.out.println("3 - Prioridade");
            System.out.println("4 - Round Robin");
            System.out.println("5 - Rodar todos (comparativo)");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> simular("FCFS", new EscalonadorFCFS(), processos, true);
                case "2" -> simular("SJF", new EscalonadorSJF(), processos, true);
                case "3" -> simular("Prioridade", new EscalonadorPrioridade(), processos, true);
                case "4" -> {
                    int quantum = lerQuantum(scanner);
                    simular("RR (q=" + quantum + ")", new EscalonadorRR(quantum), processos, true);
                }
                case "5" -> {
                    listarProcessos(processos);

                    simular("FCFS", new EscalonadorFCFS(), processos, false);
                    simular("SJF", new EscalonadorSJF(), processos, false);
                    simular("Prioridade", new EscalonadorPrioridade(), processos, false);

                    for (int quantum : new int[]{2, 5, 10}) {
                        simular("RR (q=" + quantum + ")", new EscalonadorRR(quantum), processos, false);
                    }
                }
                case "0" -> System.out.println("Encerrando");
                default -> System.out.println("ERROR: opcao invalida");
            }
        }

    }

    private static void listarProcessos(List<Processo> processos) {
        System.out.println();
        System.out.println("=== PROCESSOS ===");

        for (Processo processo : processos) {
            System.out.println(
                    "Processo " + processo.getId()
                            + " | Chegada " + processo.getInstanteChegada()
                            + " | Instrucoes " + processo.getQuatidadeInstrucoes()
                            + " | Prioridade " + processo.getPrioridade()
            );
        }
    }

    private static int lerQuantum(Scanner scanner) {
        while (true) {
            System.out.print("Quantum: ");

            try {
                int quantum = Integer.parseInt(scanner.nextLine().trim());

                if (quantum > 0) {
                    return quantum;
                }
            } catch (NumberFormatException e) {
                // cai na mensagem abaixo
            }

            System.out.println("ERROR: quantum deve ser um inteiro positivo");
        }
    }

    private static void simular(String nome, Escalonador escalonador, List<Processo> processos, boolean detalhar) throws Exception {
        List<Processo> copias = new ArrayList<>();

        for (Processo processo : processos) {
            copias.add(new Processo(processo));
        }

        Despachador despachador = new Despachador(escalonador);

        int tick = 0;
        int finalizados = 0;

        System.out.println();
        System.out.println("=== " + nome + " ===");

        while (finalizados < copias.size()) {
            for (Processo processo : copias) {
                if (processo.getInstanteChegada() == tick) {
                    escalonador.adicionar(processo);
                }
            }

            despachador.chamarProcessamento();

            var processoAtual = despachador.getCpu().getProcesso();

            if (processoAtual != null && processoAtual.getInstrucoesRestantes() == 0) {
                processoAtual.setInstanteTermino(tick + 1);
                finalizados++;
            }

            if (detalhar) {
                if (processoAtual == null) {
                    System.out.println("Tick " + tick + " | CPU ociosa");
                } else {
                    System.out.println(
                            "Tick " + tick
                                    + " | Executando " + processoAtual
                                    + " | Instrucoes Restantes " + processoAtual.getInstrucoesRestantes()
                                    + " | Aguardando " + escalonador.getAProcessar()
                                    + " | Finalizados " + escalonador.getConcluidos()
                    );
                }

                Thread.sleep(SLEEP_MS);
            }

            tick++;
        }

        int somaEspera = 0;

        if (detalhar) {
            System.out.println();
        }

        for (Processo processo : copias) {
            int espera = processo.getInstanteTermino()
                    - processo.getInstanteChegada()
                    - processo.getQuatidadeInstrucoes();

            assert espera >= 0 : "tempo de espera negativo no processo " + processo.getId();

            somaEspera += espera;

            if (detalhar) {
                System.out.println(
                        "Processo " + processo.getId()
                                + " | Tempo de Espera: " + espera
                );
            }
        }

        double mediaEspera = (double) somaEspera / copias.size();

        if (detalhar) {
            System.out.println();
        }

        System.out.println("Tempo medio de espera: " + String.format("%.2f", mediaEspera));
        System.out.println("Trocas: " + despachador.getTrocas());
        System.out.println("Ordem de execucao: " + despachador.getOrdemExecucao());
    }

}
