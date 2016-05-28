/**
 * Created by Илнар on 10.02.2015.
 */

import java.util.StringTokenizer;

public class Sum {
    public static void main(String[] args) {
        System.out.println(stringTokenizer(args));
//        System.out.println(ownParse(args));
//        System.out.println("Press any key to continue...");
//        new java.util.Scanner(System.in).nextLine();
    }

    static int ownParse(String[] args) {
        int ans = 0;
        for (int i = 0; i < args.length; i++) {
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
        return ans;
    }

    static int regex(String[] args) {
        int ans = 0;
        for (int i = 0; i < args.length; i++) {
            String[] nums = args[i].split("\\s");
            for (int j = 0; j < nums.length; j++) {
                if (!nums[j].isEmpty()) {
                	try {
	                    ans = ans + Integer.parseInt(nums[j]);
	                } catch (NumberFormatException e) {
	                	System.out.println("Something wrong at string: \"" + args[i] + "\"");
	                	System.exit(0);
	                }
                }
            }
        }
        return ans;
    }

    static int stringTokenizer(String[] args) {
    	int ans = 0;
    	for (int i = 0; i < args.length; i++) {
	    	StringTokenizer st = new StringTokenizer(args[i], " \t\n\u000B\f");
	    	while (st.hasMoreTokens()) {
   		    	try {
			   		ans = ans + Integer.parseInt(st.nextToken());
				} catch (NumberFormatException e) {
			    	System.out.println("Something wrong at string: \"" + args[i] + "\"");
			    	System.exit(0);
	    		}
	    	}
	    }
	    return ans;
    }
}