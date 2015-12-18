/*
 * Copyright 2015 University of Oxford
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ox.it.ords.api.audit.resources;

import java.net.URI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;

import uk.ac.ox.it.ords.api.audit.permissions.AuditPermissions;
import uk.ac.ox.it.ords.security.model.Audit;
import uk.ac.ox.it.ords.security.services.AuditService;

@Api(value="Audit")
public class AuditResource {

	@ApiOperation(value="Creates a new audit entry")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Audit record successfully created.",
					responseHeaders = {@ResponseHeader(name = "Location", description = "The URI of the record", response = URI.class)}
					),
		    @ApiResponse(code = 400, message = "Invalid Project."),
		    @ApiResponse(code = 403, message = "Not authorized to create an audit record.")
	})
	// 
	// These are used by upstream gateways; including them here makes it easier to use an API portal
	//
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Version", value = "API version number", required = false, dataType = "string", paramType = "header"),
	    @ApiImplicitParam(name = "Authorization", value = "API key", required = false, dataType = "string", paramType = "header"),
	  })
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAuditRecord(
			Audit audit
			){
		
		if (audit == null){
			return Response.status(400).build();			
		}
		
		if (!SecurityUtils.getSubject().isPermitted(AuditPermissions.CREATE_AUDIT_RECORD)){
			return Response.status(403).build();
		}
		
		AuditService.Factory.getInstance().createNewAudit(audit);
		
		return Response.status(201).build();
	}
	
	@ApiOperation(value="Gets audit records for a project")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Audit records successfully returned.", response=Audit.class, responseContainer="List"),
		    @ApiResponse(code = 400, message = "Invalid ID supplied."),
		    @ApiResponse(code = 403, message = "Not authorized to view audit records for this project."),
		    @ApiResponse(code = 404, message = "Project not found."),
		    @ApiResponse(code = 410, message = "Project has been deleted."),
	})
	// 
	// These are used by upstream gateways; including them here makes it easier to use an API portal
	//
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Version", value = "API version number", required = false, dataType = "string", paramType = "header"),
	    @ApiImplicitParam(name = "Authorization", value = "API key", required = false, dataType = "string", paramType = "header"),
	  })
	@GET
	@Path("/project/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuditRecordsForProject(
			 @PathParam("id") int id
			) throws Exception{

		if (!SecurityUtils.getSubject().isPermitted(AuditPermissions.VIEW_AUDIT_RECORD_FOR_PROJECT(id))
			&& ! SecurityUtils.getSubject().isPermitted(AuditPermissions.VIEW_ALL_AUDIT_RECORDS)
				){
			return Response.status(403).build();
		}

		return Response.ok(AuditService.Factory.getInstance().getAuditListForProject(id)).build();
	}
	
	@ApiOperation(value="Gets audit records for a user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Audit records successfully returned.", response=Audit.class, responseContainer="List"),
		    @ApiResponse(code = 400, message = "Invalid ID supplied."),
		    @ApiResponse(code = 403, message = "Not authorized to view audit records for this user."),
		    @ApiResponse(code = 404, message = "User not found.")
	})
	// 
	// These are used by upstream gateways; including them here makes it easier to use an API portal
	//
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Version", value = "API version number", required = false, dataType = "string", paramType = "header"),
	    @ApiImplicitParam(name = "Authorization", value = "API key", required = false, dataType = "string", paramType = "header"),
	  })
	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuditRecordsForUser(
			 @PathParam("id") String id
			){
				
		//
		// You need to be logged in to view your audit records 
		//
		if (SecurityUtils.getSubject().getPrincipal() == null || SecurityUtils.getSubject().getPrincipal().equals("anonymous")){
			return Response.status(401).build();
		}
		
		//
		// You can only view your own audit records, unless you have the global audit view permission
		//
		if (!SecurityUtils.getSubject().getPrincipal().equals(id)
			&& ! SecurityUtils.getSubject().isPermitted(AuditPermissions.VIEW_ALL_AUDIT_RECORDS)){
			return Response.status(403).build();
		}
		
		return Response.ok(AuditService.Factory.getInstance().getAuditListForUser(id)).build();
	}

}
