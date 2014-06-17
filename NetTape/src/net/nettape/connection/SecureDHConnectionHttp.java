package net.nettape.connection;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;


import javax.crypto.CipherInputStream;

import javax.crypto.CipherOutputStream;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;


import com.sun.crypto.provider.SunJCE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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

import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterOutputStream;

import net.nettape.client.SmartFile;
import net.nettape.object.Constants.ConnectionType;


public class SecureDHConnectionHttp extends Connection {
	
    private Cipher outCipher = null;

    private Cipher inCipher = null;

    private CipherInputStream cis = null;

    private CipherOutputStream cos = null;
    
    private String algorithm = "BlowFish/CFB8/NoPadding";
    private String keyAlgorithm = "BlowFish";
    private boolean client = false;


    public SecureDHConnectionHttp(Socket s, URI uri, boolean serverConnection, ConnectionType connectionType)
	{
		this.s = s;
    	this.connectionType = connectionType;
    	this.uri = uri;
    	
		if(serverConnection)
			StartForServer();
		else
			StartForClient();

//		initializeCipher();
	}
	
	public void StartForClient()
	{
		try
		{
			// first do the http stuff
	    	this.outputStream = s.getOutputStream();
	    	this.inputStream = s.getInputStream();
			
	    
	        OutputStream chunkOutputStream = new Http11ChunkedOutputStream(this.outputStream);

	        sendHeadersClient(this.outputStream, uri.getHost(), uri.getPort(), uri.getPath());
	        PortUtil.writeProtocol(chunkOutputStream, 1);
	        chunkOutputStream.flush();

	        InputStream chunkInputStream = new Http11ChunkedInputStream(this.inputStream);

	        readAndCheckHeadersClient(this.inputStream);
	        int ver = PortUtil.readProtocol(chunkInputStream);
	        if (ver != 1)
	            throw new IOException("Version is other than 1: " + ver);

	        inputStream = chunkInputStream;
	        outputStream = chunkOutputStream;

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  
			
			// now the cipher stuff
			client = true;
			/*
			final BigInteger p = new BigInteger("f460d489678f7ec903293517e9193fd156c821b3e2b027c644eb96aedc85a54c971468cea07df15e9ecda0e2ca062161add38b9aa8aefcbd7ac18cd05a6bfb1147aaa516a6df694ee2cb5164607c618df7c65e75e274ff49632c34ce18da534ee32cfc42279e0f4c29101e89033130058d7f77744dddaca541094f19c394d485", 16);
	        final BigInteger g = new BigInteger("9ce2e29b2be0ebfd7b3c58cfb0ee4e9004e65367c069f358effaf2a8e334891d20ff158111f54b50244d682b720f964c4d6234079d480fcc2ce66e0fa3edeb642b0700cd62c4c02a483c92d2361e41a23706332bd3a8aaed07fe53bba376cefbce12fa46265ad5ea5210a3d96f5260f7b6f29588f61a4798e40bdc75bbb2b457", 16);
	        final int l = 1023;

	        final DHParameterSpec dhSpec = new DHParameterSpec(p, g, l);

			KeyPairGenerator clientKpairGen = KeyPairGenerator.getInstance("DH","BC");
			clientKpairGen.initialize(dhSpec);
	        KeyPair clientKpair = clientKpairGen.generateKeyPair();
	        KeyAgreement clientKeyAgree = KeyAgreement.getInstance("DH","BC");
	        clientKeyAgree.init(clientKpair.getPrivate());  
	        TransmitKey(clientKpair.getPublic());
	        PublicKey serverPublicKey = ReceiveKey();
	        clientKeyAgree.doPhase(serverPublicKey, true);
	        key = clientKeyAgree.generateSecret(keyAlgorithm);
	        */
        }
		catch(Exception ex)
		{

		}
	}
	
