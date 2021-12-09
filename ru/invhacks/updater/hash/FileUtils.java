package ru.invhacks.updater.hash;

import ru.invhacks.updater.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static void checkFiles(){
        if(!Config.LOADER_DIR.exists()) {
            try {
                Config.LOADER_DIR.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!Config.LOADER_LIBS_DIR.exists()) {
            try {
                Config.LOADER_LIBS_DIR.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!Config.LOADER_JRE_LIB.exists()) {
            try {
                Config.LOADER_JRE_LIB.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getJVMHash(){
        File f = Config.LOADER_JVM;
        if(!f.exists())
            return null;
        try {
            return MD5.getHash(f);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getExeHash(){
        File f = Config.LOADER_EXE;
        if(!f.exists())
            return null;
        try {
            return MD5.getHash(f);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getBootstrapHash(){
        File f = Config.LOADER_BOOTSTRAP;
        if(!f.exists())
            return null;
        try {
            return MD5.getHash(f);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLoaderHash(){
        File f = Config.LOADER_JAR;
        if(!f.exists())
            return null;
        try {
            return MD5.getHash(f);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setLibsHash(String hashIn) {
        File f = Config.LOADER_LIBS_HASH;
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(hashIn.getBytes());
            fileOutputStream.close();
        } catch (Exception e)  {

        }
    }

    public static void setJREHash(String hashIn) {
        File f = Config.LOADER_JRE_HASH;
        if(f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(hashIn.getBytes());
            fileOutputStream.close();
        } catch (Exception e)  {

        }
    }

    public static String getJREHash(){
        File f = Config.LOADER_JRE_HASH;
        if(!f.exists())
            return null;
        try {
            return new String(Files.readAllBytes(f.toPath()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLibsHash(){
        File f = Config.LOADER_LIBS_HASH;
        if(!f.exists())
            return null;
        try {
            return new String(Files.readAllBytes(f.toPath()));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] buffer(long n) {
        if (n < 512L) {
            n = 512L;
        }
        if (n > 65536L) {
            n = 65536L;
        }
        return new byte[(int) n];
    }

    public static File urltofile(final URL url) {
        try {
            return Paths.get(url.toURI()).toFile();
        } catch (URISyntaxException ex) {
            return new File(url.getPath().replace("file:/", "").replace("file:", ""));
        }
    }

}
