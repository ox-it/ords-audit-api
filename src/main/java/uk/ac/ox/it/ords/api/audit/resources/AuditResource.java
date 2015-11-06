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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.ac.ox.it.ords.security.services.AuditService;

public class AuditResource {
	
	@GET
	@Path("/project/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuditRecordsForProject(
			 @PathParam("id") int id
			){
		return Response.ok(AuditService.Factory.getInstance().getAuditListForProject(id)).build();
	}
	
	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuditRecordsForProject(
			 @PathParam("id") String id
			){
		return Response.ok(AuditService.Factory.getInstance().getAuditListForUser(id)).build();
	}

}
