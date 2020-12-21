package be.ugent.systemdesign.group6.verzendingsdienst.application.bpost;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Label;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class BPostKoerierImpl implements BPostKoerier {

    private final LabelGenerator labelGenerator;

    public BPostKoerierImpl(){
        this.labelGenerator = new LabelGenerator();
    }

    @Override
    public Stack<Label> vraagLabelsAan(int aantal) {
        return labelGenerator.generateLabels(aantal);
    }

}
