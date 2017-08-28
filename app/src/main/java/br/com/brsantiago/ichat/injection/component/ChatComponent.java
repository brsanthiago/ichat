package br.com.brsantiago.ichat.injection.component;

import br.com.brsantiago.ichat.injection.ChatModule;
import br.com.brsantiago.ichat.view.MainActivity;
import dagger.Component;

/**
 * Created by bruno.oliveira on 28/01/2017.
 */
@Component(modules = ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);

}
