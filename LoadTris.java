import java.io.*;

public class LoadTris {

public static void main (String[] args) throws Throwable {
	short tris[][][] = new short[26][26][26];			/* Trigraph frequencies */
	long sigma = 0;					/* Total letters */
	int nfiles = 0;

	if (args.length < 1) {
		System.err.println("Usage: java LoadTris /usr/share/dict/words ... ");
		System.exit(1);
	}
	for (int argno = 0; argno < args.length; argno++) {
		BufferedReader in = new BufferedReader(new FileReader(args[argno]));
		nfiles++;
		String line;
		while ((line = in.readLine()) != null) {
			int j = 0;					/* j indexes the input */
			int k1, k2, k3;
			k2 = -1;				/* k1, k2 are coords of previous letter */
			k1 = -1;
			while (j < line.length()) {		/* until we find the null char.. */
				k3 = line.charAt(j);		/* Pick out a letter from the input */
				if (k3 > 'Z') {
					k3 = k3 - 'a';	/* map from a-z to 0-25 */
				}
				else {
					k3 = k3 - 'A';	/* map from A-Z to 0-25 */
				}
				if (k3 >= 0 && k3 <= 25) { /* valid subscript? */
					if (k1 >= 0) { /* do we have 3 letters? */
						tris[k1][k2][k3]++;	/* count */
						sigma++;			/* grand total */
					}
					k1 = k2;		/* shift over */
					k2 = k3;
				}
				j++;
			}						/* while buf[j] */
		}							/* while fgets */
		in.close();
	}							    /* for argno */

	if (nfiles != 0) {				    /* find any input? */
		System.out.println("processed " + nfiles + " file(s), sigma is " + sigma);
		DataOutputStream out = new DataOutputStream(
			new BufferedOutputStream(
			new FileOutputStream("tris.dat")));
		out.writeLong(sigma);
		int c1, c2, c3;
		for (c1=0; c1 < 26; c1++)
			for (c2=0; c2 < 26; c2++)
				for (c3=0; c3 < 26; c3++)
					out.writeShort(tris[c1][c2][c3]);
		out.close();
	}
}
}
