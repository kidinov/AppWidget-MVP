package kidinov.org.ecosia.util;


import android.content.Context;
import android.content.Intent;

public class AndroidUtil {
    public static Intent buildIntentWithAction(Context context, Class clazz, String action) {
        Intent intent = new Intent(context, clazz);
        intent.setAction(action);
        return intent;
    }
}
