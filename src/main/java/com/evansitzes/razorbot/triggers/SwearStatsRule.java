package com.evansitzes.razorbot.triggers;

import com.evansitzes.razorbot.model.HibernateUtils;
import com.evansitzes.razorbot.model.SwearWordEntity;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by evan on 2/27/17.
 */
public class SwearStatsRule extends GeneralRule {

    private static final String NON_ALPHANUMERIC_CHARACTERS = "[^A-Za-z0-9\\s]";

    @Override
    public boolean triggersRule(final String message) {
        return !parseSwearWords(message).isEmpty();
    }

    @Override
    public void handle(final SlackSession session, final SlackMessagePosted event) {
        final String swearWordsString = parseSwearWords(event.getMessageContent());
        final String[] swearWords = swearWordsString.trim().toLowerCase().split(" ");
        final SwearWordEntity user = new HibernateUtils().getUser(event.getSender());
        final Map<String, Integer> swearWordsMap = buildCurrentWordMap(user);

        for (final String word : swearWords) {
            if (word.isEmpty()) {
                continue;
            }

            if (swearWordsMap.get(word) == null) {
                swearWordsMap.put(word, 1);
                continue;
            }

            swearWordsMap.put(word, swearWordsMap.get(word) + 1);
        }

        user.setSwearWords(swearWordsMap.toString());
        new HibernateUtils().updateUser(user);
    }

    private static String parseSwearWords(final String sentence) {
        try {
            return ExudeData.getInstance().getSwearWords(sentence.replaceAll(NON_ALPHANUMERIC_CHARACTERS, ""));
        } catch (final InvalidDataException e) {
            throw new RuntimeException("Unable to parse swear words", e.getCause());
        }
    }

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
}
