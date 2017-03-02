package com.evansitzes.razorbot;

import com.evansitzes.razorbot.messages.HackernewsMessage;
import com.evansitzes.razorbot.triggers.GeneralRule;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

import java.util.List;

/**
 * Created by evan on 2/26/17.
 */
public class MessageEventHandler {

    private final SlackSession session;
    private final List<GeneralRule> generalRules;

    public MessageEventHandler(final SlackSession session, final List<GeneralRule> generalRules) {
        this.session = session;
        this.generalRules = generalRules;
    }

    public void handleRule(final SlackMessagePosted event) {
        for (final GeneralRule rule : generalRules) {
            if (rule.triggersRule(event.getMessageContent())) {
                rule.handle(session, event);
            }
        }
    }

    public void handleDirectMessage(final SlackChannel channel, final String message) {
        if (HackernewsMessage.isValid(message)) {
            final String storyUrl = HackernewsMessage.getTopStory();
            session.sendMessage(channel, "Current top story on Hacker News");
            session.sendMessage(channel, storyUrl);
        }
    }
}