package com.flashvisions.server.rtmp.transcoder.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SessionUtil {

	public static String generateSessionSignature(String source, String template)
	{
		String content = source + template;
		MessageDigest md = null;
		byte[] thedigest = null;
		String hash = null;
		
		try
		{
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(content.getBytes());
			thedigest = md.digest();
			BigInteger bigInt = new BigInteger(1,thedigest);
			hash = bigInt.toString(16);
			while(hash.length() < 32 ){
			hash = "0"+hash;
			}
		}
		catch(Exception e)
		{
			// TO DO
		}
		finally
		{
			md = null;
			thedigest = null;
		}
		
		return hash;
	}
}
