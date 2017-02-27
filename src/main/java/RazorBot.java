import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by evan on 2/22/17.
 */
public class RazorBot {

    public static void main(final String[] args) {

        final Properties properties = new Properties();

        try {
            final InputStream inputStream = new FileInputStream("env.properties");
            properties.load(inputStream);
            final SlackSession session = SlackSessionFactory.createWebSocketSlackSession(properties.getProperty("BOT_ID"));
            session.connect();
            new MessageEventListener().registeringAListener(session, new MessageEventHandler(session));

            // TODO handle this properly
        } catch (final IOException e) {
            e.printStackTrace();
        }

//        new MessageSender().sendMessageToAChannel(session);
//        new MessageEventListener().registeringAListener(session);
//        new MessageEventHandler(session);
//        new MessageEventListener().registeringAListener(session, new MessageEventHandler(session));
    }
}
