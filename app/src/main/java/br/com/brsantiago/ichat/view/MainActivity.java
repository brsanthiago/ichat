package br.com.brsantiago.ichat.view;

import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.brsantiago.ichat.R;
import br.com.brsantiago.ichat.adapter.MensagemAdapter;
import br.com.brsantiago.ichat.app.ChatApplication;
import br.com.brsantiago.ichat.callback.EnviarMensagemCallback;
import br.com.brsantiago.ichat.callback.OuvirMensagemCallback;
import br.com.brsantiago.ichat.events.FailureEvent;
import br.com.brsantiago.ichat.events.MensagemEvent;
import br.com.brsantiago.ichat.injection.component.ChatComponent;
import br.com.brsantiago.ichat.model.Mensagem;
import br.com.brsantiago.ichat.service.ChatService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.lv_mensagem)
    ListView lvMensagem;
    @BindView(R.id.ed_mensagem)
    EditText edMensagem;
    @BindView(R.id.iv_foto)
    ImageView ivFoto;
    @BindView(R.id.btn_enviar)
    Button btnEnviar;

    @Inject
    Picasso picasso;
    @Inject
    ChatService chatService;
    @Inject
    EventBus eventBus;

    private ChatComponent chatComponent;

    private int idDoCliente = 1;

    private List<Mensagem> mensagens;
    private MensagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ChatApplication chatApp = (ChatApplication) getApplication();
        chatComponent = chatApp.getChatComponent();
        chatComponent.inject(this);

        if (savedInstanceState != null){
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        }else{
            mensagens = new ArrayList<>();
        }

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);

        picasso.with(this).load("https://api.adorable.io/avatars/285/" + idDoCliente + ".png").into(ivFoto);
        lvMensagem.setAdapter(adapter);

        Call<Mensagem> call = chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagemCallback(eventBus,this));
        eventBus.register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mensagens",(ArrayList<Mensagem>)mensagens);
    }

    @OnClick(R.id.btn_enviar)
    public void enviar() {
        chatService.enviar(new Mensagem(idDoCliente, edMensagem.getText().toString()))
                .enqueue(new EnviarMensagemCallback());
        edMensagem.getText().clear();
        chatComponent.inputMethodManager.hideSoftInputFromWindow(edMensagem.getWindowToken(),0);
    }
    @Subscribe
    public void ouvirMensagens(MensagemEvent mensagemEvent) {
        Call<Mensagem> call = chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagemCallback(eventBus,this));
    }
    @Subscribe
    public void colocaNaLista(MensagemEvent mensagemEvent) {
        mensagens.add(mensagemEvent.mensagem);
        MensagemAdapter adapter = new MensagemAdapter(1, mensagens, this);
        lvMensagem.setAdapter(adapter);
    }
    @Subscribe
    public void lidarCom(FailureEvent event) {
        ouvirMensagens(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }
}
