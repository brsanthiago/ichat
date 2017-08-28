package br.com.brsantiago.ichat.injection;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.brsantiago.ichat.app.ChatApplication;
import br.com.brsantiago.ichat.service.ChatService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bruno.oliveira on 28/01/2017.
 */

@Module
public class ChatModule {
    private ChatApplication app;

    public ChatModule(ChatApplication chatApplication) {
        this.app = chatApplication;
    }

    @Provides
    public ChatService getChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.225.80:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);

        return chatService;
    }

    @Provides
    public Picasso getPicasso() {
        Picasso picasso = new Picasso.Builder(app).build();
        return picasso;
    }
    @Provides
    public EventBus getEventBus() {
        EventBus eventBus = EventBus.builder().build();
        return eventBus;
    }

    @Provides
    public InputMethodManager inputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager;
    }
}
