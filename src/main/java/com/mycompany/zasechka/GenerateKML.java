/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zasechka;

import static com.mycompany.zasechka.Zasechka.getTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author user
 */
public class GenerateKML {

    private File filePath;
    private String harakterZeli;
    private String zametka;
    private double x;
    private double y;
    private String namePng;

    public GenerateKML(String fileName, ZasechkaFrame frame, double[] arr) {
        this.filePath = new File("D:\\YO_NA\\Zasechki" + "\\" + fileName + ".kml");
        this.harakterZeli = frame.getBoxHaracter().getSelectedItem().toString();
        //this.zametka = frame.getpZametka().getText();
        this.x = arr[0];
        this.y = arr[1];
        namePng = getNamePng(harakterZeli);
    }

    public String getNamePng(String harakterZeli) {
        if (harakterZeli.equals("Арт орудие")) {
            return "arta.png";
        } else if (harakterZeli.equals("миномет")) {
            return "min.png";
        } else if (harakterZeli.equals("РСЗО")) {
            return "rszo.png";
        } else if (harakterZeli.equals("ат")) {
            return "авто пр2.png";
        } else if (harakterZeli.equals("бт")) {
            return "бмп2.png";
        } else if (harakterZeli.equals("Колонна")) {
            return "Колонна на восток.png";
        }else if (harakterZeli.equals("ТЗБПЛА")) {
            return "малый бла пр1.png";
        }
        return "arta.png";
    }

    public void generate() {
        double[] bl = getBLPoint(x, y);
        double b = bl[0];
        double l = bl[1];

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n"
                    + "  <Document>\n"
                    + "    <Placemark>\n");
            writer.write("<name>" + getTime() + " " + harakterZeli + "</name>\n");

            writer.write("<description>" + zametka + "</description>\n");
            writer.write("<Style>\n"
                    + "        <LabelStyle>\n"
                    + "          <color>FF00FFFF</color>\n"
                    + "          <scale>1.36363636363636</scale>\n"
                    + "        </LabelStyle>\n"
                    + "        <IconStyle>\n"
                    + "          <scale>0.625</scale>\n"
                    + "          <Icon>\n"
                    + "            <href>files/" + namePng + "</href>\n"
                    + "          </Icon>\n"
                    + "          <hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n"
                    + "        </IconStyle>\n"
                    + "      </Style>\n"
                    + "      <Point>\n"
                    + "        <extrude>1</extrude>\n");
            writer.write("<coordinates>" + l + "," + b + "," + 0 + "</coordinates>\n");
            writer.write(" </Point>\n"
                    + "  </Placemark>\n"
                    + " </Document>\n"
                    + "</kml>");
        } catch (IOException e) {
            System.out.println("Шляпа: " + GenerateKML.class.getName() + e.getMessage());
        }

    }

    public double[] getBLPoint(double x, double y) {
        if (x < 99999) {
            x += 5300000;
        }
        if (y < 99999) {
            y += 7300000;
        }
        x += getDX(y);
        y += -117.0;
        int nZonu = (int) Math.round(y * Math.pow(10.0, -6.0));
        double b = x / 6367558.4968;
        double B0 = b + Math.sin(2.0 * b) * (0.00252588685 - 1.49186E-5 * Math.pow(Math.sin(b), 2.0) + 1.1904E-7 * Math.pow(Math.sin(b), 4.0));
        double z0 = (y - (double) (10 * nZonu + 5) * Math.pow(10.0, 5.0)) / (6378245.0 * Math.cos(B0));
        double dB = -Math.pow(z0, 2.0) * Math.sin(2.0 * B0) * (0.251684631 - 0.003369263 * Math.pow(Math.sin(B0), 2.0) + 1.1276E-5 * Math.pow(Math.sin(B0), 4.0) - Math.pow(z0, 2.0) * 0.10500614 - 0.04559916 * Math.pow(Math.sin(B0), 2.0) + 0.00228901 * Math.pow(Math.sin(B0), 4.0) - 2.987E-5 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.042858 - 0.025318 * Math.pow(Math.sin(B0), 2.0) + 0.014346 * Math.pow(Math.sin(B0), 4.0) - 0.001264 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.01672 - 0.0063 * Math.pow(Math.sin(B0), 2.0) + 0.01188 * Math.pow(Math.sin(B0), 4.0) - 0.00328 * Math.pow(Math.sin(B0), 6.0))));
        double l = z0 * (1.0 - 0.0033467108 * Math.pow(Math.sin(B0), 2.0) - 5.6002E-6 * Math.pow(Math.sin(B0), 4.0) - 1.87E-8 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.16778975 + 0.16273586 * Math.pow(Math.sin(B0), 2.0) - 5.249E-4 * Math.pow(Math.sin(B0), 4.0) - 8.46E-6 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.0420025 + 0.1487407 * Math.pow(Math.sin(B0), 2.0) + 0.005942 * Math.pow(Math.sin(B0), 4.0) - 1.5E-5 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.01225 + 0.09477 * Math.pow(Math.sin(B0), 2.0) + 0.03282 * Math.pow(Math.sin(B0), 4.0) - 3.4E-4 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.0038 + 0.0524 * Math.pow(Math.sin(B0), 2.0) + 0.0482 * Math.pow(Math.sin(B0), 4.0) + 0.0032 * Math.pow(Math.sin(B0), 6.0))))));
        double B = (B0 + dB) * 180.0 / Math.PI;
        double L = (6.0 * ((double) nZonu - 0.5) / 57.29577951 + l) * 180.0 / Math.PI;

        return new double[]{B, L};
    }

    public double getDX(double y) {
        double srY = 7423378.5;
        double koef = 339.0 / 140633.0;
        double dX = (y - srY) * koef + (-59.0);
        return dX;
    }

    /*
    KT1 5389480 7423290
    KT2 5277078 7423467
    KT3 5282939 7282745
    KT4 5392852 7293194
    
    приращение между 1 и 2 КТ -59м
    приращение между 3 и 4 КТ -398м
    приращение между 12КТ и 34КТ -339м
    Ср. дальность между рубежом 12 и 34 140633
    Коеф. приращ 339/140633 и добавить -59 для определения поправки
     */
}
