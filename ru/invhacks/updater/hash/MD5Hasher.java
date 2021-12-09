package ru.invhacks.updater.hash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MD5Hasher {
	MD5State state;
	MD5State finals;
	static byte[] padding = new byte[] { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0 };
	private static final char[] HEX_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	public synchronized void Init() {
		this.state = new MD5State(this);
		this.finals = null;
	}

	public MD5Hasher() {
		this.Init();
	}

	private void Decode(byte[] var1, int var2, int[] var3) {
		var3[0] = var1[var2] & 255 | (var1[var2 + 1] & 255) << 8 | (var1[var2 + 2] & 255) << 16 | var1[var2 + 3] << 24;
		var3[1] = var1[var2 + 4] & 255 | (var1[var2 + 5] & 255) << 8 | (var1[var2 + 6] & 255) << 16
				| var1[var2 + 7] << 24;
		var3[2] = var1[var2 + 8] & 255 | (var1[var2 + 9] & 255) << 8 | (var1[var2 + 10] & 255) << 16
				| var1[var2 + 11] << 24;
		var3[3] = var1[var2 + 12] & 255 | (var1[var2 + 13] & 255) << 8 | (var1[var2 + 14] & 255) << 16
				| var1[var2 + 15] << 24;
		var3[4] = var1[var2 + 16] & 255 | (var1[var2 + 17] & 255) << 8 | (var1[var2 + 18] & 255) << 16
				| var1[var2 + 19] << 24;
		var3[5] = var1[var2 + 20] & 255 | (var1[var2 + 21] & 255) << 8 | (var1[var2 + 22] & 255) << 16
				| var1[var2 + 23] << 24;
		var3[6] = var1[var2 + 24] & 255 | (var1[var2 + 25] & 255) << 8 | (var1[var2 + 26] & 255) << 16
				| var1[var2 + 27] << 24;
		var3[7] = var1[var2 + 28] & 255 | (var1[var2 + 29] & 255) << 8 | (var1[var2 + 30] & 255) << 16
				| var1[var2 + 31] << 24;
		var3[8] = var1[var2 + 32] & 255 | (var1[var2 + 33] & 255) << 8 | (var1[var2 + 34] & 255) << 16
				| var1[var2 + 35] << 24;
		var3[9] = var1[var2 + 36] & 255 | (var1[var2 + 37] & 255) << 8 | (var1[var2 + 38] & 255) << 16
				| var1[var2 + 39] << 24;
		var3[10] = var1[var2 + 40] & 255 | (var1[var2 + 41] & 255) << 8 | (var1[var2 + 42] & 255) << 16
				| var1[var2 + 43] << 24;
		var3[11] = var1[var2 + 44] & 255 | (var1[var2 + 45] & 255) << 8 | (var1[var2 + 46] & 255) << 16
				| var1[var2 + 47] << 24;
		var3[12] = var1[var2 + 48] & 255 | (var1[var2 + 49] & 255) << 8 | (var1[var2 + 50] & 255) << 16
				| var1[var2 + 51] << 24;
		var3[13] = var1[var2 + 52] & 255 | (var1[var2 + 53] & 255) << 8 | (var1[var2 + 54] & 255) << 16
				| var1[var2 + 55] << 24;
		var3[14] = var1[var2 + 56] & 255 | (var1[var2 + 57] & 255) << 8 | (var1[var2 + 58] & 255) << 16
				| var1[var2 + 59] << 24;
		var3[15] = var1[var2 + 60] & 255 | (var1[var2 + 61] & 255) << 8 | (var1[var2 + 62] & 255) << 16
				| var1[var2 + 63] << 24;
	}

	private void Transform(MD5State var1, byte[] var2, int var3, int[] var4) {
		int var5 = var1.state[0];
		int var6 = var1.state[1];
		int var7 = var1.state[2];
		int var8 = var1.state[3];
		this.Decode(var2, var3, var4);
		var5 += (var6 & var7 | ~var6 & var8) + var4[0] + -680876936;
		var5 = (var5 << 7 | var5 >>> 25) + var6;
		var8 += (var5 & var6 | ~var5 & var7) + var4[1] + -389564586;
		var8 = (var8 << 12 | var8 >>> 20) + var5;
		var7 += (var8 & var5 | ~var8 & var6) + var4[2] + 606105819;
		var7 = (var7 << 17 | var7 >>> 15) + var8;
		var6 += (var7 & var8 | ~var7 & var5) + var4[3] + -1044525330;
		var6 = (var6 << 22 | var6 >>> 10) + var7;
		var5 += (var6 & var7 | ~var6 & var8) + var4[4] + -176418897;
		var5 = (var5 << 7 | var5 >>> 25) + var6;
		var8 += (var5 & var6 | ~var5 & var7) + var4[5] + 1200080426;
		var8 = (var8 << 12 | var8 >>> 20) + var5;
		var7 += (var8 & var5 | ~var8 & var6) + var4[6] + -1473231341;
		var7 = (var7 << 17 | var7 >>> 15) + var8;
		var6 += (var7 & var8 | ~var7 & var5) + var4[7] + -45705983;
		var6 = (var6 << 22 | var6 >>> 10) + var7;
		var5 += (var6 & var7 | ~var6 & var8) + var4[8] + 1770035416;
		var5 = (var5 << 7 | var5 >>> 25) + var6;
		var8 += (var5 & var6 | ~var5 & var7) + var4[9] + -1958414417;
		var8 = (var8 << 12 | var8 >>> 20) + var5;
		var7 += (var8 & var5 | ~var8 & var6) + var4[10] + -42063;
		var7 = (var7 << 17 | var7 >>> 15) + var8;
		var6 += (var7 & var8 | ~var7 & var5) + var4[11] + -1990404162;
		var6 = (var6 << 22 | var6 >>> 10) + var7;
		var5 += (var6 & var7 | ~var6 & var8) + var4[12] + 1804603682;
		var5 = (var5 << 7 | var5 >>> 25) + var6;
		var8 += (var5 & var6 | ~var5 & var7) + var4[13] + -40341101;
		var8 = (var8 << 12 | var8 >>> 20) + var5;
		var7 += (var8 & var5 | ~var8 & var6) + var4[14] + -1502002290;
		var7 = (var7 << 17 | var7 >>> 15) + var8;
		var6 += (var7 & var8 | ~var7 & var5) + var4[15] + 1236535329;
		var6 = (var6 << 22 | var6 >>> 10) + var7;
		var5 += (var6 & var8 | var7 & ~var8) + var4[1] + -165796510;
		var5 = (var5 << 5 | var5 >>> 27) + var6;
		var8 += (var5 & var7 | var6 & ~var7) + var4[6] + -1069501632;
		var8 = (var8 << 9 | var8 >>> 23) + var5;
		var7 += (var8 & var6 | var5 & ~var6) + var4[11] + 643717713;
		var7 = (var7 << 14 | var7 >>> 18) + var8;
		var6 += (var7 & var5 | var8 & ~var5) + var4[0] + -373897302;
		var6 = (var6 << 20 | var6 >>> 12) + var7;
		var5 += (var6 & var8 | var7 & ~var8) + var4[5] + -701558691;
		var5 = (var5 << 5 | var5 >>> 27) + var6;
		var8 += (var5 & var7 | var6 & ~var7) + var4[10] + 38016083;
		var8 = (var8 << 9 | var8 >>> 23) + var5;
		var7 += (var8 & var6 | var5 & ~var6) + var4[15] + -660478335;
		var7 = (var7 << 14 | var7 >>> 18) + var8;
		var6 += (var7 & var5 | var8 & ~var5) + var4[4] + -405537848;
		var6 = (var6 << 20 | var6 >>> 12) + var7;
		var5 += (var6 & var8 | var7 & ~var8) + var4[9] + 568446438;
		var5 = (var5 << 5 | var5 >>> 27) + var6;
		var8 += (var5 & var7 | var6 & ~var7) + var4[14] + -1019803690;
		var8 = (var8 << 9 | var8 >>> 23) + var5;
		var7 += (var8 & var6 | var5 & ~var6) + var4[3] + -187363961;
		var7 = (var7 << 14 | var7 >>> 18) + var8;
		var6 += (var7 & var5 | var8 & ~var5) + var4[8] + 1163531501;
		var6 = (var6 << 20 | var6 >>> 12) + var7;
		var5 += (var6 & var8 | var7 & ~var8) + var4[13] + -1444681467;
		var5 = (var5 << 5 | var5 >>> 27) + var6;
		var8 += (var5 & var7 | var6 & ~var7) + var4[2] + -51403784;
		var8 = (var8 << 9 | var8 >>> 23) + var5;
		var7 += (var8 & var6 | var5 & ~var6) + var4[7] + 1735328473;
		var7 = (var7 << 14 | var7 >>> 18) + var8;
		var6 += (var7 & var5 | var8 & ~var5) + var4[12] + -1926607734;
		var6 = (var6 << 20 | var6 >>> 12) + var7;
		var5 += (var6 ^ var7 ^ var8) + var4[5] + -378558;
		var5 = (var5 << 4 | var5 >>> 28) + var6;
		var8 += (var5 ^ var6 ^ var7) + var4[8] + -2022574463;
		var8 = (var8 << 11 | var8 >>> 21) + var5;
		var7 += (var8 ^ var5 ^ var6) + var4[11] + 1839030562;
		var7 = (var7 << 16 | var7 >>> 16) + var8;
		var6 += (var7 ^ var8 ^ var5) + var4[14] + -35309556;
		var6 = (var6 << 23 | var6 >>> 9) + var7;
		var5 += (var6 ^ var7 ^ var8) + var4[1] + -1530992060;
		var5 = (var5 << 4 | var5 >>> 28) + var6;
		var8 += (var5 ^ var6 ^ var7) + var4[4] + 1272893353;
		var8 = (var8 << 11 | var8 >>> 21) + var5;
		var7 += (var8 ^ var5 ^ var6) + var4[7] + -155497632;
		var7 = (var7 << 16 | var7 >>> 16) + var8;
		var6 += (var7 ^ var8 ^ var5) + var4[10] + -1094730640;
		var6 = (var6 << 23 | var6 >>> 9) + var7;
		var5 += (var6 ^ var7 ^ var8) + var4[13] + 681279174;
		var5 = (var5 << 4 | var5 >>> 28) + var6;
		var8 += (var5 ^ var6 ^ var7) + var4[0] + -358537222;
		var8 = (var8 << 11 | var8 >>> 21) + var5;
		var7 += (var8 ^ var5 ^ var6) + var4[3] + -722521979;
		var7 = (var7 << 16 | var7 >>> 16) + var8;
		var6 += (var7 ^ var8 ^ var5) + var4[6] + 76029189;
		var6 = (var6 << 23 | var6 >>> 9) + var7;
		var5 += (var6 ^ var7 ^ var8) + var4[9] + -640364487;
		var5 = (var5 << 4 | var5 >>> 28) + var6;
		var8 += (var5 ^ var6 ^ var7) + var4[12] + -421815835;
		var8 = (var8 << 11 | var8 >>> 21) + var5;
		var7 += (var8 ^ var5 ^ var6) + var4[15] + 530742520;
		var7 = (var7 << 16 | var7 >>> 16) + var8;
		var6 += (var7 ^ var8 ^ var5) + var4[2] + -995338651;
		var6 = (var6 << 23 | var6 >>> 9) + var7;
		var5 += (var7 ^ (var6 | ~var8)) + var4[0] + -198630844;
		var5 = (var5 << 6 | var5 >>> 26) + var6;
		var8 += (var6 ^ (var5 | ~var7)) + var4[7] + 1126891415;
		var8 = (var8 << 10 | var8 >>> 22) + var5;
		var7 += (var5 ^ (var8 | ~var6)) + var4[14] + -1416354905;
		var7 = (var7 << 15 | var7 >>> 17) + var8;
		var6 += (var8 ^ (var7 | ~var5)) + var4[5] + -57434055;
		var6 = (var6 << 21 | var6 >>> 11) + var7;
		var5 += (var7 ^ (var6 | ~var8)) + var4[12] + 1700485571;
		var5 = (var5 << 6 | var5 >>> 26) + var6;
		var8 += (var6 ^ (var5 | ~var7)) + var4[3] + -1894986606;
		var8 = (var8 << 10 | var8 >>> 22) + var5;
		var7 += (var5 ^ (var8 | ~var6)) + var4[10] + -1051523;
		var7 = (var7 << 15 | var7 >>> 17) + var8;
		var6 += (var8 ^ (var7 | ~var5)) + var4[1] + -2054922799;
		var6 = (var6 << 21 | var6 >>> 11) + var7;
		var5 += (var7 ^ (var6 | ~var8)) + var4[8] + 1873313359;
		var5 = (var5 << 6 | var5 >>> 26) + var6;
		var8 += (var6 ^ (var5 | ~var7)) + var4[15] + -30611744;
		var8 = (var8 << 10 | var8 >>> 22) + var5;
		var7 += (var5 ^ (var8 | ~var6)) + var4[6] + -1560198380;
		var7 = (var7 << 15 | var7 >>> 17) + var8;
		var6 += (var8 ^ (var7 | ~var5)) + var4[13] + 1309151649;
		var6 = (var6 << 21 | var6 >>> 11) + var7;
		var5 += (var7 ^ (var6 | ~var8)) + var4[4] + -145523070;
		var5 = (var5 << 6 | var5 >>> 26) + var6;
		var8 += (var6 ^ (var5 | ~var7)) + var4[11] + -1120210379;
		var8 = (var8 << 10 | var8 >>> 22) + var5;
		var7 += (var5 ^ (var8 | ~var6)) + var4[2] + 718787259;
		var7 = (var7 << 15 | var7 >>> 17) + var8;
		var6 += (var8 ^ (var7 | ~var5)) + var4[9] + -343485551;
		var6 = (var6 << 21 | var6 >>> 11) + var7;
		var1.state[0] += var5;
		var1.state[1] += var6;
		var1.state[2] += var7;
		var1.state[3] += var8;
	}

	public void Update(MD5State var1, byte[] var2, int var3, int var4) {
		this.finals = null;
		if (var4 - var3 > var2.length) {
			var4 = var2.length - var3;
		}

		int var5 = (int) (var1.count & 63L);
		var1.count += (long) var4;
		int var6 = 64 - var5;
		int var7;
		if (var4 >= var6) {
			int[] var9 = new int[16];
			if (var6 == 64) {
				var6 = 0;
			} else {
				for (var7 = 0; var7 < var6; ++var7) {
					var1.buffer[var7 + var5] = var2[var7 + var3];
				}

				this.Transform(var1, var1.buffer, 0, var9);
			}

			for (var7 = var6; var7 + 63 < var4; var7 += 64) {
				this.Transform(var1, var2, var7 + var3, var9);
			}

			var5 = 0;
		} else {
			var7 = 0;
		}

		if (var7 < var4) {
			for (int var8 = var7; var7 < var4; ++var7) {
				var1.buffer[var5 + var7 - var8] = var2[var7 + var3];
			}
		}

	}

	public void Update(byte[] var1, int var2, int var3) {
		this.Update(this.state, var1, var2, var3);
	}

	public void Update(int var1) {
		this.Update((byte) (var1 & 255));
	}

	private byte[] Encode(int[] var1, int var2) {
		byte[] var5 = new byte[var2];
		int var4 = 0;

		for (int var3 = 0; var4 < var2; var4 += 4) {
			var5[var4] = (byte) (var1[var3] & 255);
			var5[var4 + 1] = (byte) (var1[var3] >>> 8 & 255);
			var5[var4 + 2] = (byte) (var1[var3] >>> 16 & 255);
			var5[var4 + 3] = (byte) (var1[var3] >>> 24 & 255);
			++var3;
		}

		return var5;
	}

	public synchronized byte[] Final() {
		if (this.finals == null) {
			MD5State var4 = new MD5State(this, this.state);
			int[] var5 = new int[] { (int) (var4.count << 3), (int) (var4.count >> 29) };
			byte[] var1 = this.Encode(var5, 8);
			int var2 = (int) (var4.count & 63L);
			int var3 = var2 < 56 ? 56 - var2 : 120 - var2;
			this.Update(var4, padding, 0, var3);
			this.Update(var4, var1, 0, 8);
			this.finals = var4;
		}

		return this.Encode(this.finals.state, 16);
	}

	public static String asHex(byte[] var0) {
		char[] var1 = new char[var0.length * 2];
		int var2 = 0;

		for (int var3 = 0; var2 < var0.length; ++var2) {
			var1[var3++] = HEX_CHARS[var0[var2] >>> 4 & 15];
			var1[var3++] = HEX_CHARS[var0[var2] & 15];
		}

		String var5 = new String(var1);
		byte[] var6 = var5.getBytes();

		for (int var4 = 0; var4 < var1.length; ++var4) {
			if (var1[var4] != var6[var4]) {
				return "nullhash";
			}
		}

		return var5;
	}

	public String asHex() {
		return asHex(this.Final());
	}

	public static String getHashOld(File var0) {
		try {
			return getHashOld(var0.toURI().toURL());
		} catch (MalformedURLException var2) {
			return "nullhash";
		}
	}

	public static String getHashOld(URL var0) {
		Object var1 = null;

		try {
			File var2 = FileUtils.urltofile(var0);
			if (!var2.exists()) {
				return "nullhash";
			} else {
				var0 = var2.toURI().toURL();
				byte[] var3 = FileUtils.buffer(var2.length());
				MD5InputStream var4 = new MD5InputStream(var0.openStream());

				while (var4.read(var3) != -1) {
					;
				}

				var4.close();
				var4.close();
				return var4.asHex();
			}
		} catch (IOException var6) {
			if (var1 != null) {
				try {
					((InputStream) var1).close();
				} catch (Exception var5) {
					;
				}
			}

			return "nullhash";
		}
	}

	public static String getHash(InputStream var0) {
		Object var1 = null;

		try {
			MD5InputStream var2 = new MD5InputStream(var0);
			byte[] var3 = new byte[4096];

			while (var2.read(var3) != -1) {
				;
			}

			var2.close();
			var2.close();
			return var2.asHex();
		} catch (IOException var5) {
			if (var1 != null) {
				try {
					((InputStream) var1).close();
				} catch (Exception var4) {
					;
				}
			}

			return "nullhash";
		}
	}

	public static String getHash(File var0) {
		return !var0.exists() ? "nullhash" : getHashOld(var0);
	}

	public static String getHash(URL var0) {
		return getHash(FileUtils.urltofile(var0));
	}


}
