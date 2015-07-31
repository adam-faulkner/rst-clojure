# rst-clojure

A Clojure wrapper around SistaNLP's RST parser.

### About

This clojure module wraps the Rhetorical Structure Theory parser in the [**SistaNLP/Processors** scala library] (https://github.com/sistanlp/processors).

### Installation

Runnning `lein deps` in the project directory should get you all of the dependencies. However, **processors_2.11-5.3.jar**
does not contain the RST parser models. If you take a look at lines 148-9 of **edu/arizona/sista/discourse/rstparser/RSTParser.scala**
you'll note the following lines:

```
val DEFAULT_CONSTITUENTSYNTAX_MODEL_PATH = "edu/arizona/sista/discourse/rstparser/model.const.rst.gz"
val DEFAULT_DEPENDENCYSYNTAX_MODEL_PATH = "edu/arizona/sista/discourse/rstparser/model.dep.rst.gz"
```

So, both **.gz** model files (along with **discourse_connectives.txt**) will need to go into  **processors_2.11-5.3.jar.** at that location.
cd to the location of your maven repo's **processors_2.11-5.3.jar**, e.g.,

```
cd ~/.m2/repository/edu/arizona/sista/processors_2.11/5.3
```

Next, get all of the files contained in **/models/src/main/resources/edu/arizona/sista/discourse/rstparser** in 
[sista's github repo](https://github.com/sistanlp/processors). You should have the following three files:

```
discourse_connectives.txt	
model.const.rst.gz	
model.dep.rst.gz
```
Annoyingly, to update the jar with these files you'll need to recreate the directory structure **edu/arizona/sista/discourse/rstparser** first
and then put the files in there. Assuming the model files and the discourse connectives file are in the same 
directory as the jar, do

```
sudo mkdir -p edu/arizona/sista/discourse/rstparser/
sudo mv model.* edu/arizona/sista/discourse/rstparser
sudo mv  discourse_connectives.txt  edu/arizona/sista/discourse/rstparser
jar uf processors_2.11-5.3.jar edu/arizona/sista/discourse/rstparser/model.const.rst.gz
jar uf processors_2.11-5.3.jar edu/arizona/sista/discourse/rstparser/model.dep.rst.gz
jar uf processors_2.11-5.3.jar edu/arizonaista/discourse/rstparser/discourse_connectives.txt

```

Now rerun `lein deps` and you should all set.

### Usage 

```
user=> (require '[rst-clojure.core :as rst])
nil
user=> ;using second paragraph of the Gettysburg Address as an example
user=> (def getty "Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this.") 
 
#'user/getty
user=> (rst/rst-tree getty)
#<Some Some(elaboration (LeftToRight)
  elaboration (LeftToRight)
    attribution (LeftToRight)
      TEXT:Now we are engaged in a great civil war , testing whether that nation , or any nation so conceived and so dedicated ,
      TEXT:can long endure .
    TEXT:We are met on a great battle-field of that war .
  evaluation (LeftToRight)
    cause (LeftToRight)
      TEXT:We have come to dedicate a portion of that field ,
      elaboration (LeftToRight)
        TEXT:as a final resting place for those
        elaboration (LeftToRight)
          TEXT:who here gave their lives
          TEXT:that that nation might live .
    attribution (RightToLeft)
      TEXT:It is altogether fitting and proper
      TEXT:that we should do this .
)>
user=> (rst/get-rst-parse getty)
{:relLabel "elaboration", :relDir "LeftToRight", :kids [{:relLabel "elaboration", :relDir "LeftToRight", :kids [{:relLabel "attribution", :relDir "LeftToRight", :kids [{:text "Now we are engaged in a great civil war , testing whether that nation , or any nation so conceived and so dedicated ,"} {:text "can long endure ."}]} {:text "We are met on a great battle-field of that war ."}]} {:relLabel "evaluation", :relDir "LeftToRight", :kids [{:relLabel "cause", :relDir "LeftToRight", :kids [{:text "We have come to dedicate a portion of that field ,"} {:relLabel "elaboration", :relDir "LeftToRight", :kids [{:text "as a final resting place for those"} {:relLabel "elaboration", :relDir "LeftToRight", :kids [{:text "who here gave their lives"} {:text "that that nation might live ."}]}]}]} {:relLabel "attribution", :relDir "RightToLeft", :kids [{:text "It is altogether fitting and proper"} {:text "that we should do this ."}]}]}]}
```
