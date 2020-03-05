package de.nkm.manualchecker.checks.tasks

import de.kosit.validationtool.impl.tasks.CheckAction
import de.nkm.manualchecker.checks.config.TransformationConfiguration
import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class SourceSaverActionTest extends Specification {
    def "it should take a Source and save it"(){
        given: "A Bag with a source Result"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationResult(new StreamSource(new File(getClass().getResource('/examples/simple/input/foo.xml').getPath())))

        and: "config with a Target to save to"
        def transformationConfig = new TransformationConfiguration('', '%temp%/ggf.txt', new ArrayList<String>());

        and: "A SourceSaver"
        def sourceSaver = new SourceSaverAction(transformationConfig);
        when: "the action is called"
        sourceSaver.check(bag)

        then: "A file should exist"
        def file = new File('%tmp%/ggf.txt')
        file.exists()
    }

}
