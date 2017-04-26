# Mendix DynamicChangeObject module

Welcome to the Mendix *DynamicChangeObject* module. This module provides a Java action that mimics the functionality of the Change object microflow activity, but enables the use of expressions for all inputs.


![DynamicChangeObject logo][5]

# Table of Contents

* [Getting Started](#getting-started)
* [Application](#application)
* [Technical implementation](#technical-implementation)
* [Development Notes](#development-notes)

# Getting started
* Download the module from the Mendix App store.
* Apply the *DynamicChangeObject* in any microflow where an object member value is changed. 
* Adjust the DATETIME_FORMAT constant if necessary.

# Application
Applicable in situations when dynamic behavior is desired for change of object members, for instance:
* Change member values of various objects, depending on other conditions.
* Change member values of various object members, depending on other conditions.
* Different commit/refresh in client behavior, depending on other conditions.

## Examples

### Change object in front-end
This front-end implements the change object functionalities:

![Implement change object in front-end][1]

The DynamicChangeObjectView object is used to pass all parameters from the front-end:

![DynamicChangeObjectView application][2]

### Set a new DateTime value:
A new DateTime object is parsed as a string using the DATETIME_FORMAT constant:

![New DateTime value][3]

The microflow in which the DateTime value is set:

![MicroFlow that sets new DateTime value][4]

# Technical implementation
* Checks if the object member exists. If not, an error is shown and false is returned.
* Validates the new member value using the *ValidateMemberValue* class.
	* DateTime values are parsed according to the format defined in the DATETIME_FORMAT constant.
	* Booleans are parsed "True"/"true" => true and "False"/"false" => false.
	* Values of AutoNumbers and MendixObjectReference(Set) cannot be changed. An exception will be thrown.

# Development notes
* For contributions: fork this repository, make changes, fix/add unit tests in dynamicchangeobject.tests package and issue pull request.

 [1]: docs/DynamicChangeObject_Popup.PNG
 [2]: docs/DynamicChangeObject_Window.PNG
 [3]: docs/DynamicChangeObject_WindowDateTime.PNG
 [4]: docs/DynamicChangeObject_Microflow.PNG
 [5]: docs/DynamicChangeObject_logo.png