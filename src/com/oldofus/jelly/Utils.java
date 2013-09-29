package com.oldofus.jelly;

import java.util.Random;

public class Utils {

    private static Random _rand = new Random();

    /**
     * Génère une chaine aléatoire
     *
     * @param size taille de la chaine résultante
     * @return
     */
    public static String str_aleat(int size) {
        StringBuilder b = new StringBuilder(size);

        char[] chrs = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z'};

        for (int i = 0; i < size; i++) {
            b.append(chrs[_rand.nextInt(chrs.length)]);
        }

        return b.toString();
    }

    /**
     * Génère un antier aliéatoire entre min (inclue) et max (exclue)
     *
     * @param min
     * @param max
     * @return
     */
    public static int rand(int min, int max) {
        return _rand.nextInt(max - min + 1) + min;
    }

    /**
     * Décodage des packets encodé (cf: pass)
     *
     * @param pass
     * @param key
     * @return
     */
    public static String decryptPacket(String pass, String key) {
        if (pass.startsWith("#1")) {
            pass = pass.substring(2);
        }
        String Chaine = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

        char PPass, PKey;
        int APass, AKey, ANB, ANB2, somme1, somme2;

        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < pass.length(); i += 2) {
            PKey = key.charAt(i / 2);
            ANB = Chaine.indexOf(pass.charAt(i));
            ANB2 = Chaine.indexOf(pass.charAt(i + 1));

            somme1 = ANB + Chaine.length();
            somme2 = ANB2 + Chaine.length();

            APass = somme1 - (int) PKey;
            if (APass < 0) {
                APass += 64;
            }
            APass *= 16;

            AKey = somme2 - (int) PKey;
            if (AKey < 0) {
                AKey += 64;
            }

            PPass = (char) (APass + AKey);

            decrypted.append(PPass);
        }

        return decrypted.toString();
    }

    /**
     * Recole les parties de pieces avec glue, dans un String
     *
     * @param delimiter
     * @param pieces
     * @return
     */
    public static String implode(String glue, Object[] pieces) {
        if (pieces == null || pieces.length < 1) {
            return "";
        }

        StringBuilder b = new StringBuilder();

        for (Object p : pieces) {
            b.append(p).append(glue);
        }

        String r = b.toString();

        return r.substring(0, r.length() - 1);
    }

    /**
     * Retourne une valeur aléatoirement du tableau
     *
     * @param arr
     * @return
     */
    public static Object array_rand(Object[] arr) {
        return arr[_rand.nextInt(arr.length)];
    }

    /**
     * Retourne un caractère d'une chaine au hasard
     *
     * @param s
     * @return
     */
    public static char char_rand(String s) {
        return s.charAt(_rand.nextInt(s.length()));
    }

    /**
     * Retourne la valeur du caractère c en base64
     * @param c
     * @return 
     */
    public static byte parseBase64Char(char c) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        for (byte a = 0; a < HASH.length; a++) {
            if (HASH[a] == c) {
                return a;
            }
        }
        return -1;
    }
    
    public static String getUptime(){
        StringBuilder ret = new StringBuilder();
        
        long time = System.currentTimeMillis() - Jelly.start;
        int days = (int) (time / (1000 * 3600 * 24));
        int hours = (int)(time / (1000 * 3600) % 24);
        int minutes = (int)(time / (1000 * 60) % 60);
        
        if(days != 0){
            ret.append(days).append(" jours ");
        }
        ret.append(hours).append("h ").append(minutes).append("min");
        
        return ret.toString();
    }
}
