package de.nkm.manualchecker.checks.tasks;

import de.kosit.validationtool.impl.tasks.CheckAction;
import de.nkm.manualchecker.checks.config.TransformationConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SourceSaverAction implements CheckAction {

    private TransformationConfiguration transformationConfiguration;

    @Override
    public void check(Bag results) {

    }

    @Override
    public boolean isSkipped(Bag results) {
        return false;
    }
}
