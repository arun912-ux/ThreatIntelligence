package com.example.restwo.controller;

import com.example.restwo.HelloApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Controller {

    static File file = HelloApplication.file;
//    static boolean flag = false;

    static DB mapdb = DBMaker.fileDB(file)
                        .fileLockDisable()
                        .checksumHeaderBypass()
                        .closeOnJvmShutdown()
                        .make();
    public static HTreeMap<String, Map<String, Map<String, String>>> hashmap =
                        (HTreeMap<String, Map<String, Map<String, String>>>) mapdb.hashMap("hashmapfile").createOrOpen();

//    public static HTreeMap<String, Map<String, String>> hashmap = (HTreeMap<String, Map<String, String>>) mapdb.hashMap("hashmapfile").createOrOpen();

//    static DB mapdb = DBMaker.memoryDB().make();
//    public static HTreeMap<String, Map<String, String>> hashmap = (HTreeMap<String, Map<String, String>>) mapdb.hashMap("hashmap").createOrOpen();





    public static void start() throws IOException {
        String xml = getXMLFromSource();
        List<Map<String, Map<String, String>>> mapList = jsonParse(xml);
        save(mapList);
    }







    public static List<Map<String, Map<String, String>>> jsonParse(String xmlString){

        System.out.println("Inside JSONParse Method");

        Map<String, Map<String, String>> ipMap = new HashMap<>(0);
        Map<String, Map<String, String>> domainMap = new HashMap<>(0);

        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonString = xmlJSONObj.toString(4);

//        System.out.println("\n\n\n\n\n\n" + jsonString + "\n\n\n\n\n");

        JSONObject taxii_11_Poll_Response = xmlJSONObj.getJSONObject("taxii_11:Poll_Response");
        JSONArray contentsBlock = taxii_11_Poll_Response.getJSONArray("taxii_11:Content_Block");


        for(int i=0; i< contentsBlock.length(); i++){
            JSONObject obj1 = contentsBlock.getJSONObject(i);
            JSONObject stixPackage1 = obj1.getJSONObject("taxii_11:Content").getJSONObject("stix:STIX_Package");

            try{
                JSONObject stix_Indicators = stixPackage1.getJSONObject("stix:Indicators");
                JSONObject stix_Indicator = stix_Indicators.getJSONObject("stix:Indicator");
//                stix_Indicator.remove("indicator:Producer");
                stix_Indicator.remove("indicator:Observable");
                stix_Indicator.remove("id");
                stix_Indicator.remove("version");
                String description = stix_Indicator.get("indicator:Description").toString();
                stix_Indicator.remove("indicator:Description");
                stix_Indicator.remove("indicator:Title");

                String producedTimeZoned = stix_Indicator.getJSONObject("indicator:Producer")
                                        .getJSONObject("stixCommon:Time")
                                        .get("cyboxCommon:Produced_Time").toString();

                ZonedDateTime fromDate = ZonedDateTime.parse(producedTimeZoned);
                String producedTime = fromDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, EEE 'at' hh:mm a"));
//
//                System.out.println("\n\n\n" + producedTime + "\n" + stix_Indicators.toString(4) + "\n\n\n");

                if(description.contains("IP")){
//                    System.out.println("IP : " + description.split(" ")[3]);

                    String type = stix_Indicator.getJSONObject("indicator:Type").get("content").toString();
//                    System.out.println(type);

                    Map<String, String> obj = new LinkedHashMap<>();
                    obj.put("IP", description.split(" ")[3]);
                    obj.put("Description", description.split("\\. ")[0]);
                    obj.put("Response", stix_Indicator.toString()
//                            .replaceAll("\":\"", "\"=\"")
                            .replaceAll("\n", ""));
                    obj.put("Produced_Time", producedTime);
                    obj.put("Indicator_Type",type);
//                    System.out.println(obj);
                    ipMap.put(description.split(" ")[3], obj);


                }else if(description.contains("domain")){
//                    System.out.println("Domain : " + description.split(" ")[2]);

                    StringBuilder type = new StringBuilder();
                    JSONArray indicatorTypeArray = stix_Indicator.getJSONArray("indicator:Type");
                    indicatorTypeArray.forEach(x -> {
                        JSONObject jObj = (JSONObject) x;
                        String t = jObj.get("content").toString();
                        type.append(t).append(", ");
                    });
//                    System.out.println("domain types : " + type.deleteCharAt(type.lastIndexOf(",")));


                    Map<String, String> obj = new LinkedHashMap<>();
                    obj.put("Domain", description.split(" ")[2]);
                    obj.put("Description", description.split("\\. ")[0]);
                    obj.put("Response", stix_Indicator.toString()
//                            .replaceAll("\":\"", "\"=\"")
                            .replaceAll("\n", ""));
                    obj.put("Produced_Time", producedTime);
                    obj.put("Indicator_Type",type.deleteCharAt(type.lastIndexOf(",")).toString());
//                    System.out.println(obj);
                    domainMap.put(description.split(" ")[2], obj);
                }



            }catch (Exception e){
                System.out.print("");
            }



        }


        List< Map<String, Map<String, String>> > mapList = new ArrayList<>(2);
        mapList.add(0, ipMap);
        mapList.add(1, domainMap);

//        System.out.println(domainMap.get("all-texproducts.com") + "\n\n\n");
        return mapList;





    }




















    public static String getXMLFromSource() throws IOException {

        System.out.println("Inside getXMLFromSource() Method");

        // ! Discovery Service
//        url = new URL("http://hailataxii.com/taxii-discovery-service");
        // ! Collection Information, POLL
        URL url = new URL("http://hailataxii.com/taxii-data");

        // * Headers
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setConnectTimeout(50000);
        http.setReadTimeout(50000);
        http.setRequestProperty("Content-Type", "application/xml");
        http.setRequestProperty("Accept", "*/*");
        http.setRequestProperty("X-TAXII-Content-Type", "urn:taxii.mitre.org:message:xml:1.1");
        http.setRequestProperty("X-TAXII-Accept", "urn:taxii.mitre.org:message:xml:1.1");
        http.setRequestProperty("X-TAXII-Services", "urn:taxii.mitre.org:services:1.1");
        http.setRequestProperty("X-TAXII-Protocol", "urn:taxii.mitre.org:protocol:http:1.0");


        // ! POLL
        String data = "\n" +
                "<taxii_11:Poll_Request \n" +
                "    xmlns:taxii_11=\"http://taxii.mitre.org/messages/taxii_xml_binding-1.1\"\n" +
                "    message_id=\"123456\"\n" +
                "    collection_name=\"guest.Abuse_ch\">\n" +
                "    <taxii_11:Exclusive_Begin_Timestamp>" + "2014-04-20" + "T15:00:00Z \n" +
                "    </taxii_11:Exclusive_Begin_Timestamp>\n" +
                "    <taxii_11:Inclusive_End_Timestamp>2018-05-25T15:18:00Z\n" +
                "    </taxii_11:Inclusive_End_Timestamp>\n" +
                "    <taxii_11:Poll_Parameters allow_async=\"false\">\n" +
                "        <taxii_11:Response_Type>FULL</taxii_11:Response_Type>\n" +
                "    </taxii_11:Poll_Parameters>\n" +
                "</taxii_11:Poll_Request>\n" +
                "\n";


        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));

        System.out.println();
        System.out.println("Connection Established ...");
        System.out.println(http.getResponseCode() + " : " + http.getResponseMessage());
        StringBuilder xmlString = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            xmlString.append(line);
        }

        http.disconnect();
        stream.close();

        return xmlString.toString();

    }




















    static void save(List<Map<String, Map<String, String>>> mapList /*, Map<String, String> ipMap, Map<String, String> uriMap, Map<String, String> domainMap*/){

        System.out.println("in Controller.save()");
        Map<String, Map<String, String>> ipMap = mapList.get(0);
        Map<String, Map<String, String>> domainMap = mapList.get(1);


        System.out.println(ipMap.size());
        System.out.println(domainMap.size());

        System.out.println(hashmap.size());

        // ! Saving
        hashmap.put("IP", ipMap);
        hashmap.put("DOMAIN", domainMap);

        System.out.println("before commit");

        System.out.println(hashmap.size());
        mapdb.commit();

        System.out.println("after commit");

//        Map<String, Map<String, String>> ip = hashmap.get("IP");
//        System.out.println(ip);


    }












}

