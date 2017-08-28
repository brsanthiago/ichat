package br.com.brsantiago.ichat.events;

import br.com.brsantiago.ichat.model.Mensagem;

/**
 * Created by bruno.oliveira on 28/01/2017.
 */

public class MensagemEvent {
    public Mensagem mensagem;

    public MensagemEvent(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
