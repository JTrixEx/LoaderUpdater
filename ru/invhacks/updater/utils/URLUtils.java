package ru.invhacks.updater.utils;

import ru.invhacks.updater.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class URLUtils {

    public static void download(String path, Path file) {
        try {
            URL url = createURL(path);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");
            conn.setRequestProperty("User-Agent", "Yammi");

            InputStream inputStream = conn.getInputStream();
            //InputStream inputStream = url.openStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            Files.deleteIfExists(file);
            Files.createFile(file);

            FileOutputStream fileOutputStream = new FileOutputStream(file.toFile());

            byte[] buffer = new byte[1024];
            int len = 0;

            /*Main.getInstance().getDownloadGUI().updateDownload(dataInputStream.available());
            while((len = dataInputStream.read(buffer,0,1024)) != -1){
                fileOutputStream.write(buffer, 0, len);
                Main.getInstance().getDownloadGUI().onDownload(file.toFile().getName(), len);
            }*/

            byte[] buf = new byte[65536];
            Main.getInstance().getDownloadGUI().updateDownload((int)conn.getContentLengthLong());
            int size;
            while ((size = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, size);
                Main.getInstance().getDownloadGUI().onDownload(file.toFile().getName(), size);
            }

            fileOutputStream.close();
            dataInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getResponce(String type) {
        try {
            URL url = createURL(type);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Yammi");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();
            in.close();

            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String getQuery(String type, HashMap<String, String> values) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for(Map.Entry<String, String> entry : values.entrySet()) {
                if (stringBuilder.length() != 0) stringBuilder.append('&');
                stringBuilder.append(entry.getKey());
                stringBuilder.append('=');
                stringBuilder.append(entry.getValue());
            }

            String data = stringBuilder.toString();
            URL url = createURL(type + "?" + data);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();
            in.close();

            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static URL createURL(String path) {
        try {
            return new URL("https://invhacks.ru/updater/" + path);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
