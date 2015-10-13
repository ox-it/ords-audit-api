package uk.ac.ox.is.ords.api.audit.model;

import java.sql.Timestamp;
import java.util.Date;

public class Audit {
    private int auditId;
    public enum AuditType { GENERIC_NOTAUTH, LOGIN, LOGIN_FAILED, LOGOFF, SIGNUP, SIGNUP_FAILED,
    CREATE_PROJECT, DELETE_PROJECT, UPDATE_PROJECT, CREATE_PROJECT_FAILED,
    CREATE_PROJECT_BILLING, DELETE_PROJECT_BILLING, UPDATE_PROJECT_BILLING,
    UPDATE_PROJECT_USER, DELETE_PROJECT_USER, CREATE_PROJECT_USER,
    COMMIT_NEW_DATA, COMMIT_DATA_CHANGE, DELETE_DATA_ROW,
    CREATE_PHYSICAL_DATABASE, CREATE_LOGICAL_DATABASE, EDIT_LOGICAL_DATABASE, UPLOAD_DATABASE,
    DELETE_PHYSICAL_DATABASE, DELETE_LOGICAL_DATABASE,
    CREATE_VIEW, DELETE_VIEW,
    ADD_USER_TO_DATABASE_FOR_ODBC, REMOVE_USER_TO_DATABASE_FOR_ODBC, ADD_ODBC_ROLE, REMOVE_ODBC_ROLE,
    ADD_IP_FOR_USER,
    RUN_USER_QUERY};
    private String auditType;
    private String message = "";
    private int projectId;
    /**
     * This is the user who causes the audit - i.e. the actor
     */
    private int userId;
    private Timestamp timeOfOperation = new Timestamp(new Date().getTime());// = new Date(System.currentTimeMillis());

    
	public int getAuditId() {
		return auditId;
	}
  
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}
	
	public String getAuditType() {
		return auditType;
	}
	
	public void setAuditType(String auditType) {
        this.auditType = auditType.toString().replace("_", " ");
	}
	
	public String getMessage() {
		return message;
	}
	
	
    public void setMessage(String message) {
        if (message == null) {
            this.message = "";
        }
        else if (message.length() > 10000) {
            this.message = message.substring(0, 9999);
        }
        else {
            this.message = message;
        }
    }
    
    
    
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getTimeOfOperation() {
		return timeOfOperation;
	}
	public void setTimeOfOperation(Timestamp timeOfOperation) {
		this.timeOfOperation = timeOfOperation;
	}

    
    
}
