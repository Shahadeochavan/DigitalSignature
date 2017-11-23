package com.nextech.dscrm.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;


public class EncryptDecrypt {

	private Cipher encryptCipher;
	private Cipher decryptCipher;
	private sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	public EncryptDecrypt() throws SecurityException{

		java.security.Security.addProvider(new com.sun.crypto.provider.SunJCE());

		//char[] pass = "CHANGE THIS TO A BUNCH OF RANDOM CHARACTERS".toCharArray();
		char[] pass = "A1QW6ER8TVBHJSD9GH1YU5IOPMA0KL4ZXC3".toCharArray();

		byte[] salt = {(byte) 0xa3, (byte) 0x21, (byte) 0x24, (byte) 0x2c,
						(byte) 0xf2, (byte) 0xd2, (byte) 0x3e, (byte) 0x19 };

		int iterations = 3;
		init(pass, salt, iterations);
	}

	public void init(char[] pass, byte[] salt, int iterations)throws SecurityException{
		try{
			PBEParameterSpec ps = new javax.crypto.spec.PBEParameterSpec(salt,20);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(pass));
			encryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, k, ps);
			decryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, k, ps);

		}catch (Exception e){
			throw new SecurityException("Could not initialize CryptoLibrary: "+ e.getMessage());
		}
	}

	/**
	 * 
	 * convenience method for encrypting a string.
	 *  
	 * @param str
	 *            Description of the Parameter
	 * 
	 * @return String the encrypted string.
	 * 
	 * @exception SecurityException
	 *                Description of the Exception
	 * 
	 */

	public synchronized String encrypt(String str) throws SecurityException
	{

		try{

			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = encryptCipher.doFinal(utf8);
			return encoder.encode(enc);

		}catch (Exception e){
			throw new SecurityException("Could not encrypt: " + e.getMessage());
		}
	}

	/**
	 * 
	 * convenience method for encrypting a string.
	 * 
	 * @param str
	 *            Description of the Parameter
	 * 
	 * @return String the encrypted string.
	 * 
	 * @exception SecurityException
	 *                Description of the Exception
	 * 
	 */

	public synchronized String decrypt(String str)throws SecurityException
	{
		try{

			byte[] dec = decoder.decodeBuffer(str);
			byte[] utf8 = decryptCipher.doFinal(dec);

			return new String(utf8, "UTF8");
		}catch (Exception e){
			throw new SecurityException("Could not decrypt: " + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		try
		{
			if(args.length != 1){
				System.out.print("Uses : EncryptDecrypt <enc|dec>");
				return ;
			}
			else if(!("enc".equalsIgnoreCase(args[0])|| "dec".equalsIgnoreCase(args[0]))){
				System.out.print("Uses : EncryptDecrypt <enc|dec>");
				return ;
			}
			
			EncryptDecrypt cl = new EncryptDecrypt();
			String user = "cryptotest";
			String pass = "blackmagic";
			String euser = cl.encrypt(user);
			String epass = cl.encrypt(pass);
			String duser = cl.decrypt(euser);
			String dpass = cl.decrypt(epass);
			System.out.println("User: " + user + " --> " + euser + " --> "+ duser);
			System.out.println("Pass: " + pass + " --> " + epass + " --> "+ dpass);
			ResourceBundle rb = ResourceBundle.getBundle("stagingExpress_flag");
			Enumeration enu = rb.getKeys();
			if(args[0].equalsIgnoreCase("enc")){
				while(enu.hasMoreElements()){
				String tmp = null ;
				String key = (String) enu.nextElement();
				if(key.lastIndexOf(".") != -1)
					tmp = key.substring(key.lastIndexOf(".")+1);
				else
					tmp = key ;
				
				String val = rb.getString(key);
				String enc =  cl.encrypt(tmp+val);
				System.out.println(key+"="+enc);	
				}
			}
			if(args[0].equalsIgnoreCase("dec")){
			while(enu.hasMoreElements()){
				String tmp = null ;
				String key = (String) enu.nextElement();
				if(key.lastIndexOf(".") != -1)
					tmp = key.substring(key.lastIndexOf(".")+1);
				else
					tmp = key ;
				
				String val = rb.getString(key);
				String enc =  cl.decrypt(val);
				enc = enc.substring(tmp.length());
				System.out.println(key+"="+enc);	
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		

		
		
		
	}
}
