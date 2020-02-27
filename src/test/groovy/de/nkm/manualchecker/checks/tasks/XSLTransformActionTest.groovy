package de.nkm.manualchecker.checks.tasks

import de.kosit.validationtool.impl.tasks.CheckAction
import spock.lang.Specification

class XSLTransformActionTest extends Specification {
    def "it skips when there is no base file"() {
        given: "a bag with no target file"
        def bag = new CheckAction.Bag()
        and: "a Transformer"
        def transformer = new XSLTransformAction()

        when: "a check on skip state is performed"
        def skipped = transformer.isSkipped(bag)

        then: "it should skip"
        skipped == true
    }

    def "it doesnt skip when there is  base file"() {
        given: "a bag with a target file"
        def bag = new CheckAction.Bag()
        bag.setXsltTransformationTarget('Whatever')
        and: "a Transformer"
        def transformer = new XSLTransformAction()

        when: "a check on skip state is performed"
        def skipped = transformer.isSkipped(bag)

        then: "it shouldnt skip"
        skipped == false
    }
}
