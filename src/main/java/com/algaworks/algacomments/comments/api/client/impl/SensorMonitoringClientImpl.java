package com.algaworks.algacomments.comments.api.client.impl;

import com.algaworks.algacomments.comments.api.client.ModerationClient;
import com.algaworks.algacomments.comments.api.client.RestClientFactory;
import com.algaworks.algacomments.comments.api.model.ModerationInput;
import com.algaworks.algacomments.comments.api.model.ModerationOutput;
import org.springframework.web.client.RestClient;

public class SensorMonitoringClientImpl implements ModerationClient {

    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestCLient();
    }


    @Override
    public ModerationOutput moderateComment(ModerationInput input) {
        return restClient.put()
                .uri("/api/moderate", input)
                .retrieve().body(ModerationOutput.class);
    }
}
