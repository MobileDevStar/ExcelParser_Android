package com.itdevstar.excelparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText output;
    private ListView        m_list;

    private ArrayList<TableDataModel> m_tableData = new ArrayList<TableDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (EditText) findViewById(R.id.textOut);
        m_list = (ListView) findViewById(R.id.lv_data);

        readFile();
        populateList();
    }

    protected  void readFile() {
        printlnToUser("reading XLSX file from resources");
        InputStream stream = getResources().openRawResource(R.raw.options_journal);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int r = 3; r<rowsCount-2; r++) {
                Row row = sheet.getRow(r);
                //int cellsCount = row.getPhysicalNumberOfCells();
                String symbol="", entranceType="", optionType="", strike="",
                        orderDate="", orderTime="", expiration="", contacts="",
                        premium="", totalValue="", strategy="", bearish_bullish="";
                int cellsCount = 12;
                for (int c = 0; c<cellsCount; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    switch (c) {
                        case 0: symbol = value; break;
                        case 1: entranceType = value; break;
                        case 2: optionType = value; break;
                        case 3: strike = value; break;
                        case 4: orderDate = value; break;
                        case 5: orderTime = value; break;
                        case 6: expiration = value; break;
                        case 7: contacts = value; break;
                        case 8: premium = value; break;
                        case 9: totalValue = value; break;
                        case 10: strategy = value; break;
                        case 11: bearish_bullish = value; break;
                    }
                    String cellInfo = "r:"+r+"; c:"+c+"; v:"+value;
                    printlnToUser(cellInfo);
                }
                TableDataModel model = new TableDataModel(symbol, entranceType, optionType, strike, orderDate, orderTime, expiration, contacts, premium, totalValue, strategy, bearish_bullish);
                m_tableData.add(model);
            }
        } catch (Exception e) {
            // proper exception handling to be here
            printlnToUser(e.toString());
        }
    }

    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                        if (c == 5) {
                            formatter = new SimpleDateFormat("hh:mm a");
                        }

                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            printlnToUser(e.toString());
        }
        return value;
    }


    /**
     * print line to the output TextView
     * @param str
     */
    private void printlnToUser(String str) {
        final String string = str;
        if (output.length()>8000) {
            CharSequence fullOutput = output.getText();
            fullOutput = fullOutput.subSequence(5000,fullOutput.length());
            output.setText(fullOutput);
            output.setSelection(fullOutput.length());
        }
        output.append(string+"\n");
    }

    private void populateList() {
        TableDataAdapter customAdapter = new TableDataAdapter(this, m_tableData);
        m_list.setAdapter(customAdapter);
    }
}
