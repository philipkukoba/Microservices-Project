package be.ugent.systemdesign.group6.verzendingsdienst.application.bpost;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Label;

import java.util.Stack;

public interface BPostKoerier {

    Stack<Label> vraagLabelsAan(int aantal);

}
