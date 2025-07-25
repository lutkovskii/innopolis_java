package homeWork_4;
import java.util.Random;
import java.util.Scanner;

public class Televison {

    private String brand;           // марка телевизора
    private int screenSize;         // размер экрана в дюймах
    private boolean isOn;           // включен/выключен
    private int currentChannel;     // текущий канал
    private int volume;             // уровень громкости
    private String model;           // модель телевизора


    public Televison() {
        this.brand = "Unknown";
        this.screenSize = 0;
        this.isOn = false;
        this.currentChannel = 1;
        this.volume = 50;
        this.model = "Unknown";
    }


    public Televison(String brand, int screenSize, String model) {
        this.brand = brand;
        this.screenSize = screenSize;
        this.model = model;
        this.isOn = false;
        this.currentChannel = 1;
        this.volume = 50;
    }


    public Televison(String brand, int screenSize, String model,
                                boolean isOn, int currentChannel, int volume) {
        this.brand = brand;
        this.screenSize = screenSize;
        this.model = model;
        this.isOn = isOn;
        this.currentChannel = currentChannel;
        this.volume = volume;
    }


    public String getBrand() {
        return brand;
    }

    public int getScreenSize() {
        return screenSize;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getCurrentChannel() {
        return currentChannel;
    }

    public int getVolume() {
        return volume;
    }

    public String getModel() {
        return model;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCurrentChannel(int currentChannel) {
        if (currentChannel > 0 && currentChannel <= 100) {
            this.currentChannel = currentChannel;
        } else {
            System.out.println("Неверный номер канала! Должен быть от 1 до 100.");
        }
    }

    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
        } else {
            System.out.println("Громкость должна быть от 0 до 100!");
        }
    }


    public void turnOn() {
        this.isOn = true;
        System.out.println("Телевизор " + brand + " включен");
    }

    public void turnOff() {
        this.isOn = false;
        System.out.println("Телевизор " + brand + " выключен");
    }

    public void changeChannel(int channel) {
        if (isOn) {
            setCurrentChannel(channel);
            System.out.println("Канал изменен на " + currentChannel);
        } else {
            System.out.println("Сначала включите телевизор!");
        }
    }

    public void increaseVolume() {
        if (isOn) {
            if (volume < 100) {
                volume++;
                System.out.println("Громкость увеличена: " + volume);
            } else {
                System.out.println("Максимальная громкость достигнута!");
            }
        } else {
            System.out.println("Сначала включите телевизор!");
        }
    }

    public void decreaseVolume() {
        if (isOn) {
            if (volume > 0) {
                volume--;
                System.out.println("Громкость уменьшена: " + volume);
            } else {
                System.out.println("Минимальная громкость достигнута!");
            }
        } else {
            System.out.println("Сначала включите телевизор!");
        }
    }

    public void showInfo() {
        System.out.println("=== Информация о телевизоре ===");
        System.out.println("Марка: " + brand);
        System.out.println("Модель: " + model);
        System.out.println("Размер экрана: " + screenSize + " дюймов");
        System.out.println("Состояние: " + (isOn ? "включен" : "выключен"));
        System.out.println("Текущий канал: " + currentChannel);
        System.out.println("Громкость: " + volume);
        System.out.println("===============================");
    }


    public void setRandomParameters() {
        Random random = new Random();
        String[] brands = {"Samsung", "LG", "Sony", "Philips", "Panasonic"};
        String[] models = {"Smart TV", "LED", "OLED", "QLED", "LCD"};

        this.brand = brands[random.nextInt(brands.length)];
        this.model = models[random.nextInt(models.length)];
        this.screenSize = 32 + random.nextInt(59); // от 32 до 90 дюймов
        this.currentChannel = 1 + random.nextInt(100); // от 1 до 100
        this.volume = random.nextInt(101); // от 0 до 100
    }
}