package ru.invhacks.updater;

import java.io.File;

public class Config {

    public static final File LOADER_DIR = new File(System.getenv("APPDATA") + File.separator + ".yammi");
    public static final File LOADER_JAR = new File(LOADER_DIR, "loader.jar");
    public static final File LOADER_LIBS_DIR = new File(LOADER_DIR, "libs");
    public static final File LOADER_LIBS_HASH = new File(LOADER_LIBS_DIR, "libs.md5");
    public static final File LOADER_JRE_LIB = new File(LOADER_DIR, "jre-x64");
    public static final File LOADER_JRE_HASH = new File(LOADER_JRE_LIB, "jre-x64.md5");
    public static final File LOADER_BOOTSTRAP = new File(LOADER_DIR, "bootstrap.jar");
    public static final File LOADER_EXE = new File(LOADER_DIR, "Yammi.exe");
    public static final File LOADER_JVM = new File(LOADER_JRE_LIB, "bin" + File.separator + "server" + File.separator + "jvm.dll");

}
