package main;


import java.util.Arrays;

public class IPUtil {
    /*
    given a ip address in decimal format , return in binary format
    eg: 127.0.0.1 -> return 01111111000000000000000000000001
     */
    public static String decimalToBinary(String ip) {
        StringBuilder bStringBuilder = new StringBuilder();
        String ipParts[] = ip.split("\\.");

        for (String ipPart : ipParts) {

            String binString = Integer.toBinaryString(Integer.parseInt(ipPart));
            int length = 8 - binString.length();
            char[] padArray = new char[length];
            Arrays.fill(padArray, '0');
            bStringBuilder.append(padArray).append(binString);
        }
        return bStringBuilder.toString();
    }
    /*
    given a ip address in binary format , return in decimal format
    eg: 01111111000000000000000000000001 -> return 127.0.0.1
     */
    public static String binaryToDecimal(String binaryIP){
        StringBuilder sb = new StringBuilder();
        if(binaryIP.length() < 32){
            System.out.println(binaryIP);
            return null;
        }
        for(int i = 0; i < 4; i++){
            String curr = binaryIP.substring(i * 8, (i + 1) * 8);
            Integer num = Integer.parseInt(curr, 2);
            sb.append(num);
            sb.append(".");
        }
        int len = sb.length();
        sb.deleteCharAt(len - 1);
        return sb.toString();
    }
    public static void main(String[] args){
        System.out.println(binaryToDecimal("01111111001110110001101110111100"));

    }
}
