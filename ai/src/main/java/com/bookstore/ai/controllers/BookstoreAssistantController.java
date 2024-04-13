package com.bookstore.ai.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

    private final OpenAiChatClient openAiChatClient;

    public BookstoreAssistantController(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    @GetMapping("/informations")
    public String bookstoreChat(@RequestParam (
            value = "message",
            defaultValue = "Mostre-me um exemplo de como posso fazer uma pesquisa de livros") String search) {
        return openAiChatClient.call(search);
    }

    @GetMapping("/informations/prompt")
    public ChatResponse bookstoreChatPrompt(@RequestParam (
            value = "message",
            defaultValue = "Mostre-me um exemplo de como posso fazer uma pesquisa de livros") String search) {
        return openAiChatClient.call(new Prompt(search));
    }

    @GetMapping("/informations/reviews")
    public String bookstoreChatReview(@RequestParam (
            value = "book",
            defaultValue = "Grande Sertão: Veredas") String book) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                Me forneça um breve resumo do livro {book}.
                """);

        promptTemplate.add("book", book);

        return this.openAiChatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/informations/stream")
    public Flux<String> bookstoreChatStream(@RequestParam (
            value = "message",
            defaultValue = "Mostre-me um exemplo de como posso fazer uma pesquisa de livros") String search) {
        return openAiChatClient.stream(search);
    }

    @GetMapping("/informations/stream/prompt")
    public Flux<ChatResponse> bookstoreChatStreamPrompt(@RequestParam (
            value = "message",
            defaultValue = "Mostre-me um exemplo de como posso fazer uma pesquisa de livros") String search) {
        return openAiChatClient.stream(new Prompt(search));
    }

}
