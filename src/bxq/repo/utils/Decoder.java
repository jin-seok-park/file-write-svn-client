/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package bxq.repo.utils;

public abstract interface Decoder {
	public abstract Object decode(Object paramObject) throws DecoderException;
}