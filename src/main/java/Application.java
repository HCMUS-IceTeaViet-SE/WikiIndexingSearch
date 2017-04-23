package main.java;

import main.java.lucene.LuceneTester;
import org.apache.lucene.queryParser.ParseException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Genius Doan on 4/20/2017.
 */
public class Application
{
    private static final Pattern PATTERN_HTTP_LINK = Pattern.compile("http[s]*://(\\w+\\.)*(\\w+)/wiki/*");
    public static void main(final String[] args) throws Exception {
        LuceneTester tester;
        //splitFile();
        try {
            tester = new LuceneTester();
            tester.createIndex(); //Create index files and write to indexed folder
            tester.search("tiểu hành tinh");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void splitFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("./src/test/data.txt"));
            BufferedWriter bufferedWriter = null;
            String line;
            long count = 1;
            while ((line = br.readLine()) != null) {
                Matcher matcher = PATTERN_HTTP_LINK.matcher(line);
                while (matcher.find()) {
                    String w = matcher.group();
                    line = line.replace(w, ""); //Remove old data
                }

                String fileName = "./src/data/" +String.valueOf(count) + ".txt";
                FileWriter fw = new FileWriter(fileName);
                bufferedWriter = new BufferedWriter(fw);
                bufferedWriter.write(line);
                bufferedWriter.close();
                System.out.println(count);
                count ++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
