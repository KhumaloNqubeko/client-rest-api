package com.client.api.service;

import com.client.api.exception.ErrorCode;
import com.client.api.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
@Service
public class ValidationService {

    // {YYMMDD} {G} {SSS} {C}{A}{Z}
    // {940823} {5} {341} {0}{8}{4}
    public boolean validateIdNumber(final String idNumber) {
        if (idNumber.length() != 13) {
            throw new ValidationException(ErrorCode.ID_NUMBER_LIMIT);
        }

        try {
            Long.parseLong(idNumber);
        } catch (NumberFormatException e) {
            throw new ValidationException(ErrorCode.ID_NUMBER_IS_NOT_A_NUMBER);
        }

        if (!isValidDate(idNumber.substring(0, 6))) {
            return false;
        }

        int country = Integer.parseInt(idNumber.substring(10, 11));

        if (country != 0) {
            throw new ValidationException(ErrorCode.NOT_SA_CITIZEN);
        }


        return true;
    }

    private boolean isValidDate(final String strDate) {
        DateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
        } catch (ParseException e) {
            throw new ValidationException(ErrorCode.INVALID_DATE);
        }
        return true;
    }
}
