package com.dh.clinica.util;

import java.sql.Timestamp;
import java.util.Date;

public class Util {
    public static Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());

    }
}
