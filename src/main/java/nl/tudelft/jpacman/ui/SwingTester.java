package nl.tudelft.jpacman.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
public class SwingTester {

    public SwingTester()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                // create jeditorpane
                JEditorPane jEditorPane = new JEditorPane();

                // make it read-only
                jEditorPane.setEditable(false);

                // create a scrollpane; modify its attributes as desired
                JScrollPane scrollPane = new JScrollPane(jEditorPane);

                // add an html editor kit
                HTMLEditorKit kit = new HTMLEditorKit();
                jEditorPane.setEditorKit(kit);

                // add some styles to the html
                StyleSheet styleSheet = kit.getStyleSheet();
                styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
                styleSheet.addRule("h1 {color: blue;}");
                styleSheet.addRule("h2 {color: #ff0000;}");
                styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
                // create some simple html as a string
                String htmlString = "<html>\n"
                    + "<body>\n"
                    + "<div align=center>\n"
                    + "<h1>\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89 Score Board \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89</h1>\n"
                    + "</div>\n"
                    + "<div class=\"score\">\n"
                    + "<table style=\"width:100%;\">\n"
                    + "<tr>\n"
                    + "<th>No</th>\n"
                    + "<th>Name</th>\n"
                    + "<th>Point</th>\n"
                    + "<th>Time</th>\n"
                    + "</tr>\n"

                    // input each score
                    + "<tr>\n"
                    + "<td></td>\n"
                    + "<td></td>\n"
                    + "<td></td>\n"
                    + "<td></td>\n"
                    + "</tr>\n"
                    // end

                    + "</table>\n"
                    + "</div>\n"
                    + "</body>\n";

                // create a document, set it on the jeditorpane, then add the html
                Document doc = kit.createDefaultDocument();
                jEditorPane.setDocument(doc);
                jEditorPane.setText(htmlString);

                // now add it all to a frame
                JFrame j = new JFrame("HtmlEditorKit Test");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // make it easy to close the application
                j.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                // display the frame
                j.setSize(new Dimension(400,600));

                // pack it, if you prefer
                //j.pack();

                // center the jframe, then make it visible
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
        });
    }
}
