import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    public int Add(String number) throws IllegalArgumentException {
        String[] delimiterArr = new String[0];
        if (StringUtils.isEmpty(number) || number.equals("0")) {
            return 0;
        } else if (number.startsWith("//[")) {
            int delimiterEndIndex = number.indexOf("]\n");
            String delimiters = number.substring(3, delimiterEndIndex);
            delimiterArr = delimiters.split("\\]\\[");
            for (int i = 0; i < delimiterArr.length; i++) {
                delimiterArr[i] = Pattern.quote(delimiterArr[i]);
            }
            number = number.substring(number.indexOf("]\n") + 2);
        } else if (number.startsWith("//")) {
            int delimiterEndIndex = number.indexOf("\n");
            delimiterArr = new String[]{number.substring(2, delimiterEndIndex)};
            number = number.substring(delimiterEndIndex + 1);
        } else if (number.contains("\n")) {
            if (number.indexOf("\n") == (number.length()-1)) {
               return 0;
            }
            number = number.replace("\n", ",");
        }
        if (delimiterArr.length == 0) {
            delimiterArr = new String[]{","};
        }

        String[] splitList = number.split(String.join("|", delimiterArr));

        List<Integer> negativeNumbersList = checkForNegativeNumbers(splitList);
        if (!negativeNumbersList.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed:"+  negativeNumbersList.toString().replaceAll("\\[|\\]", ""));
        }

        return Arrays.stream(splitList)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < 1000)
                .reduce(0, Integer::sum);
    }

    private List<Integer> checkForNegativeNumbers(String[] splitList) {
        return Arrays.stream(splitList)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < 0)
                .boxed()
                .toList();
    }
}
