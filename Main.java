// imports
//import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;
import java.util.regex.Pattern;

class Utils { // helper methods go here as base class (scalable)
    Utils() {}  
    public static void isNull(String input) { if (input == null) throw new NullPointerException("Input string cannot be null"); }
    public static void isEmpty(String input) { if (input.isEmpty()) throw new IllegalArgumentException("Input string cannot be empty."); }

    public static String clean(String s) {
        return s.toLowerCase()
                .replaceAll("[^a-z]", "");
    }

    public static String rmSpace(String s) {
        return s.replaceAll("\\s+", "");
    }
}

class StringMethods { // only methods mentioned in the instructions go here

    public static String stringReverse(String strOriginal) {
        Utils.isNull(strOriginal); // check
        Utils.isEmpty(strOriginal);

        char[] arrayOriginal = strOriginal.toCharArray(), arrayReversed = new char[arrayOriginal.length]; // create a new character array in the size of input

        for(int i = 0; i < arrayOriginal.length; i++) {
            arrayReversed[i] = arrayOriginal[arrayOriginal.length - 1 - i];
        }

        String stringReversed = String.copyValueOf(arrayReversed);
        return stringReversed;
    }

    public static boolean isPalindrome(String strText) {
        Utils.isNull(strText); // check
        Utils.isEmpty(strText);

        if (strText.equals(stringReverse(strText))) return true;
        return false;
    }

    public static boolean isAnagram(String strFirst, String strSecond) {
        for (String s : new String[]{strFirst, strSecond}) { // check 
            Utils.isNull(s);
            Utils.isEmpty(s);
        }

        char[] arrFirst = (Utils.clean(strFirst)).toCharArray();
        char[] arrSecond = (Utils.clean(strSecond)).toCharArray();

        if (arrFirst.length != arrSecond.length) return false;  // check if string lengths are equal

        //System.out.println(Arrays.toString(arrFirst)); tests
       // System.out.println(Arrays.toString(arrSecond));

        Map<Character, Integer> mapFreq = new HashMap<>();

        for (char a: arrFirst) {
            mapFreq.put(a, mapFreq.getOrDefault(a, 0) + 1);
        }

        for (char b: arrSecond) {
            int intFreq = mapFreq.getOrDefault(b, 0); // if b is recorded for the first time, it returns default val 0
            if (intFreq == 0) return false; // if a key is recorded for the first time, then return false. 
            mapFreq.put(b, intFreq - 1);
        }
        
        return true;
        } 

    public static int wordCount(String strText) { // words here are defined as substrings > 1 consisting of aphabets. only 2 words are 1 character long, i and a, and "'", "-" are allowed to continue substrings
        Utils.isNull(strText); // check
        Utils.isEmpty(strText);

        char[] arrText= (strText.trim().toLowerCase()).toCharArray(); // clean, trim, convert to char array

        int intSubstring = 0;
        int intWordCount = 0;
        char lastChar = '\0';

        for(char a: arrText) {

            if (!isAllowed(a)) {                                                        // if its not allowed, set substring to next character immediately
                if (intSubstring > 1 || isSpecialWord(lastChar)) intWordCount++;        // +1 in word count if substring length reached
                intSubstring = 0;      
                lastChar = a;                                                           // either way reset substring formed
                continue;
            } 

            if (!(a >= 'a' && a <= 'z') &&
                !(lastChar >= 'a' && lastChar <= 'z')) {                                // if its a dash or/and apostrophe or/and a not allowed character back to back;
                if (lastChar == '\'' || lastChar == '-') intSubstring -= 1;             // dont consider last non alphabet (but valid) as part of the word (the word stands for itself)
                if (intSubstring > 1) intWordCount++;                                   // +1 in word count if substring length reached
                intSubstring = 0;                                                       // either way reset substring formed
            } 

            intSubstring++; // if no terminating conditions apply, just add a it to substring length count
            lastChar = a;
        }

        if (intSubstring > 1 || isSpecialWord(lastChar)) intWordCount++; // check last word before termination
        
        return intWordCount;
    } 

    public static int charCount(String strText) {
        Utils.isNull(strText); // check
        Utils.isEmpty(strText);

        String strNoSpace = Utils.rmSpace(strText);     // remove space
        return strNoSpace.length();                     // return the length
    }
    
    public static int substringCount(String strText, String strSubstring, boolean ignoreCase) { // for mode
        Utils.isNull(strText); // check
        Utils.isEmpty(strText);
        if (ignoreCase == true) {
            return substringCount(strText.toLowerCase(), strSubstring.toLowerCase());
        } else {
             return substringCount(strText, strSubstring);
        }
    }

    public static int substringCount(String strText, String strSubstring) {    
        Utils.isNull(strText); // check
        Utils.isEmpty(strText);

        int intSubstringCount = 0;
        int index = 0;
        while((index = strText.indexOf(strSubstring, index)) != -1 &&
                index < (strText.length()-(strSubstring.length()-1))) {
            index += strSubstring.length();
            intSubstringCount++;
        }

        return intSubstringCount;
    }

