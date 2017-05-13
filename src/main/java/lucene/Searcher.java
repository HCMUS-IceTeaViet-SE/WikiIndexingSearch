package main.java.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by Genius Doan on 4/20/2017.
 * This is the main class to search data from indexed files
 */
public class Searcher {

    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
    Query query;

    public Searcher(String indexDirectoryPath, String property)
            throws IOException {
        Directory indexDirectory =
                FSDirectory.open(new File(indexDirectoryPath));
        indexSearcher = new IndexSearcher(indexDirectory);
        queryParser = new QueryParser(Version.LUCENE_36, property, analyzer);
    }

    /*
    * Use for search data.
    * Search with property specified in main constructor
    *
    * @see LuceneTester.search()
     */
    public TopDocs search(String searchQuery)
            throws IOException, ParseException {
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
    }

    //Helper
    public Document getDocument(ScoreDoc scoreDoc)
            throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
        indexSearcher.close();
    }
}