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

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.ws.rs.core.GenericType;
import org.junit.Test;
import uk.ac.ox.it.ords.security.model.Audit;

public class AuditResourceTest extends AbstractResourceTest{

	
	@Test
	public void voidCreateAuditRecordsInvalid(){
		loginUsingSSO("admin", "admin");
		assertEquals(400, getClient().path("/").post(null).getStatus());
		logout();
	}
	
	@Test
	public void voidCreateAuditRecordsUnauth(){
		Audit audit = new Audit();
		audit.setAuditType(Audit.AuditType.CREATE_PROJECT.name());
		audit.setProjectId(1);
		audit.setUserId("pingu");
		audit.setMessage("pingu has created a project");
		
		assertEquals(403, getClient().path("/").post(audit).getStatus());
	}
	
	@Test
	public void voidCreateAuditRecords(){
		loginUsingSSO("admin", "admin");
		
		Audit audit = new Audit();
		audit.setAuditType(Audit.AuditType.CREATE_PROJECT.name());
		audit.setProjectId(1);
		audit.setUserId("pingu");
		audit.setMessage("pingu has created a project");
		
		assertEquals(201, getClient().path("/").post(audit).getStatus());
		
		List<Audit> audits = getClient().path("/project/1").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(1, audits.get(0).getProjectId());
		assertEquals(Audit.AuditType.CREATE_PROJECT.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingu", audits.get(0).getUserId());
		assertEquals("pingu has created a project", audits.get(0).getMessage());
		
		audits = getClient().path("/user/pingu").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(1, audits.get(0).getProjectId());
		assertEquals(Audit.AuditType.CREATE_PROJECT.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingu", audits.get(0).getUserId());
		assertEquals("pingu has created a project", audits.get(0).getMessage());
		
		logout();
	}
	
	@Test
	public void voidCreateDatabaseAuditRecords(){
		loginUsingSSO("admin", "admin");
		
		Audit audit = new Audit();
		audit.setAuditType(Audit.AuditType.CREATE_PHYSICAL_DATABASE.name());
		audit.setLogicalDatabaseId(11);
		audit.setUserId("pingo");
		audit.setMessage("pingo has created a database");
		assertEquals(201, getClient().path("/").post(audit).getStatus());
		
		List<Audit> audits = getClient().path("/database/11").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(0, audits.get(0).getProjectId());
		assertEquals(11, audits.get(0).getLogicalDatabaseId());
		assertEquals(Audit.AuditType.CREATE_PHYSICAL_DATABASE.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingo", audits.get(0).getUserId());
		assertEquals("pingo has created a database", audits.get(0).getMessage());
		
		audits = getClient().path("/user/pingo").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(11, audits.get(0).getLogicalDatabaseId());
		assertEquals(Audit.AuditType.CREATE_PHYSICAL_DATABASE.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingo", audits.get(0).getUserId());
		assertEquals("pingo has created a database", audits.get(0).getMessage());
		
		logout();
	}
	
	@Test
	public void voidViewAuditAsUser(){
		
		loginUsingSSO("pingu", "pingu");
		
		List<Audit> audits = getClient().path("/project/1").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(1, audits.get(0).getProjectId());
		assertEquals(Audit.AuditType.CREATE_PROJECT.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingu", audits.get(0).getUserId());
		assertEquals("pingu has created a project", audits.get(0).getMessage());
		
		audits = getClient().path("/user/pingu").get().readEntity(new GenericType<List<Audit>>(){});
		assertEquals(1, audits.size());
		assertEquals(1, audits.get(0).getProjectId());
		assertEquals(Audit.AuditType.CREATE_PROJECT.name().replace("_", " "), audits.get(0).getAuditType());
		assertEquals("pingu", audits.get(0).getUserId());
		assertEquals("pingu has created a project", audits.get(0).getMessage());
		
		logout();
	}
	
	@Test
	public void viewProjectAuditUnauth(){
		
		loginUsingSSO("pinga", "pinga");
		assertEquals(403, getClient().path("/project/1").get().getStatus());
		logout();
		assertEquals(403, getClient().path("/project/1").get().getStatus());
		
		
	}
	
	@Test
	public void viewUserAuditUnauth(){
		
		loginUsingSSO("pinga", "pinga");
		assertEquals(403, getClient().path("/user/pingu").get().getStatus());
		logout();
		assertEquals(401, getClient().path("/user/pingu1").get().getStatus());
		
		
	}
	
	

}
