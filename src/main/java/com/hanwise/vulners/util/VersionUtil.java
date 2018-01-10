package com.hanwise.vulners.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VersionUtil {
    public static String NUMBER_DIVIDER="-";
    public static Pattern CONTINUOUS_PATTERN=Pattern.compile("([0-9]+)|([^0-9]+)");

    public static  String encodeRPMVersion(String version) {
        if (StringUtils.isEmpty(version)) return "";
        String encodedVersion =null;
        String tmp_version = version.replaceAll("\\.el7$", "");
        List<String> segments = splitToSegement(tmp_version);
        encodedVersion = segments.stream()
                .map(s -> encodeSegment(s)).collect(Collectors.joining("."));

        return encodedVersion;
    }

    private static List<String> splitToSegement(String version) {
        List<String> segments= new LinkedList<String>();
        String[] simpleSegments = version.split("\\.");
        for(String seg: simpleSegments) {
            List<String> subsegments = _splitContinuousSegment(seg);
            segments.addAll(subsegments);
        }
        return segments;
    }

    private static List<String> _splitContinuousSegment(String seg) {
        List<String> subsegs = new LinkedList<String>();
        Matcher m =CONTINUOUS_PATTERN.matcher(seg);
        while(m.find()) {
            subsegs.add(m.group());
        }
        return subsegs;
    }

    private static  String encodeSegment(String segment) {
        if(isInteger(segment)) {
            return encodeInt(segment);
        }else {
            return encodeString(segment);
        }
    }

    private static String encodeInt(String segment){
        Integer intV= Integer.parseInt(segment);
        String intStr = String.valueOf(intV);
        int length = intStr.length();
        String lengthStr = String.format ("%02d", length);
        StringBuffer buf= new StringBuffer(lengthStr).append(NUMBER_DIVIDER).append(intStr);
        return buf.toString();
    }

    private static  String encodeString(String segment) {
        StringBuffer buf= new StringBuffer("$");
        buf.append(keepAlphabet(segment));
        return buf.toString();
    }

    public static String keepAlphabet(String s) {
        if(s!=null) {
            return s.replaceAll("[^a-zA-Z0-9]", "");
        }else {
            return "";
        }
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static int compareVersion(String v1, String v2){
       String nV1= encodeRPMVersion(v1);
       String nV2= encodeRPMVersion(v2);
       return nV1.compareTo(nV2);
    }

    public static String[] splitEpochVersion(String epochVersion) {
        String[] specs=epochVersion.split(":", 2);
        if(specs.length<2){
            return new String[]{"0", epochVersion};
        }else {
            return specs;
        }
    }
}
