package com.algaworks.algacomments.comments.api.client;

import com.algaworks.algacomments.comments.api.model.ModerationInput;
import com.algaworks.algacomments.comments.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerationClient {

    @PostExchange
    ModerationOutput moderateComment(@RequestBody ModerationInput input);

}
