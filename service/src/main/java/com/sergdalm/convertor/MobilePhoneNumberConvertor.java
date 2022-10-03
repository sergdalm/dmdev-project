package com.sergdalm.convertor;

import com.sergdalm.entity.MobilePhoneNumber;

import javax.persistence.AttributeConverter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePhoneNumberConvertor implements AttributeConverter<MobilePhoneNumber, String> {

    private static final String MOBILE_PHONE_NUMBER_REGEX =
            "\\+7\\((?<zoneCode>\\d{3})\\)(?<firstPart>\\d{3})-(?<secondPart>\\d{2})-(?<thirdPart>\\d{2})";
    private static final Pattern MOBILE_PHONE_NUMBER_PATTERN = Pattern.compile(MOBILE_PHONE_NUMBER_REGEX);
    private final static String ZONE_CODE_GROUP = "zoneCode";
    private final static String FIRST_PART_GROUP = "firstPart";
    private final static String SECOND_PART_GROUP = "secondPart";
    private final static String THIRD_PART_GROUP = "thirdPart";

    @Override
    public String convertToDatabaseColumn(MobilePhoneNumber mobilePhoneNumber) {
        return Optional.ofNullable(mobilePhoneNumber)
                .map(MobilePhoneNumber::getMobilePhoneNumber)
                .orElse(null);
    }

    @Override
    public MobilePhoneNumber convertToEntityAttribute(String dbData) {
        Matcher matcher = MOBILE_PHONE_NUMBER_PATTERN.matcher(dbData);
        String zoneCode = "";
        String firstPart = "";
        String secondPart = "";
        String thirdPart = "";

        while (matcher.find()) {
            zoneCode = matcher.group(ZONE_CODE_GROUP);
            firstPart = matcher.group(FIRST_PART_GROUP);
            secondPart = matcher.group(SECOND_PART_GROUP);
            thirdPart = matcher.group(THIRD_PART_GROUP);
        }

        return new MobilePhoneNumber(zoneCode, firstPart, secondPart, thirdPart);
    }
}