    public static String makeLowerCase(String strText) {
        return strText.toLowerCase();
    }

    public static String makeUpperCase(String strText) {
        return strText.toUpperCase();
    }

    public static String removeVowel(String strOriginal) {
        return ISVOWEL.matcher(strOriginal).replaceAll("").trim();
    }

    public static String removeConsonants(String strOriginal) {
        return ISCONSONANT.matcher(strOriginal).replaceAll("").trim();
    }
    
    private static final Pattern ISVOWEL =  // compile once pattern for session use
        Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

    private static final Pattern ISCONSONANT =  // compile once pattern for session use
        Pattern.compile("[b-df-hj-np-tv-z]", Pattern.CASE_INSENSITIVE);

    private static boolean isAllowed(char c) {
    return switch (c) {
        case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
             'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
             'u', 'v', 'w', 'x', 'y', 'z',
             '-', '\'' -> true;
        default -> false;
        };
    }

    private static boolean isSpecialWord(char c) {
    return switch (c) {
        case 'i', 'a' -> true;
        default -> false;
        };
    }
}  

class UI {
    // const choice
    String[][] menu;

    UI() {
        menu = new String[][] {
            {"stringReverse", "Reverse a string"},
            {"isPalindrome", "Check if palindrome"},
            {"isAnagram", "Check if two strings are anagrams"},
            {"wordCount", "Count words in string"},
            {"charCount", "Count characters (with/without spaces)"},
            {"substringCount", "Count occurrences of substring"},
            {"toLower", "Convert to lowercase"},
            {"toUpper", "Convert to uppercase"},
            {"removeVowel", "Remove all vowels"}, 
            {"removeConsonant", "Remove all consonants"}
        };
    }
    // print menu
    public String acceptStringInput(Scanner scanner) {
        System.out.print(" Input Text (3 Sentences) > ");
        return scanner.nextLine();
    }

    public int acceptChoice(Scanner scanner, int maxChoice) {
        int choice;
        try {
            System.out.print(" Input Choice > ");
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(" ERROR : " + e);
            return -1;
        } finally {
            scanner.nextLine();
        }

        if (choice < 1 || choice > maxChoice) {
            System.out.println(" ERROR : Invalid choice");
            return -1;
        }
        return choice;
    }

    public void printHeader() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          STRING UTILITY MENU           ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void printChoices(){
        System.out.println(" Choose one of the following (input method number)");
        for (int i = 0; i < menu.length; i++) {
            String key = menu[i][0];
            String desc = menu[i][1];
            System.out.printf("%2d) %-20s - %s%n", i + 1, key + "()", desc);
        }
    }

}
// main
public class Main {

    static boolean isDone(Scanner scanner, UI ui) {
        int choice;
        while(true) {
                System.out.println(" : Execute algorithm again?");
                System.out.println("   1) Yes\n   2) Return to Main menu");
                if ((choice = ui.acceptChoice(scanner, 2)) == 1) {
                    return false;
                } else if (choice == 2) {
                    return true;
                }
            }
        }

    static void reverse(Scanner scanner, UI ui) {     
        while (true) {
            System.out.println(" ══════ STRING REVERSE ══════ ");  
            System.out.println(" Input: a String");
            System.out.println(" Output: The input string but all characters are swapped to be in REVERSED order");

            while (true) {
                try {
                StringMethods.stringReverse(ui.acceptStringInput(scanner));
                break;
                } catch(NullPointerException e) {
                    System.out.println(" ERROR : " + e);
                } catch (IllegalArgumentException e) {
                    System.out.println(" ERROR : " + e);
                } 
            }

            if (isDone(scanner, ui)) {
                break;
            }
        }
    }
 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI();
        int choice;

        while (true) {
            ui.printHeader();
            ui.printChoices();
            choice = ui.acceptChoice(scanner, 10);

            switch (choice) {
                case 1 -> reverse(scanner, ui);
                default -> System.out.println(" ERROR : Invalid choice");
            }
        }
        // String original = "madam";
        // String text = "aeiou ssssss  abcdefg";
        // String sentence = "ched hcdbe checbj";
        // String test = "LITTLE red riding hood little women little li.ttle";
        // System.out.println(StringMethods.stringReverse(original));
        // System.out.println(StringMethods.isPalindrome(original));
        // System.out.println(StringMethods.isAnagram("helloo", "helloo"));
        // System.out.println(StringMethods.wordCount(sentence));
        // System.out.println(StringMethods.charCount(sentence));
        // System.out.println(StringMethods.substringCount(test, "little"));
        // System.out.println(StringMethods.substringCount(test, "little",true));
        // System.out.println(StringMethods.makeLowerCase(test));
        // System.out.println(StringMethods.makeUpperCase(test));
        // System.out.println(StringMethods.removeVowel(text));
        // System.out.println(StringMethods.removeConsonants(text));
    }
}