package com.hanwise.vulners.util;

import org.junit.Test;

public class VersionUtilTest {

    @Test
    public void testEncodeRPMVersion(){
        System.out.println("TestEncodeRPMVersion ... ");
        String[] testRPMVersions= {
          "3.9", "3.10", "5.256", "1.1a", "1.a+", "123a3bc.el7", "2xFg33.+f.5", "1.05"
        };

        for(String v: testRPMVersions) {
            String encodedV = VersionUtil.encodeRPMVersion(v);
            if(v.equals("1.05")){
                assert (encodedV.equals("01-1.01-5"));
            }
            System.out.println(encodedV);
        }
    }

    /*'1.0010' is newer than '1.9' because 10 is more than 9.
    '1.05' is equal to '1.5', because both '05' and '5' are treated as the number 5.
    '1.0' is newer than '1', because it has one more element in the list, while previous elements are equal.
    '2.50' is newer than '2.5', because 50 is more than 5.
    'fc4' is equal to 'fc.4', because the alphabetic and numeric sections will always get separated into different elements anyway.
    'FC5' is older than 'fc4', because it uses uppercase letters.
    '2a' is older than '2.0', because numbers are considered newer than letters.
    '1.0' is newer than '1.fc4' because numbers are considered newer than letters.
    '3.0.0_fc' is the same as '3.0.0.fc', because the separators themselves are not important.
    */
    @Test
    public void testVersionCompare() {
        assert (VersionUtil.compareVersion("1.0010", "1.9")>0);
        assert (VersionUtil.compareVersion("1.05", "1.5")==0);
        assert (VersionUtil.compareVersion("1.0", "1")>0);
        assert (VersionUtil.compareVersion("2.50", "2.5")>0);
        assert (VersionUtil.compareVersion("fc4", "fc.4")==0);
        assert (VersionUtil.compareVersion("FC5", "fc4")<0);
        assert (VersionUtil.compareVersion("2a", "2.0")<0);
        assert (VersionUtil.compareVersion("1.0", "1.fc4")>0);
        assert (VersionUtil.compareVersion("3.0.0_fc", "3.0.0.fc")==0);

    }

    @Test
    public void testSplitEpochVersion(){
        String[] specs=VersionUtil.splitEpochVersion("0:1.1");
        assert(specs[0].equals("0"));
        assert(specs.length==2);
    }
}
