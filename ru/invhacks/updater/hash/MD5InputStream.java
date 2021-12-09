// 
// Decompiled by Procyon v0.5.36
// 

package ru.invhacks.updater.hash;

import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

class MD5InputStream extends FilterInputStream
{
    private MD5Hasher md5;
    
    MD5InputStream(final InputStream in) {
        super(in);
        this.md5 = new MD5Hasher();
    }
    
    @Override
    public int read() throws IOException {
        final int read = super.in.read();
        if (read == -1) {
            return -1;
        }
        if ((read & 0xFFFFFF00) != 0x0) {
            System.out.println("MD5InputStream.read() got character with (c & ~0xff) != 0)!");
        }
        else {
            this.md5.Update(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int len) throws IOException {
        final int read;
        if ((read = super.in.read(array, n, len)) == -1) {
            return read;
        }
        this.md5.Update(array, n, read);
        return read;
    }
    
    String asHex() {
        return this.md5.asHex();
    }
}
