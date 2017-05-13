package main.java.lucene;

import main.java.Utils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Genius Doan
 *         This is the main class to index files.
 */
public class Indexer {
    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        //this directory will contain the indexes
        Directory indexDirectory =
                FSDirectory.open(new File(indexDirectoryPath));

        //create the indexer
        writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_36), true, IndexWriter.MaxFieldLength.LIMITED);
    }


    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        String line = "";
        try {
            line = Utils.readLineFromFile(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //index file contents
        Field contentField = new Field(LuceneConstants.CONTENTS, new FileReader(file));
        //index file name
        Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES, Field.Index.NO);
        //index file path
        Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES, Field.Index.NO);
        Field urlField = new Field(LuceneConstants.URL, Utils.getProperty(line, LuceneConstants.URL), Field.Store.YES, Field.Index.NO);
        Field titleField = new Field(LuceneConstants.TITLE, Utils.getProperty(line, LuceneConstants.TITLE), Field.Store.YES, Field.Index.ANALYZED);
        Field summaryField = new Field(LuceneConstants.SUMMARY, Utils.getProperty(line, LuceneConstants.SUMMARY), Field.Store.YES, Field.Index.NOT_ANALYZED);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);
        document.add(urlField);
        document.add(titleField);
        document.add(summaryField);

        return document;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing " + file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter)
            throws IOException {
        //get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();

        if (files == null)
            files = new File[0];

        for (File file : files) {
            if (!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)
                    ) {
                indexFile(file);
            }
        }
        return writer.numDocs();
    }
}