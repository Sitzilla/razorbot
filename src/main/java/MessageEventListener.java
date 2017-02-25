import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

/**
 * Created by evan on 2/22/17.
 */
public class MessageEventListener {

    public void registeringAListener(final SlackSession session) {
        // first define the listener
        final SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener() {
            public void onEvent(final SlackMessagePosted event, final SlackSession session)
            {
                final SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
                final String messageContent = event.getMessageContent();
                final SlackUser messageSender = event.getSender();

            }
        };
        //add it to the session
        session.addMessagePostedListener(messagePostedListener);

        //that's it, the listener will get every message post events the bot can get notified on
        //(IE: the messages sent on channels it joined or sent directly to it)
    }

}
