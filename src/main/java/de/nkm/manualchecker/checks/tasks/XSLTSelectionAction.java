package de.nkm.manualchecker.checks.tasks;

import de.kosit.validationtool.impl.tasks.CheckAction;
import de.nkm.manualchecker.checks.config.TransformationConfiguration;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class XSLTSelectionAction implements CheckAction {
    private TransformationConfiguration transformationConfiguration;

    @Override
    public void check(Bag results) {
        if (fileExists(transformationConfiguration.getTransformationTargetURI())) {
            results.setXsltTransformationTarget(transformationConfiguration.getTransformationTargetURI());
        }
        transformationConfiguration.getXsltFileLocationURIS().forEach((fileURI) -> {
            if (fileExists(fileURI)) {
                results.getXsltSelectionResults().add(fileURI);
            }
        });
        //TODO Check wether files are present.
        //TODO DO NOT PRELOAD!
    }

    @Override
    public boolean isSkipped(Bag results) {
        return false;
    }

    private boolean fileExists(String URI) {
        File checkFile = new File(URI);
        System.out.println(checkFile.getPath());
        return checkFile.exists();
    }
}
