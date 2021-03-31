package ca.ghost_team.sapp.Utils;

import android.icu.text.SimpleDateFormat;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Conversion {

    /**
     * Methode qui permet de translate une valeur de type long en Date [Utile pour Room]
     *
     * @param value l'Entité Date dont on veut transformer en Date.
     * @return Date
     * */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null :  new Date(value);
    }

    /**
     * Methode return the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this date.
     *
     * @param date l'Entité Date dont on veut transformer en long.
     * @return long
     * */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * Methode qui permet de translate la Date en String [timestamps en String]
     *
     * @param time l'Entité Date dont on veut transformer en String. Exemple : "08:15"
     * @return String
     * */
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
