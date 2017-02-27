import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

/**
 * Created by evan on 2/26/17.
 */
public class MessageEventHandler {

    private final SlackSession session;

    public MessageEventHandler(final SlackSession session) {
        this.session = session;
    }

    public void handle(final SlackChannel channel, final String message) {
        if (RazorbackTrigger.isTrigger(message)) {
            session.sendMessage(channel, "Arkansas? Go Hogs!!");
            session.sendMessage(channel, RazorbackTrigger.IMAGE_URL);
        }
    }
}