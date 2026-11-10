package escalonador;

import java.util.ArrayDeque;

public class EscalonadorFCFS extends Escalonador {
    public EscalonadorFCFS() {
        super(new ArrayDeque<>());
    }
}
