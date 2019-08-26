package com.digitalsolutionsbydon.devdesk.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Custom View for Roles Table", description = "This is the view for the /users/roles Endpoint")
public interface RoleIdsAndNames
{
    @ApiModelProperty(name="roleid", value = "RoleId from the Database", required = false, example = "1")
    long getRoleid();

    @ApiModelProperty(name="name", value = "Name from the Database", required = false, example = "admin")
    String getName();
}
