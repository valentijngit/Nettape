package net.nettape.connection;

import java.io.*;
import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import net.nettape.connection.*;

public class InFileDelta {
	   // Constants and variables.
	   // -----------------------------------------------------------------
		private Connection connection;
	   /** The strong checksum length. */
	   public static final int SUM_LENGTH = MD4.DIGEST_LENGTH;

	   public static final int CHUNK_SIZE = 32768;

	   /** Rdiff/rproxy default block length. */
	   public static final int RDIFF_BLOCK_LENGTH = 2048;

	   /** Rdiff/rproxy default sum length. */
	   public static final int RDIFF_STRONG_LENGTH = 8;

	   /** Rdiff/rproxy signature magic. */
	   public static final int SIG_MAGIC = 0x72730136;

	   /** Rdiff/rproxy delta magic. */
	   public static final int DELTA_MAGIC = 0x72730236;

	   public static final byte OP_END = 0x00;
	   public static final byte OP_CONTINUE = 0x01;

	   public static final byte OP_LITERAL_N1 = 0x41;
	   public static final byte OP_LITERAL_N2 = 0x42;
	   public static final byte OP_LITERAL_N4 = 0x43;
	   public static final byte OP_LITERAL_N8 = 0x44;

	   public static final byte OP_COPY_N4_N4 = 0x4f;

	   /** The `signature' command. */
	   public static final String SIGNATURE = "signature";

	   /** The `delta' command. */
	   public static final String DELTA = "delta";

	   /** The `patch' command. */
	   public static final String PATCH = "patch";

	   /** The program name printed to the console. */
	   public static final String PROGNAME = "rdiff";

	   public static final short CHAR_OFFSET = 31;

	   /** Whether or not to trace to System.err. */
	   protected static boolean verbose = false;

	   /**
	    * The length of blocks to checksum.
	    */
	   protected int blockLength;

	   /**
	    * The effective strong signature length.
	    */
	   protected int strongSumLength;


	   /**
	    * Generate and write the signatures.
	    */
	   public InFileDelta(Connection connection)
	   {
		   this.blockLength = RDIFF_BLOCK_LENGTH;
		   this.strongSumLength = RDIFF_STRONG_LENGTH;
		   this.connection = connection;
	   }
	   
	   public void makeSignatures(RandomAccessFile in, long length, final OutputStream out)
	      throws IOException, NoSuchAlgorithmException
	   {
	      Configuration c = new Configuration();
	      c.strongSum = MessageDigest.getInstance("MD4");
	      c.weakSum = new Checksum32(CHAR_OFFSET);
	      c.blockLength = blockLength;
	      c.strongSumLength = strongSumLength;
	      GeneratorStream gen = new GeneratorStream(c);
	      gen.addListener(new GeneratorListener() {
	         public void update(GeneratorEvent ev) throws ListenerException {
	            ChecksumPair pair = ev.getChecksumPair();
	            try {	            
	               out.write(OP_CONTINUE);
	               writeInt(pair.getWeak(), out);
	               out.write(pair.getStrong(), 0, strongSumLength);
	               out.flush();
	            } catch (IOException ioe) {
	               throw new ListenerException(ioe);
	            }
	         }
	         public void doFinal(GeneratorEvent ev) throws ListenerException {
	            ChecksumPair pair = ev.getChecksumPair();
	            try {	            
		               out.write(OP_CONTINUE);
		               writeInt(pair.getWeak(), out);
		               out.write(pair.getStrong(), 0, strongSumLength);
		               out.flush();
	            } catch (IOException ioe) {
	               throw new ListenerException(ioe);
	            }
		     }

	      });
	      writeInt(SIG_MAGIC, out);
	      writeInt(blockLength, out);
	      writeInt(strongSumLength, out);
	      int len = 0;
	      byte[] buf = new byte[CHUNK_SIZE];
	      while (len < length) {
	         try {
        		 in.read(buf);
        		 if(CHUNK_SIZE <= length - len)
	        		 gen.update(buf, 0, CHUNK_SIZE);
	        	 else
	        		 gen.update(buf,0,(int)(length - len));
	            len += CHUNK_SIZE;
	         } catch (ListenerException le) {
	            throw (IOException) le.getCause();
	         }
	      }
	      try {
	         gen.doFinal();
	      } catch (ListenerException le) {
	         throw (IOException) le.getCause();
	      }
	      out.write(OP_END);
          out.flush();
	   }
	   
