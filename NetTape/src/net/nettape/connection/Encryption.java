package net.nettape.connection;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import javax.crypto.CipherInputStream;

import javax.crypto.CipherOutputStream;

import javax.crypto.NoSuchPaddingException;


import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.RandomAccessFile;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;

import java.security.*;
import java.util.zip.GZIPInputStream;

import net.nettape.object.Constants.ConnectionType;

public class Encryption {
	
	private SecretKey key;
	
    private Cipher outCipher = null;

    private Cipher inCipher = null;

    private CipherInputStream cis = null;

    private CipherOutputStream cos = null;
    
    private String algorithm = "DES";
    
	public Encryption(String password) throws Exception
	{
		GenerateKey(password);
    }
	
	private void GenerateKey(String password) throws Exception 
	{
	    PBEKeySpec pbeKeySpec;
	    PBEParameterSpec pbeParamSpec;
	    SecretKeyFactory keyFac;

	    // Salt
	    byte[] salt = {
	        (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
	        (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
	    };

	    // Iteration count
	    int count = 20;

	    // Create PBE parameter set
	    pbeParamSpec = new PBEParameterSpec(salt, count);


	    pbeKeySpec = new PBEKeySpec(password.toCharArray());
	    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	    key = keyFac.generateSecret(pbeKeySpec);
	    algorithm = key.getAlgorithm();
		initializeCipher(pbeParamSpec);
	}
	
    private void initializeCipher(PBEParameterSpec pbeParamSpec)  throws Exception
    {

        try {

             outCipher = Cipher.getInstance(algorithm);

             outCipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec);

             inCipher = Cipher.getInstance(algorithm);

             inCipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec);

         }

         catch (NoSuchAlgorithmException e) {

             e.printStackTrace();

         }

         catch (NoSuchPaddingException e) {

             e.printStackTrace();

         }

         catch (InvalidKeyException e) {

             e.printStackTrace();

         }


    }

    public InputStream DecryptedInputStream(InputStream inputStream) throws IOException {

        cis = new CipherInputStream(inputStream, inCipher);

        return cis;

    }
   
    public OutputStream EncryptedOutputStream(OutputStream outputStream) throws IOException {

        cos = new CipherOutputStream(outputStream, outCipher);

        return cos;

    }
 }
