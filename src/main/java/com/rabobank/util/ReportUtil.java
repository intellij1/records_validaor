package com.rabobank.util;


import com.rabobank.model.ErrorRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This is Utility Class for Report generation.
 *
 * */

public class ReportUtil {


    public static void generateReport(List<ErrorRecord> errorList) {

        Path path = Paths.get('.' + File.separator + "output_files"+ File.separator +"report.txt");
        StringBuilder sb = new   StringBuilder();
      if (!errorList.isEmpty()) {
        writeRecord( " Transaction Reference ", "Description");
        errorList.forEach(item-> sb.append( writeRecord(item.getRefid(),item.getDesc())) );
    }
        try {
            Files.write(path, sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String  writeRecord( String ref, String desc) {
        return  String.format("%-20s %-20s%n",  ref, desc);

    }


}
