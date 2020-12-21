package be.ugent.systemdesign.group6.order.application;

public class MagazijnRekResponse extends Response {
    final public int rek;

    public MagazijnRekResponse(String message, ResponseStatus status, int rek) {
        super(message, status);
        this.rek = rek;
    }
}
