package br.com.iandev.midiaindoor.dao;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 09/12/2016.
 */

public class SelectArgs {
    private final StringBuilder where;
    private final List<String> args;
    private boolean first;

    SelectArgs() {
        where = new StringBuilder();
        args = new ArrayList<>();
        first = true;
    }

    public SelectArgs put(String key, String value) {
        args.add(value);
        if (first) {
            first = false;
        } else {
            where.append(" and ");
        }
        where.append(key);
        return this;
    }

    @NonNull
    String getWhere() {
        return where.toString();
    }

    String[] getArgs() {
        String[] argsArray = new String[args.size()];
        for (int i = 0; i < argsArray.length; i++) {
            argsArray[i] = args.get(i);
        }
        return argsArray;
    }
}
