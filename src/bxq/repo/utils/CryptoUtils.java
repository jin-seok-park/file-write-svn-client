package bxq.repo.utils;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
	
	private static final CryptoUtils instance = new CryptoUtils();

	private CryptoUtils() {
	}

	public static CryptoUtils instance() {
		return instance;
	}
	
	public interface ALGORITHM_NAME {
		String MD5 = "MD5";
		String AES = "AES";
		String SHA_256 = "SHA-256";
		String AESWrap = "AESWrap";
	}

	public interface ALGORITHM_MODE {
		String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5PADDING";
		String AES_CBC_ISO10126Padding = "AES/CBC/ISO10126Padding";
	}

	private static final String INITIALIZATION_VECTOR = "e511a66d84d977d2";
	private static final String ENCRYPTION_KEY = "2f88ba962b490935";
	
	private String charset = Charset.defaultCharset().name();
	
	public String encrypt(String plainText) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE.AES_CBC_PKCS5PADDING);
		SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(charset), ALGORITHM_NAME.AES);
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(charset)));
		byte[] raw = cipher.doFinal(plainText.getBytes(charset));
		return new String(Base64.encodeBase64(raw), charset);
	}

	public String decrypt(String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE.AES_CBC_PKCS5PADDING);
		SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(charset), ALGORITHM_NAME.AES);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(charset)));

		byte[] raw = Base64.decodeBase64(encrypted);

		return new String(cipher.doFinal(raw), charset);
	}

}
