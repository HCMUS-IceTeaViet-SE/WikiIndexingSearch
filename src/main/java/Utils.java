package main.java;

import main.java.lucene.LuceneConstants;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GeniusDoan on 13/05/2017.
 * <p>
 * This class is use for storing utilities
 */
public class Utils {
    /*
     * @param  line  line data of each document
      * @param  propertyName property name of what u want to get
      * @return      the String present for property
      */
    public static String getProperty(String line, String propertyName) {
        String[] properties = line.split("\t");
        if (propertyName.equals(LuceneConstants.URL)) {
            return properties[0];
        } else if (propertyName.equals(LuceneConstants.TITLE)) {
            return properties[1];
        } else if (propertyName.equals(LuceneConstants.CONTENTS)) {
            return properties[2];
        } else {
            return "";
        }
    }

    /*
    * @param  reader  file reader of the file you want to read data from
     * @return      the String content of file
     */
    public static String readLineFromFile(FileReader reader) throws IOException {
        if (reader != null) {
            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String s;

            while ((s = br.readLine()) != null) {
                buffer.append(s);
            }

            br.close();
            reader.close();

            return buffer.toString();
        }

        return "";
    }

    /*
   * @param  filePath  file path to the file that you want to pre-processing data
   * @param   removeUrlLink flag for removing url link from file content
   * @param from the beginning index of the file that you want to get
   * @param to the end index of the file that you want to get
    * @return      the String content of file
    */
    public static void preprocessingData(String filePath, boolean removeUrlLink, int from, int to) {
        if (filePath == null || filePath.isEmpty())
            filePath = "src/main/res/data/data.txt";
        final Pattern PATTERN_HTTP_LINK = Pattern.compile("http[s]*://(\\w+\\.)*(\\w+)/wiki/*");
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bufferedWriter = null;
            String line;
            long count = 1;
            while ((line = br.readLine()) != null) {
                if (count < from) {
                    count++;
                    continue;
                }

                if (count > to) {
                    break;
                }

                if (removeUrlLink) {
                    Matcher matcher = PATTERN_HTTP_LINK.matcher(line);
                    while (matcher.find()) {
                        String w = matcher.group();
                        line = line.replace(w, ""); //Remove html link
                    }
                }

                String fileName = "src/main/res/data/" + String.valueOf(count) + ".txt";
                File f = new File(fileName);
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                bufferedWriter = new BufferedWriter(fw);
                bufferedWriter.write(line);
                bufferedWriter.close();
                System.out.println(count);
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data pre-processing finished!");
    }
}
