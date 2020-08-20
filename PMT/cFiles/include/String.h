#ifndef STRING_C__H
#define STRING_C__H

#include "MemoryTest.h"
#include <stdbool.h>
#include <stdio.h>

#define abortWithError(ex){printf("File: %s Line %d: %s\n", __FILE__,__LINE__,ex),exit(EXIT_FAILURE);}

/**
 * @file   String.h
 * @author Sven Eric Panitz (sveneric@name.panitz)
 * @date   June, 2017
 * @brief  A simple library for String processing.
 *
 * This file is part of an assignment.
 **/

/** Currently only 8 bit characters are supported. **/
typedef char _char;

/** The length of a String is a positive number. **/
typedef unsigned int sizetype;

/** 
  @brief This class holds an non negative integer value for the length of the 
represented String object and a pointer do the beginning of the char array.

 **/
typedef struct{
  /** the number of characters in the String. **/
  sizetype length;
  /** a pointer to the actual character data.**/
  _char* data;
} String;

/** @brief This function constructs a new String Object from a given char pointer. 

@param str a pointer to a null terminated char array. the constructor is not owner
  of this pointer. The characters in the given array are copied to newly 
  allocated memory.
@return a new String object with a correctly set length attribute and newly
  allocated memory for the character data.

The function will allocate new memory and copy the character data from the 
parameter into the newly allocated memory.

Every object constructed with mkStringFromChars needs to be deleted manually. 
@see strDelete, mkStringForLength.
 **/
String mkStringFromChars(_char* str); 


/** @brief This function constructes a new String object for a given length. 

@param l the number of characters the newly created String is to have..
@return a new String object with a correctly set length attribute and newly
  allocated memory for the character data. The values of the characters is 
  unspecified. The string data is null terminated,

The function will allocate new memory.

Every object constructed with mkStringForLength needs to be deleted manually. 
@see strDelete,  mkStringFromChars.
 **/
String mkStringForLength(sizetype l);


/** @brief This function will free the memory pointed to by the char pointer 
    of the this-object.

    @param this is the object which will be deleted from the memory.
 **/
void strDelete(String this);


/** @brief constructs a String representation of an integer value.
    
    @param i is the integer to be represented as a String.
    @return a newly created String object with the usual decimal 
    representation of the given number.
 **/
String strFromInt(int i);

/** @brief constructs a String representation of a boolean value.
    
    @param b is the integer to be represented as a String.
    @return a newly created String object.

    The resulting String is "true" or "false".
 **/
String strFromBool(bool b);


/** @brief prints the String object to standard out.
    @param this is the String to be printed,

    This function prints complete information of the String object, including
    its length and attribute names, e.g  <tt>{length=5;data="hello"}</tt>. 

    A newline character will be printed after the object.

 **/
void strPrintLn(String this);

/**
   @brief Concatenates the specified Strings.

    @param this  the String that is gets the beginning of the resulting String. 
    @param that  the String that is concatenated to the end of this String. 
    @return   a string that represents the concatenation of this object's characters followed by the string argument's characters.

    A new String object is created, representing a character sequence that 
    is the concatenation of the character sequence represented by this 
    String object and the character sequence represented by the that 
    argument String.
**/
String strAppend(String this,String that); 


/**
   @brief Concatenates a int number to a String.

    @param this  the String that is gets the beginning of the resulting String. 
    @param i  is the number that is concatenated to the end of this String. 
    @return   a string that represents the concatenation of this object's characters followed by the number as string.

    A new String object is created, representing a character sequence that 
    is the concatenation of the character sequence represented by this 
    String object and the character sequence represented by string representation of the that 
    argument.

**/
String strAppendInt(String this,int i); 

/** 
    @brief creates a new String from the this parameter with 
    reversed character sequence.
    
    @param this is the String which will be used from back to front to 
    generate the result.
    @result a newly created String with reversed char sequence of the  
    characters in the parameter.
**/
String strReverse(String this); 

