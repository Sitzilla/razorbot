package com.evansitzes.razorbot;

import com.evansitzes.razorbot.messages.GeneralMessage;
import com.evansitzes.razorbot.messages.HackernewsMessage;
import com.evansitzes.razorbot.messages.SwearStatsMessage;
import com.evansitzes.razorbot.triggers.GeneralRule;
import com.evansitzes.razorbot.triggers.RazorbackRule;
import com.evansitzes.razorbot.triggers.SwearStatsRule;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by evan on 2/22/17.
 */
public class RazorBot {

    public static void main(final String[] args) {

        final Properties properties = new Properties();;
        final SlackSession session;

        try {
            final InputStream inputStream = new FileInputStream("env.properties");
            properties.load(inputStream);
            session = SlackSessionFactory.createWebSocketSlackSession(properties.getProperty("BOT_ID"));
            session.connect();

        } catch (final IOException e) {
            throw new RuntimeException("Unable to create Slack Session.", e.getCause());
        }

        final MessageEventHandler messageEventHandler = new MessageEventHandler(session, loadRules(), loadMessages());
        new MessageEventListener().registeringAListener(session, messageEventHandler);
    }

    private static List<GeneralRule> loadRules() {
        final List<GeneralRule> rules = new ArrayList<GeneralRule>();

        rules.add(new RazorbackRule());
        rules.add(new SwearStatsRule());

        return rules;
    }

    private static List<GeneralMessage> loadMessages() {
        final List<GeneralMessage> messages = new ArrayList<GeneralMessage>();

        messages.add(new HackernewsMessage());
        messages.add(new SwearStatsMessage());

        return messages;
    }

}
