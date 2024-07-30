package com.jobApplication.jobApplication.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jobApplication.jobApplication.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private MedicationService medicationService;

    public byte[] generateMedicationSchedulePdf(Integer patientId) throws DocumentException, IOException {
        List<Medication> medicationList = medicationService.getMedicationByPatientId(patientId);
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        for (Medication medication : medicationList) {
            document.add(new Paragraph(getMessage(medication)));
        }
        document.close();

        return out.toByteArray();
    }

    private String getMessage(Medication medication){
        return "Medication :"+medication.getName()+" should be taken "+medication.getDosage()+" "+medication.getFrequency()+" from "+medication.getStartTime()
                +" on "+medication.getEndTime()+" .";


    }
}