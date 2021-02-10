package com.darthyk.aggromanagement.csvparser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CsvTableParser {
    public static CSVParser parseFile(File file) throws IOException {
        Reader in = new FileReader(file);
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';');
        CSVParser parser = new CSVParser(in, format);
        return parser;
    }
}
