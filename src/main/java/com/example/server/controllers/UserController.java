package com.example.server.controllers;

import com.example.server.DAO.JsonFileOperator;
import com.example.server.entity.*;
import com.example.server.service.TextCreator;
import com.example.server.service.TopicCreator;
import com.example.server.viewResponse.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "https://tg-backend-397705.web.app")
@RequestMapping("/user")
public class UserController {
    JsonFileOperator dbOperator = new JsonFileOperator();

    @PostMapping("/login")
    public Response detail(@RequestBody LoginInfo loginInfo) throws IOException {
        String email = loginInfo.email;
        String inputPassword = loginInfo.password;
        log.info("User {} wants to log in", email);
        // get user's detail and compare password correct or not
        UserInfo userInfo = dbOperator.getUser(email);
        String correctPassword = userInfo.password;
        if (inputPassword.equals(correctPassword)) {
            // password verified
            return Response.success(userInfo);
        } else return Response.fail();
    }

    @GetMapping("/topic_list/randomGenerate")
    public Response randomTopic() throws IOException {
        log.info("topic generating request");
        TopicCreator topicCreator = new TopicCreator();
        List<String> topics = topicCreator.randomTopics();
        if (!topics.isEmpty()) {
            return Response.success(topics);
        }
        return Response.fail();
    }

    @GetMapping("/topic_list/inputGenerate/{topic}")
    public Response inputTopic(@PathVariable String topic) throws IOException {
        log.info("input topic generating request");
        TopicCreator topicCreator = new TopicCreator();
        List<String> topics = topicCreator.inputTopics(topic);
        if (!topics.isEmpty()) {
            return Response.success(topics);
        }
        return Response.fail();
    }

    @PostMapping("/topic_list/add")
    public Response addTopic(@RequestBody Topics topic) throws IOException {
        List<String> topics = topic.topics;
        log.info("store topic list request");
        if (!topics.isEmpty()) {
            dbOperator.addTopic(topics);
            return Response.success();
        } else  return Response.fail();
    }

    @PostMapping("/topic_list/remove")
    public Response removeTopic(@RequestBody Topics topic) throws IOException {
        List<String> topics = topic.topics;
        log.info("remove topic list request");
        if (!topics.isEmpty()) {
            dbOperator.removeTopic(topics);
            return Response.success();
        } else  return Response.fail();
    }

    @GetMapping("/topic_list/getAll")
    public Response getTopic() throws IOException {
        log.info("get all topic list request");
        List<String> topics = dbOperator.getAllTopic();
        if (!topics.isEmpty()) {
            return Response.success(topics);
        } else  return Response.fail();
    }

    @PostMapping("/text_generation")
    public Response generateText(@RequestBody TextRequest textRequest) throws IOException {
        TextCreator textCreator = new TextCreator();
        log.info("text generating request");
        List<String> article = textCreator.getText(textRequest.topic, textRequest.wordNumber, textRequest.grade_level, textRequest.ease_level, textRequest.difficulty_level, textRequest.special_instructions, textRequest.currentText);
        if (!article.isEmpty()) {
            return Response.success(article);
        } else  return Response.fail();
    }

    @PostMapping("/text_generation/add")
    public Response addText(@RequestBody Text text) throws IOException {
        List<List<String>> articles = text.textList;
        log.info("store text list request");
        if (!articles.isEmpty()) {
            dbOperator.addText(articles);
            return Response.success();
        } else  return Response.fail();
    }

    @PostMapping("/text_generation/remove")
    public Response removeTopic(@RequestBody Text text) throws IOException {
        List<List<String>> articles = text.textList;
        log.info("remove text list request");
        if (!articles.isEmpty()) {
            dbOperator.removeText(articles);
            return Response.success();
        } else  return Response.fail();
    }

    @GetMapping("/text_generation/getAll")
    public Response getText() throws IOException {
        log.info("get all topic list request");
        List<List<String>> articles = dbOperator.getAllText();
        if (!articles.isEmpty()) {
            return Response.success(articles);
        } else  return Response.fail();
    }

}
