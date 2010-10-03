/*
 * $Id$
 * This is an unpublished work copyright (c) 2002 Jens-Uwe Mager
 * 30177 Hannover, Germany
 */

/*
 * <applet code="GpwView" width="500" height="400">
 * </applet>
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class GpwView extends JApplet {
        Gpw gpw = new Gpw();
	JFormattedTextField pwlen = new JFormattedTextField(new Integer(gpw.getPwl()));
	JFormattedTextField npw = new JFormattedTextField(new Integer(gpw.getNpw()));
	JTextArea results = new JTextArea(40, 65);

	public void init() {
		pwlen.setColumns(3);
		npw.setColumns(3);
		JPanel top = new JPanel();
		top.add(new JLabel("Length: "));
		top.add(pwlen);
		top.add(new JLabel("Count: "));
		top.add(npw);
		JButton gen = new JButton("Generate");
		gen.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				results.setText("");
				try {
                                        gpw.setNpw(((Number)npw.getValue()).intValue());
                                        gpw.setPwl(((Number)pwlen.getValue()).intValue());
					String[] pwlist = gpw.generate();
					for (int i = 0; i < pwlist.length; i++) {
						results.append(pwlist[i]);
						results.append("\n");
					}

				} catch (Throwable ex) {
					JOptionPane.showMessageDialog((Component)e.getSource(), ex, "Error", JOptionPane.ERROR_MESSAGE);
				}
			 }
		});
		top.add(gen);
		Container cp = getContentPane();
		cp.add(BorderLayout.NORTH, top);
		cp.add(new JScrollPane(results));
	}

	public static void main(String[] args) {
		JApplet applet = new GpwView();
		JFrame frame = new JFrame(/*applet.getClass().getName()*/"Gpw");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(applet);
		frame.setSize(500, 400);
		applet.init();
		//frame.pack();
		applet.start();
		frame.setVisible(true);
	}

    static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
    }
}
