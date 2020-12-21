package be.ugent.systemdesign.group6.bestellingen.API.rest;

import be.ugent.systemdesign.group6.bestellingen.application.CQRS.StatistiekenQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/statistieken")
public class StatistiekenController {

    @Autowired
    private StatistiekenQuery statistiekenQuery;

    @GetMapping
    public List<MedicijnViewModel> geefStatistieken(){
        return statistiekenQuery.genereerStatistieken()
                .entrySet()
                .stream()
                .map(m -> new MedicijnViewModel(m.getValue()))
                .collect(Collectors.toList());
    }
}
