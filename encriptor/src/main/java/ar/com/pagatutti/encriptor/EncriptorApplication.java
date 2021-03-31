package ar.com.pagatutti.encriptor;

public class EncriptorApplication {
	
	public static final String encrypt = "encrypt";
	
	public static final String decrypt = "decrypt";

	public static void main(String[] args) {
		
		if(args.length > 0) {
	    	String method = args[0]; 
	    	String phrase = args[1];
	    	EncryptorAES256 encriptor = new EncryptorAES256();
	    	if(encrypt.equalsIgnoreCase(method)) {
	    		try {
	    			System.out.println(encriptor.encrypt(phrase));
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    	}
	   
	    	if(decrypt.equalsIgnoreCase(method)) {
	    		try {
	    			System.out.println(encriptor.decryptPhrase(phrase));
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    	}
	    }
	}

}
