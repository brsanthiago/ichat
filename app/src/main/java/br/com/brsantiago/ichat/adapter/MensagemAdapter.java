package br.com.brsantiago.ichat.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.com.brsantiago.ichat.R;
import br.com.brsantiago.ichat.model.Mensagem;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Color.CYAN;

/**
 * Created by bruno.oliveira on 27/01/2017.
 */

public class MensagemAdapter extends BaseAdapter {

    private List<Mensagem> mensagens;
    private Activity activity;
    private int idDoCliente;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_texto)
    TextView tvTexto;

    @Inject
    Picasso picasso;

    public MensagemAdapter(final int idDoCliente, final List<Mensagem> mensagens, final Activity activity) {
        this.mensagens = mensagens;
        this.activity = activity;
        this.idDoCliente = idDoCliente;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int pos) {
        return mensagens.get(pos);
    }

    @Override
    public long getItemId(int itemId) {
        return itemId;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        View linha = activity.getLayoutInflater().inflate(R.layout.item_mensagem, viewGroup, false);
        ButterKnife.bind(this, linha);

        final Mensagem mensagem = getItem(pos);
        tvTexto.setText(mensagem.getTexto());

        final int idMensagem = mensagem.getId();
        picasso.with(activity).load("https://api.adorable.io/avatars/285/" + idMensagem + ".png").into(ivAvatar);
        if (idDoCliente != idMensagem) {
            tvTexto.setBackgroundColor(CYAN);
        }

        return linha;
    }
}
