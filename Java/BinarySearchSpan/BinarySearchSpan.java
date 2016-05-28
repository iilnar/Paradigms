/**
 * Created by Илнар on 23.02.2015.
 */


public class BinarySearchSpan {
   	//P: 0 < i < a.length && (type == 'l' || type == 'r')
   	static boolean compare(int[] a, int x, int i, char type) {
   		return (type == 'l') ? a[i] > x : a[i] >= x;
   	}
    //S: 

    //P: l < r && compare(a, x, l, type) == false && compare(a, x, r, type) == true
    static int binarySearchRecursive(int[] a, int x, int l, int r, char type) {
        if (r - l <= 1) {
            return (type == 'l') ? r : l;
        }
        int m = l + (r - l) / 2;
        if (compare(a, x, m, type)) {
            return binarySearchRecursive(a, x, m, r, type);
        } else {
            return binarySearchRecursive(a, x, l, m, type);
        }
    }
	//S:l < res <= r && ret = min i: a[i]  <= x

	//P: l < r && (type = 'l' or type == 'r')
    static int binarySearchIterative(int[] a, int x, int l, int r, char type) {
    	//I: l < r - 1 && a[l] > x && a[r] <= x 
    	while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (compare(a, x, m, type)) {
                l = m;
            } else {
                r = m;
            }
        }
        return (type == 'l') ? r : l;
    }
   	//S:l < res <= r && ret = min i: a[i] <= x

 	

    public static void main(String[] args) {
    	try {
	        int x = Integer.parseInt(args[0]);
    	    int n = args.length - 1;
        	int[] a = new int[n];
	        for (int i = 0; i < n; i++) {
    	        a[i] = Integer.parseInt(args[i + 1]);
        	}
			System.out.println(binarySearchIterative(a, x, -1, n, 'l') + " " + 
				(binarySearchRecursive(a, x, -1, n, 'r') - binarySearchIterative(a, x, -1, n, 'l') + 1));
		} catch (Throwable e) {
			System.out.println("Incorrect input");
		}
    }

}