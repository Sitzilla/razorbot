package com.evansitzes.razorbot.triggers;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

/**
 * Abstract Rule class that is extended by different rules.  Rules are checked everytime that RazorBot receives
 * a non-DM message from Slack.
 */
public abstract class GeneralRule {

    public abstract boolean triggersRule(final String message);
    public abstract void handle(final SlackSession session, final SlackMessagePosted event);

}
