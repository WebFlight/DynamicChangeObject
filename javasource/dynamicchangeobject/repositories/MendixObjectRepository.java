package dynamicchangeobject.repositories;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;
import dynamicchangeobject.proxies.constants.Constants;
import static com.mendix.webui.FeedbackHelper.*;

public class MendixObjectRepository {
	private IContext context;
	
	public MendixObjectRepository (IContext context) {
		this.context = context;
	}
	
	public void setValue (IMendixObject object, String memberName, Object value) {
		object.setValue(this.context, memberName, value);
	}
	
	public IMendixObject commit (IMendixObject object) throws CoreException {
		return (Core.commit(context, object));
	}
	
	public IMendixObject commitWithoutEvents (IMendixObject object) throws CoreException {
		return (Core.commitWithoutEvents(context, object));
	}
	
	public void refresh (IMendixObject object) {
		addRefreshClass(context, object.getType());
	}
	
	public IMendixObjectMember<?> getMember(IMendixObject object, String memberName) {
		return object.getMember(context, memberName);
	}
	
	public String getDateTimeFormat() {
		return Constants.getDATETIME_FORMAT();
	}
}