	   public List readSignatures(InputStream in) throws IOException {
		      List sigs = new LinkedList();
		      int header = readInt(in);
		      if (header != SIG_MAGIC) {
		         throw new IOException("Bad signature header: 0x"
		            + Integer.toHexString(header));
		      }
		      long off = 0;
		      blockLength = readInt(in);
		      strongSumLength = readInt(in);

		      int weak;
		      byte[] strong = new byte[strongSumLength];
		      while ((in.read()) == OP_CONTINUE) {
		         try {
		            weak = readInt(in);
		            strong = connection.receiveData(in, strongSumLength);
		            sigs.add(new ChecksumPair(weak, strong, off));
		            off += blockLength;
		            
		            
		         } catch (Exception ex) {
		            break;
		         }
		      }
		      return sigs;
		   }
	   public void makeDeltas(List sums, InputStream in, final OutputStream out)
	      throws IOException, NoSuchAlgorithmException
	   {
	      Configuration c = new Configuration();
	      c.strongSum = MessageDigest.getInstance("MD4");
	      c.weakSum = new Checksum32(CHAR_OFFSET);
	      c.blockLength = blockLength;
	      c.strongSumLength = strongSumLength;
	      MatcherStream match = new MatcherStream(c);
	      match.setChecksums(sums);
	      writeInt(DELTA_MAGIC, out);
	      match.addListener(new MatcherListener() {
	         public void update(MatcherEvent me) throws ListenerException {
	            Delta d = me.getDelta();
	            try {
	               if (d instanceof Offsets) {
	                  writeCopy((Offsets) d, out);
	               } else if (d instanceof DataBlock) {
	                  writeLiteral((DataBlock) d, out);
	               }

	            } catch (IOException ioe) {
	               throw new ListenerException(ioe);
	            }
	         }
	         public void doFinal(MatcherEvent me) throws ListenerException {
		            Delta d = me.getDelta();
		            try {
		               if (d instanceof Offsets) {
		                  writeCopy((Offsets) d, out);
		               } else if (d instanceof DataBlock) {
		                  writeLiteral((DataBlock) d, out);
		               }


		            } catch (IOException ioe) {
		               throw new ListenerException(ioe);
		            }
		         }
	      });
	      int len = 0;
	      byte[] buf = new byte[CHUNK_SIZE];
	      while ((len = in.read(buf)) != -1) {
	         try {
	            match.update(buf, 0, len);
	         } catch (ListenerException le) {
	            throw (IOException) le.getCause();
	         }
	      }
	      try {
	            match.doFinal();
	         } catch (ListenerException le) {
	            throw (IOException) le.getCause();
	         }
	      out.write(OP_END);
          out.flush();
	   }

