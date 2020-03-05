package de.nkm.manualchecker.checks.tasks;

import de.kosit.validationtool.impl.tasks.CheckAction;
import net.sf.saxon.s9api.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTransformAction implements CheckAction {

    @Override
    public void check(Bag results) {
        //TODO Make Transformation chain
        Source editFile = new StreamSource(new File(results.getXsltTransformationTarget()));
        Processor proc = new Processor(false);
        XsltCompiler compiler = proc.newXsltCompiler();
        XdmNode xdmNode = null;
        for (String filePath : results.getXsltSelectionResults()) {
            StreamSource source = new StreamSource(new File(filePath));
            try {
                XsltExecutable executable = compiler.compile(source);
                XsltTransformer transformer = executable.load();
                final XdmDestination destination = new XdmDestination();
                transformer.setSource(editFile);
                transformer.setDestination(destination);
                transformer.transform();
                xdmNode = destination.getXdmNode();
                editFile = xdmNode.asSource();
            } catch (SaxonApiException e) {
                e.printStackTrace();
            }
        }
        results.setXsltTransformationResult(xdmNode);
    }

    @Override
    public boolean isSkipped(Bag results) {
        return results.getXsltTransformationTarget() == null;
    }
}
