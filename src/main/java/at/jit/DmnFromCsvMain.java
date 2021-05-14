package at.jit;

import org.camunda.bpm.model.dmn.DmnModelInstance;

public class DmnFromCsvMain {
    public static void main(final String[] args) {
        final DmnFromCsvMain app = new DmnFromCsvMain();
        app.run(args);
    }

    public void run(final String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar CsvToDmnConverter mode input-file output-file");
        }

        final String mode = args[0].trim();

        final String inputFile = args[1];
        final String outputFile = args[2];
        if (!new FileExtensionsValidator().fileExtensionsValid(inputFile, outputFile, mode)) {
            System.out.println("One of the entered file extensions is wrong, exiting program..");
            return;
        }

        if (Modes.CSV_TO_DMN.equals(mode)) {
            convertCsvToDmn(inputFile, outputFile);
        } else if (Modes.DMN_TO_CSV.equals(mode)) {
            convertDmnToCsv(inputFile, outputFile);
        } else {
            System.out.println("Entered option is not 1 or 2, exiting program..");
            return;
        }
    }

    void convertDmnToCsv(String inputFile, String outputFile) {
        DmnToCsvConverter dmnToCsvConverter = new DmnToCsvConverter();
        dmnToCsvConverter.convertDmnToCsv(inputFile, outputFile);
    }

    void convertCsvToDmn(String inputFile, String outputFile) {
        final CsvReader csvReader = new CsvReader();
        final CsvPojo csvPojo = csvReader.readCsv(inputFile);

        final DmnCreator dmnCreator = new DmnCreator();
        final DmnModelInstance dmnModelInstance = dmnCreator.createDmnFromCsvPojo(csvPojo);

        final DmnFileExporter dmnFileExporter = new DmnFileExporter();
        dmnFileExporter.exportToDmnFile(dmnModelInstance, outputFile);
    }
}
