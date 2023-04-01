package com.ubb.postuniv.Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CarValidator {
    public void validate(Car auto) throws Exception {
        StringBuilder sb = new StringBuilder();

        if (auto.getCarKm() < 0) {
            sb.append("The km cannot be negative!\n");
        }

        Date dateString = auto.getCarDate();
        Date minDate = new Date(0L);
        if(dateString.before(minDate)){
            sb.append("The date is not correct!\n");
        }

        if (sb.length() > 0) {
            throw new CarValidatorException(sb.toString());
        }
    }
}
