#ifndef EINTRAG_H
#define EINTRAG_H

class Eintrag{
    public:
        Eintrag(String text);
        void setText(String text);
        String getTExt(String text);
        virtual String toString();
    private:
        String text;
    
}

#endif