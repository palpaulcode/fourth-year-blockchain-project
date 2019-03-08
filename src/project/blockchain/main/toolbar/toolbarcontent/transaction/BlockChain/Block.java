package project.blockchain.main.toolbar.toolbarcontent.transaction.BlockChain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {
    private int index;
    private long timeStamp;
    private String hash;
    private String prevHash;
    private String data;
    private int nonce;

    public Block(int index, long timeStamp, String prevHash, String data){
        this.index = index;
        this.timeStamp = System.currentTimeMillis();
        this.prevHash = prevHash;
        this.data = data;
        nonce = 0;
        hash = Block.calculateHash(this);
    }

    public int getIndex() {
        return index;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getData() {
        return data;
    }

    public String str(){
        return index + timeStamp + prevHash + data + nonce;
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();

        builder.append("Block #").append(index).append(" [previousHash : ").append(prevHash).append(", ")
                .append("timeStamp : ").append(new Date(getTimeStamp())).append(", ").append(", ").append("Hash : ")
                .append(hash).append("]");

        //print data to console
        //System.out.println(builder);

        return builder.toString();
    }

    public static String calculateHash(Block block){
        if (block != null) {
            MessageDigest digest = null;
            try {
                digest =MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
//            final byte bytes[] = digest.digest((block.str()).getBytes());
           String txt = block.str();
           final byte bytes[] = digest.digest(txt.getBytes());
            final StringBuilder builder = new StringBuilder();

            for (final byte b: bytes){
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    builder.append(hex);
                }

                builder.append(hex);
            }
            return builder.toString();
        }
        return null;
    }

    public void mineBlock(int difficulty) {
        nonce  = 0;

        while ( !getHash().substring(0, difficulty).equals(Utils.zeros(difficulty))) {
            nonce++;
            hash = Block.calculateHash(this);

            System.out.println(hash);
        }
    }
}
