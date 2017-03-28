package com.evansitzes.razorbot.messages;

import com.evansitzes.razorbot.model.HibernateUtils;
import com.evansitzes.razorbot.model.SwearWordEntity;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by evan on 3/2/17.
 */
public class SwearStatsMessage extends GeneralMessage {

    @Override
    public boolean isValid(final String message) {
        return message.toLowerCase().contains("stats");
    }

    @Override
    public void handle(final SlackSession session, final SlackMessagePosted event) {
        final List<SwearWordEntity> users = new HibernateUtils().getAllUsers();
        final StringBuilder message = new StringBuilder();
        session.sendMessage(event.getChannel(), "Current top stats:");

        Collections.sort(users, new Comparator<SwearWordEntity>() {
            public int compare(final SwearWordEntity s1, final SwearWordEntity s2) {
                // TODO maintaining a count would cut all of this down
                return ((Integer)sumSwearWords(buildCurrentWordMap(s2))).compareTo(sumSwearWords(buildCurrentWordMap(s1)));
            }
        });

        int place = 1;

        for (final SwearWordEntity user : users) {
            final Map<String, Integer> swearWordsMap = buildCurrentWordMap(user);
            final int sum = sumSwearWords(swearWordsMap);
            message.append("*" + place + ") " + user.getName() + "*" + " _total:_ " + sum + ", _breakdown:_ " + user.getSwearWords() + " \n");
            place++;
        }

        session.sendMessage(event.getChannel(), String.valueOf(message));
    }

    // TODO clean this up
    private static Map<String, Integer> buildCurrentWordMap(final SwearWordEntity user) {
        final Map<String, Integer> swearWordsMap = new HashMap<String, Integer>();
        String words = user.getSwearWords();
        words =  StringUtils.substringBetween(words, "{", "}");

        if (words.isEmpty()) {
            return swearWordsMap;
        }

        final String[] keyValuePairs = words.split(",");

        for(final String pair : keyValuePairs) {
            final String[] entry = pair.split("=");
            swearWordsMap.put(entry[0].trim(), Integer.valueOf(entry[1].trim()));
        }

        return swearWordsMap;
    }

    // TODO why am I not maintaining a count in the model?
    private static int sumSwearWords(final Map<String, Integer> swearWordsMap) {
        int sum = 0;

        for (final int value : swearWordsMap.values()) {
            sum += value;
        }

        return sum;
    }
}
