#### COMMIT STRATEGY

##### Git Set up

 Some times too many merge conflicts occur  due to having different line endings.
 On all the repositories we will use LF for line endings. Make sure that this is
 properly set on your workstation. 
 Differing line endings also make commits very difficult read. 
 Read more from https://help.github.com/articles/dealing-with-line-endings/
 
##### How to set the Right Line endings

`Configure Git on OS X or Linux to properly handle line endings`

git config --global core.autocrlf input

`Configure Git on Windows to properly handle line endings`

git config --global core.autocrlf true


If a repository does not have .gitattributes please demand for it. 
So see to it that it is there. A basic .gitattributes  should be configured to fix line endings issues. 
See link above

##### Rebase

Git should be set to rebase by default. 

In git >= 1.7.9:

git config --global pull.rebase true

In git < 1.7.9:

git config --global branch.autosetuprebase always

#### Working on a new task.

Please make sure that you know the objective of the task that you are about to start. 
All tasks must be done from a new branch. 

Each new branch must be created from the master.

Do not create a branch from another branch which is not master. 

Incase you do that. Make sure you rebase your changes correctly.

A  new branch that is a feature must be prefixed with feature followed by a slash and then a 
descriptive title of the task you are to work on. eg `feature/click`
 
If it is bug it should prefixed with patch followed by slash and then a descriptive patch title 
eg `patch/loginbug`

When you are done with working on the task from your repository, make a pull request to the master 
from your repository

##### QUALITY CHECKLIST

Review your code with one of the developers as early as possible.

Work only on what you are supposed to work on

Commit and rebase often depending on how the tasks are interlinked. 
Tasks that are not that interlinked can be rebased upon task completion.

No committing anyhow, Each commit should have purpose and so the commit message should 
clearly communicate the purpose of the commit.

The commit message should be in present tense eg `Add readme`

Do not commit anything that can be regenerated from other things eg IDE settings,etc
