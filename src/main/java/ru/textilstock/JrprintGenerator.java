package ru.textilstock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

public class JrprintGenerator {

    public static void main(String[] argv) {
        JrprintGenerator main = new JrprintGenerator();
        String dataFilePath = argv[0];
        String jrxmFilePath = argv[1];
        String outputJrxmlFile = argv[2];
        try {
            main.generateJasper(jrxmFilePath);
            main.fill(dataFilePath, outputJrxmlFile);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static JRCsvDataSource getDataSource(String dataFilePath) throws JRException
    {
        String[] columnNames = new String[]{"номер короба", "артикул", "количество", "Баркод", "Бренд",
                "Предмет", "едизм", "порядковый номер", "всего коробов", "код груза"};
        JRCsvDataSource ds = new JRCsvDataSource(JRLoader.getLocationInputStream(dataFilePath));
        ds.setRecordDelimiter("\n");
        ds.setColumnNames(columnNames);
        ds.setUseFirstRowAsHeader(true);
        return ds;
    }

    private void generateJasper(String jrxmlFile) throws JRException, FileNotFoundException {
//        InputStream employeeReportStream
//                = getClass().getResourceAsStream("mix_boxes.jrxml");
        InputStream employeeReportStream = new FileInputStream(jrxmlFile);
        JasperReport jasperReport
                = JasperCompileManager.compileReport(employeeReportStream);
        JRSaver.saveObject(jasperReport, "mix_boxes.jasper");
    }

    public void fill(String dataFilePath, String outputJrxmlFile) throws JRException
    {
        long start = System.currentTimeMillis();
        Map parameters = new HashMap();
        JasperFillManager
                .fillReportToFile("mix_boxes.jasper", outputJrxmlFile, parameters, getDataSource(dataFilePath));
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }
}
