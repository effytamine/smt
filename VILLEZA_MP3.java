// imports
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;
import java.util.regex.Pattern;

class Utils { // helper methods go here as base class 
    Utils() {}  
    public static void isEmpty(String strInput) { if (strInput.isEmpty()) throw new IllegalArgumentException("Input string cannot be empty."); }

    public static String clean(String strInput) {
        return strInput.toLowerCase()
                .replaceAll("[^a-z]", "");
    }

    public static String rmSpace(String strInput) {
        return strInput.replaceAll("\\s+", "");
    }
}

class StringMethods { // only methods mentioned in the instructions go here

    private String strInput;

    public void setString(String strInput){
        if (strInput == null) strInput = "";
        this.strInput = strInput;
    }

    public String getString(){
        return strInput;
    }

    public String stringReverse() {
        Utils.isEmpty(strInput);

        char[] arrayOriginal = strInput.toCharArray(), arrayReversed = new char[arrayOriginal.length]; // create a new character array in the size of input

        for(int i = 0; i < arrayOriginal.length; i++) {
            arrayReversed[i] = arrayOriginal[arrayOriginal.length - 1 - i];
        }

        String stringReversed = String.copyValueOf(arrayReversed);
        return stringReversed;
    }

    public boolean isPalindrome() {
        Utils.isEmpty(strInput);
        String strTemp = Utils.clean(this.stringReverse());
        return (Utils.clean(strInput)).equalsIgnoreCase(strTemp);
    }

    public boolean isAnagram(String strToCompare) {
        for (String s : new String[]{strInput, strToCompare}) { // check 
            Utils.isEmpty(s);
        } 

        if(strInput.equalsIgnoreCase(strToCompare)) {
            System.out.println(" isAnagram notice : Anagrams are formed by a rearrangement of the original word, entering equal strings are not considered as anagrams of each other");
            return false; // a word is not an anagram of itself, it must be a permutation
            }

        char[] arrInput = (Utils.clean(strInput)).toCharArray();
        char[] arrToCompare = (Utils.clean(strToCompare)).toCharArray();

        if (arrInput.length != arrToCompare.length) return false;        // check if string lengths are equal

        Map<Character, Integer> mapFreq = new HashMap<>();

        for (char character: arrInput) {
            mapFreq.put(character, mapFreq.getOrDefault(character, 0) + 1);
        }

        for (char b: arrToCompare) {
            int intFreq = mapFreq.getOrDefault(b, 0);  // if b is recorded for the first time, it returns default val 0
            if (intFreq == 0) return false;                         // if a key is recorded for the first time, then return false. 
            mapFreq.put(b, intFreq - 1);
        }

        for (Integer index : mapFreq.values()) {                    // check if all elements that were counted all became 0 after subtraction loop
        if (index!= 0) return false;
        }
        return true;
    } 

    public int wordCount() { // words here are defined as substrings > 1 consisting of alphabets. only 2 words are 1 character long, i and a, and "'", "-" are allowed to continue substrings
        Utils.isEmpty(strInput);

        char[] arrText= (strInput.trim().toLowerCase()).toCharArray(); // clean, trim, convert to char array

        int intSubstring = 0;
        int intWordCount = 0;
        char charPrevious = '\0';

        for(char a: arrText) {

            if (!isAllowed(a)) {                                                        // if its not allowed, set substring to next character immediately
                if (intSubstring > 1 || isSpecialWord(charPrevious)) intWordCount++;        // +1 in word count if substring length reached
                intSubstring = 0;      
                charPrevious = a;                                                           // either way reset substring formed
                continue;
            } 

            if (!(a >= 'a' && a <= 'z') &&
                !(charPrevious >= 'a' && charPrevious <= 'z')) {                                // if its a dash or/and apostrophe or/and a not allowed character back to back;
                if (charPrevious == '\'' || charPrevious == '-') intSubstring -= 1;             // dont consider last non alphabet (but valid) as part of the word (the word stands for itself)
                if (intSubstring > 1) intWordCount++;                                   // +1 in word count if substring length reached
                intSubstring = 0;                                                       // either way reset substring formed
            } 

            intSubstring++; // if no terminating conditions apply, just add a it to substring length count
            charPrevious = a;
        }

        if (intSubstring > 1 || isSpecialWord(charPrevious)) intWordCount++; // check last word before termination
        
        return intWordCount;
    } 

    public int charCount() {
        Utils.isEmpty(strInput);

        String strNoSpace = Utils.rmSpace(strInput);     // remove space
        return strNoSpace.length();                     // return the length
    }
    

