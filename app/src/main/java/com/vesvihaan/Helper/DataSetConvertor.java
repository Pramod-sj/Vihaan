package com.vesvihaan.Helper;

import java.util.ArrayList;

public class DataSetConvertor {

    public static StringBuilder makeHtmlList(ArrayList<String> arrayList){
        StringBuilder builder=new StringBuilder();
        builder.append("<ul>");
        for(String temp:arrayList){
            builder.append("<li>&nbsp;"+temp+"</li>");
        }
        builder.append("</ul>");
        return builder;
    }
}
