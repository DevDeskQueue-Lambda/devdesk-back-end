package com.digitalsolutionsbydon.devdesk.models;

import com.digitalsolutionsbydon.devdesk.exceptions.ValidationError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value = "ErrorDetail",
        description = "Error Messages")
public class ErrorDetail
{
    @ApiModelProperty(name = "title",
            value = "Error Title",
            required = false,
            example = "Resource Not Found")
    private String title;
    @ApiModelProperty(name = "status",
            value = "Error Status",
            required = false,
            example = "404")
    private int status;
    @ApiModelProperty(name = "detail",
            value = "Error details",
            required = false,
            example = "User 400 is not in the system.")
    private String detail;
    @ApiModelProperty(name = "timestamp",
            value = "Time Error Occurred",
            required = false,
            example = "25 Aug 2019 12:46:38:887 -0400")
    private String timestamp;
    @ApiModelProperty(name = "developerMessage",
            value = "Developer Specific Messsage",
            required = false,
            example = "com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException")
    private String developerMessage;
    @ApiModelProperty(name = "Validation Errors",
            value = "Validation Errors",
            required = false,
            example = "{}")
    private Map<String, List<ValidationError>> errors = new HashMap<>();

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date(timestamp));
    }

    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage)
    {
        this.developerMessage = developerMessage;
    }

    public Map<String, List<ValidationError>> getErrors()
    {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors)
    {
        this.errors = errors;
    }
}
