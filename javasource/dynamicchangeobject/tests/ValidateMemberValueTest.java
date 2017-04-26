package dynamicchangeobject.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.mendix.core.objectmanagement.member.MendixAutoNumber;
import com.mendix.core.objectmanagement.member.MendixBoolean;
import com.mendix.core.objectmanagement.member.MendixDateTime;
import com.mendix.core.objectmanagement.member.MendixObjectReference;
import com.mendix.core.objectmanagement.member.MendixObjectReferenceSet;
import com.mendix.core.objectmanagement.member.MendixString;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.DataValidationRuntimeException;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;

import dynamicchangeobject.helpers.ValidateMemberValue;
import dynamicchangeobject.repositories.MendixObjectRepository;

public class ValidateMemberValueTest {
	
	private MendixObjectRepository mendixObjectRepository;
	private IMendixObject object;
	private MendixDateTime mendixDateTime;
	private MendixBoolean mendixBoolean;
	private MendixAutoNumber mendixAutoNumber;
	private MendixObjectReference mendixObjectReference;
	private MendixString mendixString;
	private MendixObjectReferenceSet mendixObjectReferenceSet;
	private ValidateMemberValue validateMemberValue;

	@Before
	public void setUp() throws Exception {
		mendixObjectRepository = mock(MendixObjectRepository.class);
		object = mock(IMendixObject.class);
		mendixDateTime = mock(MendixDateTime.class);
		mendixBoolean = mock(MendixBoolean.class);
		mendixAutoNumber = mock(MendixAutoNumber.class);
		mendixObjectReference = mock(MendixObjectReference.class);
		mendixString = mock(MendixString.class);
		mendixObjectReferenceSet = mock(MendixObjectReferenceSet.class);
		validateMemberValue = new ValidateMemberValue();
	}

	@Test
	public void TestDateTime() throws Exception {
		String newDateString = "2017-04-26T11:58:12.123+0200";
		
		when(mendixObjectRepository.getMember(object, "DateTimeAttribute")).thenReturn((IMendixObjectMember) mendixDateTime);
		when(mendixObjectRepository.getDateTimeFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		DateFormat df = new SimpleDateFormat(mendixObjectRepository.getDateTimeFormat(), Locale.ENGLISH);
		Date expectedDate = df.parse(newDateString);
		Date actualDate = (Date) validateMemberValue.validate(mendixObjectRepository, object, "DateTimeAttribute", newDateString);
		assertEquals(expectedDate, actualDate);
	}

	@Test
	public void TestBooleanTrue() throws Exception {
		String booleanString = "true";
		
		when(mendixObjectRepository.getMember(object, "BooleanAttribute")).thenReturn((IMendixObjectMember) mendixBoolean);
		boolean actualBoolean = (boolean) validateMemberValue.validate(mendixObjectRepository, object, "BooleanAttribute", booleanString);
		boolean expectedBoolean = true;
		
		assertEquals(expectedBoolean, actualBoolean);
	}
	
	@Test
	public void TestBooleanFalse() throws Exception {
		String booleanString = "false";
		
		when(mendixObjectRepository.getMember(object, "BooleanAttribute")).thenReturn((IMendixObjectMember) mendixBoolean);
		boolean actualBoolean = (boolean) validateMemberValue.validate(mendixObjectRepository, object, "BooleanAttribute", booleanString);
		boolean expectedBoolean = false;
		
		assertEquals(expectedBoolean, actualBoolean);
	}
	
	@Test(expected = DataValidationRuntimeException.class)
	public void TestBooleanException() throws Exception {
		String booleanString = "noboolean";
		
		when(mendixObjectRepository.getMember(object, "BooleanAttribute")).thenReturn((IMendixObjectMember) mendixBoolean);
		validateMemberValue.validate(mendixObjectRepository, object, "BooleanAttribute", booleanString);
	}
	
	@Test(expected = MendixRuntimeException.class)
	public void TestAutoNumberException() throws Exception {		
		when(mendixObjectRepository.getMember(object, "AutoNumberAttribute")).thenReturn((IMendixObjectMember) mendixAutoNumber);
		validateMemberValue.validate(mendixObjectRepository, object, "AutoNumberAttribute", "NewValue");
	}
	
	@Test(expected = MendixRuntimeException.class)
	public void TestObjectReferenceException() throws Exception {		
		when(mendixObjectRepository.getMember(object, "ReferenceAttribute")).thenReturn((IMendixObjectMember) mendixObjectReference);
		validateMemberValue.validate(mendixObjectRepository, object, "ReferenceAttribute", "NewValue");
	}
	
	@Test(expected = MendixRuntimeException.class)
	public void TestObjectReferenceSetException() throws Exception {		
		when(mendixObjectRepository.getMember(object, "ReferenceSetAttribute")).thenReturn((IMendixObjectMember) mendixObjectReferenceSet);
		validateMemberValue.validate(mendixObjectRepository, object, "ReferenceSetAttribute", "NewValue");
	}
	
	@Test
	public void TestString() throws Exception {
		String newString = "NewString";
		
		when(mendixObjectRepository.getMember(object, "StringAttribute")).thenReturn((IMendixObjectMember) mendixString);
		
		String actualString = (String) validateMemberValue.validate(mendixObjectRepository, object, "DateTimeAttribute", newString);
		String expectedString = newString;
		assertEquals(expectedString, actualString);
	}
}
