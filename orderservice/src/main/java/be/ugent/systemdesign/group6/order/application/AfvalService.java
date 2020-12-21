package be.ugent.systemdesign.group6.order.application;

public interface  AfvalService {
    Response afvalcontainersOpgehaald();
    Response plaatsBijAfval(Integer catalogusId, int aantal);
    Response retourBijAfval(int aantal);
}
