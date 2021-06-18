person(Georg).
person(Klaus).
person(Paul).
person(Wolfgang).
person(Leo).

hatPistole(Georg).
hatPistole(Leo).
hatPistole(Wolfgang).

hatMotiv(Sus, Opfer) :-
    
erbtGeld(Bernd).
hatMotiv(Georg).
hatMotiv(Leo).
hatMotiv(Wolfgang).

hatAlibi(Leo).
hatAlibi(Georg).

nichtGlaubwürdig(Wolgang).

mörder(Sus) :-
    hatMotiv(Sus),
    hatPistole(Sus),
    not(hasAlibi(Sus)).