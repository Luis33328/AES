import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class KDC {

	private User user1;
	private User user2;
	private byte[] user1CiphKS;
    private byte[] user2CiphKS;
    private String user1SessionKey;
    private String user2SessionKey;
    private int nounce;
	
	public KDC(User user1, User user2) {
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public void genSessionKey(String id, byte[] ciphId, byte[] ciphDest) throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {
		String deciphID = AES.decifra(ciphId, user1.getKey());
		
		if(id.equals(deciphID)) {
			
			String destination = AES.decifra(ciphDest, user1.getKey());
            if(destination.equals(user2.getId())){

                String sessionKey = getKs();
                this.user1CiphKS = AES.cifra(sessionKey, user1.getKey());
                this.user2CiphKS = AES.cifra(sessionKey, user2.getKey());
            }
        }else{
            System.out.println("Usuario Invalido.");
        }
	}
	
	
	public String getKs(){
        Random genKey = new Random();
        String randKey = "";
        for (int i = 0; i < 16; i++) {
        	randKey += genKey.nextInt(5);
        }
        return randKey;
    }

    public byte[] getUser1CiphKS(){
        return this.user1CiphKS;
    }
    public byte[] getUser2CiphKS(){
        return this.user2CiphKS;
    }

    public void user1Ks() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

        user1SessionKey = AES.decifra(getUser1CiphKS(), user1.getKey());
        System.out.println(user1CiphKS);
        System.out.println("Chave de sessão do usuário 1: " + user1SessionKey);
    }

    public void user2Ks() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

    	user2SessionKey = AES.decifra(getUser2CiphKS(), user2.getKey());
        System.out.println(user2CiphKS);
        System.out.println("Chave de sessão do usuário 2: " + user2SessionKey);
    }

    public byte[] user2Nounce() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

        Random genNounce = new Random();
        int nounceNum = genNounce.nextInt(1000);
        System.out.println(nounceNum);
        this.nounce = nounceNum;

        byte[] user2CiphedNounce = AES.cifra(Integer.toString(nounceNum), user2SessionKey);
        
        return user2CiphedNounce;
        
    }

    public String deciphedUser1Nounce() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

        String deciphNounce = AES.decifra(user2Nounce(), user1SessionKey);
        return deciphNounce;
    }

    public int mathNounce() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

        int result = Integer.parseInt(deciphedUser1Nounce()) + 10;
        return result;
    }

    public int getNounce() {
        return nounce;
    }

    public void verifiedNounce() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {

        int nounceUser1 = mathNounce();
        int nounceUser2 = getNounce();
        int nounceUser2Math = nounceUser2 + 10;

        System.out.println("Nounce: " + nounce);
        System.out.println("Nounce do usuário 1: " + nounceUser1);
        System.out.println("Nounce do usuário 2: " + nounceUser2Math);

        if (nounceUser1 == nounceUser2Math) {
            System.out.println("Autenticação sucedida!");
        } else {
            System.out.println("Erro na autenticação.");
        }
    }
	
}
