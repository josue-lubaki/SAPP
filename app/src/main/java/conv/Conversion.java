package conv;

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
    //Converion  de Long a  date pour Vieuw
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
