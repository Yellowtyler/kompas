package sample.Model;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;

public class ExcelReader {
//    private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);

    public static XSSFWorkbook readWorkbook(File file) throws NoSuchFileException {
        try {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            return  myExcelBook;
        }
        catch (Exception e) {
//            log.error("Can`t read file", e);
            throw new NoSuchFileException(e.toString());
        }
    }
}