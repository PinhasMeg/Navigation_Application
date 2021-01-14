package il.co.expertize.androidapplicationjava.Utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
    /**
     * check if a string is a number
     * @param str
     * @return "true" is the string is numeric
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Function that prints a toast message with entered color
     * @param msg the message we want to print
     * @param color the color of the message
     */
    public static void PrintColorToast(Context context, String msg, Integer color)
    {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(color);
        toast.show();
    }
}

