import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

/**
 * Created by evan on 2/22/17.
 */
public class MessageEventListener {

    private static final String BOT_ID = "U3BGLQKA9";

    public void registeringAListener(final SlackSession session, final MessageEventHandler messageEventHandler) {

        // first define the listener
        final SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener() {
            public void onEvent(final SlackMessagePosted event, final SlackSession session) {

                // ignore messages from the bot
                if (event.getSender().getId().equals(BOT_ID)) {
                    return;
                }

                final SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
                final String messageContent = event.getMessageContent();
                final SlackUser messageSender = event.getSender();
                messageEventHandler.handle(channelOnWhichMessageWasPosted, messageContent);
            }
        };

        //add it to the session
        session.addMessagePostedListener(messagePostedListener);
    }

}
