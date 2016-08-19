package kidinov.org.ecosia.data;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class DataManager {
    private final Context context;

    public DataManager(Context context) {
        this.context = context;
    }

    public
    @NonNull
    List<MusicFileMetaData> getMp3Files() {
        List<MusicFileMetaData> result = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        Cursor cur = null;
        try {
            cur = cr.query(uri, null, selection, null, null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    String path = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    result.add(new MusicFileMetaData(name, path));
                }

            }
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return result;
    }
}
