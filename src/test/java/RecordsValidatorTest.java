import org.junit.*;
import com.rabobank.validator.CSVRecValidator;
import com.rabobank.validator.IValidator;
import com.rabobank.validator.XMLValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import com.rabobank.model.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class RecordsValidatorTest {

    public  Path path;

    @Before
    public void setup() {
         path = Paths.get('.' + File.separator + "test_files"+ File.separator +"test.csv");
    }

    @Test
    public void testCSVTransactionRefIsEmpty() {
        //Get the file reference

        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                ",NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertTrue(errorRec.getRefid().isEmpty());
    }

    @Test
    public void testCSVTransactionRefNotEmpty() {
        //Get the file reference

        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "1234,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());
    }

    @Test
    public void testCSVTransactionRefIsNotUnique() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "177666,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61\n" +
               "177666,NL69ABNA0433647324,Subscription for Jan Theuß,45.59,48.18,93.77" ;
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "177666", errorRec.getRefid() );
    }

    @Test
    public void testCSVTransactionRefIsUnique() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "177696,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61\n" +
                "177666,NL69ABNA0433647324,Subscription for Jan Theuß,45.59,48.18,93.77" ;
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());

    }


    @Test
    public void testCSVTransactionRefIsNontInteger() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "1234D,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "1234D", errorRec.getRefid() );

    }


    @Test
    public void testCSVTransactionRefIsInteger() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "1234,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());
    }

    @Test
    public void testCSVInvalidEndBalance() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "1234,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.71";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "1234", errorRec.getRefid() );
    }

    @Test
    public void testCSVValidEndBalance() {
        String fileContent = "Reference, Account Number, Description, Start Balance, Mutation, End Balance\n" +
                "1234,NL93ABNA0585619023,Flowers for Rik Theuß,44.85,-22.24,22.61";
        IValidator validator = new CSVRecValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());

    }


    @Test
    public void testXMLTransactionRefIsEmpty() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertTrue(errorRec.getRefid().isEmpty());

    }

    @Test
    public void testXMLTransactionRefNotEmpty() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"12345\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());
    }


    @Test
    public void testXMLTransactionRefIsNotUnique() {

        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"187997\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "        <record reference=\"187997\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>TEST for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +

                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "187997", errorRec.getRefid() );

    }



    @Test
    public void testXMLTransactionRefIsUnique() {

        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"187997\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "        <record reference=\"187977\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>TEST for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +

                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());

    }

       @Test
    public void testXMLTransactionRefIsNontInteger() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"12345ABCD\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "12345ABCD", errorRec.getRefid() );


    }


    @Test
    public void testXMLTransactionRefIsInteger() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"12345\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());


    }

    @Test
    public void testXMLInvalidEndBalance() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"12345\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.92</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        ErrorRecord errorRec = validator.getErrorList().get(0);
        Assert.assertEquals( "12345", errorRec.getRefid() );


    }

    @Test
    public void testXMLValidEndBalance() {
        String fileContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "    <records>\n" +
                "        <record reference=\"12345\">\n" +
                "            <accountNumber>NL91RABO0315273637</accountNumber>\n" +
                "            <description>Clothes for Rik King</description>\n" +
                "            <startBalance>57.6</startBalance>\n" +
                "            <mutation>-32.98</mutation>\n" +
                "            <endBalance>24.62</endBalance>\n" +
                "        </record>\n" +
                "    </records>" ;
        IValidator validator = new XMLValidator(getFileName(fileContent));
        validator.validate();
        Assert.assertTrue(validator.getErrorList().isEmpty());


    }

    @After
    public void tearDown() {
        if (path.toFile().exists()) {
            try {
                Files.delete(path.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getFileName(String content) {

        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return path.toString();

    }
}