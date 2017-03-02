package com.evansitzes.razorbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

/**
 * Created by evan on 2/22/17.
 */
public class MessageEventListener {

    private static final String BOT_ID = "U3BGLQKA9";

    public void registeringAListener(final SlackSession session, final MessageEventHandler messageEventHandler) {

        // First define the listener
        final SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener() {
            public void onEvent(final SlackMessagePosted event, final SlackSession session) {

                // Ignore messages from bots
                if (event.getSender().isBot()) {
                    return;
                }

                // Handle DM
                if (event.getMessageContent().contains(BOT_ID)) {
                    messageEventHandler.handleDirectMessage(event.getChannel(), event.getMessageContent());
                    return;
                }

                messageEventHandler.handleRule(event);
            }
        };

        //add it to the session
        session.addMessagePostedListener(messagePostedListener);
    }

}
