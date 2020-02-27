package de.nkm.manualchecker.checks.config;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class TransformationConfiguration {

    @Setter(AccessLevel.NONE)
    private String transformationTargetURI;

    private ArrayList<String> xsltFileLocationURIS;
}
