package oop.lecture2.logger.impl;

import oop.lecture2.logger.Logger;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String data) {
        System.out.println("ConsoleLogger: " + data);
    }
}
