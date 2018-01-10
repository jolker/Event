package cc.blisscorp.event.game.dev;

public class RadomNumberGenerator {
	/**
	 * 
	 * @param prefix
	 * @param r the random number
	 * @param lo
	 * @param hi
	 * @return Find the index that a[index] >= r
	 */
	public static int findCeil(int[] prefix, int r, int lo, int hi) {		
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (prefix[mid] < r) {
				lo = mid + 1;
			} else {
				hi = mid;
			}
		}
		return prefix[lo] >= r ? lo : -1;
	}

	public static int customizedRandom(int[] a, int[] freq) {
		//Create and fill the prefix array
		int[] prefix = new int[a.length];
		prefix[0] = freq[0];
		for (int i = 1; i < a.length; i++) {
			prefix[i] = freq[i - 1] + freq[i];
		}

		int r = (int)(Math.random() * prefix[a.length - 1]) + 1;
		//findCeil convert the random value to index of original array.
		int indexCeil = findCeil(prefix, r, 0, a.length - 1);
		return a[indexCeil];   
	}

	public static void main(String[] args) {
		try {
			int[] a = {2, 1, 0};
			int[] freq = {5, 15, 80}; // TODO: sort frequency
			for (int i = 0; i < 10; i ++) {
				int rand = customizedRandom(a, freq);
				System.out.println(rand);
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
