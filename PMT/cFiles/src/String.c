#include "../include/String.h"
#include <stdlib.h>

String mkStringFromChars(_char* str){
    int myLength=0;
    while(str[myLength] != '\0')
        myLength++;
    
    String* this = (String *) malloc(sizeof(String));
    this->data = str;
    this->length = myLength;
    
    return *this;
}

String mkStringForLength(sizetype l){
    String* this =  (String *) malloc(sizeof(String));
    this->length=l;
    this->data[l+1]='\0';
    return *this;
}

void strDelete(String this){
    free(&this);
}

String strFromBool(bool b){
    if(b){
        _char chars[] = {'t','r','u','e','\0'};
        return mkStringFromChars(chars);
    }
    
    _char chars[] = {'f','a','l','s','e','\0'};
    return mkStringFromChars(chars);
}

String strFromInt(int i){
  
    _char charlist[12];
    sprintf(charlist, "%d", i);
    return mkStringFromChars(charlist);
}

void strPrintLn(String this){
    printf("length=%i;data=[", this.length);
    for(int i=0; i<this.length-1; i++){
        printf("%i ", this.data[i]);
    }
    printf("%i]", this.data[this.length-1]);
} 

String strAppend(String this,String that){
    String* result=(String *)malloc(sizeof(String));
    result->length=this.length+that.length;
    for(int i = 0; i<this.length; i++){
        result->data[i]=this.data[i];
    }
    for(int i=0; i<that.length; i++){
        result->data[this.length+i]=that.data[i];
    }
    result->data[result->length]='\0';
    return *result;
}

String strAppendInt(String this,int i){
    return strAppend(this, strFromInt(i));
}

String strReverse(String this){
    int length = this.length;
    String* result=(String *)malloc(sizeof(String));
    result->length=length;
    
    for(int i=0; i<length; i++){
        result->data[length-i-1]=this.data[i];
    }
    
    strDelete(this);
    return *result;
}

String strMap(String this,_char f(_char)){
    for(int i=0; i<this.length; i++){
        this.data[i]=f(this.data[i]);
    }
    
    return this;
}

String strToLowerCase(String this){
    
    
    return this;
}

String strToUpperCase(String this){
    String result;
    
    return result;
}

String strSubstring(String this,sizetype from, sizetype to){
    String result;
    
    return result;
}

String strTrim(String this){
    String result;
    
    return result;
}

Ord strCompare(String this,String that){
}

bool strStartsWith(String this,String prefix){
    bool result = true;
    
    return result;
}


bool strEndsWith(String this,String suffix){
    bool result = true;
    
    return result;
}

String strReplace(String this,_char oldChar, _char newChar){
    String result;
    
    return result;
}
