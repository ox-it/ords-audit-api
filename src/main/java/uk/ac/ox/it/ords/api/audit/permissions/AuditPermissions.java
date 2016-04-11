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
package uk.ac.ox.it.ords.api.audit.permissions;

public class AuditPermissions {
	
	public static final String CREATE_AUDIT_RECORD = "audit:create";

	public static final String VIEW_ALL_AUDIT_RECORDS = "audit:view";

	/**
	 * Get the permission string used to view the audit for a project.
	 * 
	 * @param projectId
	 * @return the permission for viewing this specific project
	 */
	public static String VIEW_AUDIT_RECORD_FOR_PROJECT(int projectId){
		return "project:view:"+projectId;
	}
	
	/**
	 * Get the permission string used to view the audit for a database.
	 * 
	 * @param logicalDatabaseId
	 * @return the permission for viewing this specific database
	 */
	public static String VIEW_AUDIT_RECORD_FOR_DATABASE(int logicalDatabaseId){
		return "database:view:"+logicalDatabaseId;
	}

}
