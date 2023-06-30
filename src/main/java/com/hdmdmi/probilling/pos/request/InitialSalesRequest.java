package com.hdmdmi.probilling.pos.request;

import lombok.Data;

@Data
public class InitialSalesRequest {

    private String customerPhoneNumber;

    private String salesUserId;

}
