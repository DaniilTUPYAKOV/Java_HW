package md2html;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;


public class Md2Html {
    public static void main(String[] args) {
        args = new String[] {"bat"};
        if (args.length != 2){
            System.out.println("Not enough filenames. Expexted: inputFilename outputFilename, Has: " 
            + Arrays.toString(args).replaceAll("\\[|]", "") + "."
            );
            System.exit(0);
        }
        StringBuilder result = new StringBuilder();
        try {
            Parser parser = new Parser(args[0], result);
            parser.Parse();
            parser.Close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file '" + args[0] +"' not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error while closing the input file '"+args[0] +"': "+ e.getMessage());
        }
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF-8"));
            out.write(result.toString());
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Output file '" + args[1] +"' not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error while closing the output file '"+args[0] +"': "+ e.getMessage());
        }
    } 
}
