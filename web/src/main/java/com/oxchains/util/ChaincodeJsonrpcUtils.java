package com.oxchains.util;

/**
 * ChaincodeJsonrpcUtils
 *
 * @author liuruichao
 * Created on 2017/3/11 11:08
 */
public class ChaincodeJsonrpcUtils {
    public static final String ITEM_SP = "\\n";

    public static final String LIST_SP = "!@#\\$";

    public static final String MIN_SP = "\\^&\\*";

    private static final String deployJsonrpcTmpl = "{\"jsonrpc\": \"2.0\",\"method\": \"deploy\"," +
            "\"params\": {\"type\": 1,\"chaincodeID\":{\"path\":\"#{path}#\"}," +
            "\"ctorMsg\": {\"args\":[#{args}#]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";

    private static final String queryJsonrpcTmpl = "{\"jsonrpc\": \"2.0\",\"method\": \"query\"," +
            "\"params\": {\"type\": 1,\"chaincodeID\":{\"name\":\"#{chaincodeID}#\"}," +
            "\"ctorMsg\": {\"args\":[#{args}#]},\"secureContext\": \"user_type1_0\"},\"id\": 5}";

    private static final String invokeJsonrpcTmpl = "{\"jsonrpc\": \"2.0\",\"method\": \"invoke\"," +
            "\"params\": {\"type\": 1,\"chaincodeID\":{\"name\":\"#{chaincodeID}#\"}," +
            "\"ctorMsg\": {\"args\":[#{args}#]},\"secureContext\": \"user_type1_0\"},\"id\": 3}";

    public static String genDeployJsonReqStr(String path, String... args) {
        return deployJsonrpcTmpl.replace("#{path}#", path).replace("#{args}#", genArgsStr(args));
    }

    public static String genInvokeJsonReqStr(String chaincodeID, String... args) {
        return invokeJsonrpcTmpl.replace("#{chaincodeID}#", chaincodeID).replace("#{args}#", genArgsStr(args));
    }

    public static String genQueryJsonReqStr(String chaincodeID, String... args) {
        return queryJsonrpcTmpl.replace("#{chaincodeID}#", chaincodeID).replace("#{args}#", genArgsStr(args));
    }

    private static String genArgsStr(String... args) {
        StringBuilder sbu = new StringBuilder();
        if (args != null && args.length > 0) {
            for (String arg : args) {
                sbu.append("\"").append(arg).append("\",");
            }
        }
        sbu.deleteCharAt(sbu.length() - 1);
        return sbu.toString();
    }
}