    public int substringCount(String strToCompare) {    
        Utils.isEmpty(strInput);

        return (int) substringPattern(strToCompare)     // generate RegEx pattern for substrings
                .matcher(strInput)                      // get matches 
                .results()                              // return as results in set and count
                .count();
    }

    public String toLower() {
        Utils.isEmpty(strInput);
        return strInput.toLowerCase();
    }

    public String toUpper() {
        Utils.isEmpty(strInput);
        return strInput.toUpperCase();
    }

    public String removeVowel() {
        Utils.isEmpty(strInput);
        return ISVOWEL.matcher(strInput).replaceAll("").trim();
    }

    public String removeConsonants() {
        Utils.isEmpty(strInput);
        return ISCONSONANT.matcher(strInput).replaceAll("").trim();
    }
    
    private static final Pattern ISVOWEL =  // compile once pattern for session use
        Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

    private static final Pattern ISCONSONANT =  // compile once pattern for session use
        Pattern.compile("[b-df-hj-np-tv-z]", Pattern.CASE_INSENSITIVE);

    private static Pattern substringPattern(String strToCompare) {
    return Pattern.compile(Pattern.quote(strToCompare), Pattern.CASE_INSENSITIVE);
    }

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
    String[][] arrMenu;

    UI() {
        arrMenu = new String[][] {
            {"stringReverse", "Reverse a string"},
            {"isPalindrome", "Check if palindrome"},
            {"isAnagram", "Check if two strings are anagrams"},
            {"wordCount", "Count words in string"},
            {"charCount", "Count characters (without spaces)"},
            {"substringCount", "Count occurrences of substring"},
            {"toLower", "Convert to lowercase"},
            {"toUpper", "Convert to uppercase"},
            {"removeVowel", "Remove all vowels"}, 
            {"removeConsonant", "Remove all consonants"},
            {"exit", "exit program"}
        };
    }
    
    // print menu
    public String acceptStringInput(Scanner scanner) {
        System.out.print(" Input Text > ");
        String strTemp = scanner.nextLine();
        System.out.println();
        return strTemp;
    }

    public int acceptChoice(Scanner scanner, int intMaxChoice) {
        int intChoice;
        try {
            System.out.print(" Input Choice > ");
            intChoice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(" ERROR : " + e);
            return -1;
        } finally {
            scanner.nextLine();
        }

        if (intChoice < 1 || intChoice > intMaxChoice) {
            System.out.println(" ERROR : Invalid choice");
            return -1;
        }
        System.out.println();
        return intChoice;
        
    }

    public void printHeader() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          STRING UTILITY MENU           ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void printChoices(){
        System.out.println(" Choose one of the following (input method number)");
        for (int i = 0; i < arrMenu.length; i++) {
            String strMethodName = arrMenu[i][0];
            String strDescription = arrMenu[i][1];
            System.out.printf("%2d) %-20s - %s%n", i + 1, strMethodName + "()", strDescription);
        }
    }
}

// main
public class VILLEZA_MP3 {
 
    public static boolean isSecondaryInput(Scanner scanner, int intMethodName, UI ui) {
        String[] strMethodName = {"stringReverse", "isPalindrome", "isAnagram", "wordCount", "charCount", "substringCount", "toLower", "toUpper", "removeVowel", "removeConsonant"};
        int intChoice;
        System.out.println(" " + strMethodName[intMethodName - 1]+" needs a secondary string input to compare with");

        while (true) {
            System.out.println(" 1) input secondary string");
            System.out.println(" 2) return");

            intChoice = ui.acceptChoice(scanner, 2);

            if (intChoice != -1) break;
        }

        if (intChoice == 1) {
            System.out.println(" Input secondary string input: ");
            return true;
        }
        return false;
    }

    public static boolean isPrimaryInput(Scanner scanner, int intMethodName, UI ui, StringMethods stringProcessor) {
        String[] strMethodName = {"stringReverse", "isPalindrome", "isAnagram", "wordCount", "charCount", "substringCount", "toLower", "toUpper", "removeVowel", "removeConsonant"};
        int intChoice;
        while (true) {
            System.out.println(" Choose an option ; " +  strMethodName[intMethodName - 1]+ " needs a primary string input");

            while (true) {
                System.out.println(" 1) set new string input");
                System.out.println(" 2) use current string input");
                System.out.println(" 3) return");

                intChoice = ui.acceptChoice(scanner, 3);

                if (intChoice != -1) {
                    break;
                } else {
                    pause(scanner);
                }
            }

            switch (intChoice) {
                case 1 -> {
                    System.out.println(" Input new string to object: ");
                    String strTemp = "";
                    strTemp = ui.acceptStringInput(scanner);
                    stringProcessor.setString(strTemp);
                    System.out.println(" / Using new input string; proceeding with algorithm.");
                    pause(scanner);
                    return true;
                }
                case 2 -> {
                    if (stringProcessor.getString() != null) {
                        System.out.println(" / Using current/previous input string; proceeding with algorithm.");
                        pause(scanner);
                        return true;
                    } else {
                        System.out.println(" / Input string field is currently null; set string input first.");
                        pause(scanner);
                    }
                }
                case 3 -> {
                    return false;
                }
                default -> {
                    return false; // for safekeeping
                }
            }
        }
    }

