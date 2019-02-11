# Contributing Guide

## Branches

*No* development should be conducted on `master`. Use a dev branch specific to
the User-story you are working on. For example call your branch
`story/mal-rec-50` if you are working on the recording malaria results story
(work item id 50). If multiple people are working on the same story then
create extra dev branches `story/mal-rec-50-name1` and `story/mal-rec-50-name2`
then merge these seperately into the original `story/mal-rec-50` and 
then merge that into `master` when it's finally finished. This means 
nobody will step on eachothers toes ever (I hope).

## PRs

Please don't merge immediately 

Ping someone else and get them to give it a once over

Give a brief description to help speed up the review and so they don't have to
ask you what the h\*ck is going on!

Link the Work items that are relevant (probably the tasks).

Rebase -> squash -> merge!
Please do this to keep our git tree all neat tidy and slim!

### Quick overview of those words
`Rebase` pretends your branch started after the top of master
do this with `git rebase master`

`Squash` pretends you only made one commit. You can do this from the gui on
github.

`Merge` moves master to your head. Again do this with the gui with a squash and
merge (there's a singular button for both of these! real neato).
