package de.nkm.manualchecker.checks;

import de.kosit.validationtool.api.CheckConfiguration;
import de.kosit.validationtool.cmd.ExtractHtmlContentAction;
import de.kosit.validationtool.impl.DefaultCheck;

import java.nio.file.Paths;

public class HtmlOutputCheck extends DefaultCheck {
    /**
     * Erzeugt eine neue Instanz mit der angegebenen Konfiguration.
     *
     * @param configuration die Konfiguration
     */
    public HtmlOutputCheck(CheckConfiguration configuration, String outputFolder) {
        super(configuration);
        super.getCheckSteps().add(new ExtractHtmlContentAction(super.getContentRepository(), Paths.get(outputFolder)));
    }
}
