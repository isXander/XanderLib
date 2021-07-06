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

public final class HttpsUtils {

    public static Request.Builder setupRequest(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader("User-Agent", XanderLib.MOD_NAME + "/" + XanderLib.MOD_VER);
    }

    public static Response getResponse(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = setupRequest(url).build();

            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getBytes(String url) {
        try {
            return getResponse(url).body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String url) {
        try {
            return getResponse(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadFile(String url, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getBytes(url));
        fos.close();
    }

}