	public void StartForServer()
	{
		try
		{
			//First do the http stuff
	    	this.outputStream = s.getOutputStream();
	    	this.inputStream = s.getInputStream();
	  
	    	InputStream chunkInputStream = new Http11ChunkedInputStream(this.inputStream);

	        readAndCheckHeadersServer(this.inputStream);
	        int ver = PortUtil.readProtocol(chunkInputStream);
	        if (ver != 1)
	            throw new IOException("Version is other than 1: " + ver);

	        OutputStream chunkOutputStream = new Http11ChunkedOutputStream(this.outputStream);

	        sendHeadersServer(this.outputStream);
	        PortUtil.writeProtocol(chunkOutputStream, 1);
	        chunkOutputStream.flush();

	        inputStream = chunkInputStream;
	        outputStream = chunkOutputStream;
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  

			
			//And now the cipher stuff
	        /*
			final BigInteger p = new BigInteger("f460d489678f7ec903293517e9193fd156c821b3e2b027c644eb96aedc85a54c971468cea07df15e9ecda0e2ca062161add38b9aa8aefcbd7ac18cd05a6bfb1147aaa516a6df694ee2cb5164607c618df7c65e75e274ff49632c34ce18da534ee32cfc42279e0f4c29101e89033130058d7f77744dddaca541094f19c394d485", 16);
	        final BigInteger g = new BigInteger("9ce2e29b2be0ebfd7b3c58cfb0ee4e9004e65367c069f358effaf2a8e334891d20ff158111f54b50244d682b720f964c4d6234079d480fcc2ce66e0fa3edeb642b0700cd62c4c02a483c92d2361e41a23706332bd3a8aaed07fe53bba376cefbce12fa46265ad5ea5210a3d96f5260f7b6f29588f61a4798e40bdc75bbb2b457", 16);
	        final int l = 1023;
	
	        final DHParameterSpec dhSpec = new DHParameterSpec(p, g, l);
			KeyPairGenerator serverKpairGen = KeyPairGenerator.getInstance("DH","BC");
			serverKpairGen.initialize(dhSpec);
	        KeyPair serverKpair = serverKpairGen.generateKeyPair();
	        KeyAgreement serverKeyAgree = KeyAgreement.getInstance("DH","BC");
	        serverKeyAgree.init(serverKpair.getPrivate());  
	        PublicKey clientPublicKey = ReceiveKey();
	        TransmitKey(serverKpair.getPublic());
	        serverKeyAgree.doPhase(clientPublicKey, true);
	        key = serverKeyAgree.generateSecret(keyAlgorithm);
	        */
        }
		catch(Exception ex)
		{
			
		}		
	}
	
	public void TransmitKey(PublicKey key)
	{
		try
		{
			byte[] byteArray = key.getEncoded();
			OutputStream os = this.outputStream;
			writeNormalInt(byteArray.length,os);
			os.flush();
			os.write(byteArray);
			os.flush();

		}
		catch(Exception ex)
		{
			
		}
	}
	
	public PublicKey ReceiveKey()
	{
		try
		{
			InputStream is = this.inputStream;
	        int availableBytes = readNormalInt(is);
	        byte[] byteArray = new byte[availableBytes];
			DataInputStream buffin = new DataInputStream(this.inputStream);
	        buffin.readFully(byteArray);
	        KeyFactory bobKeyFac = KeyFactory.getInstance("DH","BC");
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(byteArray);
	        PublicKey key = bobKeyFac.generatePublic(x509KeySpec);
	        return key;
		}
		catch(Exception ex)
		{
			return null;
		}

	}
	
    private void initializeCipher() {

        try {

             outCipher = Cipher.getInstance(algorithm, "BC");
             String initialVector = "azertyui";
             IvParameterSpec ivs = new IvParameterSpec(initialVector.getBytes());
             outCipher.init(Cipher.ENCRYPT_MODE, key, ivs);
             inCipher = Cipher.getInstance(algorithm, "BC");
             inCipher.init(Cipher.DECRYPT_MODE, key, ivs);

         }

         catch (NoSuchAlgorithmException e) {

             e.printStackTrace();

         }
         
         catch (InvalidAlgorithmParameterException e) {

             e.printStackTrace();

         }

         catch (NoSuchPaddingException e) {

             e.printStackTrace();

         }

         catch (InvalidKeyException e) {

             e.printStackTrace();

         }
         catch (NoSuchProviderException e)
         {
        	 e.printStackTrace();
         }

         catch(Exception ex)
         {
        	 int test= 0;
         }
    }

    public InputStream getInputStream() throws IOException {
    	/*
        cis = new CipherInputStream(this.inputStream, inCipher);
        return cis;
        */
    	return this.inputStream;
    }
    
    public OutputStream getOutputStream() throws IOException {
    	/*
        cos = new CipherOutputStream(this.outputStream, outCipher);
        return cos;
        */
    	return this.outputStream;
    }
    
