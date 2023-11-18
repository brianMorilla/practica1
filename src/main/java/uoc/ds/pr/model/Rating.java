package uoc.ds.pr.model;

import java.math.BigDecimal;

public class Rating {
    private String id;
    private String workerId;
    private String jobOfferId;
    private int value;
    private String message;

    public Rating(String workerId, String jobOffer, int value, String message) {
        this.workerId = workerId;
        this.jobOfferId = jobOffer;
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
