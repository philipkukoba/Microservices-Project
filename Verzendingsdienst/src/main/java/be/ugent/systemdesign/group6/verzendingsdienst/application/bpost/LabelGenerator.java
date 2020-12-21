package be.ugent.systemdesign.group6.verzendingsdienst.application.bpost;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Label;

import java.time.LocalDate;
import java.util.Random;
import java.util.Stack;

public class LabelGenerator {

    private final Random random;

    public LabelGenerator() {
        this.random = new Random();
    }

    public Stack<Label> generateLabels(int aantal){
        Stack<Label> res = new Stack<Label>();
        String barCode;
        LocalDate geldigheidsDatum = LocalDate.now().plusMonths(6); // 6 maand geldig
        for (int i = 0; i < aantal; i++) {
            barCode = generateBarCode();
            res.add(new Label(barCode,geldigheidsDatum));
        }
        return res;
    }

    //generate random numeric barcode of 18 digits
    private String generateBarCode(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 3; i++){
            s.append(random.nextInt(1000000 - 100000) + 100000);
        }
        return s.toString();
    }
}
