package com.apd545.hotel;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class CsvUtil {

    // simple csv export
    public static void exportReport(File file,
                                    List<?> revenue,
                                    List<?> occupancy,
                                    List<?> logs) {

        try (FileWriter fw = new FileWriter(file)) {

            fw.write("=== Revenue ===\n");
            for (Object o : revenue) fw.write(o.toString() + "\n");

            fw.write("\n=== Occupancy ===\n");
            for (Object o : occupancy) fw.write(o.toString() + "\n");

            fw.write("\n=== Activity Logs ===\n");
            for (Object o : logs) fw.write(o.toString() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
