package oop.lecture2.logger.impl.files;

import oop.lecture2.logger.Logger;

import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;

public abstract class FileLogger implements Logger{
    protected String fileName;

    public FileLogger(){
        this.fileName = "default_logger" + getFileExtension();
    }


    public abstract String getFileExtension();

    @Override
    public void log(String data) {
        try(Writer writer = new FileWriter(fileName, true)){
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
