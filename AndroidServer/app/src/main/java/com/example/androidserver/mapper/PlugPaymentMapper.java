package com.example.androidserver.mapper;

import java.util.LinkedHashMap;

import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;

public class PlugPaymentMapper {

    public static PlugPagPaymentData mapFrom(LinkedHashMap linkedHashMap) {
        return new PlugPagPaymentData.Builder().setType(Integer.parseInt(linkedHashMap.get("mType").toString()))
                .setAmount(Integer.parseInt(linkedHashMap.get("mAmount").toString()))
                .setInstallmentType(Integer.parseInt(linkedHashMap.get("mInstallmentType").toString()))
                .setUserReference(linkedHashMap.get("mUserReference").toString()).build();
    }
}
