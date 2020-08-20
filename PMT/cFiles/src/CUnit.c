#include "CUnit.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "MemoryTest.h"

unsigned int errorcount = 0;
unsigned int testcount = 0;
unsigned int speicherAlloc=0;
unsigned int speicherFree=0;

bool assertTrueLine(int line,char* message, bool val){
    testcount++;
    if (!val){
      errorcount++;
      printf("    <failure message=\"true expected\" type=\"AssertionFailedError\">\n");
      printf("       (%d) fail: %s\n",line,message);
      printf("    </failure>\n");
      fflush(stdout);
    }
    return val;
}

bool assertIntEqLine(int line,char* message, int expected, int found){
  testcount++;
  if (expected!=found){
      errorcount++;
      printf("    <failure message=\"expected:&lt;%d&gt; but was:&lt;%d&gt;\" type=\"AssertionFailedError\">\n",expected,found);
      printf("      (%d) fail: %s, expected %d found %d\n",line,message,expected,found);
      printf("    </failure>\n");
      fflush(stdout);
      return false;
  }
  return true;
}


bool assertStringEqLine(int line,char* message, char* expected,char* this){
  testcount++;
  bool result = strcmp(expected,this)==0;
  if (!result){
    errorcount++;
    printf("    <failure message=\"expected:&lt;%s&gt; but was:&lt;%s&gt;\" type=\"AssertionFailedError\">\n",expected,this);
    printf("      (%d) fail: %s, expected %s but found %s\n",line,message,expected,this);
    printf("    </failure>\n");
    fflush(stdout);
  }
  return result;
}


void printHeader(){
  printf("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
  printf("<testsuite>\n"); 
}

void printResults(){
  printf("  <results errors=\"%d\" failures=\"%d\" tests=\"%d\"/>\n",0,errorcount,testcount);
}

void printStorage(){
  printf("  <storage allocated=\"%d\" free=\"%d\" diff=\"%d\"/>\n",speicherAlloc,speicherFree,speicherAlloc-speicherFree);
}
void printFoot(){
  printf("</testsuite>");
  fflush(stdout);
}

void testStart(char* name){
  printf("  <testcase name=\"%s\">\n",name);
  fflush(stdout);
}

void testEnd(){
  printf("  </testcase>\n");
  fflush(stdout);
}
