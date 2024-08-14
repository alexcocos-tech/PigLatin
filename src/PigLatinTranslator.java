import java.util.Arrays;
import java.util.List;

public class PigLatinTranslator {

    private static final List<Character> VOWELS = Arrays.asList('a', 'e', 'i', 'o', 'u');

    public String translate(String sentence) {
        StringBuilder result = new StringBuilder();
        String[] words = sentence.split("\\s+");

        for (String word : words) {
            result.append(translateWord(word)).append(" ");
        }
        return result.toString().trim();
    }


    private String translateWord(String word) {
        if (beginsWithVowelSound(word)) {
            return word + "ay";
        } else if (beginsWithConsonantFollowedByQu(word)) {
            return moveConsonantClusterAndQuToEnd(word) + "ay";
        } else if (containsYAfterConsonantCluster(word)) {
            return moveConsonantClusterBeforeYToTheEnd(word) + "ay";
        } else {
            return moveConsonantClusterToEnd(word) + "ay";
        }
    }

    private boolean beginsWithVowelSound(String word) {
        return VOWELS.contains(word.charAt(0)) || word.startsWith("xr") || word.startsWith("yt");
    }

    private boolean beginsWithConsonantFollowedByQu(String word) {
        return word.matches("^[^aeiou]*qu.*");
    }

    private boolean containsYAfterConsonantCluster(String word) {
        int yIndex = word.indexOf('y');
        return yIndex > 0 && !VOWELS.contains(word.charAt(yIndex - 1));
    }

    private String moveConsonantClusterAndQuToEnd(String word) {
        int quIndex = word.indexOf("qu") + 2;
        return word.substring(quIndex) + word.substring(0, quIndex);
    }

    private String moveConsonantClusterBeforeYToTheEnd(String word) {
        int yIndex = word.indexOf('y');
        return word.substring(yIndex) + word.substring(0, yIndex);
    }

    private String moveConsonantClusterToEnd(String word) {
        int vowelIndex = findFirstVowelIndex(word);
        return word.substring(vowelIndex) + word.substring(0, vowelIndex);
    }

    private int findFirstVowelIndex(String word) {
        for (int i = 0; i <word.length(); i++){
            if (VOWELS.contains(word.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        PigLatinTranslator translator = new PigLatinTranslator();
        System.out.println(translator.translate("hello world"));
        System.out.println(translator.translate("square"));
        System.out.println(translator.translate("rhythm"));
    }
}