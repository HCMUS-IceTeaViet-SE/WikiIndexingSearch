package main.java;

import main.java.lucene.LuceneConstants;
import main.java.lucene.LuceneTester;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;

/**
 * Created by Genius Doan on 4/20/2017.
 */
public class Application {
    public static void main(final String[] args) throws Exception {
        //Un comment these lines to pre-process data
        //Utils.preprocessingData("src/main/res/data.txt", false, 500000, 510000);
        //Utils.preprocessingData("src/main/res/data.txt", false, 300000, 310000);

        //Main test class
        LuceneTester tester;
        try {
            //Init tester
            tester = new LuceneTester();
            tester.createIndex(); //Create index files and write to indexed folder
            tester.search("Cylindromyia", LuceneConstants.TITLE); //Place any string that you want to search here.
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
