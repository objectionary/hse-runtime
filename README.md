[![Build Status](https://github.com/HSE-Eolang/hse-runtime/actions/workflows/build-status.yml/badge.svg)](https://github.com/HSE-Eolang/hse-runtime/actions/workflows/build-status.yml)
## What this repository is for?
This repository proposes and demonstrates an alternative branch of the EO runtime based on a new transcompilation model of EO programs to Java source codes.  
The original (CQFN) model is depicted [here](https://miro.com/app/board/o9J_lM0FZHk=/).
And the visualization of this (HSE) model can be found [here](https://miro.com/app/board/o9J_lMMDKrk=/).  
Also you might want to read [a draft of our paper](https://github.com/HSE-Eolang/Report-materials/raw/main/eorepo/EO%20Programming%20Language%20Transcompilation%20Model%20for%20Java%20Source%20Code%20Generation.pdf) that describes the model.
## How is this runtime better?
Our runtime has two great advantages:
1. It if much faster than the CQFN original implementation.
2. The code based on this runtime is much easier to read and comprehend. The runtime library is also much more readable and understandable.

You can see the difference in performance of the old and the new models below:
![This model is faster!](https://raw.githubusercontent.com/HSE-Eolang/Report-materials/main/eorepo/faster.png) 
Have a look at this [this Google Sheet](https://docs.google.com/spreadsheets/d/1YsalbO6piExC3begifeNNsaz7PEDsqlV3Xx7c6TGbOU/edit?usp=sharing) for more comparisons.

## How to Contribute

First, fork and clone this repo to your local machine and go
to the `hse-runtime` directory (you will need
[Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
installed):

```bash
$ git clone git@github.com:HSE-Eolang/hse-runtime.git
$ cd hse-runtime
```
Second, make changes to the code you would like to enhance or fix. Don't forget to write some tests!
Then, compile the runtime (you will need
[Maven 3.3+](https://maven.apache.org/)
and [Java SDK 8+](https://www.java.com/en/download/) installed):  
```bash
$ mvn clean install
```  
If everything is smooth, submit a PR. We would appreciate it!

Just in case if you are looking for the transpiler implementation, it is [here](https://github.com/HSE-Eolang/hse-transpiler).
Have a look at our samples in [this repository]([#examples](https://github.com/cqfn/eo/tree/master/sandbox/hse)).  
