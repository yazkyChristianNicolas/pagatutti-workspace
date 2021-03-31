package ar.com.pagatutti.encriptor;


import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class EncryptorAES256 extends BaseEncryptor{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseEncryptor.class);
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

	@Override
	public String encrypt(String value) throws Exception{
        try
        {
        	IvParameterSpec iv = new IvParameterSpec(SEED.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
             
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } 
        catch (Exception e) 
        {
			logger.error("EncriptionException: ", e);
            throw e;
        }
	}

	@Override
	public String decryptPhrase(String value) throws Exception{
		try {
			
			value = (value.startsWith(ENCRYPTED_PREFIX))? value.replace(ENCRYPTED_PREFIX, ""):value;
			
            IvParameterSpec iv = new IvParameterSpec(SEED.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
           
            return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
        } catch (Exception e) {
        	logger.error("EncriptionException: ", e);
			throw e;
        }
	}

	@Override
	public Boolean matches(String password, String encryptedString) throws Exception{
		try {
			String textDecrypted = this.decryptPhrase(encryptedString);
			return password.equals(textDecrypted);
		} catch (Exception e) {
			logger.error("EncriptionException: ", e);
			throw e;
		}
	}

}
