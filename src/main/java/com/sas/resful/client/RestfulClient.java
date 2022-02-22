package com.sas.resful.client;

import com.sas.pojo.AlertEvent;
import com.sas.pojo.AlertingEvent;
import com.sas.pojo.RequestParm;
import com.sas.pojo.ScenarioFiredEvent;
import com.sas.util.HttpClientService;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestfulClient {
    public boolean insertNow;
    public static void main(String[] args) throws Exception {
        RestfulClient rc = new RestfulClient();
        System.out.println(!rc.insertNow);
        String host = "http://10.70.12.114";
        String act = "sasdemo01";
        String pwd = "demopw";
//        if (args.length != 3){
//            System.out.println("Please input host、username、pwd");
//            return ;
//        }

        String token = getToken(host, act, pwd);
        if (!StringUtils.isEmpty(token)){
            String urlPath = "/svi-alert/alertingEvents";
            RestTemplate rs = getRestTemplate(token);
            JSONObject jsonObject= new JSONObject(genAlertEvent());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.sas.fcs.tdc.alertingeventsdataflat+json"));

            HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

            String response = rs.postForObject(host+urlPath, request, String.class);
            System.out.println(response);
        }

    }

    public static String getToken(String host, String username, String pwd) throws Exception {
        String url = "/SASLogon/oauth/token";
        List<NameValuePair> list = new ArrayList<>();

        list.add(new RequestParm("grant_type", "password"));
        list.add(new RequestParm("username", username));
        list.add(new RequestParm("password", pwd));

        String result = null;

        if (StringUtils.startsWithIgnoreCase(host, "https")){
            result = HttpClientService.sendHttpsGet(host + url, list);
        }else{
            result = HttpClientService.sendGet(host + url, list);
        }

        if (!StringUtils.isEmpty(result)){
            JSONObject jsonObject = new JSONObject(result);
            result = jsonObject.getString("access_token");
        }
        return result;
    }

    public static RestTemplate getRestTemplate(String bearerToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, clientHttpRequestExecution) -> {
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey("Authorization")) {
                String token = bearerToken.toLowerCase().startsWith("bearer") ? bearerToken : "Bearer " + bearerToken;
                request.getHeaders().add("Authorization", token);
            }
            return clientHttpRequestExecution.execute(request, body);
        });
        return restTemplate;
    }

    public static AlertEvent genAlertEvent(){
        AlertEvent ae = new AlertEvent();
//        ae.setJsonLayout("nested");
        AlertingEvent alertingEvent = new AlertingEvent();
        ScenarioFiredEvent sfe = new ScenarioFiredEvent();
        sfe.setAlertingEventId("A3_SFE_9");
        sfe.setDisplayFlg("true");
        sfe.setDisplayTypeCode("text");
        sfe.setScenarioDescription("first demo VICC scenario");
        sfe.setScenarioId("Scenario9");
        sfe.setScenarioName("viccscen1");
        sfe.setScenarioFiredEventId("SFE1_A3_9");
        sfe.setScenarioOriginCode("apiVICC");
        sfe.setScore(20);
        List<ScenarioFiredEvent> sfes = new ArrayList<>();
        sfes.add(sfe);
        ae.setScenarioFiredEvents(sfes);
        alertingEvent.setAlertingEventId("A3_SFE_9");
        alertingEvent.setAlertTriggerTxt("Alert with SFE");
//        alertingEvent.setScore(10);
        alertingEvent.setAlertOriginCode("SA");
        alertingEvent.setAlertTypeCode("DEFAULT");
        alertingEvent.setActionableEntityId("A225739701");
        alertingEvent.setActionableEntityType("SALES");
        alertingEvent.setRecommendedQueueId("queue_demo");
        alertingEvent.setScoreDescription("SFE Score");
        List<AlertingEvent> aes = new ArrayList<>();
        aes.add(alertingEvent);
        ae.setAlertingEvents(aes);
        return ae;
    }
}
