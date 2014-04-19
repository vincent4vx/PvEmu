package org.pvemu.jelly.utils;

import java.util.ArrayList;
import java.util.Random;
import org.pvemu.jelly.Jelly;

public class Utils {

    final private static Random rand = new Random();

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
            b.append(chrs[rand.nextInt(chrs.length)]);
        }

        return b.toString();
    }

    /**
     * Generate random integer in range [ min..max [
     * @param min
     * @param max
     * @return
     */
    public static int rand(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    
    static public int rand(int max){
        return rand.nextInt(max);
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
     * Retourne une valeur aléatoirement du tableau
     *
     * @param arr
     * @return
     */
    public static Object array_rand(Object[] arr) {
        return arr[rand.nextInt(arr.length)];
    }

    /**
     * Retourne un caractère d'une chaine au hasard
     *
     * @param s
     * @return
     */
    public static char char_rand(String s) {
        return s.charAt(rand.nextInt(s.length()));
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
    
    /**
     * Sépare une chaine en sous chaines, délimité par une chaine de caractère A
     * utiliser à la place de String.split (quand il n'y a pas besoin de regexp,
     * soit 99% du temps), car ~50% plus rapide
     *
     * @param str Chaine à découper
     * @param delimiter séparateur
     * @param limit Nombre de sous chaine maximum (taille maximal du tableau de
     * fin)
     * @return la chaine explosé
     */
    public static String[] split(String str, String delimiter, int limit) {
        ArrayList<String> splited = new ArrayList<String>();

        int last = 0, pos, step = 0;

        if (limit < 1) {
            limit = Integer.MAX_VALUE; //devrait suffire amplement x)
        }

        while ((pos = str.indexOf(delimiter, last)) != -1 && ++step < limit) {
            splited.add(str.substring(last, pos));
            last = pos + 1;
        }

        splited.add(str.substring(last));

        String[] ret = new String[splited.size()];
        return splited.toArray(ret);
    }

    /**
     * Sépare une chaine en sous chaines, délimité par une chaine de caractère A
     * utiliser à la place de String.split (quand il n'y a pas besoin de regexp,
     * soit 99% du temps), car ~50% plus rapide
     *
     * @param str Chaine à découper
     * @param delimiter séparateur
     * @return la chaine explosé
     */
    public static String[] split(String str, String delimiter) {
        return split(str, delimiter, 0);
    }

    /**
     * Concatène les élément d'un tableau, en les séparant par un séparateur
     *
     * @param pieces Pièces à joindre
     * @param separator Séparateur
     * @return élément concatété
     */
    public static String join(Object[] pieces, String separator) {
        StringBuilder str = new StringBuilder(pieces.length * 16); //allocation de mémoire "assez" large pour éviter un resize

        for (int i = 0; i < pieces.length; ++i) {
            if (i > 0) {
                str.append(separator);
            }

            str.append(pieces[i]);
        }

        return str.toString();
    }

    /**
     * Génère une chaine aléatoire
     *
     * @param size Taille de la chaine
     * @return une chaine aléatoire
     */
    public static String stringAleat(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();

        StringBuilder str = new StringBuilder(size);

        for (int i = 0; i < size; ++i) {
            str.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }

        return str.toString();
    }
    
    static public boolean contains(Iterable array, Object obj){
        for(Object o : array){
            if(obj.equals(o))
                return true;
        }
        
        return false;
    }
}
