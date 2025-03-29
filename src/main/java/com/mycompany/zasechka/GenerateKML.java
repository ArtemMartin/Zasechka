/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zasechka;

import org.osgeo.proj4j.*;

import static com.mycompany.zasechka.Zasechka.getTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
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

    public static List<Double> refactorXYtoBL(double x, double y) {
        List<Double> list = new ArrayList();
        if (x < 99999) {
            x += 5300000;
        }

        if (y < 99999) {
            if (y > 50000) {
                y += 7300000;
            } else {
                y += 7400000;
            }
        }

// Создаем исходную и целевую системы координат
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:28407");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:4326");

        // Создаем объект для преобразования координат
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(srcCRS, dstCRS);

        // Преобразуем координаты
        //сначала вводим долготу потом широту
        ProjCoordinate srcCoord = new ProjCoordinate(y, x);
        ProjCoordinate dstCoord = new ProjCoordinate();
        transform.transform(srcCoord, dstCoord);

        // Выводим результат(наоборот x->y)
        //System.out.println("Преобразованные координаты: " + dstCoord.x + ", " + dstCoord.y);
        //возвращаем масив b, l
        list.add(dstCoord.y);
        list.add(dstCoord.x);
        return list;
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
        } else if (harakterZeli.equals("ТЗБПЛА")) {
            return "малый бла пр1.png";
        } else if (harakterZeli.equals("пехота")) {
            return "дот типовой пр6.png";
        }
        return "arta.png";
    }

    public void generate() {
        List<Double> bl = refactorXYtoBL(x, y);
        double b = bl.get(0);
        double l = bl.get(1);

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
}
