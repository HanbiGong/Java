package com.apd545.hotel;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfUtil {

    // simple pdf export
    public static void exportReport(File file,
                                    List<?> revenue,
                                    List<?> occupancy,
                                    List<?> logs) {

        try {

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            doc.add(new Paragraph("Hotel Reports", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            doc.add(new Paragraph("\n"));

            doc.add(new Paragraph("Revenue", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            for (Object o : revenue) doc.add(new Paragraph(o.toString()));
            doc.add(new Paragraph("\n"));

            doc.add(new Paragraph("Occupancy", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            for (Object o : occupancy) doc.add(new Paragraph(o.toString()));
            doc.add(new Paragraph("\n"));

            doc.add(new Paragraph("Activity Logs", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            for (Object o : logs) doc.add(new Paragraph(o.toString()));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