	   public boolean isDifferent(List sums, InputStream in)
			      throws IOException, NoSuchAlgorithmException
			   {
			      Configuration c = new Configuration();
			      c.strongSum = MessageDigest.getInstance("MD4");
			      c.weakSum = new Checksum32(CHAR_OFFSET);
			      c.blockLength = blockLength;
			      c.strongSumLength = strongSumLength;
			      MatcherStream match = new MatcherStream(c);
			      match.setChecksums(sums);
			      match.addListener(new MatcherListener() {
			         public void update(MatcherEvent me) throws ListenerException {
			            Delta d = me.getDelta();
			               if (d instanceof Offsets) {
			               } else if (d instanceof DataBlock) {
			               }

			             
			         }
			         public void doFinal(MatcherEvent me) throws ListenerException {
				            Delta d = me.getDelta();
				               if (d instanceof Offsets) {
				               } else if (d instanceof DataBlock) {
				               }
   				     }
			      });
			      int len = 0;
			      byte[] buf = new byte[CHUNK_SIZE];
			      while ((len = in.read(buf)) != -1) {
			         try {
			            match.update(buf, 0, len);
			         } catch (ListenerException le) {
			            throw (IOException) le.getCause();
			         }
			      }
			      try {
			            match.doFinal();
			         } catch (ListenerException le) {
			            throw (IOException) le.getCause();
			         }
			      return match.isDifferent;
			   }

	   
	   public long readDeltas(InputStream in, OutputStream out) throws Exception {
		      int header = readInt(in);
		      if (header != DELTA_MAGIC) {
		         throw new IOException("Bad delta header: 0x" +
		            Integer.toHexString(header));
		      }
		      int command;
		      long offset = 0;
		      byte[] buf;
		      long length = 0;
		      int len = 0;
		      int param_len;
		      while ((command = in.read()) != -1) {
		         switch (command) {
		            case OP_END:
		               out.write(command);
		               return length + 1;
		            case OP_LITERAL_N1:
		               len = (int) readInt(1, in);
		               buf = connection.receiveData(in, len);
		               param_len = writeLiteral(new DataBlock(offset, buf), out);
		               offset += buf.length;
		               length += 1 + buf.length + param_len;
		               break;
		            case OP_LITERAL_N2:
		               len = (int) readInt(2, in);
		               buf = connection.receiveData(in, len);
		               param_len = writeLiteral(new DataBlock(offset, buf), out);
		               offset += buf.length;
		               length += 1 + buf.length + param_len;
		               break;
		            case OP_LITERAL_N4:
		               len = (int) readInt(4, in);
		               buf = connection.receiveData(in, len);
		               param_len = writeLiteral(new DataBlock(offset, buf), out);
		               offset += buf.length;
		               length += 1 + buf.length + param_len;
		               break;
		            case OP_COPY_N4_N4:
		               int oldOff = (int) readInt(4, in);
		               int bs = (int) readInt(4, in);
		               writeCopy(new Offsets(oldOff, offset, bs), out);
		               offset += bs;
		               length += 9;
		               break;
		            default:
		               throw new IOException("Bad delta command: 0x" +
		                  Integer.toHexString(command));
		         }
		      }
		      throw new IOException("Didn't recieve RS_OP_END.");
		   }
	   
	   
	   public void rebuildFile(RandomAccessFile basis, RandomAccessFile deltas, final RandomAccessFile out, Long oldPosition)
	      throws IOException,Exception
	   {
	      
	      RebuilderStream rs = new RebuilderStream();
	      rs.setBasisFile(basis);
	      rs.addListener(new RebuilderListener() {
	         public void update(RebuilderEvent re) throws ListenerException {
	            try {
	               out.seek(re.getOffset());
	               out.write(re.getData());
	            } catch (IOException ioe) {
	               throw new ListenerException(ioe);
	            }
	         }
	      });
	      int command;
	      long offset = 0;
	      byte[] buf;
	      boolean end = false;
	      read:while ((command = deltas.read()) != -1) {
	         try {
	            switch (command) {
	               case OP_END:
	                  end = true;
	                  break read;
	               case OP_LITERAL_N1:
	                  buf = new byte[(int) readInt(1, deltas)];
	                  deltas.read(buf);
	                  rs.update(new DataBlock(offset, buf));
	                  offset += buf.length;
	                  break;
	               case OP_LITERAL_N2:
	                  buf = new byte[(int) readInt(2, deltas)];
	                  deltas.read(buf);
	                  rs.update(new DataBlock(offset, buf));
	                  offset += buf.length;
	                  break;
	               case OP_LITERAL_N4:
	                  buf = new byte[(int) readInt(4, deltas)];
	                  deltas.read(buf);
	                  rs.update(new DataBlock(offset, buf));
	                  offset += buf.length;
	                  break;
	               case OP_COPY_N4_N4:
	                  int oldOff = (int) readInt(4, deltas);
	                  int bs = (int) readInt(4, deltas);
	                  rs.update(new Offsets(oldOff + oldPosition, offset, bs));
	                  offset += bs;
	                  break;
	               default:
	                  throw new IOException("Bad delta command: 0x" +
	                     Integer.toHexString(command));
	            }
	         } catch (ListenerException le) {
	            throw (IOException) le.getCause();
	         }
	      }
	      if (!end)
	         throw new IOException("Didn't recieve RS_OP_END.");
	   }
	   
