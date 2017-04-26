package dynamicchangeobject.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import dynamicchangeobject.helpers.ValidateMemberValue;
import dynamicchangeobject.proxies.ENU_Commit;
import dynamicchangeobject.repositories.MendixObjectRepository;
import dynamicchangeobject.usecases.SetMemberValue;

public class SetMemberValueTest {
	
	private ILogNode logger;
	private MendixObjectRepository mendixObjectRepository;
	private ValidateMemberValue validateMemberValue;
	private IMendixObject object;
	private SetMemberValue setMemberValue;

	@Before
	public void setUp() throws Exception {
		logger = mock(ILogNode.class);
		mendixObjectRepository = mock(MendixObjectRepository.class);
		validateMemberValue = mock(ValidateMemberValue.class);
		object = mock(IMendixObject.class);
	}

	@Test
	public void setValueInvalidMember() throws Exception {
		String notExistingMember = "NotExistingMember";
		
		when(mendixObjectRepository.hasMember(object, notExistingMember)).thenReturn(false);
		setMemberValue = new SetMemberValue(logger, mendixObjectRepository, validateMemberValue, object, notExistingMember, "NewValue", ENU_Commit.No, false);
		assertFalse(setMemberValue.setValue());
		verify(mendixObjectRepository, times(1)).hasMember(object, notExistingMember);
	}

	@Test
	public void setValueValidMemberNoCommitNoRefresh() throws Exception {
		String existingMember = "ExistingMember";
		String newValue = "NewValue";
		
		when(mendixObjectRepository.hasMember(object, existingMember)).thenReturn(true);
		when(validateMemberValue.validate(mendixObjectRepository, object, existingMember, newValue)).thenReturn(newValue);
		setMemberValue = new SetMemberValue(logger, mendixObjectRepository, validateMemberValue, object, existingMember, newValue, ENU_Commit.No, false);
		assertTrue(setMemberValue.setValue());
		verify(mendixObjectRepository, times(1)).hasMember(object, existingMember);
		verify(mendixObjectRepository, times(1)).setValue(object, existingMember, newValue);
		verify(mendixObjectRepository, times(0)).commit(object);
		verify(mendixObjectRepository, times(0)).commitWithoutEvents(object);
		verify(mendixObjectRepository, times(0)).refresh(object);
	}
	
	@Test
	public void setValueValidMemberCommitNoRefresh() throws Exception {
		String existingMember = "ExistingMember";
		String newValue = "NewValue";
		
		when(mendixObjectRepository.hasMember(object, existingMember)).thenReturn(true);
		when(validateMemberValue.validate(mendixObjectRepository, object, existingMember, newValue)).thenReturn(newValue);
		setMemberValue = new SetMemberValue(logger, mendixObjectRepository, validateMemberValue, object, existingMember, newValue, ENU_Commit.Yes, false);
		assertTrue(setMemberValue.setValue());
		verify(mendixObjectRepository, times(1)).hasMember(object, existingMember);
		verify(mendixObjectRepository, times(1)).setValue(object, existingMember, newValue);
		verify(mendixObjectRepository, times(1)).commit(object);
		verify(mendixObjectRepository, times(0)).commitWithoutEvents(object);
		verify(mendixObjectRepository, times(0)).refresh(object);
	}
	
	@Test
	public void setValueValidMemberCommitWithoutEventsNoRefresh() throws Exception {
		String existingMember = "ExistingMember";
		String newValue = "NewValue";
		
		when(mendixObjectRepository.hasMember(object, existingMember)).thenReturn(true);
		when(validateMemberValue.validate(mendixObjectRepository, object, existingMember, newValue)).thenReturn(newValue);
		setMemberValue = new SetMemberValue(logger, mendixObjectRepository, validateMemberValue, object, existingMember, newValue, ENU_Commit.Yes_without_events, false);
		assertTrue(setMemberValue.setValue());
		verify(mendixObjectRepository, times(1)).hasMember(object, existingMember);
		verify(mendixObjectRepository, times(1)).setValue(object, existingMember, newValue);
		verify(mendixObjectRepository, times(0)).commit(object);
		verify(mendixObjectRepository, times(1)).commitWithoutEvents(object);
		verify(mendixObjectRepository, times(0)).refresh(object);
	}
	
	@Test
	public void setValueValidMemberCommitWithoutEventsRefresh() throws Exception {
		String existingMember = "ExistingMember";
		String newValue = "NewValue";
		
		when(mendixObjectRepository.hasMember(object, existingMember)).thenReturn(true);
		when(validateMemberValue.validate(mendixObjectRepository, object, existingMember, newValue)).thenReturn(newValue);
		setMemberValue = new SetMemberValue(logger, mendixObjectRepository, validateMemberValue, object, existingMember, newValue, ENU_Commit.Yes_without_events, true);
		assertTrue(setMemberValue.setValue());
		verify(mendixObjectRepository, times(1)).hasMember(object, existingMember);
		verify(mendixObjectRepository, times(1)).setValue(object, existingMember, newValue);
		verify(mendixObjectRepository, times(0)).commit(object);
		verify(mendixObjectRepository, times(1)).commitWithoutEvents(object);
		verify(mendixObjectRepository, times(1)).refresh(object);
	}
}
