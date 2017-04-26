package dynamicchangeobject.usecases;

import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import dynamicchangeobject.helpers.ValidateMemberValue;
import dynamicchangeobject.proxies.ENU_Commit;
import dynamicchangeobject.repositories.MendixObjectRepository;

public class SetMemberValue {
	private ILogNode logger;
	private MendixObjectRepository mendixObjectRepository;
	private ValidateMemberValue validateMemberValue;
	private IMendixObject object;
	private String member;
	private String newValue;
	private dynamicchangeobject.proxies.ENU_Commit commit;
	private boolean refreshInClient;
	
	public SetMemberValue(ILogNode logger, MendixObjectRepository mendixObjectRepository, ValidateMemberValue validateMemberValue, IMendixObject object, String member, String newValue, ENU_Commit commit,
			boolean refreshInClient) {
		super();
		this.logger = logger;
		this.mendixObjectRepository = mendixObjectRepository;
		this.validateMemberValue = validateMemberValue;
		this.object = object;
		this.member = member;
		this.newValue = newValue;
		this.commit = commit;
		this.refreshInClient = refreshInClient;
	}

	public boolean setValue() throws Exception {
		if(isInvalidMember()) {
			logger.error("Member " + member + " does not exist. New value is not set.");
			return false;
		} else { 
			Object newValueValidated = validateMemberValue.validate(mendixObjectRepository, object, member, newValue);
			mendixObjectRepository.setValue(object, member, newValueValidated);
			commit();
			refreshInClient();
			return true;
		}
	}
	
	private boolean isInvalidMember() {
		if (!object.hasMember(member)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void commit() throws CoreException {
		switch (commit) {
			case Yes: 
				mendixObjectRepository.commit(object);
				break;
			case Yes_without_events:
				mendixObjectRepository.commitWithoutEvents(object);
			case No:
				break;
		}
	}
	
	private void refreshInClient() {
		if(refreshInClient) {
			mendixObjectRepository.refresh(object);
		}
	}
 }
