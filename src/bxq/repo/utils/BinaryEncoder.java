/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package bxq.repo.utils;

public abstract interface BinaryEncoder extends Encoder {
	public abstract byte[] encode(byte[] paramArrayOfByte) throws EncoderException;
}