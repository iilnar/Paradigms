/**
 * Created by Илнар on 23.02.2015.
 */


public class BinarySearch {
   	//P; l < r && a[l] > x = false && a[r] >= x = true
    static int BinarySearchRecursive(int[] a, int x, int l, int r) {
        if (r - l <= 1) {
            return r;
        }
        int m = l + (r - l) / 2;
        if (a[m] > x) {
            return BinarySearchRecursive(a, x, m, r);
        } else {
            return BinarySearchRecursive(a, x, l, m);
        }
    }
	//S:l < res <= r && ret = min i: a[i]  <= x
	//P;l < r
    static int BinarySearchIterative(int[] a, int x, int l, int r) {
    	//I: l < r - 1 && a[l] > x && a[r] <= x 
    	while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (a[m] > x) {
                l = m;
            } else {
                r =m;
            }
        }
        return r;
    }
	//S:l < res <= r && ret = min i: a[i] <= x

   	static boolean compare(int[] a, int x, int i, char type) {
   		return (type == 'l') ? a[i] > x : a[i]  >= x;
   	}

    static int BinarySearchRecursive(int[] a, int x, int l, int r, char type) {
        if (r - l <= 1) {
            return r;
        }
        int m = l + (r - l) / 2;
        if (compare(a, x, m, type)) {
            return BinarySearchRecursive(a, x, m, r, type);
        } else {
            return BinarySearchRecursive(a, x, l, m, type);
        }
    }
	//S:l < res <= r && ret = min i: a[i]  <= x

	//P: l < r
    static int BinarySearchIterative(int[] a, int x, int l, int r, char type) {
    	//I: l < r - 1 && a[l] > x && a[r] <= x 
    	while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (compare(a, x, m, type)) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }


    public static void main(String[] args) {
    	try {
	        int x = Integer.parseInt(args[0]);
    	    int n = args.length - 1;
        	int[] a = new int[n];
	        for (int i = 0; i < n; i++) {
    	        a[i] = Integer.parseInt(args[i + 1]);
        	}
			System.out.println(BinarySearchRecursive(a, x, -1, n, 'l'));
		} catch (Throwable e) {
			System.out.println("Incorrect input");
		}
    }

}