#include "../include/String.h"
#include "../include/CUnit.h"



_char __charToUpper(_char c){
  if ('a'<=c && c<= 'z'){
    return c+('A'-'a');
  }
  return c;
}
String strToUpperCaseTest(String this){
  return strMap(this,__charToUpper);
}



int main(){
  printHeader();

  String str1 = mkStringFromChars("HELLO");
  String str2 = mkStringFromChars("world");
  String strE = mkStringFromChars("");

  testStart("mkStringFromChars 1");
  assertIntEq("String hello has 5 characters",str1.length,5);
  testEnd();
  
  String str3 = strFromBool(42==17);
  testStart("strFromBool1");
  assertStringEq("wrong result of strFromBool","false",str3.data);
  strDelete(str3);
  testEnd();
  
  str3 = strFromBool(42==42);
  testStart("strFromBool2");
  assertStringEq("wrong result of strFromBool","true",str3.data);
  strDelete(str3);
  testEnd();
  
  str3 = strAppend(str1,str2);
  testStart("strAppend1");
  assertStringEq("wrong result of strAppend","HELLOworld",str3.data);
  testEnd();
  
  String str4 = strAppend(str1,str3);
  testStart("strAppend2");
  assertStringEq("wrong result of strAppend","HELLOHELLOworld",str4.data);
  strDelete(str3);
  strDelete(str4);
  testEnd();
  
  testStart("strAppend3");
  str3 = strAppend(str1,strE);
  assertStringEq("wrong result of strAppend","HELLO",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strAppend4");
  str3 = strAppend(strE,str1);
  assertStringEq("wrong result of strAppend","HELLO",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strReverse 1");
  str3 = strReverse(str1);
  assertStringEq("wrong result of strReverse","OLLEH",str3.data);
  strDelete(str3);
  testEnd();

  testStart("strReverse 2");
  str3 = strReverse(strE);
  assertStringEq("wrong result of strReverse","",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strToLowerCase 1");
  str3 = strToLowerCase(str1);
  assertStringEq("wrong result of strToLowerCase(\"HELLO\")","hello",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strToUpperCase 1");
  str3 = strToUpperCase(str2);
  assertStringEq("wrong result of strToUpperCase(\"world\")","WORLD",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strToUpperCase 2");
  str3 = strToUpperCase(strE);
  assertStringEq("wrong result of strToUpperCase(\"\")","",str3.data);
  strDelete(str3);
  testEnd();

  testStart("map 1");
  str3 = strToUpperCaseTest(str2);
  assertStringEq("wrong result of map with charToUpper function (\"world\")","WORLD",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("map 2");
  str3 = strToUpperCaseTest(strE);
  assertStringEq("wrong result of map with charToUpper function (\"\")","",str3.data);
  strDelete(str3);
  testEnd();


  
  testStart("strToUpperCase 2");
  str3 = strToLowerCase(strE);
  assertStringEq("wrong result of strToLowerCase(\"\")","",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strSubstring 1");
  str3 = strSubstring(str1,1,3);
  assertStringEq("wrong result of strSubstring(str,1,3)","EL",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strSubstring 2");
  str3 = strSubstring(str1,0,0);
  assertStringEq("wrong result of strSubstring(str,0,0)","",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strSubstring 3");
  str3 = strSubstring(strE,1,3);
  assertStringEq("wrong result of strSubstring","",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("trim 1");
  str4 = mkStringFromChars("   HE LLO    ");
  str3 = strTrim(str4);
  assertStringEq("wrong result of strTrim(\"   HE LLO    \")","HE LLO",str3.data);
  strDelete(str3);
  strDelete(str4);
  testEnd();

  
  testStart("trim 2");
  str4 = mkStringFromChars("       ");
  str3 = strTrim(str4);
  assertStringEq("wrong result of strTrim(\"     \")","",str3.data);
  strDelete(str3);
  strDelete(str4);
  testEnd();
  
  testStart("strCompare 1");
  String str5 = mkStringFromChars("worldahkhgj");
  String str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strCompare(\"worldahkhgj\",\"worldbhkkh\")", strCompare(str5,str6),lt);
  assertIntEq("strCompare(\"worldbhkkh\",\"worldahkhgj\")", strCompare(str6,str5),gt);
  assertIntEq("strCompare(\"worldbhkkh\",\"worldbhkkh\")" , strCompare(str6,str6),eq);
  strDelete(str5);
  strDelete(str6);  
  testEnd();
  
  testStart("strStartsWith 1");
  str5 = mkStringFromChars("worl");
  str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strStartsWith",strStartsWith(str6,str5),true);
  strDelete(str5);
  strDelete(str6);
  testEnd();
  
  testStart("strStartsWith 2");
  str5 = mkStringFromChars("");
  str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strStartsWith",strStartsWith(str6,str5),true);
  strDelete(str5);
  strDelete(str6);
  testEnd();

  testStart("strStartsWith 3");
  str5 = mkStringFromChars("worlt");
  str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strStartsWith",strStartsWith(str6,str5),false);
  strDelete(str5);
  strDelete(str6);
  testEnd();
  
  testStart("strStartsWith 4");
  str5 = mkStringFromChars("p");
  str6 = mkStringFromChars("");
  assertIntEq("strStartsWith",strStartsWith(str6,str5),false);
  strDelete(str5);
  strDelete(str6);
  testEnd();
  
  testStart("strEndsWith 1");
  str5 = mkStringFromChars("bhkkh");
  str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strEndsWith \"worldbhkkh\" \"bhkkh\"" ,strEndsWith(str6,str5),true);
  strDelete(str5);
  strDelete(str6);
  testEnd();
  
  testStart("strEndsWith 2");
  str5 = mkStringFromChars("");
  str6 = mkStringFromChars("worldbhkkh");
  assertIntEq("strEndsWith",strEndsWith(str6,str5),true);
  strDelete(str5);
  strDelete(str6);
  testEnd();


  
  testStart("strReplace 1");
  str3 = strReplace(str1,'E','A');
  assertStringEq("wrong result of strReplace(str,'E','A')","HALLO",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strFromInt 1");
  str3 = strFromInt(42);
  assertStringEq("wrong result of strFromInt","42",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strFromInt 2");
  str3 = strFromInt(-42);
  assertStringEq("wrong result of strFromInt","-42",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strFromInt 3");
  str3 = strFromInt(0);
  assertStringEq("wrong result of strFromInt","0",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strFromInt 4");
  str3 = strFromInt(10000);
  assertStringEq("wrong result of strFromInt","10000",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strAppendInt 1");
  str3 = strAppendInt(str2,42);
  assertStringEq("wrong result of strAppendInt","world42",str3.data);
  strDelete(str3);
  testEnd();
  
  testStart("strAppendInt 2");
  str3 = strToLowerCase(str1);
  assertStringEq("wrong result of strToLowerCase","hello",str3.data);
  strDelete(str3);
  testEnd();

  testStart("strAppend 4");
  str5 = mkStringFromChars("");
  str6 = mkStringFromChars("worldbhkkh");
  String result = strAppend(str5,str6);
  assertStringEq("strAppen(\"\",str)",str6.data,result.data );
  strDelete(str5);
  strDelete(str6);
  strDelete(result);
  testEnd();

  testStart("strAppend 5");
  str5 = mkStringFromChars("");
  str6 = mkStringFromChars("worldbhkkh");
  result = strAppend(str6,str5);
  assertStringEq("strAppen(str,\"\")",str6.data,result.data );
  strDelete(str5);
  strDelete(str6);
  strDelete(result);
  testEnd();

  testStart("strAppend 6");
  str5 = mkStringFromChars("hello ");
  str6 = mkStringFromChars("world");
  String str7 = mkStringFromChars("hello world");
  result  = strAppend(str5,str6);
  assertStringEq("strAppend(\"hello \",\"world\")",str7.data,result.data);
  strDelete(str5);
  strDelete(str6);
  strDelete(str7);
  strDelete(result);
  testEnd();
  
  strDelete(str1);
  strDelete(str2);
  strDelete(strE);

  printResults();
  printStorage();
  
  printFoot();
  return EXIT_SUCCESS;
}
