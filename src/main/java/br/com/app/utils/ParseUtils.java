package br.com.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {
    public static String parseDate(Date date) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
