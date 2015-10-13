package uk.ac.ox.it.ords.api.audit.services;

import java.util.List;
import uk.ac.ox.is.ords.api.audit.model.*;



public interface AuditService {

	/**
	 * getAuditListForProject
	 * @param projectId
	 * @return List<Audit>: A list of audit objects associated with that project id
	 */
	abstract List<Audit>getAuditListForProject( int projectId );
	
	/**
	 * getAuditListForUser
	 * @param userId
	 * @return List<Audit>: A list of audit objects associated with the user id
	 */
	abstract List<Audit>getAuditListForUser( int userId );
	
	/**
	 * createNewAudit
	 * @param newAudit: an audit object initialized with the values to save
	 * @return true or false
	 */
	abstract Boolean createNewAudit( Audit newAudit );
	
	/**
	 * deleteAudit
	 * @param auditToDelete: an audit object which will be deleted along with the associated row.
	 */
	abstract void deleteAudit( Audit auditToDelete );
}
