package teamfour.tasc.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//@@author A0127014W
public class KeywordParser {

    private final HashSet<String> keywords;

    /**
     * Constructor
     * @param keywords used to parse strings
     */
    public KeywordParser(String... inputKeywords) {
        this.keywords = new HashSet<String>();
        for (String key : inputKeywords) {
            this.keywords.add(key);
        }
    }

    /**
     * Parses input string arguments using keywords provided at construction.
     * Substring associated with keyword starts after keyword,
     * and ends before the next keyword or end of line.
     * Keyword and associated substring put in a HashMap, with key = keyword and value = associated substring.
     * If no match found then empty HashMap returned.
     *
     * @param inputString   String to be parsed
     * @return entryPairs   HashMap containing the keyword - associated substring pairs
     */
    public HashMap<String, String> parseKeywordsWithoutFixedOrder(String inputString) {
        assert inputString != null;
        String[] parts = combinePartsBetweenQuotes(inputString.split(" "));
        HashMap<String, String> entryPairs = extractEntryPairsFromParts(parts);
        return entryPairs;
    }

    /**
     * Combine the String elements between open " and close " into one
     * If less close " than open " found, rest of the string after the open " will be combined.
     * If more close " than open " found, rest of the close " will be ignored
     *
     * @param parts             Array of Strings
     * @return combinedParts    Array of Strings with elements between open and close "" combined into one
     */
    private String[] combinePartsBetweenQuotes(String[] parts) {
        ArrayList<Integer> openQuoteStartIndices = new ArrayList<Integer>();
        ArrayList<Integer> closeQuoteEndIndices = new ArrayList<Integer>();
        String[] combinedParts = parts;

        getOpenQuoteStartIndices(combinedParts, openQuoteStartIndices);
        if (!openQuoteStartIndices.isEmpty()) {
            getCloseQuoteEndIndices(combinedParts, openQuoteStartIndices, closeQuoteEndIndices);
            movePartsBetweenQuotesIntoFirstElement(combinedParts, openQuoteStartIndices, closeQuoteEndIndices);
            combinedParts = getNewArrayWithoutNullElements(combinedParts);
        }
        return combinedParts;
    }

    /**
     * For each group of strings between a pair of open and close ",
     * remove every string after the start index and append them to the
     * element at the start index.
     *
     * @param parts                     Array of Strings
     * @param openQuoteStartIndices     Array containing start indices of groups of parts between quotes
     * @param closeQuoteEndIndices      Array containing end indices of groups of parts between quotes
     */
    private void movePartsBetweenQuotesIntoFirstElement(String[] parts, ArrayList<Integer> openQuoteStartIndices,
            ArrayList<Integer> closeQuoteEndIndices) {
        for (int i = 0; i < openQuoteStartIndices.size(); i++) {
            int startOfGroup = openQuoteStartIndices.get(i);
            int endOfGroup = closeQuoteEndIndices.get(i);
            for (int j = startOfGroup + 1; j <= endOfGroup; j++) {
                parts[startOfGroup] = parts[startOfGroup] + " " + parts[j];
                parts[j] = null;
            }
        }
    }

    /**
     * Gets a new String array from input array but without null elements
     *
     * @param parts     Array of Strings
     * @return          Array of String without null elements
     */
    private String[] getNewArrayWithoutNullElements(String[] parts) {
        ArrayList<String> newParts = new ArrayList<String>();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i] != null) {
                newParts.add(parts[i]);
            }
        }
        return newParts.toArray(new String[newParts.size()]);
    }

    /**
     * Gets the indices of strings which start with an open quote ".
     * These strings form the start of a group of strings between open and close quotes.
     *
     * @param parts                     Array of Strings
     * @param openQuoteStartIndices     Array containing start indices of groups of parts between quotes
     */
    private void getOpenQuoteStartIndices(String[] parts, ArrayList<Integer> openQuoteStartIndices) {
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("\"")) {
                openQuoteStartIndices.add(i);
            }
        }
    }

    /**
     * Gets the indices of strings which end with a close quote ".
     * These strings form the end of a group of strings between open and close quotes.
     *
     * @param parts                     Array of Strings
     * @param openQuoteStartIndices     Array containing end indices of groups of parts between quotes
     */
    private void getCloseQuoteEndIndices(String[] parts, ArrayList<Integer> openQuoteStartIndices,
            ArrayList<Integer> closeQuoteEndIndices) {
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].endsWith("\"")) {
                closeQuoteEndIndices.add(i);
            }
        }
        while (openQuoteStartIndices.size() > closeQuoteEndIndices.size()) {
            // If more open " than close ", let the end of line serve as
            // additional close "
            closeQuoteEndIndices.add(parts.length - 1);
        }
    }

    /**
     * Finds the keywords and their associated substrings in the parts array,
     * and puts them in a HashMap, with key = keyword and value = substring associated with keyword.
     *
     * @param parts         String array of parts extracted from the input String
     * @return entryPairs   Keyword-substring pairs extracted from the String array
     */
    private HashMap<String, String> extractEntryPairsFromParts(String[] parts) {
        HashMap<String, String> entryPairs = new HashMap<String, String>();

        for (int i = 0; i < parts.length; i++) {
            if (isStringAKeyword(parts[i])) {
                String currentKeyword = parts[i];
                StringBuilder stringBuilder = new StringBuilder();

                int nextPartToCheck = i + 1;
                while (nextPartToCheck < parts.length && !isStringAKeyword(parts[nextPartToCheck])) {
                    stringBuilder.append(parts[nextPartToCheck] + " ");
                    nextPartToCheck++;
                }

                String finalValue = stringBuilder.toString().trim();
                finalValue = stripOpenAndCloseQuotationMarks(finalValue);

                entryPairs.put(currentKeyword.toLowerCase(), finalValue);
                i = nextPartToCheck - 1;
            }
        }
        return entryPairs;
    }

    private boolean isStringAKeyword(String string) {
        return keywords.contains(string.toLowerCase());
    }
    //@@author
    private String stripOpenAndCloseQuotationMarks(String input) {
        if (input.startsWith("\"")) {
            input = input.substring(1);
        }

        if (input.endsWith("\"")) {
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }

}
