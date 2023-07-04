package listeners;

import com.slack.api.bolt.App;
import listeners.actions.ActionListeners;
import listeners.actions.SampleBlockActionListener;
import listeners.commands.CommandListeners;
import listeners.events.AppHomeOpenedListener;
import listeners.events.EventListeners;
import listeners.messages.MessageListeners;
import listeners.messages.SampleMessageListener;
import listeners.shortcuts.SampleGlobalShortcutListener;
import listeners.shortcuts.ShortcutListeners;
import listeners.views.ViewListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listeners {

    public static Logger logger = LoggerFactory.getLogger(Listeners.class);

    public static void register(App app) {
        new SampleMessageListener(app);
        new SampleBlockActionListener(app);
        new AppHomeOpenedListener(app);
        new SampleMessageListener(app);
        new SampleGlobalShortcutListener(app);
        new SampleGlobalShortcutListener(app);
        logger.info("{} Registered sample");
        for (ListenerProvider provider : getAllListeners()) {
            provider.register(app);
            logger.info("{} Registered", provider.getClass().getSimpleName());
        }
    }

    private static ListenerProvider[] getAllListeners() {
        return new ListenerProvider[] {
            new ActionListeners(),
            new CommandListeners(),
            new EventListeners(),
            new MessageListeners(),
            new ShortcutListeners(),
            new ViewListeners()
        };
    }
}
