package com.frankmoley.lil.bones;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MainController {

    @GetMapping("/hello")
    Mono<String> getGreeting() {
        return Mono.just("Hello LinkedIn Learning! Never stop learning");
    }

    @GetMapping("/roll")
    Mono<DicePairRoll> getDiceRoll() {
        DicePairRoll dice = new DicePairRoll();
        dice.rollDice();
        return Mono.just(dice);
    }
}