    public ObjectInputStream getObjectInputStream() throws IOException {
    	if(objectInputStream == null)
    		objectInputStream = new ObjectInputStream(getInputStream());
    	return objectInputStream;

    }
    public ObjectOutputStream getObjectOutputStream() throws IOException {
    	if(objectOutputStream == null)
    		objectOutputStream = new ObjectOutputStream(getOutputStream());
    	return objectOutputStream;

    }  
    public DataInputStream getDataInputStream() throws IOException {
    	return new DataInputStream(getInputStream());
    }
    public DataOutputStream getDataOutputStream() throws IOException {
    	return new DataOutputStream(getOutputStream());
    } 
    public void sendDataInt(int i) throws IOException
    {
    	DataOutputStream dos = getDataOutputStream();
    	dos.writeInt(i);
    	dos.flush();
    }
    public int receiveDataInt() throws IOException
    {
    	return getDataInputStream().readInt();
    }
    public void sendObject(Object object) throws Exception, IOException
	{
		ObjectOutputStream out = getObjectOutputStream();
		out.writeObject(object);
		out.flush();
	}
	public Object receiveObject() throws Exception, IOException
	{
		return getObjectInputStream().readObject();
	}
	public void sendInt(int i) throws Exception, IOException
	{
		ObjectOutputStream out = getObjectOutputStream();
		out.writeInt(i);
		out.flush();
	}
	public int receiveInt() throws Exception, IOException
	{
		return getObjectInputStream().readInt();
	}
	public void sendLong(long l) throws Exception, IOException
	{
		ObjectOutputStream out = getObjectOutputStream();
		out.writeLong(l);
		out.flush();
	}
	public long receiveLong() throws Exception, IOException
	{
		return getObjectInputStream().readLong();
	}
	
	
	public void sendFileOnClient(SmartFile smartFile) throws Exception, IOException
	{
	    byte b[]=new byte[2048];
	    InputStream is = smartFile.GetInputStream();
	    int numRead=0;
	    OutputStream out = getOutputStream();
	    while ( ( numRead=is.read(b)) > 0) {
	      sendInt(numRead);
	      out.flush();
	      out.write(b, 0, numRead);
	      out.flush();
	    }
	    sendInt(0);
	    out.flush();
	    is.close();
	}
	
	
	
	public void sendFileOnServer(RandomAccessFile ras, long length) throws IOException, Exception
	{
		byte buffer[]=new byte[2048];
	    int count=0;
	    OutputStream out = getOutputStream();
	    sendDataInt((int)length);
	    // possible problem: this keeps sending data and does not end after length has been sent (ras)
	    while ( ( count=ras.read(buffer)) > 0) {
	      out.write(buffer, 0, count);
	    }
	    out.flush();
	    
		/*
	    sendLong(length);
	    OutputStream out = (OutputStream) getOutputStream();
	    readWithStream(ras,out,length,1024);
	    out.flush();*/	
	}
	
	public boolean isConnected()
	{
		if(this.s.isClosed())
		{
			return false;
		}
		else
		{
			try
			{
				for(int i=0; i<10; i++)
				{
					SendCommand(Command.NOOP);
				}
				return true;
			}
			catch(Exception ex)
			{
				return false;
			}
		}
	}

	
	private void readWithStream(RandomAccessFile ras, OutputStream os, long len, int buf_size) throws 
	    java.io.FileNotFoundException, 
	    java.io.IOException {
	
		byte[] buffer = new byte[buf_size];
		
		int       len_read=0;
		int total_len_read=0;
		
		while ( total_len_read + buf_size <= len) {
		  len_read = ras.read(buffer);
		  total_len_read += len_read;
		  os.write(buffer, 0, len_read);
		}
	
		if (total_len_read < len) {
		  readWithStream(ras, os, len-total_len_read, buf_size/2);
		}
	}
	
	public long receiveFileOnClient(SmartFile smartFile) throws Exception, IOException
	{
	//problem could be here that only initial length is sent to client, while it should be of each buffer
	// the last read could be for 0 bytes, but it would be a read that gets nothing in response and hangs there (although docs say it shouldnt)
	// so change to the way receiveFileOnServer works (and change SendOnServer appropriately as well)
		byte[] buffer = new byte[2048];
		InputStream in = (InputStream)getInputStream();
		OutputStream os = smartFile.GetOutputStream();
		int len = receiveDataInt();
		int count;
		while((count = in.read(buffer, 0, Math.min(buffer.length, len))) > 0)
		{
			os.write(buffer, 0, count);
			len -= count;
		}
		return 0;
		/*
		long len = receiveLong();
		OutputStream os = smartFile.GetOutputStream();
	    readWithStream((InputStream)getInputStream(), os, len, 1024);
	    os.close();
	    return len;
	    */
	}
	
	public void receiveFileOnServer(FileOutputStream fos) throws Exception, IOException
	{
		byte[] b = new byte[2048];
		int len = receiveInt();
		InputStream in = (InputStream)getInputStream();
		while(len > 0)
		{
			in.read(b,0,len);
			fos.write(b, 0, len);
			len = receiveInt();
		}
	}
	
