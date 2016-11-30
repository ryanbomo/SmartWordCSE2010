# SmartWordCSE2010

This is the README.md for our CSE2010 Group Project.

## Goal

Our initial goal is to turn in a functional product.  Timing and space are irrelevant to our goal of delivery.

## Design

#### Trie Layout

## Implementation

####Description of the overall algorithm:

1. Grab word list and build trie from the word list [Done in SmartWord]
  a. These words are then inserted into the Trie
  b. General structure for trie is used, but instead of worrying about leaves, the last letter of each word is given the word weight and the word string. This allows the ends of words to be seen logically, but saves space in not needing blank nodes at the end of the trie.

2. Process the old messages by chopping the file into lines, and then the lines into words. [Done in processOldMessages]
  a.  check to make sure each word is valid
    -If the word has ANY non-alphabet characters, the word is ignored.
    -Currently an issue of ignoring the last word of a sentence, due to punctuation.
  b. each valid word is inserted
    - On insertion, if it's not in the trie, it is added
    - If it is in the trie, then the weight is incremented by 1

3. Guess by traversing the trie and returning highest weighted words that could possibly start with letters typed so far

    a. given a letter and a word position, check to see if still on current word
    
    b. if still on current word, append letter

    c. if not still on current word, create new word starting with letter

    d. send word into find on trie

    e. Trie does a recursive search through all possible strings that start with the prefix

    f. Creates a priority queue with max at root, pops three times and returns those three

4. Evaluate our accuracy

    -If accurate, increment weight of word by 1

    -If Inaccurate but target is in trie, increment weight of correct word by 1

    -If Inaccurate but target is not in trie, add to trie and set weight to 1

## Testing

#### Test Cases
We tested using the clinton and trump txt files provided by the teacher.  I will create a few more test files of my own out of essays I typed in undergrad.

## Results - Initial Submission

## Changes to Initial Submission

## Results - Final Submission
