/**
 * Created by Илнар on 10.02.2015.
 */

public class SumLongHex {
    public static void main(String[] args) {
    	long ans = 0;
        for (int i = 0; i < args.length; i++) {
        	StringBuilder number = new StringBuilder();
        	for (int j = 0; j < args[i].length(); j++) {
        	    if (Character.isWhitespace(args[i].charAt(j))) {
        	    	continue;
        	    }
				while (j < args[i].length() && !Character.isWhitespace(args[i].charAt(j))) {
					number.append(args[i].charAt(j));
					j++;
				}
				String num = number.toString();
				try {
					if (num.startsWith("0x") || num.startsWith("0X")) {
						ans += Long.parseUnsignedLong(num.substring(2), 16);
					} else {
						ans += Long.parseLong(num, 10); 
					}
					number.delete(0, number.length());
				} catch (Throwable e) {
					System.out.println("Incorrect input. Please use only decimal and hex numbers.");
					return;
				}
        	}
        }

	    System.out.println(ans);
    }
}
/*        for (int i = 0; i < args.length; i++) {
            int num = 0;
            boolean isNegative = false;
            for (int j = 0; j < args[i].length(); j++) {
                if (args[i].charAt(j) == '-') {
                    isNegative = true;
                }
                if (!Character.isDigit(args[i].charAt(j))) {
                    continue;
                }
                while (j < args[i].length() && Character.isDigit(args[i].charAt(j))) {
                    num = num * 10 + (args[i].charAt(j) - '0');
                    j++;
                }
                if (isNegative) {
                    num *= -1;
                    isNegative = false;
                }
                ans += num;
                num = 0;
            }
        }
*/
/*        for (int i = 0; i < args.length; i++) {
            String[] nums = args[i].split("[^-+0-9a-zA-Z]");
            for (int j = 0; j < nums.length; j++) {
                if (!nums[j].isEmpty()) {
	                try {
    	            	if (nums[j].startsWith("0x") || nums[j].startsWith("0X")) {
        	        		ans = ans + Long.parseUnsignedLong(nums[j].substring(2), 16);
            	    	} else {
    	            			ans = ans + Long.parseLong(nums[j], 10);
    	            	}
    	            } catch (Throwable e) {
    	            	System.out.println("Wrong input. Please use only decimal or hex numbers.");
    	            	return;
    	            }
                }
            }
        }
*/