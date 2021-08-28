package ca.ghost_team.sapp.Utils;

import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class Utilitaire {

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

     /**
     * Methode qui permet de hasher le mot de passe de l'Utilisateur
     * @return String
     * */
    public static String hashage(@NonNull String in) {
        try {

            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(in.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Methode qui permet d'encryptage
     * @param text : le text à encrypter
     * @param key : la clé secrète utilisée
     * @return String
     * */
    public static String encrypt(@NonNull String text, int key){
        StringBuilder convert = new StringBuilder(text);
        StringBuilder result = new StringBuilder();
        String textReverse = convert.reverse().toString();
        char[] tableChar = textReverse.toCharArray();

        for (char c : tableChar){
            result.append((char) (c + key));
        }
        return result.toString();
    }

    /**
     * Methode qui permet le decryptage
     * @param text : le text à decrypter
     * @param key : la clé secrète utilisée
     * @return String
     * */
    public static String decode(@NonNull String text, int key){
        StringBuilder convert = new StringBuilder(text);
        StringBuilder result = new StringBuilder();
        String textReverse = convert.reverse().toString();
        char[] tableChar = textReverse.toCharArray();

        for (char c : tableChar){
            result.append((char) (c - key));
        }
        return result.toString();
    }



}
