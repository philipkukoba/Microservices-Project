package be.ugent.systemdesign.group6.order.infrastructure;

import be.ugent.systemdesign.group6.order.domain.Pakket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class OrderDataModel {
    @Id
    private String id;
    private List<Pakket> pakketten;
    private boolean klaargemaakt;
}
