package com.evansitzes.razorbot;

import com.evansitzes.razorbot.messages.GeneralMessage;
import com.evansitzes.razorbot.triggers.GeneralRule;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

import java.util.List;

/**
 * Created by evan on 2/26/17.
 */
public class MessageEventHandler {

    private final SlackSession session;
    private final List<GeneralRule> generalRules;
    private final List<GeneralMessage> directMessages;

    public MessageEventHandler(final SlackSession session, final List<GeneralRule> generalRules, final List<GeneralMessage> directMessages) {
        this.session = session;
        this.generalRules = generalRules;
        this.directMessages = directMessages;
    }

    public void handleRule(final SlackMessagePosted event) {
        for (final GeneralRule rule : generalRules) {
            if (rule.triggersRule(event.getMessageContent())) {
                rule.handle(session, event);
            }
        }
    }

    public void handleDirectMessage(final SlackMessagePosted event) {
        for (final GeneralMessage message : directMessages) {
            if (message.isValid(event.getMessageContent())) {
                message.handle(session, event);
            }
        }
    }
}