package com.evansitzes.razorbot.triggers;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

/**
 * Created by evan on 2/26/17.
 */
public class RazorbackRule extends GeneralRule {

    private static final String IMAGE_URL = "http://a.espncdn.com/combiner/i?img=%2Fi%2Fteamlogos%2Fncaa%2F500%2F8.png";
    private static final String TRIGGER_WORD = "arkansas";

    @Override
    public boolean triggersRule(final String message) {
        return message.toLowerCase().contains(TRIGGER_WORD);
    }

    @Override
    public void handle(final SlackSession session, final SlackMessagePosted event) {
        session.sendMessage(event.getChannel(), "Arkansas? Go Hogs!!");
        session.sendMessage(event.getChannel(), IMAGE_URL);
    }
}