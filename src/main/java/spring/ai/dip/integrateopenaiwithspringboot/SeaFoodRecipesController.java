package spring.ai.dip.integrateopenaiwithspringboot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seafood-recipes")
public class SeaFoodRecipesController {

    private final ChatClient chatClient;

    public SeaFoodRecipesController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/suggest-recipe")
    public String suggestRecipe(
            @RequestParam(
                    name = "message",
                    defaultValue = "Suggest a recipe for dinner"
            ) String message
    ){
        final String systemMessage = """
            Suggest a seafood recipe based on the user's request.
            If Someone asks about something else, just say i don't know about that.
            """;
        return this.chatClient.prompt()
                .system(c-> c.text(systemMessage))
                .user(message)
                .call()
                .content();
    }
}
