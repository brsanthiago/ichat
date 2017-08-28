package br.com.brsantiago.ichat.app;

import android.app.Application;

import br.com.brsantiago.ichat.injection.ChatModule;
import br.com.brsantiago.ichat.injection.component.ChatComponent;
import br.com.brsantiago.ichat.injection.component.DaggerChatComponent;

/**
 * Created by bruno.oliveira on 28/01/2017.
 */

public class ChatApplication extends Application {

    private ChatComponent chatComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        chatComponent = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this)).build();
    }

    public ChatComponent getChatComponent() {
        return chatComponent;
    }

}