    public static void printResult(String strResult, String strSecondary, int intChoice, StringMethods stringProcessor) {
        System.out.println(" ════════ METHOD RESULT ════════");
        System.out.println(" Input: " + stringProcessor.getString());

        switch (intChoice) {
            case 2 -> {
                if (strResult.equalsIgnoreCase("true")) {
                    System.out.println(" Result: " + stringProcessor.getString() + " IS A palindrome(" + strResult + ")");
                } else {
                    System.out.println(" Result: " + stringProcessor.getString() + " IS NOT A palindrome(" + strResult + ")");
                }
            }
            case 3 -> {
                if (strResult.equalsIgnoreCase("true")) {
                    System.out.println(" Result: " + strSecondary + " IS AN anagram(" + strResult + ") of " + stringProcessor.getString());
                } else {
                    System.out.println(" Result: " + strSecondary + " IS NOT AN anagram(" + strResult + ") of " + stringProcessor.getString());
                }
            }
            case 4 -> {
                System.out.println(" Result: [" + strResult+ "] words in the input string");
            }
            case 5 -> {
                System.out.println(" Result: [" + strResult + "] characters in the input string");
            }
            case 6 -> {
                System.out.println(" Result: [" + strResult + "] instances of substring (" + strSecondary + ")");
            }
            default -> {
                System.out.println(" Result: " + strResult);
            }
        }
    }

    public static void outro() {
        System.out.println(" Thank you for using StringMethods");
        System.out.println(" by: Villeza, Sean Russell B., CS 2-5");

    }

    public static void pause(Scanner scanner) {
        System.out.print(" press enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringMethods stringProcessor = new StringMethods();
        
        UI ui = new UI();
        int intChoice;
        boolean boolIsExit = false;
        
        while (!boolIsExit) {

            ui.printHeader();
            ui.printChoices();
            intChoice = ui.acceptChoice(scanner, 11);

            if (intChoice == 11) {
                boolIsExit = true;
                continue; // dont evaluate switch case
            } else if (intChoice == -1) {
                pause(scanner);
                continue;
            }  // dont evaluate switch case

            if(!isPrimaryInput(scanner, intChoice, ui, stringProcessor)) continue; // dont switch case 
            String strSecondary;
            try {
                switch (intChoice) {
                case 1 -> printResult(stringProcessor.stringReverse(), "", intChoice, stringProcessor);
                case 2 -> printResult(String.valueOf(stringProcessor.isPalindrome()), "", intChoice, stringProcessor);
                case 3 -> {
                    if (isSecondaryInput(scanner, intChoice, ui)) {
                        strSecondary = ui.acceptStringInput(scanner);
                        boolean boolTemp = stringProcessor.isAnagram(strSecondary);
                        printResult(String.valueOf(boolTemp), strSecondary, intChoice, stringProcessor);
                    } else {
                        break;
                    }
                }
                case 4 -> printResult(String.valueOf(stringProcessor.wordCount()),"", intChoice, stringProcessor);
                case 5 -> printResult(String.valueOf(stringProcessor.charCount()), "", intChoice, stringProcessor);
                case 6 -> {
                    if (isSecondaryInput(scanner, intChoice, ui)) {
                        strSecondary = ui.acceptStringInput(scanner);
                        int intTemp = stringProcessor.substringCount(strSecondary);
                        printResult(String.valueOf(intTemp), strSecondary, intChoice, stringProcessor);
                    } else {
                        break;
                    }
                }
                case 7 -> printResult(stringProcessor.toLower(), "", intChoice, stringProcessor);
                case 8 -> printResult(stringProcessor.toUpper(), "", intChoice, stringProcessor);
                case 9 -> printResult(stringProcessor.removeVowel(), "", intChoice, stringProcessor);
                case 10 -> printResult(stringProcessor.removeConsonants(), "", intChoice, stringProcessor);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(" ERROR : " + e);
            } 
            pause(scanner);
        }
            outro();
    }
}