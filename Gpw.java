/* GPW - Generate pronounceable passwords
   This program uses statistics on the frequency of three-letter sequences
   in English to generate passwords.  The statistics are
   generated from your dictionary by the program loadtris.

   THVV 06/01/94 Coded
   THVV 04/14/96 converted to Java
   THVV 07/30/97 fixed for Netscape 4.0
   */

import java.util.Random;
import java.io.*;

@SuppressWarnings("serial")
public class Gpw implements Serializable {
        int npw;    // number of passwords to generate
        int pwl;    // length of the individual passwords

        public Gpw() {
            this(12, 8);
        }

        public Gpw(int npw, int pwl) {
            this.npw = npw;
            this.pwl = pwl;
        }

        public int getNpw() {
            return npw;
        }

        public void setNpw(int npw) {
            this.npw = npw;
        }

        public int getPwl() {
            return pwl;
        }

        public void setPwl(int pwl) {
            this.pwl = pwl;
        }

	public String[] generate() {
		int c1, c2, c3;
		long sum = 0;
		int nchar;
		long ranno;
		int pwnum;
		double pik;
		StringBuffer password;
		String[] result = new String[npw];
		Random ran = new Random(); // new random source seeded by clock

		// Pick a random starting point.
		for (pwnum=0; pwnum < npw; pwnum++) {
			password = new StringBuffer(pwl);
			pik = ran.nextDouble(); // random number [0,1]
			ranno = (long)(pik * sigma); // weight by sum of frequencies
			sum = 0;
			for (c1=0; c1 < 26; c1++) {
				for (c2=0; c2 < 26; c2++) {
					for (c3=0; c3 < 26; c3++) {
						sum += tris[c1][c2][c3];
						if (sum > ranno) {
							password.append(alphabet.charAt(c1));
							password.append(alphabet.charAt(c2));
							password.append(alphabet.charAt(c3));
							c1 = 26; // Found start. Break all 3 loops.
							c2 = 26;
							c3 = 26;
						} // if sum
					} // for c3
				} // for c2
			} // for c1

			// Now do a random walk.
			nchar = 3;
			while (nchar < pwl) {
				c1 = alphabet.indexOf(password.charAt(nchar-2));
				c2 = alphabet.indexOf(password.charAt(nchar-1));
				sum = 0;
				for (c3=0; c3 < 26; c3++)
					sum += tris[c1][c2][c3];
				if (sum == 0) {
					break;	// exit while loop
				}
				pik = ran.nextDouble();
				ranno = (long)(pik * sum);
				sum = 0;
				for (c3=0; c3 < 26; c3++) {
					sum += tris[c1][c2][c3];
					if (sum > ranno) {
						password.append(alphabet.charAt(c3));
						c3 = 26; // break for loop
					} // if sum
				} // for c3
				nchar ++;
			} // while nchar
			result[pwnum] = password.toString();
		} // for pwnum
		return result;
	}

	final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

	static short tris[][][];
	static long sigma;

	static {
		tris = new short [26][26][26];
		try {
			DataInputStream in = new DataInputStream(
				new BufferedInputStream(
				Gpw.class.getResourceAsStream("tris.dat")));
			sigma = in.readLong();
			int c1, c2, c3;
			for (c1=0; c1 < 26; c1++)
				for (c2=0; c2 < 26; c2++)
					for (c3=0; c3 < 26; c3++)
						tris[c1][c2][c3] = in.readShort();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			tris = null;
		}
	}
}
