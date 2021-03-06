package org.pvemu.common.utils;

import java.util.Collection;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.common.Constants;

public class Crypt {

    public final static char[] HASH = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };
    
    final static public char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Hash data with md5
     * @param data
     * @return 
     */
    public static String md5(String data) {
        return hash("MD5", data);
    }

    /**
     * Hash data with sha1
     * @param data
     * @return 
     */
    public static String sha1(String data) {
        return hash("SHA-1", data);
    }

    /**
     * Hash message
     * @param algo the algo use to hash
     * @param message message to hash
     * @return 
     */
    public static String hash(String algo, String message) {
        try {
            byte[] hashData = java.security.MessageDigest.getInstance(algo).digest(message.getBytes());
            return byteArrayToHex(hashData);
        } catch (Exception e) {
            return "";
        }
    }

    private static String byteArrayToHex(byte[] hash) {
        StringBuilder hexStr = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            int n = hash[i];
            if (n < 0) {
                n += 256;
            }
            String hex = Integer.toHexString(n);
            if (hex.length() == 1) {
                hexStr.append('0');
            }
            hexStr.append(hex);
        }

        return hexStr.toString();
    }

    public static String encodePacket(String packet, String key) {
        if(Constants.DOFUS_VER_ID < 1100){
            return oldCryptPassword(packet, key);
        }
        StringBuilder encode = new StringBuilder();

        encode.append("#1");

        for (int i = 0; i < packet.length(); i++) {
            int current = (int) packet.charAt(i);
            int k = (int) key.charAt(i % key.length());

            int encode_c1 = current / 16 + k;
            int encode_c2 = current % 16 + k;

            encode.append(HASH[encode_c1 % HASH.length]);
            encode.append(HASH[encode_c2 % HASH.length]);
        }

        return encode.toString();
    }

    public static String decodePacket(String packet, String key) {
        if (Constants.DOFUS_VER_ID < 1100) {
            return "";
        }
        packet = packet.substring(2);
        StringBuilder decode = new StringBuilder();

        for (int i = 0; i < packet.length(); i += 2) {
            int k = (int) key.charAt((i / 2) % key.length());
            int encode_c1 = getHashIndex(packet.charAt(i));
            int encode_c2 = getHashIndex(packet.charAt(i + 1));

            encode_c1 = 64 + encode_c1 - k;
            encode_c2 = 64 + encode_c2 - k;
            if (encode_c2 < 0) {
                encode_c2 += 64;
            }

            char d = (char) (encode_c1 * 16 + encode_c2);

            decode.append(d);
        }

        return decode.toString();
    }

    public static String oldCryptPassword(String pwd, String key) {
        pwd = pwd + "\n";
        int _loc6 = pwd.length();
        int _loc7 = key.length();
        String _loc2 = "";
        for (int _loc1 = 0; _loc1 < _loc7; ++_loc1) {
            _loc2 = _loc2 + HASH[(pwd.charAt(_loc1 % _loc6) ^ key.charAt(_loc1 % 32)) % 64];
        } // end of for
        int _loc4 = _loc2.length();
        pwd = _loc2;
        _loc2 = "";
        for (int _loc1 = 0; _loc1 < _loc4; ++_loc1) {
            _loc2 = _loc2 + HASH[(pwd.charAt(_loc4 - _loc1 - 1) ^ key.charAt((_loc1 + 8) % 32)) % 64];
        } // end of for
        return (_loc2);
    }

    public static byte getHashIndex(char c) {
        if(c >= 'a' && c <= 'z'){
            return (byte)(c - 'a');
        }
        if(c >= 'A' && c <= 'Z'){
            return (byte)(c - 'A' + 26);
        }
        if(c >= '0' && c <= '9'){
            return (byte)(c - '0' + 52);
        }
        if(c == '-'){
            return 62;
        }
        if(c == '_'){
            return 63;
        }

        return -1;
    }

    public static String CryptIP(String IP) {
        String[] Splitted = IP.split("\\.");
        String Encrypted = "";
        int Count = 0;
        for (int i = 0; i < 50; i++) {
            for (int o = 0; o < 50; o++) {
                if (((i & 15) << 4 | o & 15) == Integer.parseInt(Splitted[Count])) {
                    Character A = (char) (i + 48);
                    Character B = (char) (o + 48);
                    Encrypted += A.toString() + B.toString();
                    i = 0;
                    o = 0;
                    Count++;
                    if (Count == 4) {
                        return Encrypted;
                    }
                }
            }
        }
        return "DD";
    }

    public static String CryptPort(int config_game_port) {
        int P = config_game_port;
        String nbr64 = "";
        for (int a = 2; a >= 0; a--) {
            nbr64 += HASH[(int) (P / (java.lang.Math.pow(64, a)))];
            P = (int) (P % (int) (java.lang.Math.pow(64, a)));
        }
        return nbr64;
    }
    
    public static int base64Decode(String code){
        int ret = 0;
        int multiplier = 1;
        for(int i = code.length() - 1; i >= 0; --i){
            ret += getHashIndex(code.charAt(i)) * multiplier;
            multiplier *= 64;
        }
        
        return ret;
    }
    
    public static String base64Encode(int i){
        StringBuilder code = new StringBuilder();
        
        while(i != 0){
            code.append(HASH[i % 64]);
            i /= 64;
        }
        
        return code.reverse().toString();
    }
    
    static public String prepareKey(String key){
        StringBuilder decode = new StringBuilder(key.length() / 2);
        
        for(int i = 0; i < key.length(); i += 2){
            decode.append((char)Integer.parseInt(key.substring(i, i + 2), 16));
        }
        
        return decode.toString();
    }
    
    static public char checksum(String str){
        int checksum = 0;
        
        for(int i = 0; i < str.length(); ++i){
            checksum += str.charAt(i) % 16;
        }
        
        return HEX_CHARS[checksum % 16];
    }
    
    static public String decypherData(String str, String key, int checksum){
        StringBuilder decoded = new StringBuilder();
        int keylen = key.length();
        int keypos = 0;
        
        for(int i = 0; i < str.length(); i += 2){
            decoded.append((char)(Integer.parseInt(str.substring(i, i + 2), 16) ^ (int)(key.charAt((keypos++ + checksum) % keylen))));
        }
        
        return decoded.toString();
    }
    
    static public String decodeMapData(String mapData, String key){
        key = prepareKey(key);
        
        return decypherData(mapData, key, Integer.parseInt(checksum(key) + "", 16) * 2);
    }
    
    public static short cellCode_To_ID(String cellCode) {
        char char1 = cellCode.charAt(0), char2 = cellCode.charAt(1);
        short code1 = 0, code2 = 0, a = 0;
        while (a < HASH.length) {
            if (HASH[a] == char1) {
                code1 = (short) (a * 64);
            }
            if (HASH[a] == char2) {
                code2 = a;
            }
            a++;
        }
        return (short) (code1 + code2);
    }

    public static String cellID_To_Code(short cellID) {
        int char1 = cellID / 64, char2 = cellID % 64;
        return HASH[char1] + "" + HASH[char2];
    }
    
    static public String compressPath(GameMap map, short startCell, Collection<Short> path, boolean inFight){
        StringBuilder sb = new StringBuilder(path.size() * 3);
        
        short lastCell = startCell;
        char lastDirection = 'a';
        for(short cell : path){
            char direction = MapUtils.getDirBetweenTwoCase(lastCell, cell, map, inFight);
            
            if(direction != lastDirection){
                sb.append(lastDirection).append(cellID_To_Code(lastCell));
                lastDirection = direction;
            }
            lastCell = cell;
        }
        
        sb.append(lastDirection).append(cellID_To_Code(lastCell));
        
        return sb.toString();
    }
}
