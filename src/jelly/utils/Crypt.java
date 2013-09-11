package jelly.utils;

import jelly.Constants;
import jelly.Loggin;

public class Crypt {

    public final static char[] HASH = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    public static String md5(String data) {
        return hash("MD5", data);
    }

    public static String sha1(String data) {
        return hash("SHA-1", data);
    }

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
            int encode_c1 = getHASHIndex(packet.charAt(i));
            int encode_c2 = getHASHIndex(packet.charAt(i + 1));

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

    private static int getHASHIndex(char c) {
        for (int i = 0; i < HASH.length; i++) {
            if (HASH[i] == c) {
                return i;
            }
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
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        int P = config_game_port;
        String nbr64 = "";
        for (int a = 2; a >= 0; a--) {
            nbr64 += HASH[(int) (P / (java.lang.Math.pow(64, a)))];
            P = (int) (P % (int) (java.lang.Math.pow(64, a)));
        }
        return nbr64;
    }
}
