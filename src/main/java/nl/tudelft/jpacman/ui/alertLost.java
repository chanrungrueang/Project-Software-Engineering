package nl.tudelft.jpacman.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class alertLost extends JFrame implements ActionListener {
    // JTextField
    static JTextField t;

    // JFrame
    static JFrame f;

    // JButton
    static JButton b;

    // label to display text
    static JLabel l;

    static JLabel scoreText;

    // default constructor
    public alertLost()
    {
    }

    // main class
    public void show()
    {
        // create a new frame to store text field and button
        f = new JFrame("textfield");

        // create a label to display text
        l = new JLabel("please enter your name");

        scoreText = new JLabel("Your Score is 99");
        scoreText.setHorizontalAlignment((int) JPanel.CENTER_ALIGNMENT);
        // create a new button
        b = new JButton("submit");

        // create a object of the text class
        alertLost te = new alertLost();

        // addActionListener to button
        b.addActionListener(te);

        // create a object of JTextField with 16 columns
        t = new JTextField(16);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(scoreText);
        p.add(l);
        p.add(t);
        p.add(b);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(300, 100);

        f.show();
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("submit")) {
            // set the text of the label to the text of the field
            l.setText(t.getText());

            // set the text of field to blank
            t.setText("  ");
        }
    }
}
