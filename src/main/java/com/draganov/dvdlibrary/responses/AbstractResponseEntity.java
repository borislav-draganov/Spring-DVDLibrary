package com.draganov.dvdlibrary.responses;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * An interface for the different responses the web service will return
 *
 * @author borislav.draganov
 */

@XmlRootElement(name = "response")
public abstract class AbstractResponseEntity {
    protected boolean success;
    protected int statusCode;
    protected String statusMessage;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}