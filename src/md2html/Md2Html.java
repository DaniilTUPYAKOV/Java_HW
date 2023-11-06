package md2html;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Md2Html {
    public static void main(String[] args) {
        // args = new String[] { "in.txt", "out.txt" };
        StringBuilder result = new StringBuilder();
        try {
            Parser parser = new Parser(args[0], result);
            parser.Parse();
            parser.Close();
        } catch (IOException e) {
            System.out.println("Fix me!");
        }
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF-8"));
            out.write(result.toString());
            out.close();
        } catch (IOException e) {
            System.out.println("Fix me!");
        }
    } 
}
