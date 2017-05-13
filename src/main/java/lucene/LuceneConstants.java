package main.java.lucene;

/**
 * Created by Genius Doan on 4/20/2017.
 * <p>
 * This class store constants used as flag of searcher,...
 */
public class LuceneConstants {
    /* Search all contents of data files (url link, title, summary) */
    public static final String CONTENTS = "contents";

    /* Search only url link of data files */
    public static final String URL = "url";

    /* Search only post title link of data files */
    public static final String TITLE = "title";

    /* Search only file name of pre-processed files*/
    public static final String FILE_NAME = "file_name";

    /* Search only file path of pre-processed files*/
    public static final String FILE_PATH = "file_path";

    /* Search only summary text of data files*/
    public static final String SUMMARY = "summary";

    /* Maximum search */
    public static final int MAX_SEARCH = 100;
}