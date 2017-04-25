package dynamicchangeobject.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.mendix.core.objectmanagement.member.*;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.DataValidationRuntimeException;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;

import dynamicchangeobject.repositories.MendixObjectRepository;

public class ValidateMemberValue {
	
	public ValidateMemberValue() {}
	
	public static Object validate(MendixObjectRepository mendixObjectRepository, IMendixObject object, String memberName, String newValue) throws Exception {
		IMendixObjectMember<?> member = mendixObjectRepository.getMember(object, memberName);
		if (member instanceof MendixDateTime) {
			DateFormat df = new SimpleDateFormat(mendixObjectRepository.getDateTimeFormat(), Locale.ENGLISH);
			return df.parse(newValue);
		}
		if (member instanceof MendixBoolean) {
			if(newValue.toLowerCase() == "true" || newValue.toLowerCase() == "false") {
				return new Boolean(newValue);
			}
			throw new DataValidationRuntimeException("New value is not a boolean: true or false.");
		}
		if (member instanceof MendixAutoNumber) {
			throw new MendixRuntimeException("Value of AutoNumber cannot be changed.");
		}
		if (member instanceof MendixObjectReference || member instanceof MendixObjectReferenceSet) {
			throw new MendixRuntimeException("Value of references cannot be changed.");
		}
		
		return newValue;
	}
}
