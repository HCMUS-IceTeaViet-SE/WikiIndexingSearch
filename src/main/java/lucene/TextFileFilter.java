package main.java.lucene;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Genius Doan on 4/20/2017.
 *
 * This class is for filtering text files
 */
public class TextFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".txt");
    }
}