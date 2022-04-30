import com.example.restwo.HelloApplication;
import com.example.restwo.controller.Controller;
import com.example.restwo.service.Service;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mapdb.HTreeMap;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ControllerTest {

    List<Map<String, Map<String, String>>> maps;

    @Test
    public void getXMLTest(){

        Controller controller = new Controller();

        String xmlFromSource = null;
        try {
            xmlFromSource = Controller.getXMLFromSource();
//            System.out.println(xmlFromSource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        maps = Controller.jsonParse(xmlFromSource);
        int size = maps.size();
        System.out.println(size);
//        System.out.println(maps.get(0));
        Map<String, String> ip = maps.get(0).get("192.227.112.57");

        String responseJson = ip.get("Response");

        JSONObject obj = new JSONObject(ip);
        obj.remove("Response");
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(obj.toString(4)).append("\n").append(responseJson).append("\n}\n\n\n\n");

        System.out.println(sb);
//        System.out.println(new JSONObject(sb).toString(4));


    }

    @Test
    public void searchTest()  {

        HelloApplication hA = new HelloApplication();

        Service service = new Service();

        HTreeMap<String, Map<String, Map<String, String>>> hashmap = service.hashmap;
        Map<String, Map<String, String>> ip = hashmap.get("IP");
//        System.out.println(ip);





        System.out.println("\n\n\n\n\n");


        String ipSearch = service.search("IP", "136.243.197.230");
//        System.out.println(ipSearch);
        System.out.println(new JSONObject(ipSearch).toString(4));


//        System.out.println(new JSONArray(ipSearch).toString(4));


    }

    @Test
    public void testTime(){
        ZonedDateTime fromDate = ZonedDateTime.parse("2017-05-24T11:43:28+00:00");
        System.out.println(fromDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, EEE 'at' hh:mm a")));

    }

    @Test
    public void getThings(){
        Service service = new Service();

        String ips = service.getIps();
        System.out.println(ips);
//        System.out.println(new JSONArray(ips).toString(4));
        String domains = service.getDomains();
        System.out.println(domains);
//        System.out.println(new JSONArray(domains).toString(4));

    }


    @Test
    public void start() throws IOException {
        Controller.start();
    }

}
