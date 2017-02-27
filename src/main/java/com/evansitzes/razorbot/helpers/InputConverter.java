package com.evansitzes.razorbot.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by evan on 2/27/17.
 */
public class InputConverter {

    public static String convertStreamToString(final InputStream is) throws IOException {

        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        is.close();
        return sb.toString();
    }
}
