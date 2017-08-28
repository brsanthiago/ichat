package br.com.brsantiago.ichat.callback;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import br.com.brsantiago.ichat.events.FailureEvent;
import br.com.brsantiago.ichat.events.MensagemEvent;
import br.com.brsantiago.ichat.model.Mensagem;
import br.com.brsantiago.ichat.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuvirMensagemCallback implements Callback<Mensagem> {

    private Context context;

    private EventBus eventBus;

    public OuvirMensagemCallback(EventBus eventBus, MainActivity context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if (response.isSuccessful()) {
            Mensagem mensagem = response.body();
            eventBus.post(new MensagemEvent(mensagem));
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        eventBus.post(new FailureEvent());
    }
}
