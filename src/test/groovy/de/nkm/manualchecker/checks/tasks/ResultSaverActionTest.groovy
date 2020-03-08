package de.nkm.manualchecker.checks.tasks

import de.kosit.validationtool.impl.tasks.CheckAction
import de.nkm.manualchecker.checks.config.TransformationConfiguration
import spock.lang.Specification

import java.nio.file.Files

class ResultSaverActionTest extends Specification {
    def "it should take a Source and save it"(){
        given: "a bag with a base file"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationTarget(getClass().getResource('/examples/simple/input/foo.xml').getPath())
        and: "a transformation"
        bag.getXsltSelectionResults().add(getClass().getResource('/examples/simple/repository/random-thing.xsl').getPath())
        and: "a Transformer which creates a result"
        def transformer = new XSLTransformAction()
        transformer.check(bag)

        and: "config with a Target to save to"
        def transformationConfig = new TransformationConfiguration('', getClass().getResource('/examples/').getPath()+'ggf.txt', new ArrayList<String>())

        and: "no file at the location"
        def file = new File(getClass().getResource('/examples/').getPath()+'ggf.txt')
        file.delete()

        and: "A SourceSaver"
        def sourceSaver = new ResultSaverAction(transformationConfig)
        when: "the action is called"
        sourceSaver.check(bag)

        then: "A file should exist"
        file.exists()
    }

    def "it should should skip if no target file path is present"(){
        given: "a bag with a base file"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationTarget(getClass().getResource('/examples/simple/input/foo.xml').getPath())

        and: "a transformation"
        bag.getXsltSelectionResults().add(getClass().getResource('/examples/simple/repository/random-thing.xsl').getPath())

        and: "a Transformer which creates a result"
        def transformer = new XSLTransformAction()
        transformer.check(bag)


        and: "config with no Target to save to"
        def transformationConfig = new TransformationConfiguration('', null, new ArrayList<String>())

        and: "a SaveAction"
        def resultSaver = new ResultSaverAction(transformationConfig)

        when: "Skip check is performed"
        def skipped = resultSaver.isSkipped(bag)

        then: "The action will be skipped"
        skipped
    }

    def "it should skip if no result exists but a location to save to"(){
        given: " a bag w/o a result"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationResult(null)

        and: "config with a Target to save to"
        def transformationConfig = new TransformationConfiguration('', getClass().getResource('/examples/').getPath()+'ggf.txt', new ArrayList<String>())

        and: "a SaveAction"
        def resultSaver = new ResultSaverAction(transformationConfig)

        when: "Skip check is performed"
        def skipped = resultSaver.isSkipped(bag)

        then: "The action will be skipped"
        skipped
    }

    def "it should do a multi transform"(){
        given: "a bag with a base file"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationTarget(getClass().getResource('/visualization/xml/maxRechnung.xml').getPath())
        and: "multiple transformations"
        bag.getXsltSelectionResults().add(getClass().getResource('/visualization/xsl/ubl-invoice-xr.xsl').getPath())
        bag.getXsltSelectionResults().add(getClass().getResource('/visualization/xsl/xrechnung-html.xsl').getPath())
        and: "a Transformer which creates a result"
        def transformer = new XSLTransformAction()
        transformer.check(bag)

        and: "config with a Target to save to"
        def transformationConfig = new TransformationConfiguration('', getClass().getResource('/visualization/').getPath()+'result.html', new ArrayList<String>())

        and: "no file at the location"
        def file = new File(getClass().getResource('/visualization/').getPath()+'result.html')
        file.delete()

        and: "A SourceSaver"
        def sourceSaver = new ResultSaverAction(transformationConfig)
        when: "the action is called"
        sourceSaver.check(bag)

        then: "A file should exist"
        file.exists()
        and: "equal the expected result"
        def expectedFile = new File(getClass().getResource('/visualization/result/result.html').getPath())
        Files.readAllLines(file.toPath()) == Files.readAllLines(expectedFile.toPath())
    }
}
