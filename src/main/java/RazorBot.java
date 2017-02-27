import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

/**
 * Created by evan on 2/22/17.
 */
public class RazorBot {

    public static void main(final String[] args) {

        final SlackSession session = SlackSessionFactory.createWebSocketSlackSession("AUTH_TOKEN");
        try {
            session.connect();
        } catch (final IOException e) {
            e.printStackTrace();
        }

//        new MessageSender().sendMessageToAChannel(session);
//        new MessageEventListener().registeringAListener(session);
//        new MessageEventHandler(session);
        new MessageEventListener().registeringAListener(session, new MessageEventHandler(session));
    }
}
