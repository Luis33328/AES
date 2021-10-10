import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {

	public static void main(String[] args) throws InvalidKeyException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException {
		
		
		User bob = new User("bob", "fg4sa5fags5f4w9l");
		User alice = new User("alice", "1sdf51g5sdsa1f4f");
		
		KDC kdc  = new KDC(bob, alice);
		
		String p1 = bob.getId();
        byte[] p2 = AES.cifra(bob.getId(), bob.getKey());
        byte[] p3 = AES.cifra(alice.getId(), bob.getKey());

        kdc.genSessionKey(p1, p2, p3);

        kdc.getUser1CiphKS();
        kdc.getUser2CiphKS();

        kdc.user1Ks();
        kdc.user2Ks();

        kdc.verifiedNounce();
	}
	
}