	public byte[] receiveData(InputStream ins, int an_int) throws 
	    java.io.IOException,  
	    Exception{
	
	    byte[] ret = new byte[an_int];
	
	    int offset  = 0;
	    int numRead = 0;
	    int outstanding = an_int;
	
	    while (
	       (offset < an_int)
	         &&
	      (  (numRead = ins.read(ret, offset, outstanding)) > 0 )
	    ) {
	      offset     += numRead;
	      outstanding = an_int - offset;
	    }
	    if (offset < ret.length) {
	      throw new Exception("Could not completely read from stream, numRead="+numRead+", ret.length="+ret.length); // ???
	    }
	    return ret;
	  }
	
	private void readWithStream(InputStream ins, OutputStream os, long len, int buf_size) throws 
	    java.io.FileNotFoundException, 
	    java.io.IOException {
	
		byte[] buffer = new byte[buf_size];
		
		int       len_read=0;
		int total_len_read=0;
		
		while ( total_len_read + buf_size <= len) {
		  len_read = ins.read(buffer);
		  total_len_read += len_read;
		  os.write(buffer, 0, len_read);
		}
		
		if (total_len_read < len) {
		  readWithStream(ins, os, len-total_len_read, buf_size/2);
		}
	}
	
    private int readNormalInt(InputStream in) throws IOException {
        int i = 0;
        for (int j = 3; j >= 0; j--) {
        	
           int k = in.read();
           if (k == -1) throw new EOFException();
           i |= (k&0xff) << 8*j;
        }
        return i;
     }
    private void writeNormalInt(int i, OutputStream out) throws IOException {

       out.write((byte) ((i >>> 24) & 0xff));

       out.write((byte) ((i >>> 16) & 0xff));

       out.write((byte) ((i >>>  8) & 0xff));

       out.write((byte) ( i & 0xff));
    }
    
    public void sendString(String s) throws Exception {
    	ObjectOutputStream out = getObjectOutputStream();
        int len = s.length();
        sendInt(len);
        for (int i=0;i<len;i++) {
          out.write((byte) s.charAt(i));
        }
        out.flush();
    }
    public String receiveString() throws Exception {
    	ObjectInputStream in = getObjectInputStream();
    	int len = receiveInt();
        String ret=new String();
        for (int i=0; i<len;i++) {
          ret+=(char) in.read();
        }
        return ret;
    }
    public InputStream getOutInputStream() throws IOException {
    	return null;
    }

    public OutputStream getInOutputStream() throws IOException {
    	return null;
    }
    
    private void sendHeadersClient(OutputStream out, String host, int port, String path) throws Exception {
        out.write(("POST " + path + " HTTP/1.1\r\n").getBytes());
        out.write("User-Agent: FullDuplexHttp\r\n".getBytes());
        out.write(("Host: " + host + ":" + port + "\r\n").getBytes());
        out.write("Transfer-Encoding: chunked\r\n".getBytes());
        out.write("\r\n".getBytes());
    }

    private void readAndCheckHeadersClient(InputStream in) throws Exception {
        String line = PortUtil.readHttpHeader(in);

        if (!line.equalsIgnoreCase("HTTP/1.1 200 OK"))
            throw new IOException("Response is not HTTP 200: " + line);

        boolean chunkedEncodingFound = false;

        line = PortUtil.readHttpHeader(in);
        while (line.length() > 0) {
            if (!chunkedEncodingFound) {
                line = line.toLowerCase();
                chunkedEncodingFound = line.startsWith("transfer-encoding")
                        && line.indexOf("chunked") > -1;
            }

            line = PortUtil.readHttpHeader(in);
        }

        if (!chunkedEncodingFound)
            throw new IOException("Missing response header: (Transfer-Encoding: chunked)");
    }
    
    private void sendHeadersServer(OutputStream out) throws Exception {
        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Transfer-Encoding: chunked\r\n".getBytes());
        out.write("\r\n".getBytes());
    }

    private void readAndCheckHeadersServer(InputStream in) throws Exception {
        String line = PortUtil.readHttpHeader(in);

        if (!line.startsWith("POST") || !line.endsWith("HTTP/1.1"))
            throw new IOException("Not a HTTP/1.1 POST: " + line);

        boolean chunkedEncodingFound = false;

        line = PortUtil.readHttpHeader(in);
        while (line.length() > 0) {
            if (!chunkedEncodingFound) {
                line = line.toLowerCase();
                chunkedEncodingFound = line.startsWith("transfer-encoding")
                        && line.indexOf("chunked") > -1;
            }

            line = PortUtil.readHttpHeader(in);
        }

        if (!chunkedEncodingFound)
            throw new IOException("Missing request header: (Transfer-Encoding: chunked)");
    }

}
