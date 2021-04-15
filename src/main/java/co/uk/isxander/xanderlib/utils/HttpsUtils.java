/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.utils;

import co.uk.isxander.xanderlib.XanderLib;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class HttpsUtils {

    public static Response getResponse(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", XanderLib.MOD_NAME + "/" + XanderLib.MOD_VER)
                .build();
        return client.newCall(request).execute();
    }

    public static byte[] getBytes(URL url) throws IOException {
        return getResponse(url).body().bytes();
    }

    public static String getString(URL url) throws IOException {
        return getResponse(url).body().string();
    }

    public static void downloadFile(URL url, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getBytes(url));
        fos.close();
    }

    public interface ResponseExecutor {
        void run(Response response);
    }

}