	   private static long readInt(int len, InputStream in) throws IOException {
	      long i = 0;
	      for (int j = len-1; j >= 0; j--) {
	         int k = in.read();
	         if (k == -1) throw new EOFException();
	         i |= (k&0xff) << 8*j;
	      }
	      return i;
	   }
	   private static long readInt(int len, RandomAccessFile in) throws IOException {
		      long i = 0;
		      for (int j = len-1; j >= 0; j--) {
		         int k = in.read();
		         if (k == -1) throw new EOFException();
		         i |= (k&0xff) << 8*j;
		      }
		      return i;
		   }
	   /**
	    * Read a four-byte big-endian integer from the InputStream.
	    *
	    * @param in The InputStream to read from.
	    * @return The integer read.
	    * @throws java.io.IOException if reading fails.
	    */
	   private static int readInt(InputStream in) throws IOException {
	      int i = 0;
	      for (int j = 3; j >= 0; j--) {
	         int k = in.read();
	         if (k == -1) throw new EOFException();
	         i |= (k&0xff) << 8*j;
	      }
	      return i;
	   }
	   
	   private static void
	   writeInt(long l, int len, OutputStream out) throws IOException {
	      for (int i = len-1; i >= 0; i--) {
	         out.write((int) (l >>> i*8) & 0xff);
	      }
	   }

	   /**
	    * Write a four-byte integer in big-endian byte order to
	    * <code>out</code>.
	    *
	    * @param i   The integer to write.
	    * @param out The OutputStream to write to.
	    * @throws java.io.IOException If writing fails.
	    */
	   private static void writeInt(int i, OutputStream out) throws IOException {
	      out.write((byte) ((i >>> 24) & 0xff));
	      out.write((byte) ((i >>> 16) & 0xff));
	      out.write((byte) ((i >>>  8) & 0xff));
	      out.write((byte) ( i & 0xff));
	   }
	   
	   private static void
	   writeCopy(Offsets off, OutputStream out) throws IOException {
	      out.write(OP_COPY_N4_N4);
	      writeInt(off.getOldOffset(), 4, out);
	      writeInt(off.getBlockLength(), out);
	   }

	   /**
	    * Write a "LITERAL" command to <code>out</code>.
	    *
	    * @param d   The {@link DataBlock} to write as a LITERAL command.
	    * @param out The OutputStream to write to.
	    * @throws java.io.IOException if writing fails.
	    */
	   private static int
	   writeLiteral(DataBlock d, OutputStream out) throws IOException {
	      byte cmd = 0;
	      int param_len;

	      switch (param_len = integerLength(d.getBlockLength())) {
	         case 1:
	            cmd = OP_LITERAL_N1;
	            break;
	         case 2:
	            cmd = OP_LITERAL_N2;
	            break;
	         case 4:
	            cmd = OP_LITERAL_N4;
	            break;
	      }

	      out.write(cmd);
	      writeInt(d.getBlockLength(), param_len, out);
	      byte[] byteArray = d.getData();
	      out.write(byteArray,0,byteArray.length);
	      out.flush();
	      return param_len;
	   }
	   
	   private static int integerLength(long l) {
		      if ((l & ~0xffL) == 0) {
		         return 1;
		      } else if ((l & ~0xffffL) == 0) {
		         return 2;
		      } else if ((l & ~0xffffffffL) == 0) {
		         return 4;
		      }
		      return 8;
		   }
}
