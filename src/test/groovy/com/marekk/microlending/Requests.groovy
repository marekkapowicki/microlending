package com.marekk.microlending

import com.jayway.restassured.response.Header

import static com.marekk.microlending.api.Specification.CUSTOMER_HEADER_NAME

class Requests {
    static Header customerHeader(String customerId) {
        return new Header(CUSTOMER_HEADER_NAME, customerId);
    }
}
