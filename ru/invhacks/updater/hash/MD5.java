// 
// Decompiled by Procyon v0.5.36
// 

package ru.invhacks.updater.hash;

import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.io.File;

public class MD5 {
	public static String getHash(final File file) {
		if (!file.exists()) {
			return "nullhash";
		}
		return getHashOld(file);
	}

	public static String getHash(final URL url) {
		return getHash(FileUtils.urltofile(url));
	}

	public static String getHashOld(URL url) {
		InputStream inputStream = null;
		try {
			final File urltofile = FileUtils.urltofile(url);
			if (!urltofile.exists()) {
				return "nullhash";
			}
			url = urltofile.toURI().toURL();
			final byte[] buffer = FileUtils.buffer(urltofile.length());
			final MD5InputStream md5InputStream = (MD5InputStream) (inputStream = new MD5InputStream(url.openStream()));
			while (md5InputStream.read(buffer) != -1) {
			}
			md5InputStream.close();
			inputStream.close();
			return md5InputStream.asHex();
		} catch (IOException ex) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception ex2) {
				}
			}
			return "nullhash";
		}
	}

	public static String getHashOld(final File file) {
		try {
			return getHashOld(file.toURI().toURL());
		} catch (MalformedURLException ex) {
			return "nullhash";
		}
	}
}
