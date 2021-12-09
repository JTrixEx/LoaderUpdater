package ru.invhacks.updater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.invhacks.updater.hash.FileUtils;
import ru.invhacks.updater.utils.DownloadGUI;
import ru.invhacks.updater.utils.URLUtils;
import ru.invhacks.updater.utils.ZipArchive;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main extends Thread {

    private static Main instance;
    private DownloadGUI downloadGUI = new DownloadGUI();

    public void launch(String[] args) {
        this.start();
    }

    @Override
    public void run() {
        try {
            downloadGUI.init();
            this.loadFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFiles() throws Exception{
        FileUtils.checkFiles();

        String responce = URLUtils.getResponce("updater.php");

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(responce);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String loaderHash = jsonObject.get("loaderHash").getAsString();
        String libsHash = jsonObject.get("libsHash").getAsString();
        String jreHash = jsonObject.get("jreHash").getAsString();
        String bootstrapHash = jsonObject.get("bootstrapHash").getAsString();
        String exeHash = jsonObject.get("exeHash").getAsString();
        String jvmHash =  jsonObject.get("jvmHash").getAsString();

        String localLibsHash = FileUtils.getLibsHash();
        if(localLibsHash == null) {
            this.downloadLibs();
            FileUtils.setLibsHash(libsHash);
        }
        if(localLibsHash != null && !libsHash.equals(localLibsHash)) {
            this.downloadLibs();
            FileUtils.setLibsHash(libsHash);
        }

        String localJREHash = FileUtils.getJREHash();
        if(localJREHash == null) {
            this.downloadJRE();
            FileUtils.setJREHash(jreHash);
        }
        if(localJREHash != null && !jreHash.equals(localJREHash)) {
            this.downloadJRE();
            FileUtils.setJREHash(jreHash);
        }

        String localLoaderHash = FileUtils.getLoaderHash();
        if(localLoaderHash == null) {
            this.downloadLoader();
        }
        if(localLoaderHash != null && !loaderHash.equals(localLoaderHash)) {
            this.downloadLoader();
        }

        String localBoostrapHash = FileUtils.getBootstrapHash();
        if(localBoostrapHash == null) {
            this.downloadBootstrap();
        }
        if(localBoostrapHash != null && !bootstrapHash.equals(localBoostrapHash)) {
            this.downloadBootstrap();
        }

        String localExeHash = FileUtils.getExeHash();
        if(localExeHash == null) {
            this.downloadExe();
        }
        if(localExeHash != null && !localExeHash.equals(exeHash)) {
            this.downloadExe();
        }

        String localJVMHash = FileUtils.getJVMHash();
        if(localJVMHash == null) {
            this.downloadJVM();
        }
        if(localJVMHash != null && !localJVMHash.equals(jvmHash)) {
            this.downloadJVM();
        }

        launchLoader();
    }

    private void launchLoader() throws Exception {
        List<String> command = new ArrayList<String>();

        command.add("cmd");
        command.add("/d");
        command.add("/c");
        command.add("start");
        command.add("\"\"");
        command.add("/D");
        command.add("\"" + Config.LOADER_DIR.getAbsolutePath()  + "\"");

        command.add("\"" + Config.LOADER_EXE.getAbsolutePath() + "\"");
        /*command.add("cmd");
        command.add("/d");
        command.add("/q");
        command.add("/c");
        command.add("start");
        command.add("\"\"");
        command.add("/D");
        command.add("\"" + Config.LOADER_DIR.getAbsolutePath() + "\"");

        command.add(Config.LOADER_JRE_LIB + File.separator + "bin" + File.separator + "java.exe");
        command.add("-cp");
        command.add(Config.LOADER_LIBS_DIR.getAbsolutePath() + File.separator + "*;" + Config.LOADER_JAR.getAbsolutePath());
        command.add("ru.invhacks.loader.gui.Gui");*/

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(Config.LOADER_DIR);
        processBuilder.start();

        System.exit(0);
    }

    private void downloadJVM() throws Exception {
        Path path = Config.LOADER_JVM.toPath();
        URLUtils.download("jvm.dll", path);
    }

    private void downloadExe() throws Exception {
        Path path = Config.LOADER_EXE.toPath();
        URLUtils.download("Yammi.exe", path);
    }

    private void downloadBootstrap() throws Exception {
        Path path = Config.LOADER_BOOTSTRAP.toPath();
        URLUtils.download("bootstrap.jar", path);
    }

    private void downloadLoader() throws Exception {
        Path path = Config.LOADER_JAR.toPath();
        URLUtils.download("loader.jar", path);
    }

    private void downloadJRE() throws Exception {
        Path zipPath = Paths.get(Config.LOADER_JRE_LIB.getAbsolutePath(), "jre-x64.zip");
        URLUtils.download("jre-x64.zip", Paths.get(Config.LOADER_JRE_LIB.getAbsolutePath(), "jre-x64.zip"));

        this.getDownloadGUI().unzip(zipPath);
        ZipArchive zipArchive = new ZipArchive(zipPath.toFile());
        zipArchive.extractSafely(Config.LOADER_JRE_LIB);
        zipArchive.close();
        Files.delete(zipPath);
    }

    private void downloadLibs() throws Exception{
        Path zipPath = Paths.get(Config.LOADER_LIBS_DIR.getAbsolutePath(), "libs.zip");
        URLUtils.download("libs.zip", Paths.get(Config.LOADER_LIBS_DIR.getAbsolutePath(), "libs.zip"));

        this.getDownloadGUI().unzip(zipPath);
        ZipArchive zipArchive = new ZipArchive(zipPath.toFile());
        zipArchive.extractSafely(Config.LOADER_LIBS_DIR);
        zipArchive.close();
        Files.delete(zipPath);
    }

    public static void main(String[] args) {
        instance = new Main();
        instance.launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    public DownloadGUI getDownloadGUI() {
        return downloadGUI;
    }
}
