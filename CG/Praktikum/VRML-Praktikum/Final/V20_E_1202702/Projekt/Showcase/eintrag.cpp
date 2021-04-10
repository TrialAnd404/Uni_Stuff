#include "eintrag.h"

class Eintrag{
    Eintrag(String text){
        this.text = text;
    };

    void setText(String text){
        this.text =text;
    };

    String getTExt(String text){
        return this.text;
    };
}