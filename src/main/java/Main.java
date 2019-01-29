import com.rabobank.util.ReportUtil;
import com.rabobank.validator.CSVRecValidator;
import com.rabobank.validator.IValidator;
import com.rabobank.validator.XMLValidator;

import java.io.File;

public class Main {

    // Driver code
    public static void main(String[] args) {
        File inFile;
        // validation if the file name has been provided.
        if (0 == args.length) {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(0);
        }
        String filePath = args[0];
        inFile = new File(filePath);
        // validation for input file
        boolean invalidFileCondition = !(inFile.exists()
                && inFile.isFile() && inFile.canRead() && (filePath.endsWith(".csv") || filePath.endsWith(".xml")));
        if (invalidFileCondition) {
            System.err.println("Invalid File / Type  or Can not read the input file :");
            System.exit(0);
        }

        IValidator validator;

        if (filePath.endsWith(".csv")) {
            validator = new CSVRecValidator(filePath);
        } else {
            validator = new XMLValidator(filePath);
        }
        validator.validate();
        ReportUtil.generateReport(validator.getErrorList());
        System.out.println("Report Validation completed ");

    }


}






