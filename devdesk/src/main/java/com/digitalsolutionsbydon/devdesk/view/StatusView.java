package com.digitalsolutionsbydon.devdesk.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Custom Status View", description = "This is the model to return all Statuses")
public interface StatusView
{
    @ApiModelProperty(name="statusid", value="Id of Status", required = false, example = "1")
    int getStatusid();

    @ApiModelProperty(name="name", value="Name of Status", required = false, example = "Pending")
    String getName();
}
