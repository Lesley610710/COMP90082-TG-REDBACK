package com.example.server.DAO;
import com.example.server.entity.Topics;
import com.example.server.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class JsonFileOperator {
    static ObjectMapper objectMapper = new ObjectMapper();
    static File dataFile = new File("userData.json");

    static File topicFile = new File("topicList.json");

    static File textFile = new File("textList.json");


    public static Boolean addUser(UserInfo userInfo) throws IOException {
        Map<String, Map<String, Object>> jsonData = objectMapper.readValue(dataFile, Map.class);
        String name = userInfo.name;
        String password = userInfo.password;
        String email = userInfo.email;
        String description = userInfo.description;
        Map<String, Object> information = new HashMap<>();
        information.put("name", name);
        information.put("password", password);
        information.put("description", description);
        jsonData.put(email, information);

        try {
            objectMapper.writeValue(dataFile, jsonData);
            log.info("new user {} successfully added to userData.json", email);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserInfo getUser(String email) throws IOException {
        Map<String, Map<String, Object>> jsonData = objectMapper.readValue(dataFile, Map.class);
        UserInfo user = new UserInfo();
        if (jsonData.containsKey(email)) {
            Map<String, Object> userInfo = jsonData.get(email);
            user.name = userInfo.get("name").toString();
            user.password = (String) userInfo.get("password");
            user.email = email;
            user.description = (String) userInfo.get("description");
            log.info("user {} found ", user.email);
            user.validUser = true;
            return user;
        } else {
            log.info("user {} is not found ", email);
            user.validUser = false;
            return null;
        }
    }

    public static boolean deleteUser(String email) throws IOException {
        Map<String, Map<String, Object>> jsonData = objectMapper.readValue(dataFile, Map.class);

        // Check if the user ID exists in the JSON data
        if (jsonData.containsKey(email)) {
            jsonData.remove(email);
            try {
                objectMapper.writeValue(dataFile, jsonData); // Update the JSON file
                log.info("user {} successfully deleted from userData.json", email);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            log.warn("user {} not found in userData.json", email);
            return false;
        }
    }

    public static boolean updateUser(UserInfo userInfo) throws IOException {
        Map<String, Map<String, Object>> jsonData = objectMapper.readValue(dataFile, Map.class);
        String name = userInfo.name;
        String password = userInfo.password;
        String email = userInfo.email;
        String description = userInfo.description;
        Map<String, Object> target = jsonData.get(email);
        target.put("name",name);
        target.put("password",password);
        target.put("email",email);
        target.put("description", description);
        jsonData.put(email, target);
        try {
            objectMapper.writeValue(dataFile, jsonData);
            log.info("new user {} successfully updated to userData.json", name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<UserInfo> getAllUser() throws IOException, JSONException {
        Map<String, Map<String, Object>> jsonData = objectMapper.readValue(dataFile, Map.class);
        ArrayList<UserInfo> userInfoList = new ArrayList<>();
        Set<String> allKeys = extractKeys();
        for (String key : allKeys) {
            UserInfo user = new UserInfo();
            if (jsonData.containsKey(key)) {
                Map<String, Object> userInfo = jsonData.get(key);
                user.name = userInfo.get("name").toString();
                user.password = (String) userInfo.get("password");
                user.email = key;
                user.description = (String) userInfo.get("description");
                user.validUser = true;
                userInfoList.add(user);
            }
        }
        return userInfoList;
    }

    public static Set<String> extractKeys() throws IOException, JSONException {
        Set<String> allKeys = new HashSet<>();
        FileReader reader = new FileReader(dataFile);
        StringBuilder jsonContent = new StringBuilder();
        int character;
        while ((character = reader.read()) != -1) {
            jsonContent.append((char) character);
        }
        reader.close();

        // Create a JSONTokener with the JSON content as a String
        JSONTokener tokener = new JSONTokener(jsonContent.toString());
        JSONObject obj = (JSONObject) tokener.nextValue();
        extractKeysHelp(obj, allKeys);

        return allKeys;
    }

    private static void extractKeysHelp(JSONObject jsonObject, Set<String> keys) throws JSONException {
        // go through JSON object keys
        Iterator<String> jsonKeys = jsonObject.keys();
        while (jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            keys.add(key);

            // if embed JSON object, iteratively extract keys
            if (jsonObject.get(key) instanceof JSONObject) {
                extractKeysHelp((JSONObject) jsonObject.get(key), keys);
            } else if (jsonObject.get(key) instanceof JSONArray) {
                // if JSON Array, go through every elem
                JSONArray jsonArray = (JSONArray) jsonObject.get(key);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i) instanceof JSONObject) {
                        extractKeysHelp((JSONObject) jsonArray.get(i), keys);
                    }
                }
            }
        }
    }

    public Boolean addTopic(List<String> topic) throws IOException {
        Map<String, List<String>> jsonData = objectMapper.readValue(topicFile, Map.class);

        List<String> topicList = jsonData.get("topicList");
        if (topicList == null) {
            topicList = new ArrayList<>();
        }
        for (String topicElem : topic) {
            if (!topicList.contains(topicElem)) {
                topicList.add(topicElem);
            }
        }
        jsonData.put("topicList", topicList);

        // write back to file
        try {
            objectMapper.writeValue(topicFile, jsonData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean removeTopic(List<String> topicsToRemove) throws IOException {
        Map<String, List<String>> jsonData = objectMapper.readValue(topicFile, Map.class);

        List<String> topicList = jsonData.get("topicList");
        if (topicList == null) {
            topicList = new ArrayList<>();
        }

        // go through remove elem
        for (String topicElem : topicsToRemove) {
            topicList.remove(topicElem);
        }

        jsonData.put("topicList", topicList);

        // write back to file
        try {
            objectMapper.writeValue(topicFile, jsonData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String>  getAllTopic() throws IOException {
        Map<String, List<String>> jsonData = objectMapper.readValue(topicFile, Map.class);
        List<String> topicList = jsonData.get("topicList");
        return topicList;
    }

    public Boolean addText(List<List<String>> articles) throws IOException {
        Map<String, List<List<String>>> jsonData = objectMapper.readValue(textFile, Map.class);

        List<List<String>> textList = jsonData.get("textList");
        if (textList == null) {
            textList = new ArrayList<>();
        }
        for (List<String> article : articles) {
            if (!textList.contains(article)) {
                textList.add(article);
            }
        }
        jsonData.put("textList", textList);

        // write back to file
        try {
            objectMapper.writeValue(textFile, jsonData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean removeText(List<List<String>> articles) throws IOException {
        Map<String, List<List<String>>> jsonData = objectMapper.readValue(textFile, Map.class);

        List<List<String>> textList = jsonData.get("textList");
        if (textList == null) {
            textList = new ArrayList<>();
        }
        for (List<String> article : articles) {
            textList.remove(article);
        }
        jsonData.put("textList", textList);

        // write back to file
        try {
            objectMapper.writeValue(textFile, jsonData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<List<String>> getAllText() throws IOException {
        Map<String, List<List<String>>> jsonData = objectMapper.readValue(textFile, Map.class);

        List<List<String>> textList = jsonData.get("textList");
        return textList;
    }

}

