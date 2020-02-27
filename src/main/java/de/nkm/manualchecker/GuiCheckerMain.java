package de.nkm.manualchecker;

import de.kosit.validationtool.api.CheckConfiguration;
import de.kosit.validationtool.api.Input;
import de.kosit.validationtool.api.InputFactory;
import de.kosit.validationtool.api.Result;
import de.nkm.manualchecker.checks.HtmlOutputCheck;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GuiCheckerMain {

    private static final String SCENARIOS_FILE = "C:\\Users\\noahn\\IdeaProjects\\validator-master\\validator-configuration-xrechnung_1.2.0_2018-12-19\\scenarios.xml";
    private static final String RESULT_FOLDER = ".\\result";

    private void run(Path testDocument) {
        // Load scenarios.xml from classpath
        // URL scenarios = this.getClass().getClassLoader().getResource("scenarios.xml");
        File scenarios = new File(SCENARIOS_FILE);
        // Load the rest of the specific Validator configuration from classpath
        CheckConfiguration config = new CheckConfiguration(scenarios.toURI());
        // Use the default validation procedure
        HtmlOutputCheck htmlTypeCheck = new HtmlOutputCheck(config, RESULT_FOLDER);
        // Validate a single document
        Input document = InputFactory.read(testDocument);
        // Get Result including information about the whole validation
        Result report = htmlTypeCheck.checkInput(document);

        System.out.println("Is processing succesful=" + report.isProcessingSuccessful());
        // Get report document if processing was successful
        // Document resultDocument = null;
        if (report.isProcessingSuccessful()) {
            System.out.println("Schema valid: " + report.isSchemaValid());
            System.out.println("Is File acceptable: " + report.isAcceptable());
        }
        // continue processing results...
    }

    public static void main(String[] args) {
        // Path of document for validation
        Path testDoc = Paths.get(args[0]);
        GuiCheckerMain example = new GuiCheckerMain();
        // run example validation
        example.run(testDoc);
    }

}
