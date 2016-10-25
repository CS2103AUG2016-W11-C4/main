package teamfour.tasc.logic.parser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser {

    private ArrayList<String> keywords;
    private String KEYWORD_PARSER_NO_MATCHES = "No matches found for given keywords";

    /**
     * Constructor
     * @param keywords used to pass strings
     */
    public KeywordParser(String... inputKeywords){
        this.keywords = new ArrayList<String>();
        for(String key: inputKeywords){
            this.keywords.add(key);
        }
    }
    /**
     * Parses input string arguments using keywords provided at construction
     * Substring associated with keyword starts after keyword, and ends before the next keyword or end of line
     * Keyword and associated substring put in a HashMap, with key = keyword and value = associated substring
     * If no match found then empty HashMap returned
     * @param string to be parsed
     * @return HashMap containing the keyword - associated substring pairs
     */
    //TODO Make parsing not depend on order of input keywords
    public HashMap<String, String> parse(String inputString){
        HashMap<String, String> words = new HashMap<String, String>();
        for(int i = 0; i < keywords.size(); i++){
            inputString = new String(inputString);
            String keyword = keywords.get(i);
            String patternString;
            if (keyword.equals("add")) {
                //Special case for add command, which takes ""
                patternString = new String("[^/]*" + keyword + " " + "\"(?<returnString>[^/]+)\"");
            }
            else{
                patternString = new String("[^/]*" + keyword + " " + "(?<returnString>[^/]+)");
            }
            if (i < keywords.size() - 1){
                patternString = patternString + " " + keywords.get(i + 1) + "[^/]+";
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString);
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
                words.put(keyword, returnString);
            }

        }
        return words;
    }

    /**
     * Parses input string arguments using keywords provided at construction
     * Substring associated with keyword starts after keyword, and ends before the next keyword or end of line
     * Keyword and associated substring put in a HashMap, with key = keyword and value = associated substring
     * If no match found then empty HashMap returned
     * @param string to be parsed
     * @return HashMap containing the keyword - associated substring pairs
     */
    public HashMap<String, String> parseKeywordsWithoutFixedOrder(String inputString){
        HashSet<String> keywordsInHashSet = new HashSet<String>();
        for (String kw : keywords) {
            keywordsInHashSet.add(kw);
        }

        HashMap<String, String> entryPairs = new HashMap<String, String>();
        String[] parts = inputString.split(" ");
        //Combine the parts between open " and close " into one part.
        //If no close " found, parser will return the rest of the string after the open "
        //note: should have error when open " exist, but no close " ?
        //note: refactor this
        ArrayList<Integer> startIndices = new ArrayList<Integer>();
        ArrayList<Integer> endIndices = new ArrayList<Integer>();
        int startIndex = -1;
        int endIndex = parts.length - 1;
        for(int i = 1; i < parts.length; i++ ){
        	if(parts[i].startsWith("\"")){
        		startIndices.add(i);
        	}
        }
        if (!startIndices.isEmpty()) {
			for (int i = 1; i < parts.length; i++) {
				if (parts[i].endsWith("\"")) {
					endIndices.add(i);
				}
			}

			while(startIndices.size() > endIndices.size()){
			    //If more open " than close ", let the end of line serve as additional close "
			    endIndices.add(parts.length - 1);
			}

			for (int i = 0; i < startIndices.size(); i++) {
			    int start = startIndices.get(i);
			    int end = endIndices.get(i);
                for (int j = start + 1; j <= end; j++) {
                    parts[start] = parts[start] + " " + parts[j];
                    parts[j] = null;
                }
            }
            ArrayList<String> newParts = new ArrayList<String>();
	        for(int i = 0; i < parts.length; i++){
	        	if (parts[i] != null) {
					newParts.add(parts[i]);
				}
	        }
	        parts = newParts.toArray(new String[newParts.size()]);
		}
        //end open close "" parts

        for (int i = 0; i < parts.length; i++) {
            if (stringIsAKeyword(keywordsInHashSet, parts[i])) {

                String currentKeyword = parts[i];
                StringBuilder stringBuilder = new StringBuilder();

                int nextPartToCheck = i + 1;
                while (nextPartToCheck < parts.length
                        && !stringIsAKeyword(keywordsInHashSet, parts[nextPartToCheck])) {
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

    private boolean stringIsAKeyword(HashSet<String> allKeywords, String string) {
        return allKeywords.contains(string.toLowerCase());
    }

    private String stripOpenAndCloseQuotationMarks(String input) {
        if (input.startsWith("\"")) {
            input = input.substring(1);
        }

        if (input.endsWith("\"")) {
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }

    /**
     * Similar to parse(), but for input string with one keyword
     * Returns first match with any keyword. Associated substring is everything after the keyword
     * @param inputString
     * @return HashMap containing the keyword - associated substring pair
     */
    public HashMap<String, String> parseForOneKeyword(String inputString){
        HashMap<String, String> words = new HashMap<String, String>();
        for(int i = 0; i < keywords.size(); i++){
            inputString = new String(inputString);
            String keyword = keywords.get(i);
            String patternString;
            if (keyword.equals("add")) {
                //Special case for add command, which takes ""
                patternString = new String("[^/]*" + keyword + " " + "\"(?<returnString>[^/]+)\"");
            }
            else{
                patternString = new String("[^/]*" + keyword + " " + "(?<returnString>[^/]+)");
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString);
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
                words.put(keyword, returnString);
                return words;
            }

        }
        return words;
    }


}
