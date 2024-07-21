package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Objects;

public class SegmentLengthAnalyze {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SegmentLengthAnalyze.class.getResourceAsStream("/data.txt"))))) {

            double maxLength = reader.lines()
                    .mapToDouble(SegmentLengthAnalyze::calculateSegmentLength)
                    .max()
                    .orElse(0.0);

            System.out.println("\nМаксимальная длина: " + DECIMAL_FORMAT.format(maxLength));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateSegmentLength(String line) {
        try {
            String[] parts = line.split("\\)-\\(");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Некорректный формат строки: " + line);
            }

            double[] startCoordinates = parseCoordinates(parts[0]);
            double[] endCoordinates = parseCoordinates(parts[1]);

            double x1 = startCoordinates[0];
            double y1 = startCoordinates[1];
            double x2 = endCoordinates[0];
            double y2 = endCoordinates[1];

            double length = calculateDistance(x1, y1, x2, y2);
            System.out.printf("x1:%s, y1:%s-x2:%s, y2:%s, \nДлина: %s%n",
                    x1, y1, x2, y2, DECIMAL_FORMAT.format(length));
            return length;
        } catch (Exception e) {
            System.err.println("Ошибка обработки строки: " + line);
            e.printStackTrace();
            return 0.0; // Возвращаем 0 в случае ошибки
        }
    }

    private static double[] parseCoordinates(String part) {
        String cleanedPart = part.replaceAll("[^0-9,]", "");
        String[] coordinateStrings = cleanedPart.split(",");
        if (coordinateStrings.length != 2) {
            throw new IllegalArgumentException("Некорректный формат координат: " + part);
        }
        return new double[]{
                Double.parseDouble(coordinateStrings[0]),
                Double.parseDouble(coordinateStrings[1])
        };
    }

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
