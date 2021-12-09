package ru.invhacks.updater.utils;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.Vector;
import java.io.IOException;
import java.util.zip.ZipException;
import java.io.File;
import java.util.zip.ZipFile;

public class ZipArchive extends ZipFile
{
    public ZipArchive(final File file) throws ZipException, IOException {
        super(file);
    }

    public void extract(final File file) {
        this.extract(file.getAbsolutePath());
    }

    public void extract(final String s) {
        try {
            final Vector<ZipEntry> vector = new Vector<ZipEntry>();
            final Enumeration<? extends ZipEntry> entries = this.entries();
            while (entries.hasMoreElements()) {
                vector.addElement((ZipEntry)entries.nextElement());
            }
            for (int i = 0; i < vector.size(); ++i) {
                this.extract(s, vector.elementAt(i));
            }
        }
        catch (Exception ex) {}
    }

    public void extractSafely(final File file) {
        this.extractSafely(file.getAbsolutePath());
    }

    public void extractSafely(final String parent) {
        try {
            final Vector<ZipEntry> vector = new Vector<ZipEntry>();
            final Enumeration<? extends ZipEntry> entries = this.entries();
            while (entries.hasMoreElements()) {
                vector.addElement((ZipEntry)entries.nextElement());
            }
            for (int i = 0; i < vector.size(); ++i) {
                final ZipEntry zipEntry = vector.elementAt(i);
                final File file = new File(parent, zipEntry.getName());
                if (!file.exists() || (!zipEntry.isDirectory() && zipEntry.getSize() != file.length())) {
                    this.extract(parent, zipEntry);
                }
            }
        }
        catch (Exception ex) {}
    }

    private void extract(final String s, final ZipEntry entry) throws Exception {
        if (entry.isDirectory()) {
            return;
        }
        final String slash2sep = slash2sep(entry.getName());
        String substring;
        if (slash2sep.lastIndexOf(File.separator) != -1) {
            substring = slash2sep.substring(0, slash2sep.lastIndexOf(File.separator));
        }
        else {
            substring = "";
        }
        new File(s, substring).mkdirs();
        final FileOutputStream out = new FileOutputStream(String.valueOf(s) + File.separator + slash2sep);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(this.getInputStream(entry));
        final byte[] array = new byte[2048];
        try {
            int read;
            while ((read = bufferedInputStream.read(array, 0, array.length)) != -1) {
                bufferedOutputStream.write(array, 0, read);
            }
        }
        finally {
            bufferedOutputStream.close();
            bufferedInputStream.close();
            out.close();
        }

        bufferedOutputStream.close();
        bufferedInputStream.close();
        out.close();
    }

    private static String slash2sep(final String s) {
        final char[] value = new char[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '/') {
                value[i] = File.separatorChar;
            }
            else {
                value[i] = s.charAt(i);
            }
        }
        return new String(value);
    }
}
