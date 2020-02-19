package services

import org.apache.poi.xwpf.converter.pdf.PdfConverter
import org.apache.poi.xwpf.converter.pdf.PdfOptions
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.sql.DriverManager.println

@Service
class DocxConverterService {

    fun convertToPDF(docPath: String, pdfPath: String) {
        try {
            val doc = FileInputStream(File(docPath))
            val document = XWPFDocument(doc)
            val options = PdfOptions.create()
            val out = FileOutputStream(File(pdfPath))
            PdfConverter.getInstance().convert(document, out, options)
        } catch (ex: IOException) {
            println(ex.message)
        }
    }
}