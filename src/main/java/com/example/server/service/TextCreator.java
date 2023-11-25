package com.example.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;


public class TextCreator extends Creator {
        public String askText(String topic) throws JsonProcessingException {
                String answer = askQuestion(topic);
                return answer;
        }

        public List<String> getText(String topic, int wordNumber, float grade_level, float ease_level, String difficulty_level, String special_instructions, String currentText) throws JsonProcessingException {
                String prompt = "Design a text for English reading exam. "
                        + (currentText.isEmpty() ? "" : "Starting with the current text: '" + currentText + "'. ")
                        + "Your reply will be recorded directly in a file, so only generate a text, omitting any additional comments, notes, text or instructions., and don’t let them know you are a generative AI or chatbot. "
                        + "Omitting sentences like ‘Certainly, here is a reading exam text’, ‘Sure, here is a reading exam text’ for the introduction. "
                        + "Now, please generate a text on the topic '" + topic + "'. "
                        + "The text should have " + wordNumber + " words, with an acceptable variation of 10% in either way. "
                        + "The school level should be suitable for " + difficulty_level + ". "
                        + "Ensure that the Flesch-Kincaid Grade Level of the text is precisely " + grade_level
                        + ", and the Flesch Reading Ease Score is exactly  " + ease_level + ". "
                        + "Special Instruction for this text is: " + special_instructions + ". "
                        + "Please use the 2 formulas and 7 guidelines provided below to ensure the text has meet the requirement for Grade Level and Reading Ease Score. "
                        + "Formula 1: Flesch Reading Ease Score = 206.835 - 1.015 × ( Total Words / Total Sentences ) - 84.6 × ( Total Syllables / Total Words ) "
                        + "Formula 2: Flesch-Kincaid Grade Level = 0.39 × ( Total Words / Total Sentences ) + 11.8 × ( Total Syllables / Total Words ) - 15.59 "
                        + "Guideline 1: If 0 <= Flesch Reading Ease Score < 30 or 5 <= Flesch-Kincaid Grade Level < 6 and school level is College Graduate, the text should be very difficult to read. Best understood by university graduates. Usually average 29 words per sentence, and 192 syllables per 100 words. "
                        + "Guideline 2: If 30 <= Flesch Reading Ease Score < 50 or 6 <= Flesch-Kincaid Grade Level < 7 and school level is College, the text should be difficult to read. Usually average 25 words per sentence, and 167 syllables per 100 words. "
                        + "Guideline 3: If 50 <= Flesch Reading Ease Score < 60 or 7 <= Flesch-Kincaid Grade Level < 8 and school level is Grade 10 to 12, the text should be fairly difficult to read. Usually average 21 words per sentence, and 155 syllables per 100 words. "
                        + "Guideline 4: If 60 <= Flesch Reading Ease Score < 70 or 8 <= Flesch-Kincaid Grade Level < 10 and school level is Grade 8 to 9, the text should be plain english. Easily understood by 13- to 15-year-old students. Usually average 17 words per sentence, and 147 syllables per 100 words. "
                        + "Guideline 5: If 70 <= Flesch Reading Ease Score < 80 or 10 <= Flesch-Kincaid Grade Level < 13 and school level is Grade 7, the text should be fairly easy to read. Usually average 14 words per sentence, and 139 syllables per 100 words. "
                        + "Guideline 6: If 80 <= Flesch Reading Ease Score < 90 or 13 <= Flesch-Kincaid Grade Level < 16 and school level is Grade 6, the text easy to read. Conversational English for consumers. Usually average 11 words per sentence, and 131 syllables per 100 words. "
                        + "Guideline 7: If 90 <= Flesch Reading Ease Score <= 100 or 16 <= Flesch-Kincaid Grade Level <= 18 and school level is Grade 5 and below, the text should be very easy to read. Easily understood by an average 11-year-old student. Usually average 8 words per sentence, and 123 syllables per 100 words. "
                        ;
                String ans = askText(prompt);
                String[] lines = ans.split("\n");

                // list to store the topics
                List<String> article = new ArrayList<>();

                for (String paragraph : lines) {
                        article.add(paragraph);
                }
                return article;
        }

        public static void main(String[] args) throws JsonProcessingException {
                TextCreator textCreator = new TextCreator();
                String prompt = "Design a text for English language exam. "
                        + "Only generate a text, omitting any additional comments, notes, text or instructions., and don’t let them know you are a generative AI or chatbot. "
                        + "Omitting sentences like ‘Certainly, here is a reading exam text’, ‘Sure, here is a reading exam text’ for the introduction. "
                        + "Design the text for reading exams and satisfy requirements: "
                        + "1. text length: around 500 words "
                        + "2. Flesch-Kincaid Grade Level: 12 "
                        + "3. Text Topic: " + "The Impact of Climate Change on Polar Bear Habitats in the Arctic";
                String ans = textCreator.askText(prompt);
                String[] lines = ans.split("\n");

                // list to store the topics
                List<String> article = new ArrayList<>();

                for (String paragraph : lines) {
                        article.add(paragraph);
                }
                for (String sentence : article) {
                        System.out.println(sentence);
                }
        }
}