#ifndef MARKT_H
#define MARKT_H

#include "eintrag.h"

class Markt : public Eintrag{
    public:
        Markt(String name);
        virtual String toString();
}

#endif