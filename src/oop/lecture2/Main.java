package oop.lecture2;

import oop.lecture2.logger.impl.ConsoleLogger;
import oop.lecture2.logger.Logger;
import oop.lecture2.logger.impl.files.CsvFilelogger;
import oop.lecture2.logger.impl.files.TxtFileLogger;

public class Main {
    public static void main(String[] args) {
        Logger myConsoleLogger = new ConsoleLogger();
        myConsoleLogger.log("Hello from log!");

        Logger myTxtLogger = new TxtFileLogger();
        myTxtLogger.log("Hello from txt logger!");

        Logger myCsvLogger = new CsvFilelogger("super_csv_log");
        myCsvLogger.log("Hello, From, CSV, Logger");
    }
}
