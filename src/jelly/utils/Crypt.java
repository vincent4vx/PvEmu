package jelly.utils;

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

    private static int getHASHIndex(char c) {
        for (int i = 0; i < HASH.length; i++) {
            if (HASH[i] == c) {
                return i;
            }
        }

        return -1;
    }
}
