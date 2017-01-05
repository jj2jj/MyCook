package zjj.com.mycookassistant.utils;


import android.content.Context;
import android.content.SharedPreferences;


public class PreUtils {


    private static SharedPreferences getSharedPreferences(Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static boolean isFirstTime(Context context, String key) {
        if (getBoolean(context, key, false)) {
            return false;
        } else {
            putBoolean(context, key, true);
            return true;
        }
    }

    public static boolean contains(Context context, String key) {
        return PreUtils.getSharedPreferences(context).contains(key);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return PreUtils.getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int pValue) {
        final SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();

        editor.putInt(key, pValue);

        return editor.commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return PreUtils.getSharedPreferences(context).getLong(key, defaultValue);
    }

    public static Long getLong(Context context, String key, Long defaultValue) {
        if (PreUtils.getSharedPreferences(context).contains(key)) {
            return PreUtils.getSharedPreferences(context).getLong(key, 0);
        } else {
            return null;
        }
    }


    public static boolean putLong(Context context, String key, long pValue) {
        final SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();

        editor.putLong(key, pValue);

        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return PreUtils.getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, boolean pValue) {
        final SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();

        editor.putBoolean(key, pValue);

        return editor.commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return PreUtils.getSharedPreferences(context).getString(key, defaultValue);
    }

    public static boolean putString(Context context, String key, String pValue) {
        final SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();

        editor.putString(key, pValue);

        return editor.commit();
    }


    public static boolean remove(final Context context, final String key) {
        final SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();

        editor.remove(key);

        return editor.commit();
    }

    public static void removeAll(Context context) {
        SharedPreferences.Editor editor = PreUtils.getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }

}