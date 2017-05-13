package main.java.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 * Created by Genius Doan on 4/20/2017.
 * Use to test feature of other class.
 * Use it as a tester class. Write more functions to test
 */
public class LuceneTester {

    String indexDir = "./src/main/res/indexed";
    String dataDir = "./src/main/res/data";
    Indexer indexer;
    Searcher searcher;

    /*
    Create indexing files for using to search
     */
    public void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int indexedCount;
        long startTime = System.currentTimeMillis();

        indexedCount = indexer.createIndex(dataDir, new TextFileFilter());
        long endTime = System.currentTimeMillis();
        indexer.close();

        System.out.println(indexedCount + " File indexed, time taken: "
                + (endTime - startTime) + " ms");
    }

    /*
    * Create indexing files for using to search
    *
    * @param searchQuery the query of user
    * @param property property name of which you want to search
    */
    public void search(String searchQuery, String property) throws IOException, ParseException {
        searcher = new Searcher(indexDir, property);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime));
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
                    + doc.get(LuceneConstants.FILE_PATH));

        }
        searcher.close();
    }

    //Helper
    public void setDataDirectory(String path) {
        this.dataDir = path;
    }

    public void setIndexDirectory(String path) {
        this.indexDir = path;
    }
}