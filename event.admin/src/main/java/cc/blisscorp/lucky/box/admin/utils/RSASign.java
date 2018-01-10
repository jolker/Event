package cc.blisscorp.lucky.box.admin.utils;
import ga.log4j.GA;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class RSASign {

	private static final String algoRSA = "RSA";
	private static final String algoSHA1withRSA = "SHA1withRSA";

	private static PublicKey pubKey;
	private static PrivateKey priKey;

	private static String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAM8NhAxjeNZ8hWlhLHPeWRdmTZHxEEzRFYy4J\n"
			+ "qVyxV9XvnUhvyXuCES719rv3cjZP4zy0U2emSQRtlag6x3vwIPsFAGIxJYfqs1IwL79ILYUgakTi\n"
			+ "DBPeNtocjFD/kRyiXDEnTVFytfs3oCaZh2f2DUwOBwsTIX48Th5r/tZA1wIDAQAB";
	private static String pri = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIAzw2EDGN41nyFaWEsc95ZF2ZNk\n"
			+ "fEQTNEVjLgmpXLFX1e+dSG/Je4IRLvX2u/dyNk/jPLRTZ6ZJBG2VqDrHe/Ag+wUAYjElh+qzUjAv\n"
			+ "v0gthSBqROIME9422hyMUP+RHKJcMSdNUXK1+zegJpmHZ/YNTA4HCxMhfjxOHmv+1kDXAgMBAAEC\n"
			+ "gYBSUOpCgBqXkxZ58+h9Ujwm4PKzEPI5Q5tsciI9UeGSfTOAhxzbqiMDgMUGaUaHu23kHaP8pREI\n"
			+ "VBnjwxQhqbSJrfNVlBAEVCJOfVx8l3fFAOGylU0XDeoGsKlxSKf/HopSt4kOvlkY56sHRelzyCPs\n"
			+ "hxMf0jazyxt8NbqQ5kIzAQJBANo7jqrUDgm83Pl1ZPZwPSBZOhfXY1aOKI4wf9OmEYD1TlwSJSyf\n"
			+ "1qv0sSycEqCgLpPDB4LwSzaHn0CzrxZav7sCQQCWY42mgblfseyiC42VqAgtDsZBTFMhwYtyP+QO\n"
			+ "wXcTZ9sYSWjfPBP0lkzrKmUdiPewDABA/68u0AXg5F7fzOuVAkEAzZegL0V5OClKEGVNSXGx1IDa\n"
			+ "3s0Wk9t3T4VXV2AzS0dOnCSoCuNGTVdHdtxOHC7r0l7A+9U/Nk8Mc9sTjr7NswJATa0Z6tiyIKVL\n"
			+ "rd79wEDMCEnLSMrdXWAUYGxzpNN0Z4eK+J5iFnt3ayt+izCYjYrDNCDeuLPXjfxQLPqHrlyviQJA\n"
			+ "UGMgSLhciglss+7Bcc2wNnQ3P+hPMTTQBunp+IsBpTWnDPax0/BhIq/GyBC25f2PqxUP7lZQCNAz\n"
			+ "rtIx+soPpg==";

	static {
		try {
			loadPRIKey(pri);
			loadPUBKey(pub);
		} catch (Exception ex) {
			GA.app.error("Exception", ex);
		}
	}
	
	
	 public static void loadPUBKey(String pub) throws Exception {
	        KeyFactory kf = KeyFactory.getInstance(algoRSA);
	        byte[] bytePub = Base64.decodeBase64(pub);

	        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePub);
	        pubKey = kf.generatePublic(publicKeySpec);
	    }

	    public static void loadPRIKey(String pri) throws Exception {
	        KeyFactory kf = KeyFactory.getInstance(algoRSA);
	        byte[] bytePub = Base64.decodeBase64(pri);

	        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePub);
	        priKey = kf.generatePrivate(privateKeySpec);
	    }
	    
	    
	    public static boolean base64Verify(String data, String signature) {
	        try {
	            Signature sig = Signature.getInstance(algoSHA1withRSA);
	            sig.initVerify(pubKey);
	            byte[] bSign = Base64.decodeBase64(signature);

	            sig.update(data.getBytes());
	            return sig.verify(bSign);
	        } catch (Exception e) {
	            e.printStackTrace();
	            GA.app.error("error", e);
	        }
	        return false;
	    }

	    public static String base64Sign(String data) {
	        try {
	            Signature sig = Signature.getInstance(algoSHA1withRSA);
	            sig.initSign(priKey);
	            sig.update(data.getBytes());
	            byte[] bSign = sig.sign();
	            return new String(Base64.encodeBase64(bSign));

	        } catch (Exception e) {
	            e.printStackTrace();
	            GA.app.error("error", e);
	        }
	        return StringUtils.EMPTY;
	    }

}
