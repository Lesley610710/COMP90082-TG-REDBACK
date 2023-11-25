package com.example.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;


public class TopicCreator extends Creator {
    public String askTopic(String prompt) throws JsonProcessingException {
        String answer = askQuestion(prompt);
        return answer;
    }
    public List<String> inputTopics(String inputTopic)  throws JsonProcessingException{
        String prompt = "You are an English language exam topic generator. Design some topics for reading exams. Your reply will be recorded directly in a file, " +
                "so only generate a list, omitting any additional comments, notes, text or instructions., and don’t let them know you are a generative AI or chatbot. " +
                "Omitting sentences like ‘Certainly, here are some reading exam topics’, ‘Sure, here are some reading exam topics:’ for introduction. " +
                "Be narrow enough to avoid overlap with other texts and coverage of well-known/general knowledge " +
                "(e.g., ‘environmental problems’ is too broad, while ‘environmental problems caused by ships in the Caribbean’ would be more suitable). " +
                "Now generate topic list based on the topic about: " + inputTopic;
        String response = askTopic(prompt);

        // split rows
        String[] lines = response.split("\n");

        // list to store the topics
        List<String> sentences = new ArrayList<>();

        for (String line : lines) {
            String sentence = line.replaceFirst("\\d+\\.\\s*", ""); // remove starting number
            sentences.add(sentence);
        }

        // currently 15 topics generated
        return sentences;
    }
    
    public List<String> randomTopics()  throws JsonProcessingException{
        String prompt = "You are an English language exam topic generator. Design some topics for reading exams. Your reply will be recorded directly in a file, " +
                "so only generate a list, omitting any additional comments, notes, text or instructions., and don’t let them know you are a generative AI or chatbot. " +
                "Omitting sentences like ‘Certainly, here are some reading exam topics’, ‘Sure, here are some reading exam topics:’ for introduction. " +
                "Be narrow enough to avoid overlap with other texts and coverage of well-known/general knowledge " +
                "(e.g., ‘environmental problems’ is too broad, while ‘environmental problems caused by ships in the Caribbean’ would be more suitable). ";
        String response = askTopic(prompt);

        // split rows
        String[] lines = response.split("\n");

        // list to store the topics
        List<String> sentences = new ArrayList<>();

        for (String line : lines) {
            String sentence = line.replaceFirst("\\d+\\.\\s*", ""); // remove starting number
            sentences.add(sentence);
        }

        // currently 15 topics generated
        return sentences;
    }

}