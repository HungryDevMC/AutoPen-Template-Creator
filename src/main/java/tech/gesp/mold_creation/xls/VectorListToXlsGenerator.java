package tech.gesp.mold_creation.xls;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import tech.gesp.maths.Vector2D;
import tech.gesp.configuration.MoldCreationConfiguration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorListToXlsGenerator {

    private final MoldCreationConfiguration moldCreationConfiguration;

    public void generate(List<Vector2D> moldPositions, String outputPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = -1;

        writeUnits(sheet.createRow(++rowCount));
        writeComponents(sheet.createRow(++rowCount));

        for (Vector2D vector : moldPositions) {
            Row row = sheet.createRow(++rowCount);
            writeVector(vector, row);
        }

        writeVector(moldPositions.get(0), sheet.createRow(++rowCount));

        try (FileOutputStream outputStream = new FileOutputStream(outputPath + "\\mold_output.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private void writeVector(Vector2D vector, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(vector.getXComponent());

        cell = row.createCell(1);
        cell.setCellValue(vector.getYComponent());
    }

    private void writeUnits(Row row) {
        Cell unitCell = row.createCell(0);
        unitCell.setCellValue(moldCreationConfiguration.getMoldUnit());
    }

    private void writeComponents(Row row) {
        Cell xComponentCell = row.createCell(0);
        xComponentCell.setCellValue("x");

        Cell yComponentCell = row.createCell(1);
        yComponentCell.setCellValue("y");
    }

}
