package ru.invhacks.updater.hash;

class MD5State {
	int[] state;
	long count;
	byte[] buffer;
	final MD5Hasher this$0;

	public MD5State(MD5Hasher var1) {
		this.this$0 = var1;
		this.buffer = new byte[64];
		this.count = 0L;
		this.state = new int[4];
		this.state[0] = 1732584193;
		this.state[1] = -271733879;
		this.state[2] = -1732584194;
		this.state[3] = 271733878;
	}

	public MD5State(MD5Hasher var1, MD5State var2) {
		this(var1);

		int var3;
		for (var3 = 0; var3 < this.buffer.length; ++var3) {
			this.buffer[var3] = var2.buffer[var3];
		}

		for (var3 = 0; var3 < this.state.length; ++var3) {
			this.state[var3] = var2.state[var3];
		}

		this.count = var2.count;
	}
}
