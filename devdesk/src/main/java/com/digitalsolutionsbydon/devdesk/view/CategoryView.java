package com.digitalsolutionsbydon.devdesk.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Custom Category View", description = "This is the view ")
public interface CategoryView
{
    @ApiModelProperty(name="categoryid", value="Category Id from Categories Table", example="1")
    int getCategoryid();

    @ApiModelProperty(name="name", value="Name of the Category", example = "React")
    String getName();
}
