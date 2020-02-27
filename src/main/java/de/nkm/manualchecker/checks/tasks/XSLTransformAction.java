package de.nkm.manualchecker.checks.tasks;

import de.kosit.validationtool.impl.tasks.CheckAction;

public class XSLTransformAction implements CheckAction {

    @Override
    public void check(Bag results) {
        //TODO Make Transformation chain
    }

    @Override
    public boolean isSkipped(Bag results) {
        return results.getXsltTransformationTarget() == null;
    }
}
