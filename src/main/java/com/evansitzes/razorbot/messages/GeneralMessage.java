package com.evansitzes.razorbot.messages;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

/**
 * Created by evan on 3/2/17.
 */
public abstract class GeneralMessage {

    public abstract boolean isValid(final String message);
        public abstract void handle(final SlackSession session, final SlackMessagePosted event);

}
