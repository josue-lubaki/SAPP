package ca.ghost_team.sapp.Utils;

import android.icu.text.SimpleDateFormat;

import androidx.room.TypeConverter;

import java.util.Date;

public class Conversion {
    //----Classe de definition conversion

    //Converion de date en Long pour ROOM
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        //
        return value == null ? null :  new Date(value);
    }
    //Converion  de Long a  date pour View
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String toTimeStr(Date time) {
        int hour = time.getHours();
        int min = time.getMinutes();
        StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            sb.append(0).append(hour);
        } else {
            sb.append(hour);
        }
        sb.append(":");
        if (min < 10) {
            sb.append(0).append(min);
        } else {
            sb.append(min);
        }
        return sb.toString();
    }

}
