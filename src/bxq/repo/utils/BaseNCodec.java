/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package bxq.repo.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
	static final int EOF = -1;
	public static final int MIME_CHUNK_SIZE = 76;
	public static final int PEM_CHUNK_SIZE = 64;
	// private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
	// private static final int DEFAULT_BUFFER_SIZE = 8192;
	protected static final int MASK_8BITS = 255;
	protected static final byte PAD_DEFAULT = 61;
	protected final byte PAD = 61;
	private final int unencodedBlockSize;
	private final int encodedBlockSize;
	protected final int lineLength;
	private final int chunkSeparatorLength;

	protected BaseNCodec(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength) {
		this.unencodedBlockSize = unencodedBlockSize;
		this.encodedBlockSize = encodedBlockSize;
		boolean useChunking = (lineLength > 0) && (chunkSeparatorLength > 0);
		this.lineLength = ((useChunking) ? lineLength / encodedBlockSize * encodedBlockSize : 0);
		this.chunkSeparatorLength = chunkSeparatorLength;
	}

	boolean hasData(Context context) {
		return (context.buffer != null);
	}

	int available(Context context) {
		return ((context.buffer != null) ? context.pos - context.readPos : 0);
	}

	protected int getDefaultBufferSize() {
		return 8192;
	}

	private byte[] resizeBuffer(Context context) {
		if (context.buffer == null) {
			context.buffer = new byte[getDefaultBufferSize()];
			context.pos = 0;
			context.readPos = 0;
		} else {
			byte[] b = new byte[context.buffer.length * 2];
			System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
			context.buffer = b;
		}
		return context.buffer;
	}

	protected byte[] ensureBufferSize(int size, Context context) {
		if ((context.buffer == null) || (context.buffer.length < context.pos + size)) {
			return resizeBuffer(context);
		}
		return context.buffer;
	}

	int readResults(byte[] b, int bPos, int bAvail, Context context) {
		if (context.buffer != null) {
			int len = Math.min(available(context), bAvail);
			System.arraycopy(context.buffer, context.readPos, b, bPos, len);
			context.readPos += len;
			if (context.readPos >= context.pos) {
				context.buffer = null;
			}
			return len;
		}
		return ((context.eof) ? -1 : 0);
	}

	protected static boolean isWhiteSpace(byte byteToCheck) {
		switch (byteToCheck) {
		case 9:
		case 10:
		case 13:
		case 32:
			return true;
		}
		return false;
	}

	public Object encode(Object obj) throws EncoderException {
		if (!(obj instanceof byte[])) {
			throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
		}
		return encode((byte[]) (byte[]) obj);
	}

	public String encodeToString(byte[] pArray) {
		return newStringUtf8(encode(pArray));
	}

	public String encodeAsString(byte[] pArray) {
		return newStringUtf8(encode(pArray));
	}

	public Object decode(Object obj) throws DecoderException {
		if (obj instanceof byte[])
			return decode((byte[]) (byte[]) obj);
		if (obj instanceof String) {
			return decode((String) obj);
		}
		throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
	}

	public byte[] decode(String pArray) {
		return decode(getBytesUtf8(pArray));
	}

	public byte[] decode(byte[] pArray) {
		if ((pArray == null) || (pArray.length == 0)) {
			return pArray;
		}
		Context context = new Context();
		decode(pArray, 0, pArray.length, context);
		decode(pArray, 0, -1, context);
		byte[] result = new byte[context.pos];
		readResults(result, 0, result.length, context);
		return result;
	}

	public byte[] encode(byte[] pArray) {
		if ((pArray == null) || (pArray.length == 0)) {
			return pArray;
		}
		Context context = new Context();
		encode(pArray, 0, pArray.length, context);
		encode(pArray, 0, -1, context);
		byte[] buf = new byte[context.pos - context.readPos];
		readResults(buf, 0, buf.length, context);
		return buf;
	}

	abstract void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Context paramContext);

	abstract void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Context paramContext);

	protected abstract boolean isInAlphabet(byte paramByte);

	public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad) {
		for (int i = 0; i < arrayOctet.length; ++i) {
			if ((!(isInAlphabet(arrayOctet[i])))
					&& (((!(allowWSPad)) || ((arrayOctet[i] != 61) && (!(isWhiteSpace(arrayOctet[i]))))))) {
				return false;
			}
		}
		return true;
	}

	public boolean isInAlphabet(String basen) {
		return isInAlphabet(getBytesUtf8(basen), true);
	}

	protected boolean containsAlphabetOrPad(byte[] arrayOctet) {
		if (arrayOctet == null) {
			return false;
		}
		for (byte element : arrayOctet) {
			if ((61 == element) || (isInAlphabet(element))) {
				return true;
			}
		}
		return false;
	}

	public long getEncodedLength(byte[] pArray) {
		long len = (pArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * this.encodedBlockSize;
		if (this.lineLength > 0) {
			len += (len + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
		}
		return len;
	}

	public static String newStringUtf8(byte[] bytes) {
		return newString(bytes, Charsets.UTF_8);
	}

	public static byte[] getBytesUtf8(String string) {
		return getBytes(string, Charsets.UTF_8);
	}

	private static byte[] getBytes(String string, Charset charset) {
		if (string == null) {
			return null;
		}
		return string.getBytes(charset);
	}

	private static String newString(byte[] bytes, Charset charset) {
		return new String(bytes, charset);
	}

	static class Context {
		int ibitWorkArea;
		long lbitWorkArea;
		byte[] buffer;
		int pos;
		int readPos;
		boolean eof;
		int currentLinePos;
		int modulus;

		public String toString() {
			StringBuilder builder = new StringBuilder().append(super.getClass().getSimpleName());
			builder.append("[buffer=").append(Arrays.toString(this.buffer));
			builder.append(", currentLinePos=").append(Integer.valueOf(this.currentLinePos));
			builder.append(", eof=").append(Boolean.valueOf(this.eof));
			builder.append(", ibitWorkArea=").append(Integer.valueOf(this.ibitWorkArea));
			builder.append(", lbitWorkArea=").append(Long.valueOf(this.lbitWorkArea));
			builder.append(", modulus=").append(Integer.valueOf(this.modulus));
			builder.append(", pos=").append(Integer.valueOf(this.pos));
			builder.append(", readPos=").append(Integer.valueOf(this.readPos));
			builder.append(']');
			return builder.toString();
		}
	}
}