# Mendix DynamicChangeObject module

Welcome to the Mendix DynamicChangeObject module. This module provides a Java action that mimics the functionality of the Change object microflow activity, but enables the use of expressions for all inputs:
* Object
* Member name
* New value
* Commit behavior
* Refresh in client

# Table of Contents

* [Getting Started](#getting-started)
* [Application](#application)
* [Technical implementation](#technical-implementation)
* [Development Notes](#development-notes)

# Getting started
* Download the module from the Mendix App store.
* Apply the DynamicChangeObject in any microflow where an object member value is changed. 
* Adjust the DATETIME_FORMAT constant if necessary.

# Application
Applicable in situations when dynamic behavior is desired for change of object members, for instance:
* Change member values of various objects, depending on other conditions.
* Change member values of various object members, depending on other conditions.
* Different commit/refresh in client behavior, depending on other conditions.

# Technical implementation
* Checks if the object member exists. If not, an error is shown and false is returned.
* Validates the new member value using the *ValidateMemberValue* class.
	* DateTime values are parsed according to the format defined in the DATETIME_FORMAT constant.
	* Booleans are parsed "True"/"true" => true and "False"/"false" => false.
	* Values of AutoNumbers and MendixObjectReference(Set) cannot be changed. An exception will be thrown.

# Development notes
* For contributions: fork this repository, make changes, fix/add unit tests in dynamicchangeobject.tests package and issue pull request.