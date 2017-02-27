/**
 * Created by evan on 2/26/17.
 */
public class RazorbackTrigger {

    public static final String IMAGE_URL = "http://a.espncdn.com/combiner/i?img=%2Fi%2Fteamlogos%2Fncaa%2F500%2F8.png";
    public static final String TRIGGER_WORD = "arkansas";

    public static boolean isTrigger(final String message) {
        return message.toLowerCase().contains(TRIGGER_WORD);
    }

}