/** @brief creates a new String with same length as the String parameter  this 
by applying the function f to every character of the original String. 

    @param this is the original String.
    @param f the function which gets applied to every character of the original String.
    @result a newly generated String.

**/
String strMap(String this,char f(char));


/** @brief Creates a new String of the paramter where latin lower case letters 
    are converted to corresponding upper case letters.

    @param this is the String used for creation of the result String.
    @result a newly created String with no latin lower case letter,

    Solely the letters 'a',...,'z' are transformed. German Umlaute or other 
    special character are neglected.
**/
String strToUpperCase(String this); 

/** @brief Creates a new String of the paramter where latin upper case letters 
    are converted to corresponding lower case letters.

    @param this is the String used for creation of the result String.
    @result a newly created String with no latin upper case letter,

    Solely the letters 'a',...,'z' are transformed. 
    Other characters are taken unchanged from the parameter. 
    German Umlaute or other  special character are notr changed.
**/
String strToLowerCase(String this); 

/**
   @brief Returns a new string that is a substring of this string. 

    @param this is the String which will be used to create a substring from.
    @param from is the beginning index, inclusive.
    @param to  is the ending index, exclusive. 


    @result a newly created String which is created from parts of the 
    this parameter.

   The substring begins at the specified beginIndex and extends to the 
   character at index endIndex - 1. 
   Thus the length of the substring is endIndex-beginIndex. 
   If the from index is larger than the to index, both are exchanged.
   If to is larger than this.length, it will be corrected to length. 
**/
String strSubstring(String this,sizetype from, sizetype to);


/**
   @brief Returns a copy of the string, with leading and 
   trailing whitespace omitted. 
   
   @param this is the String, which will be stripped of white space in the 
   beginnend and in the end.
   @return A copy of this string with leading and trailing white space 
   removed.

   Whitespace characters are: '\\n', ' ', '\\t'.
**/
String strTrim(String this);

/** @brief This enumeration is to signify, if one member is equal, smaller or greter than another.
 **/
typedef enum {
  /** equal members. **/
  eq, 
  /** first member is smaller than the second one. **/
  lt, 
  /** first member is greater than the second one. **/
  gt} Ord;


/**
   @brief compares two Strings in the standard lexicographical order.
   @param this is the first of the two String to be compared.
   @param that is the first of the two String to be compared.
   @result a enumeration value signifying that the first parameter
   is equal, smaller or greater to the second in the lexicographical ordering.

   Two Strings are equal, if they are of same length and have the same 
   character sequence.
   A String smaller than another, if it is a prefix of the other String, or 
   if it has a common prefix and the character at the following index is 
   smaller, than the corresponding character at the same index of the
   second String.

   If <tt>strCompare(str1,str2)==lt</tt> then <tt>strCompare(str2,str2)==gt</tt>.

**/
Ord strCompare(String this,String that); 

/**
   @brief Checks is the this parameter starts with the character sequence of
   the prefix.

   @param this is the String which is tested for a certain prefix.
   @param prefix is the character which in case of success the this String starts with.
   @result true if prefix is the starting character sequence of this String.

 **/
bool strStartsWith(String this,String prefix);

/**
   @brief Checks is the this parameter end with the character sequence of
   the suffix.

   @param this is the String which is tested for a certain suffix.
   @param suffix is the character which in case of success the this String ends with.
   @result true if suffix is the ending character sequence of this String.

 **/

bool strEndsWith(String this,String suffix);



/** 
    @brief Creates a new String from this by replacing every occurance 
    of <tt>oldChar</tt> by <tt>newChar</tt>.

    @param this is the String taken for creation of a new String.
    @param oldChar is the character to be replaced in the resulting String.
    @param newChar is the replacement character.
    @result is a newly created String with oldChar replaced by newChar. 
**/
String strReplace(String this,_char oldChar, _char newChar);

#endif
#endif
