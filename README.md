# rst-clojure

A Clojure wrapper around SistaNLP's RST parser.


The jarred version of sistaNLP/processors available on maven does not include the RST parser models needed 
for this Clojure module.  You must add the RST model to **processors_2.11-5.3.jar.** Here's how you do that.

Runnning `lein deps` in the project directory should get you all of the dependencies. However, **processors_2.11-5.3.jar**
does not contain the RST parser models. If you take a look at lines 148-9 of **edu/arizona/sista/discourse/rstparser/RSTParser.scala**
you'll note the following lines:

```
  val DEFAULT_CONSTITUENTSYNTAX_MODEL_PATH = "edu/arizona/sista/discourse/rstparser/model.const.rst.gz"
  val DEFAULT_DEPENDENCYSYNTAX_MODEL_PATH = "edu/arizona/sista/discourse/rstparser/model.dep.rst.gz"
```

So, both **.gz** model files (along with **discourse_connectives.txt**) will need to go into the jar at that location.
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

Now rerun `lein deps`.


