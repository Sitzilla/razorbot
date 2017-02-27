import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

/**
 * Created by evan on 2/22/17.
 */
public class MessageSender {

    public void sendMessageToAChannel(final SlackSession session) {

        //get a channel
        final SlackChannel channel = session.findChannelByName("crawl-chatty");

//        session.sendMessage(channel, "Whats up guys?");
        session.sendMessage(channel, "Go Hogs!!");
        session.sendMessage(channel, "http://a.espncdn.com/combiner/i?img=%2Fi%2Fteamlogos%2Fncaa%2F500%2F8.png");
    }
}
