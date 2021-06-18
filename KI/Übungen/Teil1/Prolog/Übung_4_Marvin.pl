gives_alibi(georg, tue, bernd).
gives_alibi(leo, tue, bernd).
gives_alibi(wolfgang, thu, georg).

is_not_credible(wolfgang).

wants_revenge(paul, klaus).
wants_revenge(georg, klaus).

inherits_sth(bernd, klaus).
inherits_sth(klaus, leo).

owes_money(leo, klaus).
owes_moneyy(georg, klaus).

caught_in_crime(wolfgang, klaus).

owns_gun(georg).
owns_gun(leo).
owns_gun(wolfgang).

has_alibi(Sus, Day, By) :-
        gives_alibi(Sus, Day, By),
        not(is_not_credible(By)).

has_special_interest(Sus, Victim) :-
        inherits_sth(Sus, Victim);
        owes_money(Sus, Victim);
        caught_in_crime(Sus, Victim).

has_motive(Sus, Victim) :-
        has_special_interest(Sus, Victim);
        wants_revenge(Sus, Victim).

is_murderer(Sus, Day, Victim) :-
        has_motive(Sus, Victim),
        owns_gun(Sus),
        not(has_alibi(Sus, Day, _)).