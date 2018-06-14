package com.speechpro.biometric.platform.onepass.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sadurtinova on 13.06.2018.
 */
public class ModelDto {

    public int id;

    public String modelType;

    public int samplesCount;

    public List<String> passwordPhrases;

    @JsonCreator()
    public ModelDto(@JsonProperty("id") int id,
                    @JsonProperty("type") String type,
                    @JsonProperty("passwordPhrases") List<String> passwordPhrases,
                    @JsonProperty("samplesCount") int samplesCount){
        this.id = id;
        this.modelType = type;
        this.passwordPhrases = passwordPhrases;
        this.samplesCount = samplesCount;
    }

}
