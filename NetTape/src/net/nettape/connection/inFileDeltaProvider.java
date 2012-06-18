package net.nettape.connection;



/**
 * This provider implements the MD4 message digest, and is provided to
 * ensure that MD4 is available.
 */
public final class inFileDeltaProvider extends java.security.Provider {
   public inFileDeltaProvider() {
      super("JARSYNC", 1.0,
            "Jarsync provider; implementing MD4, BrokenMD4");

      put("MessageDigest.MD4",       "net.nettape.connection.MD4");

   }
}
