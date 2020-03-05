package de.nkm.manualchecker.checks.tasks;

import de.kosit.validationtool.impl.ObjectFactory;
import de.kosit.validationtool.impl.tasks.CheckAction;
import de.nkm.manualchecker.checks.config.TransformationConfiguration;
import lombok.AllArgsConstructor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmNode;

import java.io.File;

@AllArgsConstructor
public class SourceSaverAction implements CheckAction {

    private TransformationConfiguration transformationConfiguration;

    @Override
    public void check(Bag results) {
        XdmNode finishedNode = results.getXsltTransformationResult();
        final Serializer serializer = ObjectFactory.createProcessor()
                .newSerializer(new File(transformationConfiguration.getTargetFilePath()));
        try {
            serializer.serializeNode(finishedNode);

        } catch (SaxonApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSkipped(Bag results) {
        return false;
    }
}
