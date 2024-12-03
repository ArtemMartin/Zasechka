/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.zasechka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class Zasechka {

    private static ZasechkaFrame frame;
    private static String message;
    private static String[] strArr;
    private static double[] xy;

    public static void main(String[] args) {
        frame = new ZasechkaFrame();
        frame.setVisible(true);

        frame.getBtnZapisat().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                message = frame.getTfMessage().getText();
                strArr = message.split("[ .,/\n=]");
                xy = getXY(strArr);
                System.out.println(xy[0] + " " + xy[1]);
                GenerateKML metka = new GenerateKML(getNewName(frame.getBoxHaracter().getSelectedItem().toString()),
                        frame, xy);
                metka.generate();
                System.exit(0);
            }
        });

    }

    //получение нового имени
    public static String getNewName(String name) {
        boolean proverka = false;
        String nameNew;
        File directory = new File("D:\\YO_NA\\Zasechki");
        File[] list = directory.listFiles();

        nameNew = name + getRandNumber();

        proverka = getSovpadenie(list, nameNew, proverka);

        if (!proverka) {
            return nameNew;
        } else {
            while (proverka) {
                nameNew = name + getRandNumber();
                proverka = getSovpadenie(list, nameNew, proverka);
            }
            return nameNew;
        }

    }

    //проверить папку на совпадение имен
    public static boolean getSovpadenie(File[] list, String name, boolean proverka) {
        if (list != null) {
            for (File file : list) {
                if (file.getName().equals(name)) {
                    proverka = true;
                }
            }
        }
        return proverka;
    }

    //сгенерировать номер
    public static int getRandNumber() {
        int min = 1;
        int max = 1000;
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    //исчем X, Y
    public static double[] getXY(String[] strArr) {
        double x;
        double y;
        Pattern pattern = Pattern.compile("\\d");//аблон для поиска цыфр
        Matcher matcher;

        int i;
        for (i = 0; i < strArr.length; i++) {
            matcher = pattern.matcher(strArr[i]);//Создаем объект Matcher для работы со строкой
            String strChis = "";
            while (matcher.find()) {
                strChis = strChis + matcher.group();
            }
            if (!strChis.equals("") && strChis.length() >= 5) {
                x = Double.parseDouble(strChis);
                for (i++; i < strArr.length; i++) {
                    matcher = pattern.matcher(strArr[i]);//Создаем объект Matcher для работы со строкой
                    strChis = "";
                    while (matcher.find()) {
                        strChis = strChis + matcher.group();
                    }
                    if (!strChis.equals("") && strChis.length() >= 5) {
                        y = Double.parseDouble(strChis);
                        return new double[]{x, y};
                    }
                }

            }
        }
        return null;
    }

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hours = cal.get(Calendar.HOUR_OF_DAY); // Получение времени в 24-часовом формате
        int minutes = cal.get(Calendar.MINUTE);
        if (minutes < 10) {
            return hours + ":" + "0" + minutes;
        } else {
            return hours + ":" + minutes;
        }
    }
}
