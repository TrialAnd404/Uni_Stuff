#include "markt.h"

class Markt{
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