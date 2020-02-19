package services

//import com.voc.webserver.config.PathConfig

import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Path
import java.nio.file.Paths

@Service
class DocxService {

    @Autowired
    //@Qualifier(PathConfig.TEMP_FOLDER)
    //lateinit var tempDirectory: Path
    val tempDirectory: Path = Paths.get("build/resources/main/tempDirectory")

    fun generateTemplate(
            textParams: Map<String, String>?,
            tableParams: Map<String, String>?,
            templateFilename: String,
            docName: String
    ): File {
        val fileInputStream = ClassPathResource(templateFilename).inputStream
        val doc = XWPFDocument(OPCPackage.open(fileInputStream))
        for (p in doc.paragraphs) {
            val runs = p.runs
            if (runs != null && textParams != null) {
                for (r in runs) {
                    var text: String? = r.getText(0)
                    textParams.forEach {
                        if (text != null && text!!.contains(it.key)) {
                            text = text!!.replace(it.key, it.value)
                            r.setText(text, 0)
                        }
                    }
                }
            }
        }

        if (textParams != null) {
            for (tbl in doc.tables) {
                for (row in tbl.rows) {
                    for (cell in row.tableCells) {
                        for (p in cell.paragraphs) {
                            for (r in p.runs) {
                                var text: String? = r.getText(0)
                                tableParams!!.forEach {
                                    if (text != null && text!!.contains(it.key)) {
                                        text = text!!.replace(it.key, it.value)
                                        r.setText(text, 0)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        val generatedTemplate = File("$tempDirectory/$docName.docx")
        doc.write(FileOutputStream(generatedTemplate))
        return generatedTemplate
    }

//    fun dome(){
//        sun.security.tools.keytool.Main.main()
//    }
}