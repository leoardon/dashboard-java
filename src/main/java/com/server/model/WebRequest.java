package com.server.model;

import java.util.Map;
import java.util.Random;

public class WebRequest {

    public WebRequest() {
    }

    public WebRequest(Double id, Double modelId, String format, String source, String key, Map<String, Object> params) {
        this.id = id;
        this.modelId = modelId;
        this.format = format;
        this.source = source;
        this.key = key;
        this.params = params;
    }

    private Double id;
    private Double modelId;
    private String format;
    private String source;
    private String key;
    private Map<String, Object> params;

    /**
     * @return the id
     */
    public Double getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Double id) {
        this.id = id;
    }

    /**
     * @return the modelId
     */
    public Double getModelId() {
        return modelId;
    }

    /**
     * @param modelId the modelId to set
     */
    public void setModelId(Double modelId) {
        this.modelId = modelId;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "WebRequest [id=" + id + ", modelId=" + modelId + ", format=" + format + ", source=" + source + ", key=" + key + ", params=" + params.toString() + "]";
    }

    public static class WebRequestBuilder {
        private Double builtId;
        private Double builtModelId;
        private String builtFormat;
        private String builtSource;
        private String builtKey;
        private Map<String, Object> builtParams;

        public WebRequestBuilder() {}

        public WebRequestBuilder withId(Double id) {
            this.builtId = id;
            return this;
        }

        public WebRequestBuilder withModelId(Double modelId) {
            this.builtModelId = modelId;
            return this;
        }

        public WebRequestBuilder withFormat(String format) {
            this.builtFormat = format;
            return this;
        }

        public WebRequestBuilder withSource(String source) {
            this.builtSource = source;
            return this;
        }

        public WebRequestBuilder withKey(String key) {
            this.builtKey = key;
            return this;
        }

        public WebRequestBuilder withParams(Map<String, Object> params) {
            this.builtParams = params;
            return this;
        }

        public WebRequestBuilder withRandomId() {
            Random rand = new Random();
            this.builtId = rand.nextDouble();
            this.builtModelId = this.builtId;
            return this;
        }

        public WebRequest build(){
            if(this.builtKey == null && this.builtParams != null) { 
                this.builtKey = this.builtParams.toString();
            }
            return new WebRequest(builtId, builtModelId, builtFormat, builtSource, builtKey, builtParams);
        }
    }

}