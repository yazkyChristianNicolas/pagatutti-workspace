package ar.com.pagatutti.encriptor;

public abstract class BaseEncryptor {
	
	public static final String ENCRYPTED_PREFIX = "Enc:";
	
	public static final String CHARSET_ISO = "ISO-8859-1";
	
	protected static final String SALT = "Wi1E858nVt5gRrtV";
	
	protected static final String KEY = "wDb97zyUYwrfbUok";
	
	protected static final String SEED = "o1cd6F0uayqEVa8b";

	public  abstract  String encrypt(String value) throws Exception;

	public abstract String decryptPhrase(String value) throws Exception;

	public abstract Boolean matches(String password, String encryptedString) throws Exception;
}
