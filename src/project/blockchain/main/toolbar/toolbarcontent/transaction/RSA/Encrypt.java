package project.blockchain.main.toolbar.toolbarcontent.transaction.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class Encrypt {
    private static final String PUBLIC_KEY_FILE = "Public.key";
    private static final String PRIVATE_KEY_FILE = "Private.key";

    public void genKey(String data){
        try{
            System.out.println("-----Generate PUBLIC and PRIVATE key-----");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);//1024 is used for normal securities.
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("\n----- PULLING OUT PARAMETERS WHICH MAKE KEYPAIR -----\n");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

            System.out.println("\n----- SAVING PUBLIC AND PRIVATE KEYS TO FILES ------\n");
            Encrypt rsaObj = new Encrypt(); //instance of this class
            rsaObj.saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
            rsaObj.saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());

            //Encrypt Data using Public key
            byte[] encryptedData = rsaObj.encryptData(data);

            //Decrypt Data using Private key
            rsaObj.decryptData(encryptedData);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e){
            e.printStackTrace();
        }
    }


    private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos =null;
        try{
            System.out.println("Generating " + fileName + " . . .");
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(mod);
            oos.writeObject(exp);
            System.out.println(fileName + " generated successfully");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (oos != null){
                oos.close();
                if (fos != null){
                    fos.close();
                }
            }
        }
    }

    private byte[] encryptData(String data) throws IOException{
        System.out.println("\n---------ENCRYPTION STARTED---------");
        System.out.println("Data before encrption : " + data);
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try{
            PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
            System.out.println("Encrypted Data: " + new String (encryptedData));
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        System.out.println("---------ENCRYPTION COMPLETED------------");
        return encryptedData;
    }

    private void decryptData(byte[] data) throws IOException{
        System.out.println("\n------------DECRYTION STARTED------------");
        byte[] decryptedData = null;
        try{
            PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);
            System.out.printf("Decrypted data : ", new String(decryptedData));
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
        System.out.println("-------------DECRYPTION COMPLETED---------------");
    }

    public PublicKey readPublicKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            //Get public key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey publicKey = null;
            publicKey = fact.generatePublic(rsaPublicKeySpec);
            return publicKey;
        }catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException{
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            //Get private key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = null;
            privateKey = fact.generatePrivate(rsaPrivateKeySpec);
            return privateKey;
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        } finally {
            if (ois != null){
                ois.close();
                if (fis != null){
                    fis.close();
                }
            }
        }
        return null;
    }

}
