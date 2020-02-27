package de.nkm.manualchecker.checks.tasks

import de.kosit.validationtool.impl.tasks.CheckAction
import de.nkm.manualchecker.checks.config.TransformationConfiguration
import spock.lang.Specification

class XSLTSelectionActionTest extends Specification {
    def "should add valid files to the result bag"() {
        given: "A configuration with a file"
        def inputFile = getClass().getResource('/examples/simple/input/foo.xml').getPath()
        def transformationFile = getClass().getResource('/examples/simple/repository/simple.xsd').getPath()
        ArrayList<String> transformationFiles = new ArrayList<>()
        transformationFiles.add(transformationFile)
        TransformationConfiguration transformationConfiguration =
                new TransformationConfiguration(inputFile, transformationFiles)
        and: "a Selector"
        def xsltSelectionAction = new XSLTSelectionAction(transformationConfiguration)
        and: "a bag"
        def bag = new CheckAction.Bag()

        when: "the check is performed"
        xsltSelectionAction.check(bag)

        then: "transformation target should be in the bag"
        bag.getXsltTransformationTarget() == inputFile
        and: "transformation files should contain the given"
        bag.getXsltSelectionResults() == transformationFiles
    }

    def "should ignore invalid files"() {
        given: "A configuration with an invalid file"
        def inputFile = 'Z:\\awfawf\\awdawdadawd\\awd.xml'
        def transformationFile = 'Z:\\awfawf\\awdawdadawd\\awd.xsl'
        ArrayList<String> transformationFiles = new ArrayList<>()
        transformationFiles.add(transformationFile)
        TransformationConfiguration transformationConfiguration =
                new TransformationConfiguration(inputFile, transformationFiles)

        and: "a Selector"
        def xsltSelectionAction = new XSLTSelectionAction(transformationConfiguration)
        and: "a bag"
        def bag = new CheckAction.Bag()

        when: "the check is performed"
        xsltSelectionAction.check(bag)

        then: "transformation target should be in the bag"
        bag.getXsltTransformationTarget() == null
        and: "transformation files should contain the given"
        bag.getXsltSelectionResults().empty
    }
}
