package com.example.kotlindsaandcoroutines

class what_is_coroutine {

    /*
    what is multi threading?

multi threading to do the operation:

single thread -- do operations sequentially

multi thread -- do operation independently

-- huge performance & less time

coroutines --
runs operations sequentially until it hits
the "suspension point".

    Suspension points:

suspension points are any instructions that
require waiting for a specific outcome.  so we can
achieve a huge performance gain just by minimizing the amount
of times in which the CPU has nothing to do.

when it hit suspension point:

it uses continuations >>>  which allows it to pause its
work and resume it later once the desired result is received



1. suspending code

-- suspending code will suspend a single
coroutine, so a thread running in cna still
do other work.



2. Blocking code:

this will block the whole thread.

    * */


}