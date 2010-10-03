
class GpwCmdLine {
	public static void main(String args[]) {
		int npw = 0;
		int pwl = 8;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-l")) {
				if (++i < args.length)
					pwl = Integer.parseInt(args[i]);
				else
					usageErr("option -l requires an argument");
			} else if (args[i].equalsIgnoreCase("-n")) {
				if (++i < args.length)
					npw = Integer.parseInt(args[i]);
				else
					usageErr("option -n requires an argument");
			} else
				usageErr("unknown option " + args[i]);
		}
		if (npw == 0) {
			GpwView.main(args);
			return;
		}
                Gpw gpw = new Gpw(npw, pwl);
		String[] pwlist = gpw.generate();
		for (int i = 0; i < pwlist.length; i++)
			System.out.println(pwlist[i]);
	}

	private static void usageErr(String msg) {
		if (msg != null)
			System.err.println(msg);
		System.err.println("Usage: Gpw [-l passwordlen] [-n numpasswd]");
		System.exit(1);
	}
}
