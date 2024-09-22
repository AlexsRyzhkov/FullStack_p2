import java.util.*;

public class FileHelper {

    public static List<List<String>> addedAndRemovedStrings(List<String> oldStrings, List<String> newStrings) {
        List<String> addedStrings = new ArrayList<>();
        List<String> removedStrings = new ArrayList<>();

        Map<String, Integer> hashedOldString = new HashMap<>();
        for (String oldString : oldStrings) {
            hashedOldString.compute(oldString, (_, value)-> (value == null)? 1 : value + 1);
        }

        Map<String, Integer> hashedNewString = new HashMap<>();
        for (String newString : newStrings) {
            hashedNewString.compute(newString, (_, value)-> (value == null)? 1 : value + 1);
        }

        for (String key: hashedOldString.keySet()) {
            if (hashedNewString.containsKey(key)) {
                int countString  = hashedNewString.get(key) - hashedOldString.get(key);

                if (countString > 0){
                    for(int i =0; i<countString; i++){
                        addedStrings.add(key);
                    }
                }

                if (countString < 0){
                    for(int i =0; i<(-countString); i++){
                        removedStrings.add(key);
                    }
                }
            }else{
                for (int i=0; i<hashedOldString.get(key); i++){
                    removedStrings.add(key);
                }
            }
        }

        for (String newKey: hashedNewString.keySet()) {
            if (!hashedOldString.containsKey(newKey)) {
                for (int i=0; i<hashedNewString.get(newKey); i++){
                    addedStrings.add(newKey);
                }
            }
        }


        return Arrays.asList(addedStrings, removedStrings);
    }
}
