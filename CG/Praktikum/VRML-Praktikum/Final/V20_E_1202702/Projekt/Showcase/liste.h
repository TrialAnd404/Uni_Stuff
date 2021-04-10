#ifndef LISTE_H
#define LISTE_H

#include <vector.h>

#include eintrag.h

class Liste{
    public:
        Liste();
        Eintrag holen(int index);
        int zaehlen();

        virtual void eintragen(Eintrag e);

    protected: 
        vector<Eintrag> eintraege;
}

#endif