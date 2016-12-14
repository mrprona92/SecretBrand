package com.badr.infodota.base.util;


/**
 * Created by ABadretdinov
 * 30.12.2014
 * 14:12
 */
public class VDFtoJsonParser {
    public static String parse(String vdf) {
        String json = vdf;
        json = "{\n" + json + "\n}";
        json=json.replace("\\\"","");
        json = json.replaceAll("\"([^\"]*)\"(\\s*)\\{", "\"$1\": {");
        json = json.replaceAll("\"([^\"]*)\"\\s*\"([^\"]*)\"", "\"$1\": \"$2\",");
        json = json.replaceAll(",(\\s*[\\}\\]])", "$1");
        json = json.replaceAll("([\\}\\]])(\\s*)(\"[^\"]*\":\\s*)?([\\{\\[])", "$1,$2$3$4");
        json = json.replaceAll("\\}(\\s*\"[^\"]*\":)", "},$1");
        return json;
    }
}