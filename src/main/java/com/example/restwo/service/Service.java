package com.example.restwo.service;

import com.example.restwo.controller.Controller;
import org.json.JSONObject;
import org.mapdb.HTreeMap;

import java.util.Map;
import java.util.Objects;




public class Service {



    public static HTreeMap<String, Map<String, Map<String, String>>> hashmap = Controller.hashmap;










//
//
//    public String getIps() {
//        System.out.println("\n\nRequested for IPs\n\n");
//        StringBuilder retIp = new StringBuilder();
//        Map<String, String> ips = hashmap.get("IP");
//        retIp.append("[\n");
//        for(Map.Entry<String, String> m : Objects.requireNonNull(ips).entrySet()){
//            retIp.append(m.getValue()).append(",\n\n");
//        }
//        String substring = retIp.substring(0, retIp.length() - 3);
//        return substring + "\n]";
//    }
//
//
//
//
//
//
//
//
//
//
//
//    public String getDomains() {
//        System.out.println("\n\nRequested for Domains\n\n");
//        StringBuilder retDomain = new StringBuilder();
//        retDomain.append("[\n");
//        Map<String, String> ips = hashmap.get("DOMAIN");
//        for(Map.Entry<String, String> m : Objects.requireNonNull(ips).entrySet()){
//            retDomain.append(m.getValue()).append(",\n\n");
//        }
//        String ret = retDomain.substring(0, retDomain.length() - 3);
//        return ret + "\n]";
//    }
//
//
//









//
//    public String getUrls() {
//        System.out.println("\n\nRequested for Urls\n\n");
//        StringBuilder retUri = new StringBuilder();
//        retUri.append("[\n");
//        Map<String, String> ips = hashmap.get("URI");
//        for(Map.Entry<String, String> m : Objects.requireNonNull(ips).entrySet()){
//            retUri.append(m.getValue()).append(",\n\n");
//        }
//        String ret = retUri.substring(0, retUri.length() - 3);
//        return ret + "\n]";
//    }















//
//    public String search(String type, String input) {
//        System.out.println("\n\n\nSearching : ");
//        System.out.println(type + " : " + input);
//        Map<String, String> getMap = hashmap.get(type.toUpperCase());
//        if(Objects.requireNonNull(getMap).containsKey(input)){
//            return getMap.get(input);
//        }
//        JSONObject json = new JSONObject();
//        json.put(type.toUpperCase() , input);
//        json.put("Response", "Safe");
//
//        String ret = json.toString(4);
////        System.out.println(ret);
//        return ret;
//
//
//
//
//    }





    public String getDomains(){
        System.out.println("Requested for domains");
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> domainMap = hashmap.get("DOMAIN");
        sb.append("[");
        String ret = iteratorPrintWithoutResponse(sb, domainMap) + "]";
//        System.out.println(ret);
        return ret;

    }


    public String getIps(){
        System.out.println("Requested for ips");
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> ipMap = hashmap.get("IP");
        sb.append("[");
        String ret = iteratorPrintWithoutResponse(sb, ipMap) + "]";
//        System.out.println(ret);
        return ret;

    }

    private String iteratorPrint(StringBuilder sb, Map<String, Map<String, String>> domainMap) {
        for (Map.Entry<String, Map<String, String>> m : domainMap.entrySet()) {
            Map<String, String> s = domainMap.get(m.getKey());
            String responseJson = s.get("Response");

            JSONObject obj = new JSONObject(s);
            obj.remove("Response");

            sb.append("[{\nDetails : ");
            sb.append(obj.toString(4)).append(",\nResponse : ").append(responseJson).append("\n}],\n\n\n\n");

        }

        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private String iteratorPrintWithoutResponse(StringBuilder sb, Map<String, Map<String, String>> domainMap) {
        for (Map.Entry<String, Map<String, String>> m : domainMap.entrySet()) {
            Map<String, String> s = domainMap.get(m.getKey());

            JSONObject obj = new JSONObject(s);
            obj.remove("Response");

            sb.append("\n");
            sb.append(obj.toString(4)).append("\n").append("\n,\n\n\n\n");

        }

        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }





    public String search(String type, String find){

        System.out.println("\n\n" + type + " : " + find + "\n\n");

        Map<String, Map<String, String>> typeMap = hashmap.get(type.toUpperCase());
//        System.out.println(typeMap);
        Map<String, String> found = typeMap.get(find);


        if(typeMap.containsKey(find)){

            String responseJson = found.get("Response").replaceAll("[\n]", "");

            JSONObject obj = new JSONObject(found);
            obj.remove("Response");
//            String sb = "[{\nDetails : " +
//                    obj.toString(0) + ",\nResponse : " + responseJson + "\n}]\n\n\n\n";
            String sb = "{\n" +
                    obj.toString(0).replaceAll("[{}]", "") + ",\nResponse : " + responseJson + "\n}\n\n\n\n";



            return new JSONObject(sb).toString(4);

//            JSONObject obj = new JSONObject(found);
//            System.out.println(obj.toString(4));
//            return obj.toString(4);
        }
        System.out.println("Not Found");

        JSONObject json = new JSONObject();
        json.put(type.toUpperCase() , find);
        json.put("Response", "Safe");

        String ret = json.toString(4);
//        System.out.println(ret);
        return ret;

    }












}
