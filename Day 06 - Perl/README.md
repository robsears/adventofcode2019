# Note on this solution

I chose Perl before I knew about how much it fears recursion.

There is a recursive solution in this code for part 1, and Perl bitches about it. Apparently Perl panics if you have a recursive level more than 100 calls deep.

The only way to silence or ignore this is to recompile Perl from source. I had to download a tarball of the most recent version, set `PERL_SUB_DEPTH_WARN` to some really high value in perl.h, then recompile.

Running `make test` with the custom value brought my computer to a halt, thanks to a stack overflow test. Turns out I didn't even need it, as `make install` put the target in my home directory and left my system binaries alone.

So, yeah. This solution works, but you'll need a custom build of Perl to run it. Fun fact: the increase in recursion depth has 0 effect on speed. The code completes in around 1.3s.
