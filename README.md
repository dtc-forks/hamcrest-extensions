hamcrest-extensions
===================

hamcrest-extensions add some matchers to hamcrest that help in more special scenarios.

adaptors
--------
This package contains matchers helping you to adapt certain matchers such that they can be used at the place you need them.

* `WideningMatcher` extends another matcher to be applicable to every object. 

arrays
------
This package contains matchers for arrays and primitive arrays.

* `ArrayMatcher` matches object typed arrays in a convenient way. Mixed arrays of matchers and elements are allowed. 
* `PrimitiveArrayMatcher` matches primitive typed arrays in a convenient way. Mixed arrays of matchers and elements are allowed.

collections
-----------
This package contains matchers for collections and maps.

* `ContainsInOrderMatcher` matches collections with respect to the given order of elements, allowing to add elements fluently in builder style.
* `ContainsInOrderMatcher` matches collections without respect to the given order of elements, allowing to add elements fluently in builder style
* `MapMatcher` matches maps, allowing to add entries fluently in builder style

conventions
-----------
This package contains matchers asserting conventional properties on classes and objects.

* `BuilderMatcher` matches and covers builder classes
* `EnumMatcher` matches and covers enums 
* `EqualityMatcher` matches any class to comply with the common equals conventions
* `OrdinaryExceptionMatcher` matches conventional exceptions 
* `UtilityClassMatcher` matches and covers utility classes

exceptions
----------
This package contains matchers for exceptions.

* `ExceptionMatcher` matches exceptions by type, message and cause 

objects
-------
This package contains matchers for all objects.

* `ReflectiveEqualsMatcher` matches objects by comparing the structure of an object (rather than the types)

strings
-------
This package contains matchers for strings.

* `WildcardStringMatcher` matches strings containing wildcard patterns (? = single wildcard, * = multiple wildcards)

Maven Dependency
----------------

```xml
<dependency>
	<groupId>net.amygdalum</groupId>
	<artifactId>hamcrest-extensions</artifactId>
	<version>0.1.15</version>
</dependency>
```