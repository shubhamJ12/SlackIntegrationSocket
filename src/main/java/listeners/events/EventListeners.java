package listeners.events;

import com.google.gson.Gson;
import com.slack.api.bolt.App;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.event.AppMentionEvent;
import listeners.ListenerProvider;

public class EventListeners implements ListenerProvider {
    @Override
    public void register(App app) {
        app.event(AppMentionEvent.class, (payload, ctx) -> {
            AppMentionEvent event = payload.getEvent();
            ChatPostMessageResponse message;
            String type = event.getText().substring(event.getText().indexOf('>') + 1);

            message = ctx.client().chatPostMessage(r -> r.channel(event.getChannel())
                    .threadTs(event.getTs()) // event.getThreadTs() is null here
                    .text("question 1"));

            return ctx.ackWithJson(new Gson().toJson(message));
        });
        ///   app.event(AppHomeOpenedEvent.class, new AppHomeOpenedListener(app));
    }
}
