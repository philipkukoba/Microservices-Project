package be.ugent.systemdesign.group6.verzendingsdienst.application;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Rolcontainer;

public interface VerzendingsAdministratie {
    Response RolcontainerOpgehaald(Rolcontainer rolcontainer);
    Response orderCompleet(String orderId);
}
