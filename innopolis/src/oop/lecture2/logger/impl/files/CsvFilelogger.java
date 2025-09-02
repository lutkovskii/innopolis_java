package oop.lecture2.logger.impl.files;

public class CsvFilelogger extends FileLogger {

    public CsvFilelogger(String fileName){
        super.fileName = fileName + getFileExtension();
    }

    public CsvFilelogger(){
        super();
    }

    @Override
    public String getFileExtension(){
        return ".csv";
    }
}
