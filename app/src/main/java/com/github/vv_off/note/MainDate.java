package com.github.vv_off.note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainDate {
    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date());
    }
}
